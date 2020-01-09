package app.interfaces;

import java.rmi.RemoteException;
import java.util.HashMap;



/**
 * 
 */
public interface SwitcherInterface extends OperationsInterface, ControlInterface, NotifyInterface {

  /**
   * Log a connection message
   * @throws RemoteException
   */
  public void check(ClientInterface c) throws RemoteException;

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
   * @return the switcher's mode
   * @throws RemoteException
   */
  public String getMode() throws RemoteException;

  /**
   * @return a shallow copy of the machines container
   * @throws RemoteException
   */
  public HashMap<MachineInterface, Integer> getMachines() throws RemoteException;

  /**
   * Get the machine which has the better load depending on the chosen mode
   * @return the selected machine under the mode
   * @throws RemoteException
   */
  public MachineInterface getMachine() throws RemoteException;
}
