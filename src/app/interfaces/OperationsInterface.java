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
    public boolean read(String filename, ClientInterface c) throws RemoteException, IOException, FileNotFoundException;

    /**
     * Declare behaviour for a write operation
     * @param filename path to file
     * @param data content to write
     * @return true if write succeed, false else
     * @throws RemoteException
     * @throws IOException
     * @throws FileNotFoundException
     */
    public boolean write(String filename, byte[] data, ClientInterface c) throws RemoteException, IOException, FileNotFoundException;

    /**
     * Say hello
     * @param name
     * @return
     * @throws RemoteException
     */
    public boolean hello(String name, ClientInterface c) throws RemoteException;
}