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


    
    public ClientReaderWriter(int id, String name, String rFilename, String wFilename, SwitcherInterface s) throws RemoteException {
        super(id, name, s);
        this.profile = "read_write";
        this.readFile  = rFilename;
        this.writeFile = wFilename;
    }


    /**
     * CONSTRUCTOR OVERLOAD for default name
     * @param id
     * @param rFilename
     * @param wFilename
     * @param s
     * @throws RemoteException
     */
    public ClientReaderWriter(int id, String rFilename, String wFilename, SwitcherInterface s) throws RemoteException {
        this(id, "", rFilename, wFilename, s);
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