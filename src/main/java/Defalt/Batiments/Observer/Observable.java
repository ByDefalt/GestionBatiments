package Defalt.Batiments.Observer;

/**
 * Interface définissant les méthodes nécessaires pour implémenter un modèle d'observateur.
 * Les classes implémentant cette interface permettent d'enregistrer, de supprimer et de notifier des observateurs.
 */
public interface Observable {

    /**
     * Ajoute un observateur à la liste des observateurs.
     *
     * @param observer l'observateur à ajouter.
     */
    void addObserver(Observer observer);

    /**
     * Supprime un observateur de la liste des observateurs.
     *
     * @param observer l'observateur à supprimer.
     */
    void removeObserver(Observer observer);

    /**
     * Notifie tous les observateurs enregistrés d'un changement d'état.
     */
    void notifyObservers();
}
