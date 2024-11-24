package Defalt.Batiments.Vue;

import Defalt.Batiments.Facade.Campus;
import Defalt.Batiments.Observer.Observer;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class VueListeBatiments implements Observer {
    private Campus campus;


    private ControllerVue controllerVue;

    public VueListeBatiments(Campus campus, ControllerVue controllerVue) {
        this.campus = campus;
        this.controllerVue = controllerVue;
        this.campus.addObserver(this);
    }

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
    private ListView listViewBatiments;
    @FXML
    private Button buttonCreeBatiment;

    private ObservableList<String> observableList = FXCollections.observableArrayList();

    public void initialize() {
        if (listViewBatiments != null) {
            listViewBatiments.setItems(observableList);
        }
        update();
    }


    private Stage formStage;
    @FXML
    private void openFormWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("interface_archi_form_add_batiment.fxml"));
            loader.setController(this); // Réutiliser le même contrôleur
            Parent formRoot = loader.load();
            loader.setController(this);
            if(formStage != null && formStage.isShowing()){
                formStage.close();
            }
            formStage = new Stage();
            formStage.setTitle("Ajouter un élément");
            formStage.setScene(new Scene(formRoot));
            formStage.show();
        } catch (IOException e) {
            showError("Erreur","Erreur","Erreur lors de l'ouverture de la fenetre");
        }
    }


    @Override
    public void update() {
        observableList.setAll(campus.getBatiments());
    }

    public void ajouterBatiment() {
        if(textFieldNomBatiment==null || textFieldNomBatiment.getText().isEmpty()){
            showError("Erreur","Nom du batiment","Le nom du batiment ne peut pas être vide");
            return;
        }
        if(textFieldUsageBatiment==null || textFieldUsageBatiment.getText().isEmpty()){
            showError("Erreur","Usage du batiment","L'usage du batiment ne peut pas être vide");
            return;
        }
        if(textFieldSurfacePiecesBatiment==null || textFieldSurfacePiecesBatiment.getText().isEmpty()){
            showError("Erreur","Surface du batiment","La surface du batiment ne peut pas être vide");
            return;
        }
        if(textFieldNombreBureauxBatiment==null || textFieldNombreBureauxBatiment.getText().isEmpty()){
            showError("Erreur","Nombre de bureaux","Le nombre de bureaux ne peut pas être vide");
            return;
        }
        if(textFieldNombreEtageBatiment==null || textFieldNombreEtageBatiment.getText().isEmpty()){
            showError("Erreur","Nombre d'étages","Le nombre d'étages ne peut pas être vide");
            return;
        }
        if(textFieldNombrePieceBatiment==null || textFieldNombrePieceBatiment.getText().isEmpty()){
            showError("Erreur","Nombre de pièces","Le nombre de pièces ne peut pas être vide");
            return;
        }
        if(!textFieldSurfacePiecesBatiment.getText().matches("[0-9]+")){
            showError("Erreur","Surface du batiment","La surface du batiment doit être un nombre entier positif");
            return;
        }
        if(!textFieldNombreBureauxBatiment.getText().matches("[0-9]++")){
            showError("Erreur","Nombre de bureaux","Le nombre de bureaux doit être un nombre entier positif");
            return;
        }
        if(!textFieldNombreEtageBatiment.getText().matches("[0-9]+")){
            showError("Erreur","Nombre d'étages","Le nombre d'étages doit être un nombre entier positif");
            return;
        }
        if(!textFieldNombrePieceBatiment.getText().matches("[0-9]+")){
            showError("Erreur","Nombre de pièces","Le nombre de pièces doit être un nombre entier positif");
            return;
        }
        boolean exists = campus.getBatiments().stream().anyMatch(i -> i.equals(textFieldNomBatiment.getText()));
        if (exists){
            showError("Erreur","Nom du batiment","Le nom du batiment existe déjà");
            return;
        }
        if(Integer.parseInt(textFieldNombreBureauxBatiment.getText()) > Integer.parseInt(textFieldNombrePieceBatiment.getText())){
            showError("Erreur","Nombre de bureaux","Le nombre de bureaux doit être inférieur ou égal au nombre de pièces");
            return;
        }


        campus.createBatiment(
                textFieldNomBatiment.getText()
                ,textFieldUsageBatiment.getText()
                ,Integer.parseInt(textFieldSurfacePiecesBatiment.getText())
                ,checkBoxNumerotationBatiment.isSelected()
                ,Integer.parseInt(textFieldNombreBureauxBatiment.getText())
                ,Integer.parseInt(textFieldNombreEtageBatiment.getText())
                ,Integer.parseInt(textFieldNombrePieceBatiment.getText()));


        Stage stage = (Stage) buttonCreeBatiment.getScene().getWindow();
        stage.close();
    }

    public void supprimerBatiment() {
        String selectedItem = (String) listViewBatiments.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            campus.destroyBatiment(selectedItem);
        }
    }

    public void afficherDetailsBatiment(){
        String selectedItem = (String) listViewBatiments.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            controllerVue.ouvertureVueBatiment(selectedItem);
        }
    }
    public void jsonToBatiments(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner un fichier");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Fichiers Json", "*.json")
        );
        Stage stage = (Stage) listViewBatiments.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile == null) {
            showError("Erreur","Erreur","Aucun fichier selectionné");
            return;
        }
        try {
            String res=campus.jsonToBatiments(selectedFile.getAbsolutePath());
            if(!res.isEmpty()){
                showError("Erreur","Erreur lecture fichier",res);
            }
        } catch (Exception e) {
            showError("Erreur","Erreur","Erreur lors de la lecture du fichier");
        }
        update();
    }

    public void fermerFenetreForm(){
        Stage stage = (Stage) textFieldNombreBureauxBatiment.getScene().getWindow();
        stage.close();
    }
    public void fermerFenetreMain(){
        Stage stage = (Stage) listViewBatiments.getScene().getWindow();
        stage.close();
    }
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
    public static void showError(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }
}
