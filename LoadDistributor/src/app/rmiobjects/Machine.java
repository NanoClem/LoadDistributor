package app.rmiobjects;

import app.interfaces.ClientInterface;
import app.interfaces.MachineInterface;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;


/**
 * 
 */
public class Machine extends UnicastRemoteObject implements MachineInterface, Serializable {

    /**
     * Serial number
     */
    private static final long serialVersionUID = 6918435107376120647L;

    /**
     * Unique identificator
     */
    private int id;

    /**
     * Name of the machine
     */
    public String surname;

    /**
     * Amount of load
     */
    private int load = 0;


    /**
     * CONSTRUCTOR
     * @param num : unique identificator of the machine
     * @param name : name of the machine
     */
    public Machine(int num, String name) throws RemoteException {
        super();
        this.id = num;
        this.surname = name;
    }

    /**
     * Set a new load for the machine
     * @param newLoad
     * @throws RemoteException
     */
    public void setLoad(int newLoad) throws RemoteException {
        this.load = newLoad;
    }

    /**
     * Set a new id for the machine
     * @param new_id
     * @throws RemoteException
     */
    public void setId(int new_id) throws RemoteException {
        this.id = new_id;
    }

    /**
     * Set a new name for the machine
     * @param new_name
     * @throws RemoteException
     */
    public void setSurname(String new_name) throws RemoteException {
        this.surname = new_name;
    }


    /* =====================================================
            MACHINE INTERFACE FUNCTIONS
     ===================================================== */

    /**
     * Return the id of the machine
     * @return (int)
     * @throws RemoteException
     * @see MachineInterface#getId()
     */
    @Override
    public int getId() throws RemoteException {
        return this.id;
    }

    /**
     * Return the current load of the machine
     * @return (int) load
     * @throws RemoteException
     * @see MachineInterface#getLoad()
     */
    @Override
    public int getLoad() throws RemoteException {
        return this.load;
    }

    /**
     * Return the name of the machine
     * @return (String)
     * @throws RemoteException
     * @see MachineInterface#getSurname()
     */
    @Override
    public String getSurname() throws RemoteException {
        return this.surname;
    }

    /**
     * Increase the load of the machine
     * @param l
     * @throws RemoteException
     * @see MachineInterface#addLoad()
     */
    @Override
    public void addLoad(int l) throws RemoteException {
        this.load = l;
    }


    /* =====================================================
            OPERATION INTERFACE FUNCTIONS
     ===================================================== */
     
    /**
     * Say hello
     * @see MachineInterface#hello()
     */
    @Override
    public boolean hello(String name, ClientInterface c) throws RemoteException {

        System.out.println("[" + LocalDateTime.now() + "] " + "hello task from " + c.getSurname());

        boolean ret = false;
        String resp = "";
        try {
            resp = "Hello " + c.getSurname() + " ! From " + this.getSurname();
            ret = true;
        }
        catch (Exception e) {
            e.printStackTrace();
            ret = false;
        }
        finally {
            c.setHelloResponse(resp);
        }

        return ret;
    }

    
    /**
     * Read a file's content
     * @see MachineInterface#read(String)
     */
    @Override
    public boolean read(String filename, ClientInterface c) throws RemoteException, IOException, FileNotFoundException {

        System.out.println("[" + LocalDateTime.now() + "] " + "reading task from " + c.getSurname() + ": " + filename);

        // PARAMS
        URL fUrl    = getClass().getResource(filename);
        File f      = new File(fUrl.getPath());
        byte[] resp = new byte[(int) f.length()];
        boolean ret = false;

        // READ FILE
        try(FileInputStream fis = new FileInputStream(f)) {
            fis.read(resp);
            fis.close();
            ret = true;
        } 
        catch (IOException e) {
            e.printStackTrace();
            ret = false;
        }
        finally {
            c.setReadResponse(resp);
        }

        return ret;
    }


    /**
     * Write content into a file
     * @see MachineInterface#write(String, byte[])
     */
    @Override
    public boolean write(String filename, byte[] data, ClientInterface c) throws RemoteException, IOException {

        System.out.println("[" + LocalDateTime.now() + "] " + "writing task from " + c.getSurname() + ": " + filename);

        // PARAMS
        File f      = new File(filename);
        boolean ret = false;

        // WRITE FILE
        try(FileOutputStream fos = new FileOutputStream(filename)) {
            if (!f.exists()) {
                f.createNewFile();
            }
            fos.write(data);
            fos.flush();
            fos.close();
            ret = true;
        } 
        catch (IOException e) {
            e.printStackTrace();
            ret = false;
        }
        finally {
            c.setWriteResponse(ret);
        }

        return ret;
    }
}