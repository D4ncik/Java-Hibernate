package md.ceiti.md.cazarefx_hibernate.view.Camere;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import md.ceiti.md.cazarefx_hibernate.model.dao.*;
import md.ceiti.md.cazarefx_hibernate.model.entity.*;
import md.ceiti.md.cazarefx_hibernate.model.impl.CameraDAO_Impl;
import md.ceiti.md.cazarefx_hibernate.model.impl.CazariDAO_Impl;
import md.ceiti.md.cazarefx_hibernate.view.Cazari.CazariController;


import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CameraController {
    @FXML
    private TableView<Camera_Data> cameraTable;

    private Clienti_Data clientSelectat;
    @FXML
    private TextField clientNameField;

    @FXML
    private TextField roomNumberField;

    @FXML
    private DatePicker checkInDate;

    @FXML
    private DatePicker checkOutDate;
    @FXML
    private TableColumn<Camera_Data, String> numeColumn;

    @FXML
    private TableColumn<Camera_Data, String> tipcameraColumn;

    @FXML
    private TableColumn<Camera_Data, String> statutColumn;

    @FXML
    private TableColumn<Camera_Data, String> stareaColumn;

    @FXML
    private TableColumn<Camera_Data, String> pretColumn;
    @FXML
    private TextField searchField;
    private CazariController cazariController;
    private CameraDAO cameraDAO = new CameraDAO_Impl();

    private static final ObservableList<Camera_Data> cameraList = FXCollections.observableArrayList();

   private CazariDAO cazariDAO = new CazariDAO_Impl();

    public void initialize() {
        cazariController =new CazariController();
        numeColumn.setCellValueFactory(new PropertyValueFactory<>("nume"));
        tipcameraColumn.setCellValueFactory(new PropertyValueFactory<>("tip_camera"));
        statutColumn.setCellValueFactory(new PropertyValueFactory<>("statut"));
        stareaColumn.setCellValueFactory(new PropertyValueFactory<>("starea"));
        pretColumn.setCellValueFactory(new PropertyValueFactory<>("pret"));
        cameraTable.setItems(cameraList);
        cameraTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        loadCameraData();
    }

    private void loadCameraData() {
        try {
            List<Camera_Data> cameras = cameraDAO.getAll();
            cameraList.setAll(cameras);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        FilteredList<Camera_Data> filteredData = new FilteredList<>(cameraList, p -> true);


        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(camera -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (camera.getNume().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (camera.getTip_camera().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (camera.getStatut().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (camera.getStarea().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }

                return false;
            });
        });
        cameraTable.setItems(filteredData);
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void handleDelete() {
        Camera_Data cameraSelectat = cameraTable.getSelectionModel().getSelectedItem();

        if (cameraSelectat != null) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmare Ștergere");
            alert.setHeaderText("Doriți să ștergeți această cameră?");
            alert.setContentText("Această acțiune nu poate fi anulată.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    int rowsAffected = cameraDAO.delete(cameraSelectat);
                    if (rowsAffected > 0) {

                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Succes");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("Camera a fost ștearsă cu succes!");
                        successAlert.showAndWait();

                        loadCameraData();
                    } else {

                        Alert failureAlert = new Alert(Alert.AlertType.ERROR);
                        failureAlert.setTitle("Eroare");
                        failureAlert.setHeaderText("Ștergere nereușită");
                        failureAlert.setContentText("Camera nu a fost găsită sau nu a putut fi ștearsă.");
                        failureAlert.showAndWait();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Eroare");
                    errorAlert.setHeaderText("Eroare la ștergere");
                    errorAlert.setContentText("A apărut o eroare la ștergerea camerei.");
                    errorAlert.showAndWait();
                }
            }
        } else {

            Alert noSelectionAlert = new Alert(Alert.AlertType.WARNING);
            noSelectionAlert.setTitle("Selecție invalidă");
            noSelectionAlert.setHeaderText("Nicio cameră selectată");
            noSelectionAlert.setContentText("Te rugăm să selectezi o cameră din tabel pentru a o șterge.");
            noSelectionAlert.showAndWait();
        }
    }

    public static void addCamera(Camera_Data camera) {
        cameraList.add(camera);
    }

    public void handleSubmitAction(ActionEvent actionEvent) throws SQLException {
        String clientName = clientNameField.getText();
        String roomNumberText = roomNumberField.getText();
        int roomNumber = Integer.parseInt(roomNumberText);
        LocalDate checkIn = checkInDate.getValue();
        LocalDate checkOut = checkOutDate.getValue();

        if (clientName.isEmpty() || checkIn == null || checkOut == null) {
            showAlert("Eroare", "Toate câmpurile trebuie completate!");
            return;
        }

        long numberOfDays = ChronoUnit.DAYS.between(checkIn, checkOut);

        if (numberOfDays <= 0) {
            showAlert("Eroare", "Data de check-out trebuie să fie mai mare decât data de check-in!");
            return;
        }

        Camera_Data selectedRoom = cameraTable.getSelectionModel().getSelectedItem();

        if (selectedRoom == null) {
            showAlert("Eroare", "Selectează o cameră din tabel!");
            return;
        }
        String numeCamera = selectedRoom.getNume();
        String statutCamera = selectedRoom.getStatut();
        String pretCamera = selectedRoom.getPret();
        Cazari_Data newCazare = new Cazari_Data(clientName, numeCamera, roomNumber, statutCamera, checkIn.toString(), checkOut.toString(), pretCamera, (int) numberOfDays);

        cazariDAO.addCazari(newCazare);
       // cazariController.addCazare(newCazare);

        loadCameraData();
    }

    public void handleUpdate(ActionEvent actionEvent) {
        Camera_Data selectedCamera = cameraTable.getSelectionModel().getSelectedItem();

        if (selectedCamera != null) {
            try {
                TextInputDialog nameDialog = new TextInputDialog(selectedCamera.getNume());
                nameDialog.setTitle("Update Camera");
                nameDialog.setHeaderText("Modifică numele camerei");
                nameDialog.setContentText("Nume nou:");
                Optional<String> resultName = nameDialog.showAndWait();

                resultName.ifPresent(selectedCamera::setNume);

                TextInputDialog tipCameraDialog = new TextInputDialog(selectedCamera.getTip_camera());
                tipCameraDialog.setTitle("Update Camera");
                tipCameraDialog.setHeaderText("Modifică tipul camerei");
                tipCameraDialog.setContentText("Tip nou:");
                Optional<String> resultTipCamera = tipCameraDialog.showAndWait();

                resultTipCamera.ifPresent(selectedCamera::setTip_camera);

                List<String> statutOptions = Arrays.asList("Very Good", "Good", "VIP");
                ChoiceDialog<String> statutDialog = new ChoiceDialog<>(selectedCamera.getStatut(), statutOptions);
                statutDialog.setTitle("Update Camera");
                statutDialog.setHeaderText("Modifică statutul camerei");
                statutDialog.setContentText("Alege statutul:");
                Optional<String> resultStatut = statutDialog.showAndWait();

                resultStatut.ifPresent(selectedCamera::setStatut);

                List<String> statutOptions2 = Arrays.asList("disponibil", "indisponibil");
                ChoiceDialog<String> stareaDialog = new ChoiceDialog<>(selectedCamera.getStatut(), statutOptions2);
                stareaDialog.setTitle("Update Camera");
                stareaDialog.setHeaderText("Modifică starea camerei");
                stareaDialog.setContentText("Stare nouă:");
                Optional<String> resultStarea = stareaDialog.showAndWait();

                resultStarea.ifPresent(selectedCamera::setStarea);

                TextInputDialog pretDialog = new TextInputDialog(String.valueOf(selectedCamera.getPret()));
                pretDialog.setTitle("Update Camera");
                pretDialog.setHeaderText("Modifică prețul camerei");
                pretDialog.setContentText("Preț nou:");
                Optional<String> resultPret = pretDialog.showAndWait();

                resultPret.ifPresent(selectedCamera::setPret);
                int rowsAffected = cameraDAO.update(selectedCamera);
                if (rowsAffected > 0) {
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Succes");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Camera a fost actualizată cu succes!");
                    successAlert.showAndWait();
                    loadCameraData();
                } else {
                    Alert failureAlert = new Alert(Alert.AlertType.ERROR);
                    failureAlert.setTitle("Eroare");
                    failureAlert.setHeaderText("Actualizare nereușită");
                    failureAlert.setContentText("Camera nu a fost găsită sau nu a putut fi actualizată.");
                    failureAlert.showAndWait();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Eroare");
                errorAlert.setHeaderText("Eroare la actualizare");
                errorAlert.setContentText("A apărut o eroare la actualizarea camerei.");
                errorAlert.showAndWait();
            } catch (NumberFormatException e) {
                Alert formatAlert = new Alert(Alert.AlertType.ERROR);
                formatAlert.setTitle("Eroare de format");
                formatAlert.setHeaderText("Preț invalid");
                formatAlert.setContentText("Te rugăm să introduci un număr valid pentru preț.");
                formatAlert.showAndWait();
            }
        } else {
            Alert noSelectionAlert = new Alert(Alert.AlertType.WARNING);
            noSelectionAlert.setTitle("Selecție invalidă");
            noSelectionAlert.setHeaderText("Nicio cameră selectată");
            noSelectionAlert.setContentText("Te rugăm să selectezi o cameră din tabel pentru a o actualiza.");
            noSelectionAlert.showAndWait();
        }
    }

}
