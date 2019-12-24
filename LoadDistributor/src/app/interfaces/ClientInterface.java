package app.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface who defines a Client behaviour
 * @author NanoClem
 * @version 1.1
 */
public interface ClientInterface extends Remote {
    
    /**
     * Return the id of the client
     * @return (int)
     * @throws RemoteException
     */
    public int getId() throws RemoteException;

    /**
     * Return the name of the client
     * @return (String)
     * @throws RemoteException
     */
    public String getSurname() throws RemoteException;

    /**
     * Get the hello response
     * @return hello response from a machine
     * @throws RemoteException
     */
    public String getHelloResponse() throws RemoteException;

    /**
     * Get the reading response
     * @return read response from a machine
     * @throws RemoteException
     */
    public byte[] getReadResponse() throws RemoteException;

    /**
     * Get the writing response
     * @return writing response from a machine
     * @throws RemoteException
     */
    public boolean getWriteResponse() throws RemoteException;

    /**
     * Set response when a hello request
     * @param response
     * @throws RemoteException
     */
    public void setHelloResponse(String response) throws RemoteException;

    /**
     * Set response when a read request
     * @param response
     * @throws RemoteException
     */
    public void setReadResponse(byte[] response) throws RemoteException;

    /**
     * Set response when a write request
     * @param response
     * @throws RemoteException
     */
    public void setWriteResponse(boolean response) throws RemoteException;
}
