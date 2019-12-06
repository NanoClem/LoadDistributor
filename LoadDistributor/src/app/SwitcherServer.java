package app;

import app.interfaces.SwitcherInterface;
import app.rmiobjects.Switcher;

import java.rmi.registry.LocateRegistry;
import java.rmi.Naming;


/**
 * @author NanoClem
 */
public class SwitcherServer {
    public static void main(String[] argv) {
        try {
        	// 10000 est le port sur lequel sera publié le service. Nous devons le préciser à la fois sur le registry et à la fois à la création du stub.
            SwitcherInterface skeleton = new Switcher();                  // génère un stub vers notre service.
            LocateRegistry.createRegistry(10000);                         // cree un nouveau registre
            Naming.rebind("rmi://localhost:10000/Switcher", skeleton);    // publie notre instance sous le nom "Switcher"
            System.out.println("Server waiting on port 10000");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
