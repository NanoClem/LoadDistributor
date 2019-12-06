package app.rmiobjects;

import app.interfaces.MachineInterface;
import app.interfaces.SwitcherInterface;

import java.util.ArrayList;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


/**
 *
 */
public class Switcher extends UnicastRemoteObject implements SwitcherInterface, Serializable {

  /**
   * Serial number
   */
  private static final long serialVersionUID = 1L;

  /**
   * Nombre de machines gerees
   */
  //private int nbMachines = 1;

  /**
   * Liste des machines connectees
   */
  public ArrayList<MachineInterface> machines;


  /**
   * CONSTRUCTEUR
   */
  public Switcher() throws RemoteException {
    this.machines = new ArrayList<MachineInterface>();
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
   * @see SwitcherInterface#hello()
   */
  @Override
  public String hello(String name) throws RemoteException {
    return "Hello " + name;
  }

  /**
   * @see SwitcherInterface#printMachines()
   * @throws RemoteException
   */
  @Override
  public String getMachines() throws RemoteException {
    String ret = "";
    for(MachineInterface m : this.machines) {
      ret += "Machine " + m.getId() + " |";
    }

    return ret;
  }


  /* =====================================================
            MACHINE INTERFACE FUNCTIONS
  ===================================================== */
  @Override
  public int getId() throws RemoteException {
    return 0;
  }

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
  public void addMachine(MachineInterface m) throws RemoteException {
    this.machines.add(m);
    System.out.println("New machine connected, welcome machine " + m.getId());
  }

  /**
   * @see ControlInterface#removeMachine
   */
  @Override
  public void removeMachine(MachineInterface m) throws RemoteException {
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
