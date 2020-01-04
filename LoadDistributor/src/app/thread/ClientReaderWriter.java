package app.thread;

import java.rmi.RemoteException;

import app.interfaces.SwitcherInterface;



/**
 * ClientThread : can read and write
 */
public class ClientReaderWriter extends ClientThread {

    /**
     * Reading filename
     */
    private String readFile;

    /**
     * Writing filename
     */
    private String writeFile;


    /**
     * CONSTRUCTOR
     * 
     * @param id
     * @param name
     * @param port
     * @throws RemoteException
     * @throws NotBoundException
     */
    public ClientReaderWriter(int id, String name, String rFilename, String wFilename, SwitcherInterface s) throws RemoteException {
        super(id, name, s);
        this.profile = "read_write";
        this.readFile  = rFilename;
        this.writeFile = wFilename;
    }


    /**
     * Thread run function
     */
    public void run() {

        String s = "Thread " + this.getName() + " writed here";
        while(true) {
            try {
                this.read(this.readFile);
                this.write(this.writeFile, s.getBytes());
                Thread.sleep(1000);
            } 
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}