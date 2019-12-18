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
            // INITIALISING SWITCHER
            Registry registry = LocateRegistry.getRegistry(10000);                       // acces au registre sur le port 10000
            SwitcherInterface stub = (SwitcherInterface) registry.lookup("Switcher");    // recupere l'objet Switcher dans le registre
            stub.check();                                                                // test cote serveur

            // SIMPLE TESTS
            System.out.println("TEST 1");
            test1(stub);    // test 1 : say Hello         
            System.out.println("TEST 2");
            test2(stub);    // test 2 : print connected machines 

            // READING AND WRITING TEST
            //read test
            String freadname = "read_test.txt";
            System.out.println("READING TEST");
            testRead(stub, freadname);
            //write test
            String fwritename = "write_test.txt";
            byte[] data = "writing test".getBytes();
            System.out.println("WRITING TEST");
            testWrite(stub, fwritename, data);


        } catch (Exception e) {
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
    public static void test1(SwitcherInterface s) throws RemoteException {
        Scanner sc = new Scanner(System.in);
        System.out.println("What's your name ?");
        String input = sc.nextLine();
        sc.close();
        System.out.println(s.hello(input));
    }

    /**
     * Test 2 : print connected machines
     * @param s
     * @throws RemoteException
    */
    public static void test2(SwitcherInterface s) throws RemoteException {
        System.out.println(s.getMachines());
    }

    /**
     * Reading test
     * @param s
     * @return byte[]
     * @throws RemoteException/IOException
     */
    public static void testRead(SwitcherInterface s, String filename) throws RemoteException, IOException {
        String fcontent = new String(s.read(filename));
        System.out.println(fcontent);
    }

    /**
     * Writing test
     * @param s
     * @return boolean
     * @throws RemoteException/IOException
     */
    public static void testWrite(SwitcherInterface s, String filename, byte[] data) throws RemoteException, IOException {
        if(s.write(filename, data)) {
            System.out.println("Data successfuly writen");
        }
        else {
            System.out.println("Write failed");
        }
    }
}
