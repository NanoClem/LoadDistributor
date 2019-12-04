package app;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


/**
 * 
 */
public interface SwitcherInterface extends MachineInterface, ControlInterface, NotifyInterface {

  /**
   * Affiche un message de verification sur le switcher (si le client a bien acces au Switcher)
   * @throws RemoteException
   */
  public void check() throws RemoteException;

  /**
   * PAS BIEN ICI, C'EST LA MACHINE QUI SE CHARGE DE CA
   * @param name
   * @return
   * @throws RemoteException
   */
   public String hello(String name) throws RemoteException;
}
