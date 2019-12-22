package app.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import app.rmiobjects.Machine;

/**
 *
 */
public interface ControlInterface extends Remote {

  /**
     * Add a machine to the container
     * @param m : machine to add
     * @throws RemoteException
     */
    public void addMachine(Machine m) throws RemoteException;

    /**
     * Remove a machine from the container
     * @param m : machine to remove
     * @throws RemoteException
     */
    public void removeMachine(Machine m) throws RemoteException;
}
