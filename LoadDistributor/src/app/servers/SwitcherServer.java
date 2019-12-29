package app.servers;

import app.rmiobjects.Switcher;

import java.rmi.registry.LocateRegistry;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;


/**
 * @author NanoClem
 */
public class SwitcherServer {
    public static void main(String[] argv) {        
        try {

            // SERVER PARAMS
            int port   = 10000;
            String url = "rmi://localhost" + ":" + port + "/Switcher";

            // REGISTRY
            Switcher skeleton = new Switcher(1, "Hermes", "AVAILABLE");   // stub for accessing Switcher services
            LocateRegistry.createRegistry(port);                    // create a new registry
            Naming.rebind(url, skeleton);                           // register our stub as "Switcher"
            System.out.println("Switcher waiting on port 10000");

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
