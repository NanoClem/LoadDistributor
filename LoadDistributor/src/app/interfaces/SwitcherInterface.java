package app.interfaces;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.HashMap;



/**
 * 
 */
public interface SwitcherInterface extends OperationsInterface, ControlInterface, NotifyInterface {

  /**
   * @return switcher's id
   * @throws RemoteException
   */
  public int getId() throws RemoteException;

  /**
   * @return switcher's surname
   * @throws RemoteException
   */
  public String getSurname() throws RemoteException;

  /**
   * Print a verification message
   * @throws RemoteException
   */
  public void check(ClientInterface c) throws RemoteException;

  /**
   * @return a shallow copy of the machines container
   * @throws RemoteException
   */
  public HashMap<MachineInterface, Integer> getMachines() throws RemoteException;

  /**
   * @return the machine with the min load among the HashMap
   * @throws RemoteException
   */
  public MachineInterface getMin() throws RemoteException;

  /**
   * read function with minimum load selection
   * @param filename
   * @param c
   * @return
   * @throws RemoteException
   * @throws IOException
   * @throws FileNotFoundException
   */
  public boolean readByMin(String filename, ClientInterface c) throws RemoteException, IOException, FileNotFoundException;

  /**
   * write function with minimum load selection
   * @param filename
   * @param data
   * @param c
   * @return
   * @throws RemoteException
   * @throws IOException
   * @throws FileNotFoundException
   */
  public boolean writeByMin(String filename, byte[] data, ClientInterface c) throws RemoteException, IOException, FileNotFoundException;
}
