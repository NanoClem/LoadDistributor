package app.rmiobjects;

import app.interfaces.ClientInterface;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


/**
 * 
 */
public class Client extends UnicastRemoteObject implements ClientInterface, Serializable {

    /**
     * Serial number
     */
    private static final long serialVersionUID = 1903134507979015418L;

    /**
     * Unique identificator
     */
    private int id;

    /**
     * Name of the client
     */
    private String surname;

    /**
     * Response when a machine says "hello"
     */
    private String helloResponse;

    /**
     * Response when client asks to read
     */
    private byte[] readResponse;

    /**
     * Response when a client asks to write
     */
    private boolean writeResponse;


    /**
     * CONSTRUCTOR
     * @param num : unique identificator of the client
     * @param name : name of the client
     */
    public Client(int num, String name) throws RemoteException {
        super();
        this.id = num;
        this.surname = name;
    }

    /**
     * Set a new id for the client
     * @param new_id
     * @throws RemoteException
     */
    public void setId(int new_id) throws RemoteException {
        this.id = new_id;
    }

    /**
     * Set a new name for the client
     * @param new_name
     * @throws RemoteException
     */
    public void setSurname(String new_name) throws RemoteException {
        this.surname = new_name;
    }


    /* =====================================================
            CLIENT INTERFACE FUNCTIONS
     ===================================================== */

    @Override
    public int getId() throws RemoteException {
        return this.id;
    }

    @Override
    public String getSurname() throws RemoteException {
        return this.surname;
    }

    @Override
    public String getHelloResponse() throws RemoteException {
        return this.helloResponse;
    }

    @Override
    public byte[] getReadResponse() throws RemoteException {
        return this.readResponse.clone();
    }

    @Override
    public boolean getWriteResponse() throws RemoteException {
        return this.writeResponse;
    }

    @Override
    public void setHelloResponse(String response) throws RemoteException {
        this.helloResponse = response;
    }

    @Override
    public void setReadResponse(byte[] response) throws RemoteException {
        this.readResponse = response;
    }

    @Override
    public void setWriteResponse(boolean response) throws RemoteException {
        this.writeResponse = response;
    }

}