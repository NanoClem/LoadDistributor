package app.rmiobjects;

import app.interfaces.SwitcherInterface;

import java.util.ArrayList;
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
  public ArrayList<Machine> machines;


  /**
   * CONSTRUCTEUR
   */
  public Switcher() {
    this.machines = new ArrayList<Machine>();
  }

  /**
   * @see SwitcherInterface#check
   */
  @Override
  public void check() throws RemoteException {
    System.out.println("Client connected to server");
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
   * Call the read function from a machine among the arraylist
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
    this.machines.add(m);
    System.out.println("New machine connected, welcome machine " + m.getId());
  }

  /**
   * @see ControlInterface#removeMachine
   */
  @Override
  public void removeMachine(Machine m) throws RemoteException {
    this.machines.remove(m);
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
