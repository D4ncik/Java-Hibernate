package md.ceiti.md.cazarefx_hibernate;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) {

        BorderPane root = new BorderPane();


        MenuBar menuBar = new MenuBar();
        menuBar.setStyle("-fx-background-color: lightgray;");
        Menu cazariMenu = new Menu("Cazari");
        MenuItem toateCazarile = new MenuItem("Toate Cazarile");
        MenuItem checkIn = new MenuItem("Check in");
        MenuItem checkOut = new MenuItem("Check out");
        MenuItem prelungesteSejur = new MenuItem("Prelungeste sejur");
        cazariMenu.getItems().addAll(toateCazarile, checkIn, checkOut, prelungesteSejur);

        Menu clientiMenu = new Menu("Clienti");
        MenuItem clientiActuali = new MenuItem("Clienti actuali");
        MenuItem adaugaClient = new MenuItem("Adauga Client");
        MenuItem stergeClient = new MenuItem("Sterge Client");
        MenuItem modificaClient = new MenuItem("Modifica Client");
        clientiMenu.getItems().addAll(clientiActuali, adaugaClient, stergeClient, modificaClient);

        Menu camereMenu = new Menu("Camere");
        MenuItem toateCamerele = new MenuItem("Toate Camerele");
        MenuItem adaugaCamera = new MenuItem("Adauga Camera");
        MenuItem stergeCamera = new MenuItem("Sterge Camera");
        MenuItem modificaCamera = new MenuItem("Modifica Camera");
        camereMenu.getItems().addAll(toateCamerele, adaugaCamera, stergeCamera, modificaCamera);

        Menu tipCamereMenu = new Menu("Tip Camere");
        MenuItem tipuriCamere = new MenuItem("Tipuri Camere");
        MenuItem adaugaTipCamera = new MenuItem("Adauga Tip Camera");
        MenuItem stergeTipCamera = new MenuItem("Sterge Tip Camera");
        MenuItem modificaTipCamera = new MenuItem("Modifica Tip Camera");
        tipCamereMenu.getItems().addAll(tipuriCamere, adaugaTipCamera, stergeTipCamera, modificaTipCamera);

        menuBar.getMenus().addAll(cazariMenu, clientiMenu, camereMenu, tipCamereMenu);
        root.setTop(menuBar);


        BorderPane mainPanel = new BorderPane();


        root.setCenter(mainPanel);

        Scene scene = new Scene(root, 900, 700);
        primaryStage.setTitle("Booking.com");
        primaryStage.setScene(scene);
        primaryStage.show();
        toateCazarile.setOnAction(e -> loadData("DataCazari.fxml", mainPanel, false));
        checkIn.setOnAction(e -> loadData("CheckIn.fxml", mainPanel, true));
        checkOut.setOnAction(e -> loadData("CheckOut.fxml", mainPanel, false));
        prelungesteSejur.setOnAction(e -> loadData("PrelungesteSejur.fxml", mainPanel, false));

        clientiActuali.setOnAction(e -> loadData("DataClienti.fxml", mainPanel, false));
        adaugaClient.setOnAction(e -> loadData("AddClient.fxml", mainPanel, true));
        stergeClient.setOnAction(e -> loadData("StergeClient.fxml", mainPanel, true));
        modificaClient.setOnAction(e -> loadData("ModificaClient.fxml", mainPanel, true));

        toateCamerele.setOnAction(e -> loadData("DataCamere.fxml", mainPanel, false));
        adaugaCamera.setOnAction(e -> loadData("AddCamera.fxml", mainPanel, true));
        stergeCamera.setOnAction(e -> loadData("StergeCamere.fxml", mainPanel, true));
        modificaCamera.setOnAction(e -> loadData("ModificaCamera.fxml", mainPanel, true));

        tipuriCamere.setOnAction(e -> loadData("DataTipCamera.fxml", mainPanel, false));
        adaugaTipCamera.setOnAction(e -> loadData("AddTipCamera.fxml", mainPanel, true));
        stergeTipCamera.setOnAction(e -> loadData("StergeTipCamera.fxml", mainPanel, true));
        modificaTipCamera.setOnAction(e -> loadData("ModificaTipCamera.fxml", mainPanel, true));
    }
    private void loadData(String fxmlFile, BorderPane mainPanel, boolean isNewWindow) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = fxmlLoader.load();
            if (isNewWindow) {
                Stage newWindow = new Stage();
                newWindow.setScene(new Scene(root, 600, 300));
                newWindow.setTitle("Adăugare Client");
                newWindow.show();
            } else {
                mainPanel.setCenter(root);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch();
    }
}
