package md.ceiti.md.cazarefx_hibernate.view.Cazari;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import md.ceiti.md.cazarefx_hibernate.model.dao.CazariDAO;
import md.ceiti.md.cazarefx_hibernate.model.entity.Cazari_Data;
import md.ceiti.md.cazarefx_hibernate.model.impl.CazariDAO_Impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CazariController {

    @FXML
    private Label clientLabel;
    @FXML
    private TableView<Cazari_Data> cazariTable;
    @FXML
    private TableColumn<Cazari_Data, String> clientColumn;

    @FXML
    private TableColumn<Cazari_Data, String> cameraColumn;

    @FXML
    private TableColumn<Cazari_Data, Integer> Nr_CameraColumn;

    @FXML
    private TableColumn<Cazari_Data, String> statutColumn;

    @FXML
    private TableColumn<Cazari_Data, String> datainColumn;

    @FXML
    private TableColumn<Cazari_Data, String> dataoutColumn;

    @FXML
    private TableColumn<Cazari_Data, String> pretColumn;

    @FXML
    private TableColumn<Cazari_Data, Integer> zileColumn;

    @FXML
    private TextField searchField;


    private static final ObservableList<Cazari_Data> cazariList = FXCollections.observableArrayList();
    private CazariDAO cazariDAO = new CazariDAO_Impl();

    public void initialize() {
        clientColumn.setCellValueFactory(new PropertyValueFactory<>("client"));
        cameraColumn.setCellValueFactory(new PropertyValueFactory<>("camera"));
        Nr_CameraColumn.setCellValueFactory(new PropertyValueFactory<>("Nr_camera"));
        statutColumn.setCellValueFactory(new PropertyValueFactory<>("statut"));
        datainColumn.setCellValueFactory(new PropertyValueFactory<>("Data_in"));
        dataoutColumn.setCellValueFactory(new PropertyValueFactory<>("Data_out"));
        pretColumn.setCellValueFactory(new PropertyValueFactory<>("pret"));
        zileColumn.setCellValueFactory(new PropertyValueFactory<>("zile"));
        loadData();
        cazariTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public void setClientName(String clientName) {
        System.out.println("Setting client name: " + clientName);
        clientLabel.setText(clientName);
    }

    private void loadData() {
        try {

            List<Cazari_Data> cazariListFromDB = cazariDAO.getAll();
            cazariList.clear();
            cazariList.addAll(cazariListFromDB);
            cazariTable.setItems(cazariList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        FilteredList<Cazari_Data> filteredData = new FilteredList<>(cazariList, p -> true);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(cazare -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (cazare.getClient().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (cazare.getCamera().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (cazare.getStatut().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        cazariTable.setItems(filteredData);
    }

    public void handleDelete() {

        Cazari_Data cazareSelectata = cazariTable.getSelectionModel().getSelectedItem();

        if (cazareSelectata != null) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmare Ștergere");
            alert.setHeaderText("Doriți să ștergeți această rezervare?");
            alert.setContentText("Această acțiune nu poate fi anulată.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {

                int rowsAffected =0;// cazariDAO.delete(cazareSelectata);

                if (rowsAffected > 0) {

                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Succes");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Rezervarea a fost ștersă cu succes!");
                    successAlert.showAndWait();
                    loadData();
                } else {

                    Alert failureAlert = new Alert(Alert.AlertType.ERROR);
                    failureAlert.setTitle("Eroare");
                    failureAlert.setHeaderText("Ștergere nereușită");
                    failureAlert.setContentText("Rezervarea nu a fost găsită sau nu a putut fi ștearsă.");
                    failureAlert.showAndWait();
                }
            }
        } else {
            Alert noSelectionAlert = new Alert(Alert.AlertType.WARNING);
            noSelectionAlert.setTitle("Selecție invalidă");
            noSelectionAlert.setHeaderText("Nicio rezervare selectată");
            noSelectionAlert.setContentText("Te rugăm să selectezi o rezervare din tabel pentru a o șterge.");
            noSelectionAlert.showAndWait();
        }
    }
    public void addCazare(Cazari_Data cazare) {
        cazariList.add(cazare);
    }
}
