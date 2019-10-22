package fr.debzoopman.client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
Dadou tu dois faire :
 renvoiyer la reponse au client
  fait : Client ==> Switcher ===> Serveur
  a faire : Serveur ====> client
*/
/**
 * @author NanoClem
 */
public class Client {
    public static void main(String[] argv) {
        try {
            Registry registry = LocateRegistry.getRegistry(10000);                       // acces au registre sur le port 10000
            SwitcherInterface stub = (SwitcherInterface) registry.lookup("Switcher");    // choix de l'instance > on recherche l'object "switcher" qui a été bind
            stub.check();                                                                // test affiche cote serveur
            System.out.println(stub.hello("David"));                                     // EDIT : bon, mais c'est le serveur Machine qui doit renvoyer le resultat

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
