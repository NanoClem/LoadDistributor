import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class SwitchServant extends UnicastRemoteObject implements SwitchService {


    protected SwitchServant() throws RemoteException {
        super();
    }

    @Override
    public String echo(String input) throws RemoteException {
        return input + "Connexion ";
    }
}
