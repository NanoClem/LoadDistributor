import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 *
 */
public interface SwitcherInterface extends Remote {

  /**
   *
   */
  public void check() throws RemoteException;
}
