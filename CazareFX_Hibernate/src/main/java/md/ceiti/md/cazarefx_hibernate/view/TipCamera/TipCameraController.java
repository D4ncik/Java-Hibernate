package md.ceiti.md.cazarefx_hibernate.view.TipCamera;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import md.ceiti.md.cazarefx_hibernate.model.impl.CameraTipDAO_Impl;
import md.ceiti.md.cazarefx_hibernate.model.entity.CameraTip_Data;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class TipCameraController {

    @FXML
    private TableView<CameraTip_Data> tipcameraTable;

    @FXML
    private TableColumn<CameraTip_Data, Integer> idColumn;

    @FXML
    private TableColumn<CameraTip_Data, String> numeColumn;

    @FXML
    private TableColumn<CameraTip_Data, String> descriereColumn;

    @FXML
    private TableColumn<CameraTip_Data, String> nrcamereColumn;

    @FXML
    private TableColumn<CameraTip_Data, String> ocupateColumn;

    @FXML
    private TableColumn<CameraTip_Data, String> disponibileColumn;

    private static final ObservableList<CameraTip_Data> cameraList = FXCollections.observableArrayList();
    private CameraTipDAO_Impl cameraTipDAO = new CameraTipDAO_Impl(); // DAO instance

    public void initialize() {
        numeColumn.setCellValueFactory(new PropertyValueFactory<>("Nume"));
        descriereColumn.setCellValueFactory(new PropertyValueFactory<>("Descriere"));
        nrcamereColumn.setCellValueFactory(new PropertyValueFactory<>("Nr_camera"));
        ocupateColumn.setCellValueFactory(new PropertyValueFactory<>("ocupate"));
        disponibileColumn.setCellValueFactory(new PropertyValueFactory<>("disponibile"));

        tipcameraTable.setItems(cameraList);
        tipcameraTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        loadData(); // Load data from the DAO to populate the table
    }

    private void loadData() {
        try {
            // Fetch all camera types from the DAO
            List<CameraTip_Data> cameraTipList = cameraTipDAO.getAll();
            // Clear the existing data and add the fetched data
            cameraList.clear();
            cameraList.addAll(cameraTipList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void handleDelete() {
        CameraTip_Data cameraTipSelectat = tipcameraTable.getSelectionModel().getSelectedItem();

        if (cameraTipSelectat != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmare Ștergere");
            alert.setHeaderText("Doriți să ștergeți acest tip de cameră?");
            alert.setContentText("Această acțiune nu poate fi anulată.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    int rowsAffected = cameraTipDAO.deleteCameraTip(cameraTipSelectat);
                    if (rowsAffected > 0) {
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Succes");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("Tipul de cameră a fost șters cu succes!");
                        successAlert.showAndWait();

                        loadData();  // Reload the table data after deletion
                    } else {
                        Alert failureAlert = new Alert(Alert.AlertType.ERROR);
                        failureAlert.setTitle("Eroare");
                        failureAlert.setHeaderText("Ștergere nereușită");
                        failureAlert.setContentText("Tipul de cameră nu a fost găsit sau nu a putut fi șters.");
                        failureAlert.showAndWait();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();

                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Eroare");
                    errorAlert.setHeaderText("Eroare la ștergere");
                    errorAlert.setContentText("A apărut o eroare la ștergerea tipului de cameră.");
                    errorAlert.showAndWait();
                }
            }
        } else {
            Alert noSelectionAlert = new Alert(Alert.AlertType.WARNING);
            noSelectionAlert.setTitle("Selecție invalidă");
            noSelectionAlert.setHeaderText("Niciun tip de cameră selectat");
            noSelectionAlert.setContentText("Te rugăm să selectezi un tip de cameră din tabel pentru a-l șterge.");
            noSelectionAlert.showAndWait();
        }
    }

    public static void addTipCamera(CameraTip_Data tipCamera) {
        cameraList.add(tipCamera);
    }

    public void handleUpdate(ActionEvent actionEvent) {
        CameraTip_Data selectedTipCamera = tipcameraTable.getSelectionModel().getSelectedItem();

        if (selectedTipCamera != null) {
            try {
                // Actualizare nume
                TextInputDialog nameDialog = new TextInputDialog(selectedTipCamera.getNume());
                nameDialog.setTitle("Update Tip Camera");
                nameDialog.setHeaderText("Modifică numele tipului de cameră");
                nameDialog.setContentText("Nume nou:");
                Optional<String> resultName = nameDialog.showAndWait();
                resultName.ifPresent(selectedTipCamera::setNume);

                // Actualizare descriere
                TextInputDialog descriereDialog = new TextInputDialog(selectedTipCamera.getDescriere());
                descriereDialog.setTitle("Update Tip Camera");
                descriereDialog.setHeaderText("Modifică descrierea tipului de cameră");
                descriereDialog.setContentText("Descriere nouă:");
                Optional<String> resultDescriere = descriereDialog.showAndWait();
                resultDescriere.ifPresent(selectedTipCamera::setDescriere);

                // Actualizare număr camere
                TextInputDialog nrCamereDialog = new TextInputDialog(selectedTipCamera.getNr_camera());
                nrCamereDialog.setTitle("Update Tip Camera");
                nrCamereDialog.setHeaderText("Modifică numărul de camere");
                nrCamereDialog.setContentText("Număr camere nou:");
                Optional<String> resultNrCamere = nrCamereDialog.showAndWait();
                resultNrCamere.ifPresent(selectedTipCamera::setNr_camera);

                // Actualizare număr camere ocupate
                TextInputDialog ocupateDialog = new TextInputDialog(selectedTipCamera.getOcupate());
                ocupateDialog.setTitle("Update Tip Camera");
                ocupateDialog.setHeaderText("Modifică numărul de camere ocupate");
                ocupateDialog.setContentText("Număr camere ocupate nou:");
                Optional<String> resultOcupate = ocupateDialog.showAndWait();
                resultOcupate.ifPresent(selectedTipCamera::setOcupate);

                // Actualizare număr camere disponibile
                TextInputDialog disponibileDialog = new TextInputDialog(selectedTipCamera.getDisponibile());
                disponibileDialog.setTitle("Update Tip Camera");
                disponibileDialog.setHeaderText("Modifică numărul de camere disponibile");
                disponibileDialog.setContentText("Număr camere disponibile nou:");
                Optional<String> resultDisponibile = disponibileDialog.showAndWait();
                resultDisponibile.ifPresent(selectedTipCamera::setDisponibile);
                int rowsAffected = cameraTipDAO.updateCameraTip(selectedTipCamera);
                if (rowsAffected > 0) {
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Succes");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Tipul de cameră a fost actualizat cu succes!");
                    successAlert.showAndWait();

                    loadData();
                } else {
                    Alert failureAlert = new Alert(Alert.AlertType.ERROR);
                    failureAlert.setTitle("Eroare");
                    failureAlert.setHeaderText("Actualizare nereușită");
                    failureAlert.setContentText("Tipul de cameră nu a putut fi actualizat.");
                    failureAlert.showAndWait();
                }

            } catch (SQLException e) {
                e.printStackTrace();
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Eroare");
                errorAlert.setHeaderText("Eroare la actualizare");
                errorAlert.setContentText("A apărut o eroare la actualizarea tipului de cameră.");
                errorAlert.showAndWait();
            }
        } else {
            Alert noSelectionAlert = new Alert(Alert.AlertType.WARNING);
            noSelectionAlert.setTitle("Selecție invalidă");
            noSelectionAlert.setHeaderText("Niciun tip de cameră selectat");
            noSelectionAlert.setContentText("Te rugăm să selectezi un tip de cameră din tabel pentru a-l actualiza.");
            noSelectionAlert.showAndWait();
        }
    }

}
