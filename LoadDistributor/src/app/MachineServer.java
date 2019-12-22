package app;

import app.interfaces.SwitcherInterface;
import app.rmiobjects.Machine;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;




/**
 * @author NanoClem
 */
public class MachineServer {

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry(10000);   // get existing registry

            // BIND A NEW MACHINE ON THE REGISTRY
            Machine machineSkeleton = new Machine(1, "Kave");
            registry.rebind("rmi://localhost:10000/Machine/" + machineSkeleton.getSurname(), machineSkeleton);
            
            // ADD IT TO THE LIST IN THE SWITCHER
            SwitcherInterface stub = (SwitcherInterface) registry.lookup("Switcher");
            stub.addMachine(machineSkeleton);
            System.out.println("Machine waiting on port 10000");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}