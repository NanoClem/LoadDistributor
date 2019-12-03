import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {
    public static void main(String[] args) throws NotBoundException, MalformedURLException, RemoteException {
        SwitchService service = (SwitchService) Naming.lookup("rmi://localhost:5099/switch");
        System.out.println("-----"+ service.echo("Reponse : "));
    }
}
