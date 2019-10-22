import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


/**
 * @author NanoClem
 */
public class Client {
    public static void main(String[] argv) {
        try {
            Registry registry = LocateRegistry.getRegistry(10000);                       // acces au registre sur le port 10000
            SwitcherInterface stub = (SwitcherInterface) registry.lookup("Switcher");    // choix de l'instance > on recherche l'object "switcher" qui a été bind
            stub.check();                     // fonction appellee cote serveur

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
