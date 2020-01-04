package app.thread;

import java.rmi.RemoteException;

import app.interfaces.SwitcherInterface;




/**
 * ClientReadThread : can only read
 */
public class ClientReader extends ClientThread {

    /**
     * Name of the processed file
     */
    private String readFile;


    /**
     * CONSTRUCTOR
     * 
     * @param id
     * @param name
     * @param port
     * @throws RemoteException
     * @throws NotBoundException
     */
    public ClientReader(int id, String name, String rFilename, SwitcherInterface s) throws RemoteException {
        super(id, name, s);
        this.profile = "reader";
        this.readFile = rFilename;
    }


    /**
     * Thread run function
     */
    public void run() {
        while(true) {
            try {
                this.read(this.readFile);
                Thread.sleep(1000);
            } 
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}