package app.thread;

import java.io.IOException;
import java.rmi.RemoteException;

import app.interfaces.ClientInterface;
import app.interfaces.SwitcherInterface;
import app.rmiobjects.Client;



/**
 * ClientThread : can only write
 */
public class ClientWriter extends Thread {

    /**
     * Name of the thread
     */
    private ClientInterface client;

    /**
     * Stub to access services
     */
    private SwitcherInterface stub;

    /**
     * Name of the processed file
     */
    private String filename = "write_file.txt";


    /**
     * CONSTRUCTOR
     * 
     * @param id
     * @param name
     * @param port
     * @throws RemoteException
     * @throws NotBoundException
     */
    public ClientWriter(int id, String name, SwitcherInterface s) throws RemoteException {

        // SET NEW CLIENT
        this.client = new Client(id, name);
        this.setName(name);

        // SET STUB
        this.stub = s;
        stub.check(this.client); 
    }


    /**
     * Thread run function
     */
    public void run() {

        String s = "Thread " + this.getName() + " writed here";
        byte[] b = s.getBytes();
        while(true) {
            try {
                // this.write(this.filename, b);
                this.writeMin(this.filename, b);
                Thread.sleep(1000);
            } 
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /* ================================================
            AVAILABLE OR OCCUPIED ALGORITHM
    ================================================ */

    /**
     * WRITING TEST
     * @param s
     * @param filename
     * @param data
     * @param c
     * @throws RemoteException
     * @throws IOException
     */
    public void write(String filename, byte[] data) throws RemoteException, IOException {
        
        long start = System.nanoTime();

        try {
            if(this.stub.write(filename, data, client)) {
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


    /* ================================================
            MIN LOAD ALGORITHM
    ================================================ */

    /**
     * WRITING TEST
     * @param s
     * @param filename
     * @param data
     * @param c
     * @throws RemoteException
     * @throws IOException
     */
    public void writeMin(String filename, byte[] data) throws RemoteException, IOException {
        
        long start = System.nanoTime();

        try {
            if(this.stub.writeByMin(filename, data, client)) {
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