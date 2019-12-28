package app.servers;

import app.interfaces.SwitcherInterface;
import app.thread.*;

import java.nio.file.Path;
import java.nio.file.Paths;
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
            INITIALISING FILES PATH
        ------------------------------- */
        Path currentRelativePath = Paths.get("");
        String path  = currentRelativePath.toAbsolutePath().toString() + "\\src\\app\\files\\";
        String rFile = path + "read_file.txt";
        String wFile = path + "write_file.txt";

        /* -------------------------------
            INITIALISING CLIENT THREADS
        ------------------------------- */
         // CLIENTS
        ClientReader reader1     = new ClientReader(1, "Paul", rFile, stub);
        ClientReader reader2     = new ClientReader(2, "Maria", rFile, stub);
        ClientWriter writer      = new ClientWriter(3, "Patrick", wFile, stub);
        ClientReaderWriter both  = new ClientReaderWriter(4, "Hector", rFile, wFile, stub);
        // THREAD LIST
        ArrayList<Thread> tList = new ArrayList<Thread>();
        tList.add(reader1);
        tList.add(reader2);
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
