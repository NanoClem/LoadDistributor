package app.interfaces;

import app.interfaces.MachineInterface;
import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 *
 */
public interface NotifyInterface extends Remote {

    /**
     * Informe de la charge d'une machine
     * @param m : machine concernee
     * @param load : charge
     * @throws RemoteException
     */
    public void notifyLoad(MachineInterface m, int load) throws RemoteException;
}
