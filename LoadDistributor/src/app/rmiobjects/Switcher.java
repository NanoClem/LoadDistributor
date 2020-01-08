package app.rmiobjects;

import app.interfaces.ClientInterface;
import app.interfaces.MachineInterface;
import app.interfaces.SwitcherInterface;

import java.util.Collections;
import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;



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
   * type of load distribution algorithm used by the switcher
   */
  private String mode;

  /**
   * Map of machines and their load
   */
  public HashMap<MachineInterface, Integer> machines;

  /**
   * 
   */
  private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

  /**
   * 
   */
  // private final Lock readLock = readWriteLock.readLock();

  /**
   * 
   */
  private final Lock writeLock = readWriteLock.writeLock();


  /**
   * CONSTRUCTOR
   */
  public Switcher(int num, String name, String mode) throws RemoteException {
    super();
    this.id       = num;
    this.surname  = name;
    this.mode     = mode;
    this.machines = new HashMap<MachineInterface, Integer>();
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
   * @param mode the mode to set
   */
  public void setMode(String new_mode) {
    this.mode = new_mode;
  }

  /**
   * @param machines the machines to set
   */
  @SuppressWarnings("unchecked")
  public void setMachines(HashMap<MachineInterface, Integer> machines) throws RemoteException {
    this.machines = (HashMap<MachineInterface, Integer>) machines.clone(); 
  }


  /* =====================================================
          LOAD DISTRIBUTION ALGORITHMS
  ===================================================== */

  /**
   * Get the first available machine.
   * A load of 0 correspond to an available machine, 1 to occupied.
   * If all machines are occupied, it will try until the max tries value is scored.
   * @return the first available machine
   * @throws RemoteException
   */
  private MachineInterface getFirstAvailable() throws RemoteException {

    this.writeLock.lock();
    MachineInterface ret = null;
    int maxtry = 10;
    try {
      for(int i=0; i<maxtry; i++) {
        for(MachineInterface m : this.machines.keySet()) {
          if(m.getLoad() <= 0) {
            ret = m;
            i   = maxtry;   // no need to retry
            break;
          }
        }
      }
    }
    finally {
      this.writeLock.unlock();
    }

    return ret;
  }


  /**
   * Get the less loaded machine
   * @return min loaded machine
   * @throws RemoteException
   */
  private MachineInterface getMin() throws RemoteException {
    this.writeLock.lock();
    try {
      MachineInterface m = Collections.min(this.machines.entrySet(), HashMap.Entry.comparingByValue()).getKey();
      return m;
    }
    finally {
      this.writeLock.unlock();
    }
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
   * @return switcher's id
   * @throws RemoteException
   * @see SwitcherInterface#getId()
   */
  @Override
  public int getId() throws RemoteException {
    return this.id;
  }

  /**
   * @return switcher's surname
   * @throws RemoteException
   * @see SwitcherInterface#getSurname()
   */
  @Override
  public String getSurname() throws RemoteException {
    return this.surname;
  }

  /**
   * @return switcher's mode
   * @throws RemoteException
   * @see SwitcherInterface#getSurname()
   */
  @Override
  public String getMode() throws RemoteException {
    return this.mode;
  }

  
  /**
   * @see SwitcherInterface#getMachines()
   * @throws RemoteException
   */
  @Override
  @SuppressWarnings("unchecked")
  public HashMap<MachineInterface, Integer> getMachines() throws RemoteException {

   writeLock.lock();
    try {
      HashMap<MachineInterface, Integer> ret = (HashMap<MachineInterface, Integer>) this.machines.clone();
      return ret;
    }
    finally {
      writeLock.unlock();
    }
  }


  /**
   * @see SwitcherInterface#getMachine()
   * @throws RemoteException
   */
  @Override
  public MachineInterface getMachine() throws RemoteException {

    MachineInterface m = null;
    switch (this.mode) {

      case "AVAILABLE":
        m = this.getFirstAvailable();
        break;

      case "MIN":
        m = this.getMin();
    
      default:
        break;
    }
    return m;
  }

  
  /* =====================================================
            OPERATION INTERFACE FUNCTIONS
  ===================================================== */

  /**
   * @see MachinerInterface#hello()
   * This function uses the "available or occupied" algorithm when looking for machines
   */
  @Override
  public boolean hello(String name, ClientInterface c) throws RemoteException {
    
    System.out.println("[" + LocalDateTime.now() + "] " + c.getSurname() + " asked for hello");
    boolean ret = false;
    
    MachineInterface m = getMachine();  // looking for a machine
    if(m == null) {
      return false;
    }
    this.notifyLoad(m, 1);              // load
    ret = m.hello(name, c);
    this.notifyLoad(m, -1);             // unload

    return ret;
  }


  /**
   * Call the read function from a machine among the arraylist
   * @see SwitcherInterface#read(String, ClientInterface)
   */
  @Override
  public boolean read(String filename, ClientInterface c) throws RemoteException, IOException, FileNotFoundException {

    System.out.println("[" + LocalDateTime.now() + "] " + c.getSurname() + " asked to read " + filename);
    boolean ret = false;
    
    MachineInterface m = this.getMachine();  // looking for a machine
    if(m == null) {
      return false;
    }
    this.notifyLoad(m, 1);                   // load
    ret = m.read(filename, c);
    this.notifyLoad(m, -1);                  // unload

    return ret;
  }


  /**
   * Call the write function from a machine among the arraylist
   * @see SwitcherInterface#write(String, byte[], ClientInterface)
   */
  @Override
  public boolean write(String filename, byte[] data, ClientInterface c) throws RemoteException, IOException, FileNotFoundException {

    System.out.println("[" + LocalDateTime.now() + "] " + c.getSurname() + " asked to write in " + filename);
    boolean ret = false;

    MachineInterface m = this.getMachine();   // looking for a machine
    if(m == null) {
      return false;
    }
    this.notifyLoad(m, 1);                    // load
    ret = m.write(filename, data, c);
    this.notifyLoad(m, -1);                   // unload

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

    this.writeLock.lock();
    try {
      System.out.println("[" + LocalDateTime.now() + "] " + "machine " + m.getId() + " connected, welcome !");
      this.machines.put(m, 0);
    }
    finally {
      this.writeLock.unlock();
    }
  }


  /**
   * @see ControlInterface#removeMachine
   */
  @Override
  public void removeMachine(MachineInterface m) throws RemoteException {

    this.writeLock.lock();
    try {
      System.out.println("[" + LocalDateTime.now() + "] " + "machine " + m.getId() + " removed, goodbye");
      this.machines.remove(m);
    }
    finally {
      this.writeLock.unlock();
    }
  }


  /* =====================================================
            NOTIFY INTERFACE FUNCTIONS
  ===================================================== */

  /**
   * @see NotifyInterface#notifyLoad
   */
  @Override
  public void notifyLoad(MachineInterface m, int load) throws RemoteException {

    this.writeLock.lock();
    try {
      System.out.println("[" + LocalDateTime.now() + "] " + "machine " + m.getId() + ": current load = " + m.getLoad());
      System.out.println("[" + LocalDateTime.now() + "] " + "machine " + m.getId() + ": incoming load " + load);

      // UPDATE LOADS
      m.addLoad(load);                            // update machine's local load
      int oldLoad = this.machines.get(m);         // switcher's load
      this.machines.replace(m, oldLoad + load);   // update loads on switcher

      // WRITE DATA LOGS
      // try {
      //   notifyData(m, Paths.get("").toAbsolutePath().toString() + "\\src\\app\\files\\logs\\machines_load.csv");
      // } 
      // catch (IOException e) {
      //   e.printStackTrace();
      // }

      System.out.println("[" + LocalDateTime.now() + "] " + "machine " + m.getId() + ": new load = " + this.machines.get(m));
    }
    finally {
      this.writeLock.unlock();
    }
  }


  /**
   * @see NotifyInterface#notifyData
   */
  @Override
  public boolean notifyData(MachineInterface m, String filename) throws RemoteException, IOException, FileNotFoundException {
    
    // PARAMS
    File f      = new File(filename);
    boolean ret = false;

    // STREAM
    try(FileOutputStream fos = new FileOutputStream(f, true)) {

      StringBuilder line = new StringBuilder();
      // if (!f.exists()) {
      //   line.append("timestamp ").append("id ").append("name ").append("load").append(System.getProperty("line.separator"));
      //   f.createNewFile();  
      // }
      // DATA
      long timestamp = LocalDateTime.now().atZone(ZoneOffset.UTC).toEpochSecond();
      int id           = m.getId();
      String name      = m.getSurname();
      int load         = m.getLoad(); 
      // FORMAT
      line.append(timestamp).append(" ");   // timestamp value
      line.append(id).append(" ");          // machine id
      line.append(name).append(" ");        // machine name
      line.append(load);                    // machine load
      // WRITE
      fos.write(new String(line).getBytes());
      fos.write(System.getProperty("line.separator").getBytes());
      fos.flush();
      fos.close();
      ret = true;
    } 
    catch (IOException e) {
      e.printStackTrace();
      ret = false;
    }

    return ret;
  }

}
