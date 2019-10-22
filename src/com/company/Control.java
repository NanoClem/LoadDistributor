import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 *
 */
public interface Control extends Remote {

  /**
   *
   */
  boolean addMachine(Machine m) throws RemoteException;

  /**
   *
   */
  boolean removeMachine(Machine m) throws RemoteException;
}
