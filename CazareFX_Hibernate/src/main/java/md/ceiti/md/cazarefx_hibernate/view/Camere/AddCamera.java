package md.ceiti.md.cazarefx_hibernate.view.Camere;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import md.ceiti.md.cazarefx_hibernate.model.dao.CameraDAO;
import md.ceiti.md.cazarefx_hibernate.model.entity.Camera_Data;
import md.ceiti.md.cazarefx_hibernate.model.impl.CameraDAO_Impl;

import java.sql.SQLException;

public class AddCamera {

    @FXML
    private TextField nameField;

    @FXML
    private ComboBox<String> tipcameraComboBox;

    @FXML
    private ComboBox<String> statutComboBox;

    @FXML
    private ComboBox<String> stareaComboBox;

    @FXML
    private TextField pretField;

    private CameraDAO camereDAO = new CameraDAO_Impl();

    public void handleAddCamera(ActionEvent actionEvent) {
        String name = nameField.getText();
        String tipcamera = tipcameraComboBox.getValue();
        String statut = statutComboBox.getValue();
        String starea = stareaComboBox.getValue();
        String pretText = pretField.getText();

        if (name.isEmpty() || tipcamera == null || statut == null || starea == null || pretText.isEmpty()) {
            showAlert(AlertType.ERROR, "Error", "Please fill all fields");
            return;
        }

        Camera_Data newCamera = new Camera_Data(name, tipcamera, statut, starea, pretText);

        try {
            int result = camereDAO.add(newCamera);
            if (result == 1) {
                showAlert(AlertType.INFORMATION, "Success", "Camera added successfully");
                clearFields();
            } else {
                showAlert(AlertType.ERROR, "Error", "Failed to add camera.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Error", "Failed to add camera: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        nameField.clear();
        tipcameraComboBox.setValue(null);
        statutComboBox.setValue(null);
        stareaComboBox.setValue(null);
        pretField.clear();
    }
}
