package fr.debzoopman.rmiinterfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 *
 */
public interface ControlInterface extends Remote {

  /**
     * Ajoute une machine a la liste des machines connectees
     * @param m : machine a ajouter
     * @throws RemoteException
     */
    public void addMachine(Machine m) throws RemoteException;

    /**
     * Supprime une machine de la liste des machines connectees
     * @param m : machine a supprimer
     * @throws RemoteException
     */
    public void removeMachine(Machine m) throws RemoteException;
}
