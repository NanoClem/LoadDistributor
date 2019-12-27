package app.rmiobjects;

import app.interfaces.ClientInterface;
import app.interfaces.MachineInterface;
import app.interfaces.SwitcherInterface;

import java.util.HashMap;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;



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
   * @throws RemoteException
   * @see SwitcherInterface#getId()
   */
  public int getId() throws RemoteException {
    return this.id;
  }

  /**
   * @return switcher's surname
   * @throws RemoteException
   * @see SwitcherInterface#getSurname()
   */
  public String getSurname() throws RemoteException {
    return this.surname;
  }

  /**
   * @param id the id to set
   */
  public void setId(int new_id) throws RemoteException {
    this.id = new_id;
  }

  /**
   * @param surname the surname to set
   */
  public void setSurname(String new_surname) throws RemoteException {
    this.surname = new_surname;
  }

  /**
   * @param machines the machines to set
   */
  @SuppressWarnings("unchecked")
  public void setMachines(HashMap<MachineInterface, Integer> machines) throws RemoteException {
    this.machines = (HashMap<MachineInterface, Integer>) machines.clone(); 
  }


  /* =====================================================
          SWITCHER INTERFACE FUNCTIONS
  ===================================================== */
  /**
   * @see SwitcherInterface#check
   */
  @Override
  public void check(ClientInterface c) throws RemoteException {
    System.out.println("[" + LocalDateTime.now() + "] " + "New client connected to server, welcome " + c.getSurname());
  } 

  /**
   * @see SwitcherInterface#getMachines()
   * @throws RemoteException
   */
  @Override
  @SuppressWarnings("unchecked")
  public HashMap<MachineInterface, Integer> getMachines() throws RemoteException {
    return (HashMap<MachineInterface, Integer>) this.machines.clone();
  }


  /* =====================================================
            OPERATION INTERFACE FUNCTIONS
  ===================================================== */

  /**
   * @see MachinerInterface#hello()
   */
  @Override
  public boolean hello(String name, ClientInterface c) throws RemoteException {
    
    System.out.println("[" + LocalDateTime.now() + "] " + c.getSurname() + " asked for hello");

    boolean ret = false;
    for(MachineInterface m : this.machines.keySet()) {
      // LOOKING FOR FREE LOADED MACHINE
      if(this.machines.get(m) <= 0) {
        this.notifyLoad(m, 1);    // load
        ret = m.hello(name, c);
        this.notifyLoad(m, -1);   // unload
        break;
      }
    }

    return ret;
  }


  /**
   * Call the read function from a machine among the arraylist
   * @see MachineInterface#read
   */
  @Override
  public boolean read(String filename, ClientInterface c) throws RemoteException, IOException, FileNotFoundException {

    System.out.println("[" + LocalDateTime.now() + "] " + c.getSurname() + " asked to read " + filename);

    boolean ret = false;
    for(MachineInterface m : this.machines.keySet()) {
      // LOOKING FOR FREE LOADED MACHINE
      if(this.machines.get(m) <= 0) {
        this.notifyLoad(m, 1);    // load
        ret = m.read(filename, c);
        this.notifyLoad(m, -1);   // unload
        break;
      }
    }

    return ret;
  }


  /**
   * Call the write function from a machine among the arraylist
   * @see MachineInterface#write
   */
  @Override
  public boolean write(String filename, byte[] data, ClientInterface c) throws RemoteException, IOException, FileNotFoundException {

    System.out.println("[" + LocalDateTime.now() + "] " + c.getSurname() + " asked to write in " + filename);

    boolean ret = false;
    for(MachineInterface m : this.machines.keySet()) {
      // LOOKING FOR FREE LOADED MACHINE
      if(this.machines.get(m) <= 0) {
        this.notifyLoad(m, 1);    // load
        ret = m.write(filename, data, c);
        this.notifyLoad(m, -1);   // unload
        break;
      }
    }

    return ret;
  }


  /* =====================================================
            CONTROL INTERFACE FUNCTIONS
  ===================================================== */
  /**
   * @see ControlInterface#addMachine
   */
  @Override
  public void addMachine(MachineInterface m) throws RemoteException {
    System.out.println("[" + LocalDateTime.now() + "] " + "machine " + m.getId() + " connected, welcome !");
    this.machines.put(m, 0);
  }

  /**
   * @see ControlInterface#removeMachine
   */
  @Override
  public void removeMachine(MachineInterface m) throws RemoteException {
    System.out.println("[" + LocalDateTime.now() + "] " + "machine " + m.getId() + " removed, goodbye");
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

    System.out.println("[" + LocalDateTime.now() + "] " + "machine " + m.getId() + ": current load = " + m.getLoad());
    System.out.println("[" + LocalDateTime.now() + "] " + "machine " + m.getId() + ": incoming load " + load);

    // LOCAL LOAD
    m.addLoad(load);

    // SWITCHER LOAD
    int oldLoad = this.machines.get(m);
    this.machines.replace(m, oldLoad + load);

    System.out.println("[" + LocalDateTime.now() + "] " + "machine " + m.getId() + ": new load = " + this.machines.get(m));
  }

}
