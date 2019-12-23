package app.interfaces;

import app.interfaces.MachineInterface;

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
}
