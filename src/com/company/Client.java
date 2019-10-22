import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


/**
 * @author NanoClem
 */
public class Client {
    public static void main(String[] argv) {
        try {
            Registry registry = LocateRegistry.getRegistry(10000);        // acces au registre
            AddInterface stub = (AddInterface) registry.lookup("Add");    // choix de l'instance
            // System.out.println(stub.add(1, 2)); // Affiche 3           // fonction appellee cote serveur
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
