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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Contrôleur de la vue pour afficher et gérer un bâtiment.
 * <p>
 * Cette classe gère l'affichage des détails d'un bâtiment dans une {@link TreeView},
 * permet de renommer le bâtiment, de changer le type des pièces et de naviguer à travers différents affichages
 * basés sur des visiteurs spécifiques (par exemple, pour afficher les types de pièces ou la surface des bureaux).
 * </p>
 */
public class VueBatiment implements Observer {
    private Campus campus;

    public String getNomBatiment() {
        return nomBatiment;
    }

    private String nomBatiment;
    private Visiteur visiteur;

    /**
     * Constructeur initialisant la vue avec un modèle {@link Campus} et un nom de bâtiment.
     *
     * @param campus      Le modèle représentant le campus.
     * @param nomBatiment Le nom du bâtiment à afficher.
     */
    public VueBatiment(Campus campus, String nomBatiment) {
        this.campus = campus;
        this.nomBatiment = nomBatiment;
        this.visiteur = new VisiteurTypePiece();
        this.campus.addObserver(this);
    }

    @FXML
    private TreeView<String> treeView = new TreeView<>();
    @FXML
    private TextField textFieldRenameBatiment;
    @FXML
    private TextField textFielNumeroEtageUpdateBureauBatiment;
    @FXML
    private TextField textFielNumeroPieceUpdateBureauBatiment;

    /**
     * Initialisation de la vue après l'injection des éléments FXML.
     * Met à jour l'affichage des détails du bâtiment.
     */
    public void initialize() {
        update();
    }

    /**
     * Modifie le visiteur utilisé pour afficher les détails du bâtiment.
     *
     * @param visiteur Le nouveau visiteur à utiliser.
     */
    public void setVisiteur(Visiteur visiteur) {
        this.visiteur = visiteur;
    }

    /**
     * Met à jour l'affichage des détails du bâtiment dans la {@link TreeView}.
     * Cette méthode est appelée chaque fois que le modèle change.
     */
    @Override
    public void update() {
        if (treeView.getRoot() != null) {
            treeView.getRoot().getChildren().clear();
        }
        treeView.setRoot(campus.afficherDetailsBatiment(nomBatiment, visiteur));
    }

    private Stage renameStage;

    /**
     * Ouvre une fenêtre permettant de renommer le bâtiment.
     */
    public void ouvrirChangerNomBatiment() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("rename_batiment.fxml"));
            loader.setController(this);
            Parent formRoot = loader.load();
            if (renameStage != null && renameStage.isShowing()) {
                renameStage.close();
            }
            renameStage = new Stage();
            renameStage.setTitle("Renommer le bâtiment");
            renameStage.setScene(new Scene(formRoot));
            renameStage.show();
        } catch (IOException e) {
            showError("Erreur", "Erreur", "Erreur lors de l'ouverture de la fenêtre");
        }
    }

    /**
     * Modifie le nom du bâtiment après une validation des entrées.
     */
    public void changerNomBatiment() {
        if (textFieldRenameBatiment == null || textFieldRenameBatiment.getText().isEmpty()) {
            showError("Erreur", "Erreur Nom", "Le nom du bâtiment ne peut pas être vide");
            return;
        }
        if (campus.getBatiments().contains(textFieldRenameBatiment.getText())) {
            showError("Erreur", "Erreur Nom", "Le nom du bâtiment existe déjà");
            return;
        }
        campus.updateNomBatiment(nomBatiment, textFieldRenameBatiment.getText());
        this.nomBatiment = textFieldRenameBatiment.getText();

        campus.notifyObservers();
        renameStage.close();
    }

    /**
     * Change le type de la pièce sélectionnée dans la {@link TreeView}.
     * La pièce sélectionnée doit être de type "Pièce" ou "Bureau".
     */
    public void changerTypePiece() {
        if (treeView.getRoot() != null) {
            if (treeView.getRoot().getChildren() != null) {
                if (treeView.getSelectionModel().getSelectedItem() != null) {
                    String selectedItem = treeView.getSelectionModel().getSelectedItem().getValue();
                    System.out.println(selectedItem);
                    Pattern pattern = Pattern.compile("(Pièce|Bureau)\\s*n°(\\d+)");
                    Matcher matcher = pattern.matcher(selectedItem);
                    if (matcher.find()) {
                        int numero = Integer.parseInt(matcher.group(2));
                        campus.updatePieceEstBureau(nomBatiment, numero);
                        campus.notifyObservers();
                    }
                }
            }
        }
    }

    /**
     * Affiche les pièces du bâtiment en utilisant le visiteur {@link VisiteurTypePiece}.
     */
    public void affichageTypePiece() {
        setVisiteur(new VisiteurTypePiece());
        update();
    }

    /**
     * Affiche les bureaux et leur surface en utilisant le visiteur {@link VisiteurBureauSurface}.
     */
    public void affichageBureauSurface() {
        setVisiteur(new VisiteurBureauSurface());
        update();
    }

    /**
     * Ferme la fenêtre de renommage du bâtiment.
     */
    public void fermerFenetreRename() {
        Stage stage = (Stage) textFieldRenameBatiment.getScene().getWindow();
        stage.close();
    }

    /**
     * Ferme la fenêtre principale affichant les détails du bâtiment.
     */
    public void fermerFenetreMain() {
        Stage stage = (Stage) treeView.getScene().getWindow();
        stage.close();
    }

    /**
     * Affiche une fenêtre d'aide pour l'utilisateur expliquant comment utiliser l'application.
     */
    public void showHelp() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Aide");
        alert.setHeaderText("Aide pour l'application");
        alert.setContentText("Pour afficher le menu, faites un clic droit.\n" +
                "Pour renommer un bâtiment, cliquez sur le bouton 'Renommer bâtiment'.\n" +
                "Pour changer le type d'une pièce, cliquez sur le bouton 'Changer type pièce'.\n" +
                "Pour afficher les types de pièces, cliquez sur le bouton 'Afficher type pièce'.\n" +
                "Pour afficher les bureaux et les surfaces, cliquez sur le bouton 'Afficher bureau surface'.");
        alert.showAndWait();
    }

    /**
     * Affiche un message d'erreur sous forme de fenêtre contextuelle.
     *
     * @param title   Le titre de la fenêtre.
     * @param header  Le header du message d'erreur.
     * @param content Le message d'erreur à afficher.
     */
    public static void showError(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
