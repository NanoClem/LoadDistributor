import java.rmi.Remote;
// import Machine;

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
