package Defalt.Batiments.Vue;

import Defalt.Batiments.Facade.Campus;
import Defalt.Batiments.Observer.Observer;

import java.io.File;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Contrôleur de la vue pour afficher la liste des bâtiments.
 * <p>
 * Cette classe gère la liste des bâtiments dans l'interface utilisateur, permet l'ajout, la suppression et
 * l'importation de bâtiments depuis un fichier JSON. Elle met à jour la vue en fonction des changements du modèle {@link Campus}.
 * </p>
 */
public class VueListeBatiments implements Observer {

    /**
     * Le modèle représentant le campus et ses bâtiments.
     */
    private Campus campus;

    /**
     * Champs de la vue pour afficher les informations d'un bâtiment.
     */
    @FXML
    private TextField textFieldNomBatiment;
    @FXML
    private TextField textFieldUsageBatiment;
    @FXML
    private TextField textFieldNombreBureauxBatiment;
    @FXML
    private TextField textFieldNombreEtageBatiment;
    @FXML
    private TextField textFieldNombrePieceBatiment;
    @FXML
    private TextField textFieldSurfacePiecesBatiment;
    @FXML
    private CheckBox checkBoxNumerotationBatiment;
    @FXML
    private Button buttonCreeBatiment;

    /**
     * Liste des bâtiments affichée dans l'interface utilisateur.
     */
    @FXML
    private ListView listViewBatiments;



    public ListView getListViewBatiments() {
        return listViewBatiments;
    }
    /**
     * Menu permettant d'afficher les détails d'un bâtiment.
     */
    @FXML
    private MenuItem menuItemAfficherDetailsBatiment;



    public MenuItem getMenuItemAfficherDetailsBatiment() {
        return menuItemAfficherDetailsBatiment;
    }


    /**
     * Liste observable des bâtiments à afficher dans la {@link ListView}.
     */
    private ObservableList<String> observableList = FXCollections.observableArrayList();

    /**
     * Constructeur initialisant la vue avec le modèle {@link Campus}.
     *
     * @param campus le modèle représentant le campus.
     */
    public VueListeBatiments(Campus campus) {
        this.campus = campus;
        this.campus.addObserver(this);
    }

    /**
     * Initialisation de la vue après l'injection des éléments FXML.
     * Met à jour la liste des bâtiments affichée.
     */
    public void initialize() {
        if (listViewBatiments != null) {
            listViewBatiments.setItems(observableList);
        }
        update();
    }

