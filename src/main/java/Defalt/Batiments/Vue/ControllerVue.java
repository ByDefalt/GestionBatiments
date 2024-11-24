package Defalt.Batiments.Vue;

import Defalt.Batiments.Facade.Campus;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.application.Application.launch;

public class ControllerVue extends Application {
    private Campus campus;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.campus = new Campus();
        campus.createBatiment("Batiment1","Usage1",100,true,10,5,50);
        ouvertureVueListeBatiments(primaryStage);
    }
    public void ouvertureVueListeBatiments(Stage primaryStage){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("interface_archi.fxml"));

        VueListeBatiments vueListeBatiments = new VueListeBatiments(campus,this);
        loader.setController(vueListeBatiments);
        try {
            Parent root=loader.load();
            primaryStage.setOnCloseRequest(event -> {
                campus.removeObserver(vueListeBatiments);
            });
            primaryStage.setTitle("Batiments");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void ouvertureVueBatiment(String nomBatiment){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("details_batiment.fxml"));

        VueBatiment vueBatiment = new VueBatiment(campus,this,nomBatiment);
        loader.setController(vueBatiment);
        try {
            Parent root=loader.load();
            Stage newStage = new Stage();
            newStage.setOnCloseRequest(event -> {
                campus.removeObserver(vueBatiment);
            });
            newStage.setTitle("Details Batiment");
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
