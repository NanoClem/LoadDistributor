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
            Machine m = new Machine(1);
            Registry registry = LocateRegistry.getRegistry(10000);      // recupere le registre existant
            SwitcherInterface stub = (SwitcherInterface) registry.lookup("Switcher");
            stub.addMachine(m);
            System.out.println("Machine waiting on port 10000");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}