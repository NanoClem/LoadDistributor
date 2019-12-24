package app;

import app.interfaces.SwitcherInterface;

import java.util.Scanner;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


/**
 * @author NanoClem
 */
public class Client {
    public static void main(String[] argv) {
        try {
            /* -------------------------------
                INITIALISING STUB
            ------------------------------- */
            Registry registry = LocateRegistry.getRegistry(10000);                       // access to registry on port 10000
            SwitcherInterface stub = (SwitcherInterface) registry.lookup("Switcher");    // get the "Switcher" object in registry 
            stub.check();                                                                // server side test

            /* -------------------------------
                HELLO TEST
            ------------------------------- */
            System.out.println("HELLO TEST");
            helloTest(stub);      

           /* -------------------------------
                READ AND WRITE TEST
            ------------------------------- */
            //read test
            String freadname = "read_test.txt";
            System.out.println("READING TEST");
            readTest(stub, freadname);

            //write test
            String fwritename = "write_test.txt";
            byte[] data = "writing test".getBytes();
            System.out.println("WRITING TEST");
            writeTest(stub, fwritename, data);
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    /* =======================================
            TEST FUNCTIONS
    ======================================= */
    /**
     * Test 1 : say hello
     * @param s
     * @throws RemoteException
     */
    public static void helloTest(SwitcherInterface s) throws RemoteException {

        Scanner sc = new Scanner(System.in);
        System.out.println("What's your name ?");
        String input = sc.nextLine();
        sc.close();
        System.out.println(s.hello(input));
    }


    /**
     * Reading test
     * @param s
     * @return byte[]
     * @throws RemoteException/IOException
     */
    public static void readTest(SwitcherInterface s, String filename) throws RemoteException, IOException {
        String fcontent = new String(s.read(filename));
        System.out.println(fcontent);
    }


    /**
     * Writing test
     * @param s
     * @return boolean
     * @throws RemoteException/IOException
     */
    public static void writeTest(SwitcherInterface s, String filename, byte[] data) throws RemoteException, IOException {
        try {
            if(s.write(filename, data)) {
                System.out.println("Data successfuly writen");
            }
            else {
                System.out.println("Write failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
