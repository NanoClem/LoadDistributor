package fr.debzoopman.rmiinterfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface definissant les fonctions qu'une machine est capable de faire
 */
public interface MachineInterface extends Remote {

    /**
     * Lit et retourne le contenu du fichier passe en parametres
     * @param filename : chemin du fichier de lecture
     * @return : contenu du fichier sous forme de tableau d'octets
     * @throws RemoteException
     */
    public byte[] read(String filename) throws RemoteException;

    /**
     * Ecrit du contenu dans le fichier passe en parametre
     * @param filename : chemin du fichier d'ecriture
     * @param data : tableau d'octets a ecrire dans le fichier
     * @return : true si l'ecriture a reussi, false sinon
     * @throws RemoteException
     */
    public boolean write(String filename, byte[] data) throws RemoteException;
}
