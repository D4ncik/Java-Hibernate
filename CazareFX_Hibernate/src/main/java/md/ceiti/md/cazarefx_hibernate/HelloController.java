package md.ceiti.md.cazarefx_hibernate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;


import java.io.IOException;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private BorderPane mainPanel;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    // Exemple de încărcare a altor feronțe în panoul central
    public void handleDataCazari(ActionEvent actionEvent) {
        loadData("DataCazari.fxml");
    }

    public void handleDataClienti(ActionEvent actionEvent) {
        loadData("DataClienti.fxml");
    }

    private void loadData(String fxmlFile) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = fxmlLoader.load();
            mainPanel.setCenter(root); // Înlocuiește conținutul panoului principal
        } catch (IOException e) {
            showErrorDialog("Error loading " + fxmlFile);
        }
    }

    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Loading Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
}