package sample.controller.taskControllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import sample.Main;
import sample.controller.actionTask.PostalAction;
import sample.controller.actionTask.ReferenceAction;
import sample.model.Postal;
import sample.model.PostalType;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PostalViewController {


    boolean isTableSet=false;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Postal> postalView_userTable;

    @FXML private TableColumn<Postal, String> Table_ReferenceNunber;
    @FXML private TableColumn<Postal, PostalType> Table_PostalType;
    @FXML private TableColumn<Postal, LocalDate> Table_Date;
    @FXML private TableColumn<Postal,String > Table_name;
    @FXML private TableColumn<Postal, String> Table_address;
    @FXML private TableColumn<Postal, String> Table_note;

    @FXML private JFXButton postalView_addPostal;

    @FXML
    private JFXButton postalView_deletePostal;

    @FXML
    private JFXButton postalView_viewAll;

    @FXML
    private JFXButton postalView_updatePostal;

    @FXML
    private JFXButton postalView_reset;

    @FXML
    private JFXTextField postalView_toName;

    @FXML
    private Label postalView_addressLable;

    @FXML
    private Label postalView_nameLabel;

    @FXML
    private DatePicker postalView_date;

    @FXML
    private JFXButton postalView_searchPostalBtn;

    @FXML
    private JFXTextArea postalView_note;

    @FXML
    private JFXTextArea postalView_address;

    @FXML
    private JFXComboBox<PostalType> postalView_type;

    @FXML
    private JFXTextField postalView_searchBox;

    @FXML
    private JFXTextField postalView_refecenceNo;

    @FXML
    private JFXTextArea postalView_displayArea;

    @FXML
    void initialize() {

        postalView_type.getItems().addAll(ReferenceAction.getPostalTypes());

        postalView_type.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!(postalView_type.getValue()==null)){
                    PostalType postalType =postalView_type.getValue();
                    switch (postalType){
                        case RECEIVED:
                            postalView_nameLabel.setText("From Name");
                            postalView_addressLable.setText("From Address");
                            break;
                        case DISPATCH:
                            postalView_nameLabel.setText("To Name");
                            postalView_addressLable.setText("To Address");
                            break;
                        default:
                            break;

                    }
                }
            }
        });

        postalView_addPostal.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                PostalAction.addPostaRecord(getInitialPostal());
            }
        });

        postalView_updatePostal.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                PostalAction.updatePostalRecord(getPostalRecord());
            }
        });

        postalView_deletePostal.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                PostalAction.deletePostalRecord(getPostalRecord());
            }
        });

        postalView_searchPostalBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int searchTerm =Integer.parseInt(postalView_searchBox.getText());

                Postal foundPostal =PostalAction.searchPostalRecord(searchTerm);
                dislpayPostalRecord(foundPostal);
                setPostaDatainView(foundPostal);


            }
        });

        postalView_reset.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                resetDisplay();
            }
        });

        postalView_viewAll.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ArrayList<Postal> postalArrayList =PostalAction.getAllPostals();
                setMainTable(postalArrayList);
            }
        });

        postalView_userTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Postal postal = postalView_userTable.getSelectionModel().getSelectedItem();
                dislpayPostalRecord(postal);
                setPostaDatainView(postal);
            }
        });

    }

    public Postal getInitialPostal(){
        Postal postal =new Postal();
        postal.setReferenceNo(Main.getPostalReferenceID());
        postal.setPostalType(postalView_type.getValue());
        postal.setDate(postalView_date.getValue());
        postal.setName(postalView_toName.getText());
        postal.setAddress(postalView_address.getText());
        postal.setNote(postalView_note.getText());

        return  postal;
    }

    public Postal getPostalRecord(){
        Postal postal = new Postal();
        postal.setPostalType(postalView_type.getValue());
        postal.setReferenceNo(Integer.parseInt(postalView_refecenceNo.getText()));
        postal.setDate(postalView_date.getValue());
        postal.setName(postalView_toName.getText());
        postal.setAddress(postalView_address.getText());
        postal.setNote(postalView_note.getText());

        return postal;
    }

    public void resetDisplay(){
        postalView_type.setValue(null);
        postalView_refecenceNo.setText(null);
        postalView_date.setValue(null);
        postalView_toName.setText(null);
        postalView_address.setText(null);
        postalView_note.setText(null);
        postalView_searchBox.setText(null);
        postalView_displayArea.setText(null);
        postalView_userTable.setItems(null);
    }

    public void dislpayPostalRecord(Postal postal){

        if (postal.getPostalType()==PostalType.RECEIVED){
            String receiveRecord ="----RECEIVED POSTAL----\n"+
                    "Reference Number : "+postal.getReferenceNo() +" \n"+
                    "Date     : "+postal.getDate() +" \n"+
                    "From Name  : "+postal.getName() +" \n"+
                    "From Address : "+postal.getAddress() +" \n"+
                    "Note   : "+postal.getNote() +" \n";

            postalView_displayArea.setText(receiveRecord);
        }else {
            String dispatchRecord ="----DISPATCH POSTAL----\n"+
                    "Reference Number : "+postal.getReferenceNo() +" \n"+
                    "Date     : "+postal.getDate() +" \n"+
                    "To Name  : "+postal.getName() +" \n"+
                    "To Address : "+postal.getAddress() +" \n"+
                    "Note   : "+postal.getNote() +" \n";

            postalView_displayArea.setText(dispatchRecord);
        }



    }

    public void setPostaDatainView(Postal postal){
        postalView_type.setValue(postal.getPostalType());
        postalView_refecenceNo.setText(String.valueOf(postal.getReferenceNo()));
        postalView_date.setValue(postal.getDate());
        postalView_toName.setText(postal.getName());
        postalView_address.setText(postal.getAddress());
        postalView_toName.setText(postal.getName());
        postalView_note.setText(postal.getNote());
    }

    public void setMainTable(ArrayList<Postal> postalArrayList){

        ObservableList<Postal> observableList = FXCollections.observableList(postalArrayList);

        if (!isTableSet){
            Table_ReferenceNunber.setCellValueFactory(new PropertyValueFactory<Postal,String>("referenceNo"));
            Table_PostalType.setCellValueFactory(new PropertyValueFactory<Postal,PostalType>("postalType"));
            Table_Date.setCellValueFactory(new PropertyValueFactory<Postal,LocalDate>("date"));
            Table_name.setCellValueFactory(new PropertyValueFactory<Postal,String>("name"));
            Table_address.setCellValueFactory(new PropertyValueFactory<Postal,String>("address"));
            Table_note.setCellValueFactory(new PropertyValueFactory<Postal,String>("note"));

        }

        postalView_userTable.setItems(observableList);

    }


}
