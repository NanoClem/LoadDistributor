package app.rmiobjects;

import app.interfaces.SwitcherInterface;
import java.rmi.RemoteException;


/**
 *
 */
public class Switcher implements SwitcherInterface {

  /**
   * Nombre de machines gerees
   */
  private int nbMachines = 1;

  /**
   * Liste des machines connectees
   */
  public Machine[] machines;


  /**
   * CONSTRUCTEUR
   * @param nbMachines
   */
  public Switcher() {
    this.machines = new Machine[this.nbMachines];
  }

  /**
   * @see SwitcherInterface#check
   */
  @Override
  public void check() throws RemoteException {
    System.out.println("Check switcher ok");
  }

  /**
   * PAS BIEN ICI, C'EST LA MACHINE QUI SE CHARGE DE CA
   * @see SwitcherInterface#hello
   */
  @Override
   public String hello(String name) throws RemoteException {
     return "Hello " + name;
   }


  /* =====================================================
            MACHINE INTERFACE FUNCTIONS
  ===================================================== */
  /**
   * @see MachineInterface#read
   */
  @Override
  public byte[] read(String filename) throws RemoteException {
    byte[] tmp = new byte[1];
    return tmp;
  }

  /**
   * @see MachineInterface#write
   */
  @Override
  public boolean write(String filename, byte[] data) throws RemoteException {
    boolean tmp = true;
    return tmp;
  }


  /* =====================================================
            CONTROL INTERFACE FUNCTIONS
  ===================================================== */
  /**
   * @see ControlInterface#addMachine
   */
  @Override
  public void addMachine(Machine m) throws RemoteException {

  }

  /**
   * @see ControlInterface#removeMachine
   */
  @Override
  public void removeMachine(Machine m) throws RemoteException {

  }


  /* =====================================================
            NOTIFY INTERFACE FUNCTIONS
  ===================================================== */
  /**
   * @see NotifyInterface#notifyLoad
   */
  @Override
  public void notifyLoad(Machine m, int load) throws RemoteException {

  }
}
