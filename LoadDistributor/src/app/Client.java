package app;

import app.interfaces.SwitcherInterface;

import java.util.Scanner;
import java.rmi.RemoteException;
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
            // INITIALISING SWITCHER
            Registry registry = LocateRegistry.getRegistry(10000);     // acces au registre sur le port 10000
            SwitcherInterface stub = (SwitcherInterface) registry.lookup("Switcher");    // recupere l'objet Switcher dans le registre
            stub.check();                                              // test affiche cote serveur

            // TESTS
            System.out.println("TEST 1");
            test1(stub);    // test 1 : say Hello         
            System.out.println("TEST 2");
            test2(stub);    // test 2 : print connected machines 

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Test 1 : say hello
     * @param s
     * @throws RemoteException
     */
    public static void test1(SwitcherInterface s) throws RemoteException {
        Scanner sc = new Scanner(System.in);
        System.out.println("What's your name ?");
        String input = sc.nextLine();
        sc.close();
        System.out.println(s.hello(input));
    }

    /**
     * Test 2 : print connected machines
     * @param s
     * @throws RemoteException
    */
    public static void test2(SwitcherInterface s) throws RemoteException {
        System.out.println(s.getMachines());
    } 
}
