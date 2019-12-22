package app;

import app.rmiobjects.Switcher;

import java.rmi.registry.LocateRegistry;
import java.rmi.Naming;


/**
 * @author NanoClem
 */
public class SwitcherServer {
    public static void main(String[] argv) {
        try {
            Switcher skeleton = new Switcher(1, "Hermes");       // stub for accessing Switcher services
            LocateRegistry.createRegistry(10000);                         // create a new registry
            Naming.rebind("rmi://localhost:10000/Switcher", skeleton);    // register our stub as "Switcher"
            System.out.println("Switcher waiting on port 10000");
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
