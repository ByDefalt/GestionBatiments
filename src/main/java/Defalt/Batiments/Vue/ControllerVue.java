package Defalt.Batiments.Vue;

import Defalt.Batiments.Facade.Campus;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Classe principale contrôlant la vue de l'application.
 * <p>
 * Cette classe gère la navigation entre les différentes interfaces graphiques
 * de l'application, comme la liste des bâtiments et les détails d'un bâtiment spécifique.
 * Elle utilise JavaFX pour afficher les vues et gère les interactions utilisateur.
 * </p>
 */
public class ControllerVue extends Application {

    /**
     * Objet {@link Campus} représentant le modèle principal de l'application.
     * Il contient les bâtiments et permet d'appliquer le pattern observateur
     * pour la synchronisation des vues.
     */
    private Campus campus;

    /**
     * Point d'entrée de l'application JavaFX.
     * Initialise le modèle et affiche la vue principale de la liste des bâtiments.
     *
     * @param primaryStage la fenêtre principale de l'application.
     * @throws Exception si une erreur survient lors de l'initialisation.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.campus = new Campus();
        campus.createBatiment("Batiment 1", "Bat",10,true,10,10, 101);
        campus.createBatiment("Batiment 2", "Bat",10,false,10,10, 101);
        ouvertureVueListeBatiments(primaryStage);
    }

    /**
     * Ouvre la vue affichant la liste des bâtiments.
     *
     * @param primaryStage la fenêtre principale dans laquelle afficher la vue.
     */
    public void ouvertureVueListeBatiments(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("interface_archi.fxml"));

        VueListeBatiments vueListeBatiments = new VueListeBatiments(campus);
        loader.setController(vueListeBatiments);

        try {
            Parent root = loader.load();
            primaryStage.setOnCloseRequest(event -> campus.removeObserver(vueListeBatiments));
            vueListeBatiments.getMenuItemAfficherDetailsBatiment().setOnAction(event -> {
                String selectedItem = (String) vueListeBatiments.getListViewBatiments().getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    ouvertureVueBatiment(selectedItem);
                }
            });

            primaryStage.setTitle("Bâtiments");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ouvre une nouvelle fenêtre affichant les détails d'un bâtiment.
     *
     * @param nomBatiment le nom du bâtiment dont les détails doivent être affichés.
     */
    public void ouvertureVueBatiment(String nomBatiment) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("details_batiment.fxml"));

        VueBatiment vueBatiment = new VueBatiment(campus, nomBatiment);
        loader.setController(vueBatiment);

        try {
            Parent root = loader.load();
            Stage newStage = new Stage();
            newStage.setOnCloseRequest(event -> campus.removeObserver(vueBatiment));
            newStage.setTitle("Détails Bâtiment");
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode principale pour lancer l'application JavaFX.
     *
     * @param args les arguments de ligne de commande.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
