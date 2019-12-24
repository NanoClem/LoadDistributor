package app.interfaces;

import java.rmi.RemoteException;
import java.util.HashMap;



/**
 * 
 */
public interface SwitcherInterface extends OperationsInterface, ControlInterface, NotifyInterface {

  /**
   * @return switcher's id
   * @throws RemoteException
   */
  public int getId() throws RemoteException;

  /**
   * @return switcher's surname
   * @throws RemoteException
   */
  public String getSurname() throws RemoteException;

  /**
   * Print a verification message
   * @throws RemoteException
   */
  public void check() throws RemoteException;

  /**
   * @return a shallow copy of the machines container
   * @throws RemoteException
   */
  public HashMap<MachineInterface, Integer> getMachines() throws RemoteException;
}
