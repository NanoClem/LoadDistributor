package app.thread;

import java.io.IOException;
import java.rmi.RemoteException;

import app.interfaces.ClientInterface;
import app.interfaces.SwitcherInterface;
import app.rmiobjects.Client;



/**
 * ClientReadThread : can only read
 */
public class ClientReader extends Thread {

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
    private String filename;


    /**
     * CONSTRUCTOR
     * 
     * @param id
     * @param name
     * @param port
     * @throws RemoteException
     * @throws NotBoundException
     */
    public ClientReader(int id, String name, String rFilename, SwitcherInterface s) throws RemoteException {

        // SET NEW CLIENT
        this.client = new Client(id, name);
        this.setName(name);

        // SET FILE
        this.filename = rFilename;

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
                // this.read(this.filename);
                this.readMin(this.filename);
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
     * Reading some file using "available or occupied" algorithm
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

    
    /* ================================================
            MIN LOAD ALGORITHM
    ================================================ */

    /**
     * Reading some file using "min load" algorithm
     * @param s
     * @param filename
     * @param c
     * @throws RemoteException
     * @throws IOException
     */
    public void readMin(String filename) throws RemoteException, IOException {
        
        long start = System.nanoTime();

        try {
            if(this.stub.readByMin(filename, this.client)) {
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