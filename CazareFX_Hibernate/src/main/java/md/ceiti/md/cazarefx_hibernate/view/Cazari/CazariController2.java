package md.ceiti.md.cazarefx_hibernate.view.Cazari;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;
import md.ceiti.md.cazarefx_hibernate.model.dao.CazariDAO;
import md.ceiti.md.cazarefx_hibernate.model.impl.CazariDAO_Impl;
import md.ceiti.md.cazarefx_hibernate.model.entity.Cazari_Data;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class CazariController2 {

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
        dataoutColumn.setCellFactory(new Callback<TableColumn<Cazari_Data, String>, TableCell<Cazari_Data, String>>() {
            @Override
            public TableCell<Cazari_Data, String> call(TableColumn<Cazari_Data, String> param) {
                return new TableCell<Cazari_Data, String>() {
                    private final DatePicker datePicker = new DatePicker();
                    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            setGraphic(datePicker);

                            if (item != null && !item.isEmpty()) {
                                try {
                                    // Convertim string-ul în LocalDate folosind formatter-ul
                                    LocalDate date = LocalDate.parse(item, formatter);
                                    datePicker.setValue(date);
                                } catch (Exception e) {
                                    // În cazul unei erori la parsarea datei, setăm data curentă
                                    datePicker.setValue(LocalDate.now());
                                }
                            } else {
                                datePicker.setValue(LocalDate.now());
                            }

                            datePicker.setOnAction(event -> {
                                if (getTableRow() != null) {
                                    Cazari_Data rowData = getTableRow().getItem();
                                    if (rowData != null) {
                                        // Actualizăm valoarea din model, convertind LocalDate la String
                                        rowData.setData_out(datePicker.getValue().format(formatter));  // Setăm data în model
                                    }
                                }
                            });
                        }
                    }
                };
            }
        });
        zileColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        zileColumn.setOnEditCommit(event -> {
            Cazari_Data rowData = event.getRowValue();
            Integer newValue = event.getNewValue();

            if (newValue != null && newValue >= 0) { // Validăm că numărul de zile este pozitiv
                rowData.setZile(newValue); // Actualizăm modelul
                try {
                    // Apelăm metoda DAO pentru a actualiza valoarea în baza de date
                    cazariDAO.updateCazari(rowData);
                } catch (SQLException e) {
                    e.printStackTrace();
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Eroare la salvare");
                    errorAlert.setHeaderText("Eroare la actualizarea numărului de zile");
                    errorAlert.setContentText("Nu s-a putut salva numărul de zile în baza de date.");
                    errorAlert.showAndWait();
                }
            } else {
                Alert validationAlert = new Alert(Alert.AlertType.WARNING);
                validationAlert.setTitle("Date invalide");
                validationAlert.setHeaderText("Valoare incorectă");
                validationAlert.setContentText("Numărul de zile trebuie să fie un număr pozitiv.");
                validationAlert.showAndWait();
                // Resetăm la valoarea anterioară
                zileColumn.getTableView().refresh();
            }
        });

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
                try {

                    int rowsAffected = cazariDAO.deleteCazari(cazareSelectata);

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
                } catch (SQLException e) {

                    e.printStackTrace();
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Eroare");
                    errorAlert.setHeaderText("Eroare la ștergere");
                    errorAlert.setContentText("A apărut o eroare la ștergerea rezervării.");
                    errorAlert.showAndWait();
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

    public void handleUpdate(ActionEvent actionEvent) {
        Cazari_Data selectedCazare = cazariTable.getSelectionModel().getSelectedItem();
        if (selectedCazare != null) {
            try {
                int rowsAffected = cazariDAO.updateCazari(selectedCazare);

                if (rowsAffected > 0) {
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Succes");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Rezervarea a fost actualizată cu succes!");
                    successAlert.showAndWait();

                    loadData();
                } else {
                    Alert failureAlert = new Alert(Alert.AlertType.ERROR);
                    failureAlert.setTitle("Eroare");
                    failureAlert.setHeaderText("Actualizare nereușită");
                    failureAlert.setContentText("Rezervarea nu a fost găsită sau nu a putut fi actualizată.");
                    failureAlert.showAndWait();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Eroare");
                errorAlert.setHeaderText("Eroare la actualizare");
                errorAlert.setContentText("A apărut o eroare la actualizarea rezervării.");
                errorAlert.showAndWait();
            }
        } else {
            Alert noSelectionAlert = new Alert(Alert.AlertType.WARNING);
            noSelectionAlert.setTitle("Selecție invalidă");
            noSelectionAlert.setHeaderText("Nicio rezervare selectată");
            noSelectionAlert.setContentText("Te rugăm să selectezi o rezervare din tabel pentru a o actualiza.");
            noSelectionAlert.showAndWait();
        }
    }
}

