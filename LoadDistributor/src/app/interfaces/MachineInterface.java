package app.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface who defines what a Machine can do
 */
public interface MachineInterface extends Remote, OperationsInterface {
    
    /**
     * Return the id of the machine
     * @return (int)
     * @throws RemoteException
     */
    public int getId() throws RemoteException;

    /**
     * Return the current load of the machine
     * @return the machine's load
     * @throws RemoteException
     */
    public int getLoad() throws RemoteException;

    /**
     * Return the name of the machine
     * @return (String)
     * @throws RemoteException
     */
    public String getSurname() throws RemoteException;

    /**
     * Increase the load of the machine
     * @param l load to add
     * @throws RemoteException
     */
    public void addLoad(int l) throws RemoteException;

    /**
     * Set a new load for the machine
     * @param newLoad to set
     * @throws RemoteException
     */
    public void setLoad(int newLoad) throws RemoteException;

    /**
     * Set a new id for the machine
     * @param new_id to set
     * @throws RemoteException
     */
    public void setId(int new_id) throws RemoteException;

    /**
     * Set a new name for the machine
     * @param new_name to set
     * @throws RemoteException
     */
    public void setSurname(String new_name) throws RemoteException;
}
