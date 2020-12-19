package sample.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MedicalOfficerController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private BorderPane medicalMain_boarderPane;

    @FXML
    private ImageView medicalMain_mainHome;

    @FXML
    private JFXButton medicalMain_appointment;

    @FXML
    private Pane medicalMain_logout;

    @FXML
    private JFXButton medicalMain_logoutButton;

    @FXML
    private ImageView adminMain_backIcon;

    @FXML
    private AnchorPane medicalMain_loaderPane;

    @FXML
    void initialize() {


        medicalMain_mainHome.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                medicalMain_boarderPane.setCenter(medicalMain_loaderPane);
            }
        });

        medicalMain_appointment.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    System.out.println("taskView/appointmentView");
                    Pane view = Main.getView("taskView/appointmentView");
                    setMedicalViewCenter(view);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        medicalMain_logoutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                medicalMain_logoutButton.getScene().getWindow().hide();
                Stage detailsStage = new Stage();
                FXMLLoader loader = new FXMLLoader();

                loader.setLocation(getClass().getResource("/sample/view/mainLoginWindow.fxml"));
                try {
                    loader.load();

                }catch (IOException e){
                    e.printStackTrace();
                }

                Parent root = loader.getRoot();
                detailsStage.setScene(new Scene(root));
                detailsStage.show();
            }

        });
    }
    public void setMedicalViewCenter(Pane view){
        medicalMain_boarderPane.setCenter(view);

    }


}
