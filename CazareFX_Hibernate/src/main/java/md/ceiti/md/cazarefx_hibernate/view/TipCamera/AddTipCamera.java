package md.ceiti.md.cazarefx_hibernate.view.TipCamera;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import md.ceiti.md.cazarefx_hibernate.model.dao.CameraTipDAO;
import md.ceiti.md.cazarefx_hibernate.model.impl.CameraTipDAO_Impl;
import md.ceiti.md.cazarefx_hibernate.model.entity.CameraTip_Data;

public class AddTipCamera {

    @FXML
    private TextField nameField;

    @FXML
    private TextField descriereField;

    @FXML
    private TextField Nr_cameraField;

    @FXML
    private TextField ocupateField;

    @FXML
    private TextField disponibilaField;

   private CameraTipDAO cameraTipDAO = new CameraTipDAO_Impl();
    private TipCameraController tipCameraController;

    public void handleAddCamera(ActionEvent actionEvent) {
        try {
            String nume = nameField.getText();
            String descriere = descriereField.getText();
            String nr_camera = Nr_cameraField.getText();
            String ocupate = ocupateField.getText();
            String disponibile = disponibilaField.getText();


            if (nume.isEmpty() || descriere.isEmpty() || nr_camera.isEmpty() || ocupate.isEmpty() || disponibile.isEmpty()) {

                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Please fill all fields");
                alert.showAndWait();
                return;
            }

            CameraTip_Data newTipCamera = new CameraTip_Data(nume, descriere, nr_camera, ocupate, disponibile);
            cameraTipDAO.addCameraTip(newTipCamera);
            TipCameraController.addTipCamera(newTipCamera);

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Tip Camera added successfully");
            alert.showAndWait();

            nameField.clear();
            descriereField.clear();
            Nr_cameraField.clear();
            ocupateField.clear();
            disponibilaField.clear();

        } catch (NumberFormatException e) {
            // Handle invalid input (e.g., non-integer ID)
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid input");
            alert.setContentText("Please ensure all fields are correctly filled.");
            alert.showAndWait();
        } catch (Exception e) {
            // Handle other exceptions (e.g., database connection issues)
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to add Tip Camera");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}