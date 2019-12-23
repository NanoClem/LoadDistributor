package app;

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
            // SERVER PARAMS
            Registry registry = LocateRegistry.getRegistry(10000);   // get existing registry
            int port          = 10000;
            String url        = "rmi://localhost" + ":" + port + "/Machine"; 

            // BIND A NEW MACHINE ON THE REGISTRY
            Machine machineSkeleton = new Machine(1, "Kave");
            registry.rebind(url + "/" + machineSkeleton.getSurname(), machineSkeleton);
            
            // ADD IT TO THE LIST IN THE SWITCHER
            SwitcherInterface stub = (SwitcherInterface) registry.lookup("Switcher");
            stub.addMachine(machineSkeleton);
            System.out.println("Machine waiting on port 10000");
            
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }
}