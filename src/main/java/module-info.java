module Defalt.gestionbatiments {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;


    opens Defalt.Batiments.Vue to javafx.fxml;
    exports Defalt.Batiments.Vue;
    exports Defalt.Batiments.BatimentsMetiers;
}