package app.thread;

import java.rmi.RemoteException;

import app.interfaces.SwitcherInterface;



/**
 * ClientThread : can only write
 */
public class ClientWriter extends ClientThread {

    /**
     * Name of the processed file
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
    public ClientWriter(int id, String name, String wFilename, SwitcherInterface s) throws RemoteException {
        super(id, name, s);
        this.profile = "writer";
        this.writeFile = wFilename;
    }


    /**
     * Thread run function
     */
    public void run() {

        String s = "Thread " + this.getName() + " writed here";
        while(true) {
            try {
                this.write(this.writeFile, s.getBytes());
                Thread.sleep(1000);
            } 
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}