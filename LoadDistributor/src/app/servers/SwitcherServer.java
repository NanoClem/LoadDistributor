package app.servers;

import app.rmiobjects.Switcher;
import app.files.config.MyProperties;

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
            // LOAD PROPERTIES
            MyProperties prop = new MyProperties();

            // SERVER PARAMS
            int port    = Integer.parseInt(prop.getProperty("port"));
            String mode = prop.getProperty("mode");
            String url  = prop.getProperty("base_url") + ":" + port + "/Switcher/" + mode;
            

            // REGISTRY
            Switcher skeleton = new Switcher(1, "Hermes", mode);    // stub for accessing Switcher services
            LocateRegistry.createRegistry(port);                    // create a new registry
            Naming.rebind(url, skeleton);                           // register our stub as "Switcher"
            System.out.println("Switcher waiting on port " + port);

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