    /**
     * Met à jour la liste des bâtiments affichée dans la vue.
     * Cette méthode est appelée lorsque le modèle change.
     */
    @Override
    public void update() {
        observableList.setAll(campus.getBatiments());
    }
    /**
     * Fenêtre de formulaire pour ajouter un nouveau bâtiment.
     */
    private Stage formStageAddBatiment;
    /**
     * Ouvre la fenêtre permettant d'ajouter un nouveau bâtiment.
     */
    @FXML
    public void openFormWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("interface_archi_form_add_batiment.fxml"));
            loader.setController(this); // Réutiliser le même contrôleur
            Parent formRoot = loader.load();
            if(formStageAddBatiment != null && formStageAddBatiment.isShowing()){
                formStageAddBatiment.close();
            }
            formStageAddBatiment = new Stage();
            formStageAddBatiment.setTitle("Ajouter un élément");
            formStageAddBatiment.setScene(new Scene(formRoot));
            formStageAddBatiment.show();
        } catch (IOException e) {
            showError("Erreur", "Erreur", "Erreur lors de l'ouverture de la fenêtre");
        }
    }

    /**
     * Ajoute un bâtiment en utilisant les informations saisies par l'utilisateur.
     * Effectue des vérifications pour s'assurer que les champs sont valides.
     */
    public void ajouterBatiment() {
        if (textFieldNomBatiment == null || textFieldNomBatiment.getText().isEmpty()) {
            showError("Erreur", "Nom du bâtiment", "Le nom du bâtiment ne peut pas être vide");
            return;
        }
        if (textFieldUsageBatiment == null || textFieldUsageBatiment.getText().isEmpty()) {
            showError("Erreur", "Usage du bâtiment", "L'usage du bâtiment ne peut pas être vide");
            return;
        }
        if (textFieldSurfacePiecesBatiment == null || textFieldSurfacePiecesBatiment.getText().isEmpty()) {
            showError("Erreur", "Surface du bâtiment", "La surface du bâtiment ne peut pas être vide");
            return;
        }
        if (textFieldNombreBureauxBatiment == null || textFieldNombreBureauxBatiment.getText().isEmpty()) {
            showError("Erreur", "Nombre de bureaux", "Le nombre de bureaux ne peut pas être vide");
            return;
        }
        if (textFieldNombreEtageBatiment == null || textFieldNombreEtageBatiment.getText().isEmpty()) {
            showError("Erreur", "Nombre d'étages", "Le nombre d'étages ne peut pas être vide");
            return;
        }
        if (textFieldNombrePieceBatiment == null || textFieldNombrePieceBatiment.getText().isEmpty()) {
            showError("Erreur", "Nombre de pièces", "Le nombre de pièces ne peut pas être vide");
            return;
        }

        if (!textFieldSurfacePiecesBatiment.getText().matches("[1-9][0-9]*")) {
            showError("Erreur", "Surface du bâtiment", "La surface du bâtiment doit être un nombre entier positif");
            return;
        }
        if (!textFieldNombreBureauxBatiment.getText().matches("[0-9]*")) {
            showError("Erreur", "Nombre de bureaux", "Le nombre de bureaux doit être un nombre entier positif ou 0");
            return;
        }
        if (!textFieldNombreEtageBatiment.getText().matches("[1-9][0-9]*")) {
            showError("Erreur", "Nombre d'étages", "Le nombre d'étages doit être un nombre entier positif");
            return;
        }
        if (!textFieldNombrePieceBatiment.getText().matches("[1-9][0-9]*")) {
            showError("Erreur", "Nombre de pièces", "Le nombre de pièces doit être un nombre entier positif");
            return;
        }
        boolean exists = campus.getBatiments().stream().anyMatch(i -> i.equals(textFieldNomBatiment.getText()));
        if (exists) {
            showError("Erreur", "Nom du bâtiment", "Le nom du bâtiment existe déjà");
            return;
        }
        if (Integer.parseInt(textFieldNombreBureauxBatiment.getText()) > Integer.parseInt(textFieldNombrePieceBatiment.getText())) {
            showError("Erreur", "Nombre de bureaux", "Le nombre de bureaux doit être inférieur ou égal au nombre de pièces");
            return;
        }

        // Création du bâtiment
        campus.createBatiment(
                textFieldNomBatiment.getText(),
                textFieldUsageBatiment.getText(),
                Integer.parseInt(textFieldSurfacePiecesBatiment.getText()),
                checkBoxNumerotationBatiment.isSelected(),
                Integer.parseInt(textFieldNombreBureauxBatiment.getText()),
                Integer.parseInt(textFieldNombreEtageBatiment.getText()),
                Integer.parseInt(textFieldNombrePieceBatiment.getText()));

        campus.notifyObservers();

        Stage stage = (Stage) buttonCreeBatiment.getScene().getWindow();
        stage.close();
    }

    /**
     * Supprime un bâtiment sélectionné dans la liste.
     */
    public void supprimerBatiment() {
        String selectedItem = (String) listViewBatiments.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            campus.destroyBatiment(selectedItem);
            campus.notifyObservers();
        }
    }

    /**
     * Ouvre une boîte de dialogue pour importer un fichier JSON et charger les bâtiments depuis ce fichier.
     */
    public void jsonToBatiments() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner un fichier");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers JSON", "*.json"));
        Stage stage = (Stage) listViewBatiments.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile == null) {
            showError("Erreur", "Erreur", "Aucun fichier sélectionné");
            return;
        }

        try {
            String res = campus.jsonToBatiments(selectedFile.getAbsolutePath());
            if (!res.isEmpty()) {
                showError("Erreur", "Erreur certain Batiments son incorrecte : \nLes Etages et Pieces doivent etre dans l'ordre en commencant par\n 0 ou 1 (pour les Pieces)\n 0 (pour les Etages)", res);
            }
            campus.notifyObservers();
        } catch (Exception e) {
            showError("Erreur", "Erreur", "Erreur lors de la lecture du fichier");
        }
    }

    /**
     * Ferme la fenêtre de formulaire d'ajout de bâtiment.
     */
    public void fermerFenetreForm() {
        Stage stage = (Stage) textFieldNombreBureauxBatiment.getScene().getWindow();
        stage.close();
    }

    /**
     * Ferme la fenêtre principale affichant la liste des bâtiments.
     */
    public void fermerFenetreMain() {
        Stage stage = (Stage) listViewBatiments.getScene().getWindow();
        stage.close();
    }

    /**
     * Affiche une fenêtre d'aide pour l'utilisateur.
     */
    public void showHelp() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Aide");
        alert.setHeaderText("Aide pour l'application");
        alert.setContentText("Pour afficher le menus faite click droit\n" +
                "Pour ajouter un batiment faite click droit puis ajouter\n" +
                "Pour supprimer un batiment selectioner le et faite click droit puis supprimer de meme pour afficher les details\n" +
                "Pour importer un fichier json faite File puis charger Batiments\n");
        alert.showAndWait();
    }

    /**
     * Affiche un message d'erreur sous forme de fenêtre contextuelle.
     *
     * @param titre   Le titre de la fenêtre.
     * @param header  Le header du message d'erreur.
     * @param message Le message d'erreur à afficher.
     */
    private void showError(String titre, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
