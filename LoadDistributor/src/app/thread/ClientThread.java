package app.thread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import app.interfaces.ClientInterface;
import app.interfaces.SwitcherInterface;
import app.rmiobjects.Client;


/**
 * 
 */
public abstract class ClientThread extends Thread {
    
    /**
     * Interface meant to ask for tasks
     */
    protected ClientInterface client;

    /**
     * Stub to access services
     */
    protected SwitcherInterface stub;

    /**
     * Shows which operation the client is able to do
     */
    public String profile;

    /**
     * File where reading results are logged
     */
    protected String logFile = "client_results.csv";

    /**
     * 
     */
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    /**
     * 
     */
    private final Lock writeLock = readWriteLock.writeLock();


    /**
     * CONSTRUCTOR
     * @param id
     * @param name
     * @param s
     * @throws RemoteException
     */
    public ClientThread(int id, String name, SwitcherInterface s) throws RemoteException {
       
        // SET NEW CLIENT
        this.client = new Client(id, name);

        // SET STUB
        this.stub = s;
        stub.check(this.client); 
    }


    /**
     * CONSTRUCTOR OVERLOADING
     * @param id
     * @param s
     * @throws RemoteException
     */
    public ClientThread(int id, SwitcherInterface s) throws RemoteException {
        this(id, "", s);
    }


    /**
     * Read some file using a client
     * @param filename
     * @throws RemoteException
     * @throws IOException
     */
    public boolean read(String filename) throws RemoteException, IOException {

        boolean success = false;
        long start      = System.nanoTime();

        try {
            if(this.stub.read(filename, this.client)) {
                success = true;
                System.out.println(new String(this.client.getReadResponse()));
            } else {
                success = false;
                System.out.println("Read failed");
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            long end = System.nanoTime();
            long timeElapsed = (end - start)/1000000;
            this.logResults(this.logFile, "read", timeElapsed, success);
            System.out.println("Execution time : " + timeElapsed + "ms");
        }

        return success;
    }


    /**
     * Write in a file using a client
     * @param filename
     * @param data
     * @throws RemoteException
     * @throws IOException
     */
    public boolean write(String filename, byte[] data) throws RemoteException, IOException {

        boolean success = false;
        long start      = System.nanoTime();

        try {
            if(this.stub.write(filename, data, client)) {
                success = true;
                System.out.println("Data successfuly writen");
            } else {
                success = false;
                System.out.println("Write failed");
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        } 
        finally {
            long end = System.nanoTime();
            long timeElapsed = (end - start)/1000000;
            this.logResults(this.logFile, "write", timeElapsed, success);
            System.out.println("Execution time : " + timeElapsed + "ms");
        }

        return success;
    }


    /**
     * Write content into a file
     */
    public boolean logResults(String filename, String task, long execTime, boolean success) throws IOException {

        // PARAMS
        long timestamp    = System.currentTimeMillis()/1000;
        Path relativePath = Paths.get("");
        String path       = relativePath.toAbsolutePath().toString() + "\\src\\app\\files\\logs\\" + filename;
        File f            = new File(path);
        boolean fExists   = true;
        boolean ret       = false;

        if (!f.exists()) {
            f.createNewFile();
            fExists = false;
        }

        // LOG RESULTS
        this.writeLock.lock();
        try(FileOutputStream fos = new FileOutputStream(f, true)) {
            if(!fExists) {
                String headers = "timestamp client type operation execTime success";
                fos.write(headers.getBytes());
                fos.write(System.getProperty("line.separator").getBytes());
            }
            String data = timestamp + " " + this.client.getId() + " " + this.profile + " " + task + " " + execTime + " " + success;
            fos.write(data.getBytes());
            fos.write(System.getProperty("line.separator").getBytes());
            fos.flush();
            fos.close();
            ret = true;
        } 
        catch (IOException e) {
            e.printStackTrace();
            ret = false;
        }
        finally {
            this.writeLock.unlock();
        }

        return ret;
    }
}