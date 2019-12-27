package app.servers;

import app.interfaces.SwitcherInterface;
import app.thread.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

/**
 * @author NanoClem
 */
public class MultiThreadClientServer {
    public static void main(String[] argv) throws RemoteException, NotBoundException {

        /* -------------------------------
            INITIALISING STUB
        ------------------------------- */
        Registry registry = LocateRegistry.getRegistry(10000);                       // access to registry on port 10000
        SwitcherInterface stub = (SwitcherInterface) registry.lookup("Switcher");    // get the "Switcher" object in registry 

        /* -------------------------------
            INITIALISING CLIENT THREADS
        ------------------------------- */
        // CLIENTS
        ClientReader reader     = new ClientReader(1, "Paul", stub);
        ClientWriter writer     = new ClientWriter(2, "Patrick", stub);
        ClientReaderWriter both = new ClientReaderWriter(3, "Hector", stub);
        // THREAD LIST
        ArrayList<Thread> tList = new ArrayList<Thread>();
        tList.add(reader);
        tList.add(writer);
        tList.add(both);
        
        /* -------------------------------
            INITIALISING TESTS
        ------------------------------- */
        for(Thread t : tList) {
            t.start();
        }

    }
}
