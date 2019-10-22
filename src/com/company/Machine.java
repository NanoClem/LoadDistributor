import java.rmi.Remote;

/**
 *
 * @author NanoClem
 * @version 1.0
 * @see java.rmi.Remote
 */
public interface Machine extends Remote {

  /**
   * Accede a un fichier et retourne son contenu
   * @param filename : chemin du fichier de lecture
   * @return : contenu du fichier sous forme de tableau d'octets
   */
  byte[] read(String filename) throws RemoteException;

  /**
   * Accede a un fichier et ecrit des octets a l'interieur
   * @param filename : chemin du fichier d'ecriture
   * @return : true si la lecture a reussi, false sinon
   */
  boolean write(String filename, byte[] data) throws RemoteException;
}
