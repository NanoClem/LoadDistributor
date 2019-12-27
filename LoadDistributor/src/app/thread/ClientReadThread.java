package app.thread;

import java.io.IOException;
import java.rmi.RemoteException;

import app.interfaces.ClientInterface;
import app.interfaces.SwitcherInterface;
import app.rmiobjects.Client;



/**
 * ClientReadThread : can only read
 */
public class ClientReadThread extends Thread {

    /**
     * Name of the thread
     */
    private ClientInterface client;

    /**
     * Stub to access services
     */
    private SwitcherInterface stub;

    /**
     * 
     */
    private String filename = "read_test.txt";


    /**
     * CONSTRUCTOR
     * 
     * @param id
     * @param name
     * @param port
     * @throws RemoteException
     * @throws NotBoundException
     */
    public ClientReadThread(int id, String name, SwitcherInterface s) throws RemoteException {

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
        while(true) {
            try {
                this.read(this.filename);
                Thread.sleep(1000);
            } 
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Reading some file
     * @param s
     * @param filename
     * @param c
     * @throws RemoteException
     * @throws IOException
     */
    public void read(String filename) throws RemoteException, IOException {
        
        long start = System.nanoTime();

        try {
            if(this.stub.read(filename, this.client)) {
                System.out.println(new String(this.client.getReadResponse()));
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

}