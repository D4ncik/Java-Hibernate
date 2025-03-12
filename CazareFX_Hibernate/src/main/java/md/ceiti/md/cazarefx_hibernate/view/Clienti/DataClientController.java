package md.ceiti.md.cazarefx_hibernate.view.Clienti;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import md.ceiti.md.cazarefx_hibernate.model.impl.ClientiDAO_Impl;
import md.ceiti.md.cazarefx_hibernate.model.entity.Clienti_Data;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DataClientController {

    private ClientiDAO_Impl clientDAO = new ClientiDAO_Impl();

    @FXML
    private TableView<Clienti_Data> clientTable;

    @FXML
    private TableColumn<Clienti_Data, String> nameColumn;

    @FXML
    private TableColumn<Clienti_Data, String> emailColumn;

    @FXML
    private TableColumn<Clienti_Data, String> telefonColumn;

    @FXML
    private TableColumn<Clienti_Data, String> genColumn;

    @FXML
    private TableColumn<Clienti_Data, String> categorieColumn;

    @FXML
    private TextField searchField;
    @FXML
    private Button goButton;


    private ObservableList<Clienti_Data> clientList = FXCollections.observableArrayList();
    private FilteredList<Clienti_Data> filteredData;

    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nume"));
        telefonColumn.setCellValueFactory(new PropertyValueFactory<>("telefon"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        genColumn.setCellValueFactory(new PropertyValueFactory<>("gen"));
        categorieColumn.setCellValueFactory(new PropertyValueFactory<>("categorie"));

        loadData();
        clientTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        filteredData = new FilteredList<>(clientList, p -> true);
        clientTable.setItems(filteredData);
    }

    private void loadData() {
        List<Clienti_Data> clientiList = clientDAO.getAllClients();
        clientList.clear();
        clientList.addAll(clientiList);
    }

    @FXML
    public void handleDelete() {
        Clienti_Data clientSelectat = clientTable.getSelectionModel().getSelectedItem();

        if (clientSelectat != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmare Ștergere");
            alert.setHeaderText("Doriți să ștergeți acest client?");
            alert.setContentText("Această acțiune nu poate fi anulată.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                int rowsAffected = clientDAO.deleteClient(clientSelectat);
                if (rowsAffected > 0) {
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Succes");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Clientul a fost șters cu succes!");
                    successAlert.showAndWait();

                    loadData();
                } else {
                    Alert failureAlert = new Alert(Alert.AlertType.ERROR);
                    failureAlert.setTitle("Eroare");
                    failureAlert.setHeaderText("Ștergere nereușită");
                    failureAlert.setContentText("Clientul nu a fost găsit sau nu a putut fi șters.");
                    failureAlert.showAndWait();
                }
            }
        } else {
            Alert noSelectionAlert = new Alert(Alert.AlertType.WARNING);
            noSelectionAlert.setTitle("Selecție invalidă");
            noSelectionAlert.setHeaderText("Niciun client selectat");
            noSelectionAlert.setContentText("Te rugăm să selectezi un client din tabel pentru a-l șterge.");
            noSelectionAlert.showAndWait();
        }
    }

    public void addClient(Clienti_Data client) {
        clientList.add(client);
    }

    public void handleUpdate(ActionEvent actionEvent) {
        Clienti_Data selectedClient = clientTable.getSelectionModel().getSelectedItem();

        if (selectedClient != null) {

            TextInputDialog nameDialog = new TextInputDialog(selectedClient.getNume());
            nameDialog.setTitle("Update Client");
            nameDialog.setHeaderText("Modifică numele clientului");
            nameDialog.setContentText("Nume nou:");
            Optional<String> resultName = nameDialog.showAndWait();

            if (resultName.isPresent() && !resultName.get().isEmpty()) {
                selectedClient.setNume(resultName.get());
            }
            TextInputDialog emailDialog = new TextInputDialog(selectedClient.getEmail());
            emailDialog.setTitle("Update Client");
            emailDialog.setHeaderText("Modifică email-ul clientului");
            emailDialog.setContentText("Email nou:");
            Optional<String> resultEmail = emailDialog.showAndWait();

            TextInputDialog phoneDialog = new TextInputDialog(selectedClient.getTelefon());
            phoneDialog.setTitle("Update Client");
            phoneDialog.setHeaderText("Modifică numărul de telefon");
            phoneDialog.setContentText("Număr nou:");
            Optional<String> resultPhone = phoneDialog.showAndWait();

            List<String> genOptions = Arrays.asList("Masculin", "Feminin");
            ChoiceDialog<String> genDialog = new ChoiceDialog<>(selectedClient.getGen(), genOptions);
            genDialog.setTitle("Update Client");
            genDialog.setHeaderText("Modifică genul clientului");
            genDialog.setContentText("Alege genul:");
            Optional<String> resultGen = genDialog.showAndWait();

            resultGen.ifPresent(selectedClient::setGen);
            resultPhone.ifPresent(selectedClient::setTelefon);
            if (resultEmail.isPresent() && !resultEmail.get().isEmpty()) {
                selectedClient.setEmail(resultEmail.get());
                selectedClient.setTelefon(resultPhone.get());
                selectedClient.setGen(resultGen.get());
            }
            int rowsAffected = clientDAO.updateClient(selectedClient);
            if (rowsAffected > 0) {
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Succes");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Clientul a fost actualizat cu succes!");
                successAlert.showAndWait();


                loadData();
            } else {
                Alert failureAlert = new Alert(Alert.AlertType.ERROR);
                failureAlert.setTitle("Eroare");
                failureAlert.setHeaderText("Actualizare nereușită");
                failureAlert.setContentText("Clientul nu a fost găsit sau nu a putut fi actualizat.");
                failureAlert.showAndWait();
            }

        } else {
            Alert noSelectionAlert = new Alert(Alert.AlertType.WARNING);
            noSelectionAlert.setTitle("Selecție invalidă");
            noSelectionAlert.setHeaderText("Niciun client selectat");
            noSelectionAlert.setContentText("Te rugăm să selectezi un client din tabel pentru a-l actualiza.");
            noSelectionAlert.showAndWait();
        }
    }
}
