package app.servers;

import app.interfaces.MachineInterface;
import app.interfaces.SwitcherInterface;
import app.rmiobjects.Machine;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;



/**
 * @author NanoClem
 */
public class MachineServer {

    public static void main(String[] args) {
        try {
            /* -------------------------------
                MACHINE SERVER PARAMS
            ------------------------------- */
            int port          = 10000;
            String url        = "rmi://localhost" + ":" + port + "/Machine";

            /* ---------------------------------------
                INITIALISING MACHINES
            --------------------------------------- */
            // MACHINES
            Machine m1 = new Machine(1, "Kave");
            Machine m2 = new Machine(2, "Seb");
            // Machine m3 = new Machine(3, "Sorana");
            // MACHINE LIST
            ArrayList<MachineInterface> mList = new ArrayList<MachineInterface>();
            mList.add(m1);
            mList.add(m2);
            // mList.add(m3);

            /* ---------------------------------------
                INITIALISING STUB
            --------------------------------------- */
            Registry registry = LocateRegistry.getRegistry(port);   // get existing registry
            SwitcherInterface stub = (SwitcherInterface) registry.lookup("Switcher");

            /* ---------------------------------------
                BIND MACHINES ON REGISTRY & SWITCHER
            --------------------------------------- */
            for(MachineInterface msklt : mList) {
                // BIND ON REGISTRY
                registry.rebind(url + "/" + msklt.getSurname(), msklt);
                // ADD IT TO THE LIST IN THE SWITCHER
                stub.addMachine(msklt);
                System.out.println("Machine waiting on port 10000");
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }
}