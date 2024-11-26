package Defalt.Batiments.Observer;

/**
 * Interface représentant un observateur dans le modèle d'observateur.
 * Les classes implémentant cette interface doivent définir la méthode {@code update()},
 * qui sera appelée lorsque l'objet observable notifie ses observateurs d'un changement.
 */
public interface Observer {

    /**
     * Méthode appelée pour notifier l'observateur d'un changement d'état dans l'objet observable.
     * Les classes implémentant cette méthode définiront les actions spécifiques à effectuer lors de la notification.
     */
    void update();
}
