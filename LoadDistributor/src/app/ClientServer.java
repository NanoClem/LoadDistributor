package app;

import app.interfaces.ClientInterface;
import app.interfaces.SwitcherInterface;
import app.rmiobjects.Client;

import java.util.Scanner;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


/**
 * @author NanoClem
 */
public class ClientServer {
    public static void main(String[] argv) {
        try {
            /* -------------------------------
                INITIALISING CLIENT
            ------------------------------- */
            Client client = new Client(1, "Pedro");

            /* -------------------------------
                INITIALISING STUB
            ------------------------------- */
            Registry registry = LocateRegistry.getRegistry(10000);                       // access to registry on port 10000
            SwitcherInterface stub = (SwitcherInterface) registry.lookup("Switcher");    // get the "Switcher" object in registry 
            stub.check();                                                                // server side test

            /* -------------------------------
                SIMPLE TESTS
            ------------------------------- */
            System.out.println("HELLO TEST");
            helloTest(stub, client);

           /* -------------------------------
                READ AND WRITE TEST
            ------------------------------- */
            //read test
            String freadname = "read_test.txt";
            System.out.println("READING TEST");
            readTest(stub, freadname, client);

            //write test
            String fwritename = "write_test.txt";
            byte[] data = "This is a write test".getBytes();
            System.out.println("WRITING TEST");
            writeTest(stub, fwritename, data, client);
        } 
        catch (Exception e) {
            e.printStackTrace();
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

        Scanner sc = new Scanner(System.in);
        System.out.println("What's your name ?");
        String input = sc.nextLine();
        sc.close();

        s.hello(input, c);
        System.out.println(c.getHelloResponse());
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
        try {
            if(s.read(filename, c)) {
                System.out.println(new String(c.getReadResponse()));
            } else {
                System.out.println("Read failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        try {
            if(s.write(filename, data, c)) {
                System.out.println("Data successfuly writen");
            } else {
                System.out.println("Write failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
