package fr.debzoopman.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import fr.debzoopman.rmiInterfaces.SwitcherInterface;
import fr.debzoopman.rmiobj.Switcher;


/**
 * @author NanoClem
 */
public class SwitcherServer {
    public static void main(String[] argv) {
        try {
        	// 10000 est le port sur lequel sera publié le service. Nous devons le préciser à la fois sur le registry et à la fois à la création du stub.
            SwitcherInterface skeleton = (SwitcherInterface) UnicastRemoteObject.exportObject(new Switcher(), 10000);   // Génère un stub vers notre service.
            Registry registry = LocateRegistry.createRegistry(10000);
            registry.rebind("Switcher", skeleton);                      // publie notre instance sous le nom "Switcher"

            System.out.println("Server waiting on port 10000");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}