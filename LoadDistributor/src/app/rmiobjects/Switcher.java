package app.rmiobjects;

import app.interfaces.MachineInterface;
import app.interfaces.SwitcherInterface;

import java.util.LinkedHashMap;
import java.io.FileNotFoundException;
import java.io.IOException;
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
   * Map of machines and their load
   */
  public LinkedHashMap<MachineInterface, Integer> machines;


  /**
   * CONSTRUCTEUR
   */
  public Switcher() throws RemoteException {
    this.machines = new LinkedHashMap<MachineInterface, Integer>();
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
    for(MachineInterface m : this.machines.keySet()) {
      ret += "Machine " + m.getId() + " |";
    }
    
    return ret;
  }


  /* =====================================================
            MACHINE INTERFACE FUNCTIONS
  ===================================================== */
  /**
   * @see MachineInterface#getId()
   */
  @Override
  public int getId() throws RemoteException {
    return 0;
  }

  /**
   * @see MachineInterface#getLoad()
   */
  @Override
  public int getLoad() throws RemoteException {
    return 0;
  }

  /**
   * @see MachineInterface#addLoad()
   */
  @Override
  public void addLoad(int l) throws RemoteException {

  }


  /**
   * Call the read function from a machine among the arraylist
   * @see MachineInterface#read
   */
  @Override
  public byte[] read(String filename) throws RemoteException, IOException, FileNotFoundException {

    byte[] ret;
    for(MachineInterface m : this.machines.keySet()) {
      // LOOKING FOR FREE LOADED MACHINE
      if(this.machines.get(m) <= 0) {
        this.notifyLoad(m, 1);    // load
        ret = m.read(filename);
        this.notifyLoad(m, -1);   // unload
        return ret;
      }
    }
    // NO MACHINE AVAILABLE
    return "All machines occupied".getBytes();
  }


  /**
   * Call the write function from a machine among the arraylist
   * @see MachineInterface#write
   */
  @Override
  public boolean write(String filename, byte[] data) throws RemoteException, IOException, FileNotFoundException {

    boolean ret;
    for(MachineInterface m : this.machines.keySet()) {
      // LOOKING FOR FREE LOADED MACHINE
      if(this.machines.get(m) <= 0) {
        this.notifyLoad(m, 1);    // load
        ret = m.write(filename, data);
        this.notifyLoad(m, -1);   // unload
        return ret;
      }
    }
    // NO MACHINE AVAILABLE
    return false;
  }


  /* =====================================================
            CONTROL INTERFACE FUNCTIONS
  ===================================================== */
  /**
   * @see ControlInterface#addMachine
   */
  @Override
  public void addMachine(MachineInterface m) throws RemoteException {
    this.machines.put(m, 0);
    System.out.println("New machine connected, welcome machine " + m.getId());
  }

  /**
   * @see ControlInterface#removeMachine
   */
  @Override
  public void removeMachine(MachineInterface m) throws RemoteException {
    this.machines.remove(m);
    System.out.println("Machine removed, goodbye machine " + m.getId());
  }


  /* =====================================================
            NOTIFY INTERFACE FUNCTIONS
  ===================================================== */
  /**
   * @see NotifyInterface#notifyLoad
   */
  @Override
  public void notifyLoad(MachineInterface m, int load) throws RemoteException {
    // LOCAL LOAD
    m.addLoad(load);

    // SWITCHER LOAD
    int oldLoad = this.machines.get(m);
    this.machines.replace(m, oldLoad + load);
  }

}
