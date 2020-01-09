package app.interfaces;

import app.interfaces.MachineInterface;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 *
 */
public interface NotifyInterface extends Remote {

    /**
     * Inform the load carried by a machine
     * @param m : machine concernee
     * @param load : charge
     * @throws RemoteException
     */
    public void notifyLoad(MachineInterface m, int load) throws RemoteException;

    /**
     * Report data into a file
     * @param m
     * @param filename
     * @return
     * @throws RemoteException
     * @throws IOException
     * @throws FileNotFoundException
     */
    public boolean notifyData(MachineInterface m, String filename) throws RemoteException, IOException, FileNotFoundException;
}
