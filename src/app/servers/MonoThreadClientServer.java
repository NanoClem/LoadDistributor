package app.servers;

import app.interfaces.ClientInterface;
import app.interfaces.SwitcherInterface;
import app.rmiobjects.Client;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;


/**
 * @author NanoClem
 */
public class MonoThreadClientServer {
    public static void main(String[] argv) {
        try {
            /* -------------------------------
                INITIALISING CLIENT
            ------------------------------- */
            Client client = new Client(1, "Pedro");

            /* -------------------------------
                INITIALISING FILES PATH
            ------------------------------- */
            Path currentRelativePath = Paths.get("");
            String path  = currentRelativePath.toAbsolutePath().toString() + "\\src\\app\\files\\tests\\";
            String rFile = path + "read_file.txt";
            String wFile = path + "write_file.txt";

            /* -------------------------------
                INITIALISING STUB
            ------------------------------- */
            Registry registry = LocateRegistry.getRegistry(10000);                       // access to registry on port 10000
            SwitcherInterface stub = (SwitcherInterface) registry.lookup("Switcher");    // get the "Switcher" object in registry 
            stub.check(client);                                                          // server side test
            
            /* -------------------------------
                INITIALISING TESTS
            ------------------------------- */
            boolean loop = true;
            Scanner in = new Scanner(System.in);

            do {
                System.out.println("=====TEST CASE=====");
                System.out.println("1 : Hello test");
                System.out.println("2 : Reading test");
                System.out.println("3 : Writing test");
                System.out.println("4 : Exit");
                
                int choice = in.nextInt();
                switch (choice) {
                    /* -------------------------------
                        HELLO TEST
                    ------------------------------- */
                    case 1:
                        System.out.println("HELLO TEST");
                        helloTest(stub, client);
                        break;

                    case 2:
                        /* -------------------------------
                            READ TEST
                        ------------------------------- */
                        String freadname = rFile;
                        System.out.println("READING TEST");
                        readTest(stub, freadname, client);
                        break;

                    case 3:
                        /* -------------------------------
                            WRITE TEST
                        ------------------------------- */
                        String fwritename = wFile;
                        byte[] data = "This is a write test".getBytes();
                        System.out.println("WRITING TEST");
                        writeTest(stub, fwritename, data, client);
                        break;

                    case 4:
                        in.close();
                        loop = false;
                        break;
                
                    default:
                        break;
                }
                System.out.println("");
            } while (loop);
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            System.out.println("END OF PROGRAM");
        }
    }


    /* =======================================
            TEST FUNCTIONS
    ======================================= */

    /**
     * HELLO TEST
     * @param s
     * @param c
     * @throws RemoteException
     */
    public static void helloTest(SwitcherInterface s, ClientInterface c) throws RemoteException {

        long start = System.nanoTime();

        try {
            s.hello(c.getSurname(), c);
            System.out.println(c.getHelloResponse());
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            long end = System.nanoTime();
            long timeElapsed = end - start;
            System.out.println("Execution time : " + timeElapsed/1000000 + "ms");
        }
    }


    /**
     * READING TEST
     * @param s
     * @param filename
     * @param c
     * @throws RemoteException
     * @throws IOException
     */
    public static void readTest(SwitcherInterface s, String filename, ClientInterface c) throws RemoteException, IOException {
        
        long start = System.nanoTime();

        try {
            if(s.read(filename, c)) {
                System.out.println(new String(c.getReadResponse()));
            } else {
                System.out.println("Read failed");
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            long end = System.nanoTime();
            long timeElapsed = end - start;
            System.out.println("Execution time : " + timeElapsed/1000000 + "ms");
        }
    }


    /**
     * WRITING TEST
     * @param s
     * @param filename
     * @param data
     * @param c
     * @throws RemoteException
     * @throws IOException
     */
    public static void writeTest(SwitcherInterface s, String filename, byte[] data, ClientInterface c) throws RemoteException, IOException {
        
        long start = System.nanoTime();

        try {
            if(s.write(filename, data, c)) {
                System.out.println("Data successfuly writen");
            } else {
                System.out.println("Write failed");
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        } 
        finally {
            long end = System.nanoTime();
            long timeElapsed = end - start;
            System.out.println("Execution time : " + timeElapsed/1000000 + "ms");
        }
    }

}
