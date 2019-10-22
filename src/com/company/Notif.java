import javva.rmi.Remote;
import Machine;

/**
 *
 */
public interface Notif extends Remote {

  /**
   *
   */
  public void notifyLoad(Machine m, int load) throws RemoteException;
}
