import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SwitchService extends Remote {
    public String echo(String input) throws RemoteException;
}

