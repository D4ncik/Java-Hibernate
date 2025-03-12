package md.ceiti.md.cazarefx_hibernate.view.Clienti;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import md.ceiti.md.cazarefx_hibernate.model.impl.ClientiDAO_Impl;
import md.ceiti.md.cazarefx_hibernate.model.entity.Clienti_Data;

public class AddClient {

    @FXML
    private TextField nameField;

    @FXML
    private TextField telefonField;

    @FXML
    private TextField emailField;

    @FXML
    private ComboBox<String> genComboBox; // ComboBox for Gen

    @FXML
    private ComboBox<String> categorieComboBox; // ComboBox for Categorie

    private DataClientController dataClientiController;
    private ClientiDAO_Impl clientDAO = new ClientiDAO_Impl(); // DAO instance for inserting client

    public void setDataClientiController(DataClientController dataClientiController) {
        this.dataClientiController = dataClientiController;
    }

    public void handleAddClient(ActionEvent actionEvent) {
        String name = nameField.getText();
        String telefon = telefonField.getText();
        String email = emailField.getText();
        String gen = genComboBox.getValue(); // Get selected value from genComboBox
        String categorie = categorieComboBox.getValue(); // Get selected value from categorieComboBox

        // Validare date
        if (name.isEmpty() || telefon.isEmpty() || email.isEmpty() || gen == null || categorie == null) {
            // If any required field is empty or ComboBox is not selected, show error
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please fill all fields");
            alert.showAndWait();
            return;
        }

        // Create a new client object and insert it into the database
        Clienti_Data newClient = new Clienti_Data(name, telefon, email, gen, categorie);

        try {

            clientDAO.insertClient(newClient);
            dataClientiController.addClient(newClient);

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Client added successfully");
            alert.showAndWait();

            nameField.clear();
            telefonField.clear();
            emailField.clear();
            genComboBox.setValue(null);
            categorieComboBox.setValue(null);

        } catch (Exception e) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Client added successfully");
            alert.showAndWait();
        }
    }
}
