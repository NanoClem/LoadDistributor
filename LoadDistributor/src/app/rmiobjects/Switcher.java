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
  private final Lock readLock = readWriteLock.readLock();

  /**
   * 
   */
  private final Lock writeLock = readWriteLock.writeLock();


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

    readLock.lock();
    try {
      HashMap<MachineInterface, Integer> ret = (HashMap<MachineInterface, Integer>) this.machines.clone();
      return ret;
    }
    finally {
      readLock.unlock();
    }
  }


  /**
   * @see SwitcherInterface#getMin()
   */
  @Override
  public MachineInterface getMin() throws RemoteException {

    this.writeLock.lock();
    try {
      MachineInterface m = Collections.min(this.machines.entrySet(), HashMap.Entry.comparingByValue()).getKey();
      return m;
    }
    finally {
      this.writeLock.unlock();
    }
  }


  /**
   * Call the read function from a machine among the arraylist
   * This function uses the "min load" algorithm to find a machine
   * @see MachineInterface#readByMin
   */
  @Override
  public boolean readByMin(String filename, ClientInterface c) throws RemoteException, IOException, FileNotFoundException {

    System.out.println("[" + LocalDateTime.now() + "] " + c.getSurname() + " asked to read " + filename);
    boolean ret = false;
    // FINDING THE MIN LOAD
    MachineInterface min = this.getMin();
    // NOTIFY AND READ
    this.notifyLoad(min, 1);    // load
    ret = min.read(filename, c);
    this.notifyLoad(min, -1);   // unload

    return ret;
  }


  /**
   * Call the write function from a machine among the arraylist
   * This function uses the "min load" algorithm to find a machine
   * @see MachineInterface#write
   */
  @Override
  public boolean writeByMin(String filename, byte[] data, ClientInterface c) throws RemoteException, IOException, FileNotFoundException {

    boolean ret = false;
    System.out.println("[" + LocalDateTime.now() + "] " + c.getSurname() + " asked to write in " + filename);

    // FINDING THE MIN LOAD
    MachineInterface min = this.getMin();
    // NOTIFY AND READ
    this.notifyLoad(min, 1);    // load
    ret = min.write(filename, data, c);
    this.notifyLoad(min, -1);   // unload

    return ret;
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
    HashMap<MachineInterface, Integer> machinesCopy = this.getMachines();   // the copy function is protected by a read lock
    
    for(MachineInterface m : machinesCopy.keySet()) {
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
   * This function uses the "available or occupied" algorithm when looking for machines
   * @see MachineInterface#read
   */
  @Override
  public boolean read(String filename, ClientInterface c) throws RemoteException, IOException, FileNotFoundException {

    System.out.println("[" + LocalDateTime.now() + "] " + c.getSurname() + " asked to read " + filename);
    boolean ret = false;
    HashMap<MachineInterface, Integer> machinesCopy = this.getMachines();   // the copy function is protected by a read lock
    
    for(MachineInterface m : machinesCopy.keySet()) {
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
   * This function uses the "available or occupied" algorithm when looking for machines
   * @see MachineInterface#write
   */
  @Override
  public boolean write(String filename, byte[] data, ClientInterface c) throws RemoteException, IOException, FileNotFoundException {

    System.out.println("[" + LocalDateTime.now() + "] " + c.getSurname() + " asked to write in " + filename);
    boolean ret = false;
    HashMap<MachineInterface, Integer> machinesCopy = this.getMachines();   // the copy function is protected by a read lock

    for(MachineInterface m : machinesCopy.keySet()) {
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
