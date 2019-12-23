package app.rmiobjects;

import app.interfaces.MachineInterface;
import app.interfaces.SwitcherInterface;

import java.util.HashMap;
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
   * Unique identifier
   */
  private int id;

  /**
   * Name of the Switcher
   */
  private String surname;

  /**
   * Map of machines and their load
   */
  public HashMap<MachineInterface, Integer> machines;


  /**
   * CONSTRUCTOR
   */
  public Switcher(int num, String name) throws RemoteException {
    super();
    this.id       = num;
    this.surname  = name;
    this.machines = new HashMap<MachineInterface, Integer>();
  }

  /**
   * @return switcher's id
   */
  public int getId() {
    return this.id;
  }

  /**
   * @return switcher's surname
   */
  public String getSurname() {
    return this.surname;
  }

  /**
   * @param id the id to set
   */
  public void setId(int new_id) {
    this.id = new_id;
  }

  /**
   * @param surname the surname to set
   */
  public void setSurname(String new_surname) {
    this.surname = new_surname;
  }

  /**
   * @param machines the machines to set
   */
  public void setMachines(HashMap<MachineInterface, Integer> machines) {
    this.machines = machines;
  }


  /* =====================================================
          SWITCHER INTERFACE FUNCTIONS
  ===================================================== */
  /**
   * @see SwitcherInterface#check
   */
  @Override
  public void check() throws RemoteException {
    System.out.println("Client connected to server");
  } 

  /**
   * @see SwitcherInterface#getMachines()
   * @throws RemoteException
   */
  @Override
  @SuppressWarnings("unchecked")
  public HashMap<Machine, Integer> getMachines() throws RemoteException {
    return (HashMap<Machine, Integer>) this.machines.clone();
  }


  /* =====================================================
            MACHINE INTERFACE FUNCTIONS
  ===================================================== */

  /**
   * @see MachinerInterface#hello()
   */
  @Override
  public String hello(String name) throws RemoteException {
    
    String ret = "";
    for(MachineInterface m : this.machines.keySet()) {
      // LOOKING FOR FREE LOADED MACHINE
      if(this.machines.get(m) <= 0) {
        this.notifyLoad(m, 1);    // load
        ret = m.hello(name);
        this.notifyLoad(m, -1);   // unload
        return ret;
      }
    }
    // NO MACHINE AVAILABLE
    return "All machines occupied";
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
    System.out.println("New machine connected, welcome " + m.getSurname());
  }

  /**
   * @see ControlInterface#removeMachine
   */
  @Override
  public void removeMachine(MachineInterface m) throws RemoteException {
    this.machines.remove(m);
    System.out.println("Machine removed, goodbye " + m.getSurname());
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
