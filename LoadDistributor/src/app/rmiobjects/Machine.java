package app.rmiobjects;

import app.interfaces.MachineInterface;

import java.io.Serializable;
import java.rmi.RemoteException;


/**
 * 
 */
public class Machine implements MachineInterface, Serializable {

    /**
     * Serial number
     */
    private static final long serialVersionUID = 6918435107376120647L;

    /**
     * Unique identificator
     */
    private int id;


    /**
     * CONSTRUCTOR
     * @param num : unique identificator of the machine
     */
    public Machine(int num) {
        this.id = num;
    }

    /**
     * @see MachineInterface#getId()
     */
    public int getId() {
        return this.id;
    }


    /* =====================================================
            MACHINE INTERFACE FUNCTIONS
     ===================================================== */

    /**
     * @see MachineInterface#read(String)
     */
    @Override
    public byte[] read(String filename) throws RemoteException {
        byte[] tmp = new byte[1];
        return tmp;
    }

    /**
     * @see MachineInterface#write(String, byte[])
     */
    @Override
    public boolean write(String filename, byte[] data) throws RemoteException {
        boolean tmp = true;
        return tmp;
    }
}