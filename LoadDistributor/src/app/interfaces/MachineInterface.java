package app.interfaces;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface who defines what a Machine can do
 */
public interface MachineInterface extends Remote {
    
    /**
     * Read and returns a file's content
     * @param filename : path to file
     * @return byte[] : file's content
     * @throws RemoteException
     */
    public byte[] read(String filename) throws RemoteException, IOException, FileNotFoundException;

    /**
     * Write data in a file
     * @param filename : path to file
     * @param data : content to write
     * @return boolean : true if write succeed, false else
     * @throws RemoteException
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
