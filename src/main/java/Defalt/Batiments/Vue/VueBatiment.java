package Defalt.Batiments.Vue;

import Defalt.Batiments.Facade.Campus;
import Defalt.Batiments.Observer.Observer;
import Defalt.Batiments.Visiteur.Visiteur;
import Defalt.Batiments.Visiteur.VisiteurBureauSurface;
import Defalt.Batiments.Visiteur.VisiteurTypePiece;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;

import java.io.IOException;


public class VueBatiment implements Observer {
    private Campus campus;
    private String nomBatiment;

    private Visiteur visiteur;

    public VueBatiment(Campus campus,String nomBatiment) {
        this.campus = campus;
        this.nomBatiment = nomBatiment;
        this.visiteur = new VisiteurTypePiece();
        this.campus.addObserver(this);
    }

    @FXML
    private TreeView<String> treeView=new TreeView<>();
    @FXML
    private TextField textFieldRenameBatiment;
    @FXML
    private TextField textFielNumeroEtageUpdateBureauBatiment;
    @FXML
    private TextField textFielNumeroPieceUpdateBureauBatiment;

    public void initialize() {
        update();
    }

    public void setVisiteur(Visiteur visiteur) {
        this.visiteur = visiteur;
    }

    @Override
    public void update() {
        if(treeView.getRoot()!=null){
            treeView.getRoot().getChildren().clear();
        }
        treeView.setRoot(campus.afficherDetailsBatiment(nomBatiment,visiteur));
    }
    private Stage renameStage;
    public void ouvrirChangerNomBatiment() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("rename_batiment.fxml"));
            loader.setController(this);
            Parent formRoot = loader.load();
            if(renameStage != null && renameStage.isShowing()){
                renameStage.close();
            }
            renameStage = new Stage();
            renameStage.setTitle("Renomer le batiment");
            renameStage.setScene(new Scene(formRoot));
            renameStage.show();
        } catch (IOException e) {
            showError("Erreur","Erreur","Erreur lors de l'ouverture de la fenetre");
        }
    }
    public void changerNomBatiment() {
        if(textFieldRenameBatiment==null || textFieldRenameBatiment.getText().isEmpty()) {
            showError("Erreur","Erreur Nom","Le nom du batiment ne peut pas etre vide");
            return;
        }
        if(campus.getBatiments().contains(textFieldRenameBatiment.getText())){
            showError("Erreur","Erreur Nom","Le nom du batiment existe deja");
            return;
        }
        campus.updateNomBatiment(nomBatiment,textFieldRenameBatiment.getText());
        this.nomBatiment = textFieldRenameBatiment.getText();
        campus.notifyObservers();
        renameStage.close();

    }
    private Stage changerTypeStage;
    public void ouvrirChangerTypePiece() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("update_estbureau.fxml"));
            loader.setController(this);
            Parent formRoot = loader.load();
            if(changerTypeStage != null && changerTypeStage.isShowing()){
                changerTypeStage.close();
            }
            changerTypeStage = new Stage();
            changerTypeStage.setTitle("Changer type piece");
            changerTypeStage.setScene(new Scene(formRoot));
            changerTypeStage.show();
        } catch (IOException e) {
            showError("Erreur","Erreur","Erreur lors de l'ouverture de la fenetre");
        }
    }
    public void changerTypePiece() {
        if(textFielNumeroEtageUpdateBureauBatiment==null || textFielNumeroEtageUpdateBureauBatiment.getText().isEmpty()){
            showError("Erreur","Erreur","Le champ numero etage ne peut pas etre vide");
            return;
        }
        if(textFielNumeroPieceUpdateBureauBatiment==null || textFielNumeroPieceUpdateBureauBatiment.getText().isEmpty()){
            showError("Erreur","Erreur","Le champ numero piece ne peut pas etre vide");
            return;
        }
        if(textFielNumeroPieceUpdateBureauBatiment.getText().matches("[a-zA-Z]+")){
            showError("Erreur","Erreur","Le champ numero piece doit etre un nombre");
            return;
        }
        if (textFielNumeroEtageUpdateBureauBatiment.getText().matches("[a-zA-Z]+")){
            showError("Erreur","Erreur","Le champ numero etage doit etre un nombre");
            return;
        }
        campus.updatePieceEstBureau(nomBatiment
                ,Integer.parseInt(textFielNumeroEtageUpdateBureauBatiment.getText())
                ,Integer.parseInt(textFielNumeroPieceUpdateBureauBatiment.getText()));
        campus.notifyObservers();
        changerTypeStage.close();
    }

    public void affichageTypePiece() {
        setVisiteur(new VisiteurTypePiece());
        update();
    }
    public void affichageBureauSurface() {
        setVisiteur(new VisiteurBureauSurface());
        update();
    }
    public void fermerFenetreRename(){
        Stage stage = (Stage) textFieldRenameBatiment.getScene().getWindow();
        stage.close();
    }
    public void fermerFenetreUpdateBureau(){
        Stage stage = (Stage) textFielNumeroPieceUpdateBureauBatiment.getScene().getWindow();
        stage.close();
    }
    public void fermerFenetreMain(){
        Stage stage = (Stage) treeView.getScene().getWindow();
        stage.close();
    }
    public void showHelp() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Aide");
        alert.setHeaderText("Aide pour l'application");
        alert.setContentText("Pour afficher le menus faite click droit\n" +
                "Pour renommer un batiment, cliquez sur le bouton renommer batiment\n" +
                "Pour changer le type d'une piece, cliquez sur le bouton changer type piece\n" +
                "Pour afficher les types de pieces, cliquez sur le bouton afficher type piece\n" +
                "Pour afficher les bureaux et les surfaces, cliquez sur le bouton afficher bureau surface\n");

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

