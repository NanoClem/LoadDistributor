package app.interfaces;

import java.rmi.RemoteException;
import java.util.HashMap;

import app.rmiobjects.Machine;



/**
 * 
 */
public interface SwitcherInterface extends OperationsInterface, ControlInterface, NotifyInterface {

  /**
   * Affiche un message de verification sur le switcher (si le client a bien acces au Switcher)
   * @throws RemoteException
   */
  public void check() throws RemoteException;

  /**
   * Return a shallow copy of the machines container
   * @throws RemoteException
   */
  public HashMap<Machine, Integer> getMachines() throws RemoteException;
}
