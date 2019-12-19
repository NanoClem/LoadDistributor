package app.rmiobjects;

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
     * Amount of load
     */
    private int load = 0;


    /**
     * CONSTRUCTOR
     * @param num : unique identificator of the machine
     */
    public Machine(int num) throws RemoteException {
        this.id = num;
    }

    /**
     * @see MachineInterface#getId()
     */
    public int getId() {
        return this.id;
    }

    /**
     * Return the current load of the machine
     * @return (int) load
     */
    public int getLoad() {
        return this.load;
    }

    /**
     * Set the id of the machine
     * @param new_id
     */
    public void setId(int new_id) {
        this.id = new_id;
    }


    /* =====================================================
            MACHINE INTERFACE FUNCTIONS
     ===================================================== */
    /**
     * @see MachineInterface#read(String)
     */
    @Override
    public byte[] read(String filename) throws RemoteException, IOException, FileNotFoundException {

        //this.load += 1;

        // PARAMS
        URL fUrl = getClass().getResource(filename);
        File f = new File(fUrl.getPath());
        byte[] ret = new byte[(int) f.length()];

        // READ FILE
        try(FileInputStream fis = new FileInputStream(f)) {
            fis.read(ret);
            fis.close();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }

        //this.load -= 1;
        return ret;
    }


    /**
     * @see MachineInterface#write(String, byte[])
     */
    @Override
    public boolean write(String filename, byte[] data) throws RemoteException, IOException {

        //this.load += 1;

        // PARAMS
        File f = new File(filename);

        // WRITE FILE
        try(FileOutputStream fos = new FileOutputStream(filename)) {
            if (!f.exists()) {
                f.createNewFile();
            }
            fos.write(data);
            fos.flush();
            fos.close();
            return true;
        } 
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            //this.load -= 1;
        }
    }
}