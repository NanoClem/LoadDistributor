package app;

import app.interfaces.MachineInterface;
import app.interfaces.SwitcherInterface;
import app.rmiobjects.Machine;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;



/**
 * @author NanoClem
 */
public class MachineServer {

    public static void main(String[] args) {
        try {
            // BIND A NEW MACHINE ON THE REGISTRY
            MachineInterface machineSkeleton = (MachineInterface) UnicastRemoteObject.exportObject(new Machine(1), 5000);
            Registry registry = LocateRegistry.getRegistry(10000);      // get the existing registry
            registry.rebind("Machine", machineSkeleton);
            
            // ADD IT TO THE LIST IN THE SWITCHER
            SwitcherInterface stub = (SwitcherInterface) registry.lookup("Switcher");
            stub.addMachine(machineSkeleton);
            System.out.println("Machine waiting on port 10000");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}