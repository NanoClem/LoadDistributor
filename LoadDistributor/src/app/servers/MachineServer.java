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
            int n      = 2;
            int port   = 10000;
            String url = "rmi://localhost" + ":" + port + "/Machine";

            /* ---------------------------------------
                INITIALISING MACHINES
            --------------------------------------- */
            ArrayList<MachineInterface> mList = new ArrayList<MachineInterface>();
            for(int i=1 ; i<n+1; i++) {
                Machine m = new Machine(i, "");
                mList.add(m);
            }

            /* ---------------------------------------
                INITIALISING STUB
            --------------------------------------- */
            Registry registry = LocateRegistry.getRegistry(port);   // get existing registry
            SwitcherInterface stub = (SwitcherInterface) registry.lookup("Switcher");

            /* ---------------------------------------
                BIND MACHINES ON REGISTRY & SWITCHER
            --------------------------------------- */
            for(MachineInterface msklt : mList) {
                registry.rebind(url + "/" + msklt.getSurname(), msklt);     // bind on registry
                stub.addMachine(msklt);                                     // add it to the container in switcher
                System.out.println("Machine waiting on port 10000");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }
}