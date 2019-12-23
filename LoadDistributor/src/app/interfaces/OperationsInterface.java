package app.interfaces;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * 
 */
public interface OperationsInterface extends Remote {

    /**
     * Declare behaviour for a read operation
     * @param filename path to file
     * @return read content
     * @throws RemoteException
     * @throws IOException
     * @throws FileNotFoundException
     */
    public byte[] read(String filename) throws RemoteException, IOException, FileNotFoundException;

    /**
     * Declare behaviour for a write operation
     * @param filename path to file
     * @param data content to write
     * @return true if write succeed, false else
     * @throws RemoteException
     * @throws IOException
     * @throws FileNotFoundException
     */
    public boolean write(String filename, byte[] data) throws RemoteException, IOException, FileNotFoundException;

    /**
     * Say hello
     * @param name
     * @return
     * @throws RemoteException
     */
    public String hello(String name) throws RemoteException;
}