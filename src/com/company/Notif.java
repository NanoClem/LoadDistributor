import javva.rmi.Remote;
import java.rmi.RemoteException;


/**
 *
 */
public interface Notif extends Remote {

  /**
   *
   */
  public void notifyLoad(Machine m, int load) throws RemoteException;
}
