package app.servers;

import app.files.config.MyProperties;
import app.interfaces.SwitcherInterface;
import app.rmiobjects.Machine;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;



/**
 * @author NanoClem
 */
public class MachineServer {

    public static void main(String[] args) {
        try {
            /* -------------------------------
                LOAD PROPERTIES
            ------------------------------- */
            MyProperties prop = new MyProperties();
            
            /* -------------------------------
                MACHINE SERVER PARAMS
            ------------------------------- */
            int n      = Integer.parseInt(prop.getProperty("amount"));
            int port   = Integer.parseInt(prop.getProperty("port"));

            /* ---------------------------------------
                INITIALISING STUB
            --------------------------------------- */
            Registry registry = LocateRegistry.getRegistry(port);   // get existing registry
            SwitcherInterface stub = (SwitcherInterface) registry.lookup("Switcher");

            /* ---------------------------------------
                INITIALISING MACHINES AND BIND
            --------------------------------------- */
            for(int i=1 ; i<n+1; i++) {
                Machine m = new Machine(i, "");
                stub.addMachine(m);                                 // add it to the container in switcher
                System.out.println("Machine waiting on port " + port);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }
}