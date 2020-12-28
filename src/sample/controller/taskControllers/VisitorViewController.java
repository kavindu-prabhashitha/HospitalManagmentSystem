package sample.controller.taskControllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import sample.Main;
import sample.controller.actionTask.ReferenceAction;
import sample.controller.actionTask.VisitorAction;
import sample.model.Gender;
import sample.model.Visitor;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class VisitorViewController {

    public Boolean isMainTableSet=false;
    private Visitor currentVisitor;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTextField Vview_idSearch;

    @FXML
    private JFXButton Vview_searchButton;

    @FXML
    private JFXButton Vview_outTimeAddBtn;

    @FXML
    private JFXButton Vview_inTimeAddBtn;

    @FXML
    private JFXTextArea Vview_note;

    @FXML
    private Label Vview_recordID;

    @FXML
    private JFXButton Vview_attchementsBtn;

    @FXML
    private DatePicker Vview_date;

    @FXML
    private JFXTextField Vview_idNumber;

    @FXML
    private JFXTextField Vview_vName;

    @FXML
    private JFXTextField Vview_purpose;

    @FXML
    private JFXTextField Vview_phoneNumber;

    @FXML
    private JFXComboBox<Gender> Vview_gender;

    @FXML
    private Label Vview_inTime;

    @FXML
    private Label Vview_outTime;

    @FXML
    private JFXTextArea Vview_displayArea;

    @FXML
    private JFXButton Vview_addBtn;

    @FXML
    private JFXButton Vview_deleteBtn;

    @FXML
    private JFXButton Vview_updateBtn;

    @FXML
    private JFXButton Vview_viewAllBtn;

    @FXML
    private JFXButton Vview_resetBtn;

    @FXML
    private JFXButton Vview_inVisitorsBtn;


    @FXML
    private TableView<Visitor> Vview_mainTable;

    @FXML
    private TableColumn<Visitor,Integer> Vview_TvisitorID;

    @FXML
    private TableColumn<Visitor,Gender> Vview_TGender;

    @FXML
    private TableColumn<Visitor, String> Vview_TidNumberCol;

    @FXML
    private TableColumn<Visitor, String> Vview_TidNameCol;

    @FXML
    private TableColumn<Visitor, String> Vview_TreasonCol;

    @FXML
    private TableColumn<Visitor, String> Vview_TiphoneCol;

    @FXML
    private TableColumn<Visitor, LocalDate> Vview_TdateCol;

    @FXML
    private TableColumn<Visitor, LocalTime> Vview_TarrivalCol;

    @FXML
    private TableColumn<Visitor, LocalTime> Vview_ToutTimeCol;

    @FXML
    private TableColumn<Visitor, String> Vview_TnotesCol;

    @FXML
    void initialize() {

        currentVisitor =new Visitor();

        Vview_gender.getItems().addAll(ReferenceAction.getGender());

        Vview_addBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                VisitorAction.addVisitorRecord(getInitialVisitorRecord());
            }
        });

        Vview_viewAllBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ArrayList<Visitor> allVisitors= VisitorAction.viewAllVisitorRecords();
                ObservableList<Visitor> allVisitorRecords = FXCollections.observableArrayList(allVisitors);
                setTable();
                Vview_mainTable.setItems(allVisitorRecords);

            }
        });

        Vview_inTimeAddBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                LocalTime inTime = LocalTime.now();
                Vview_inTime.setText(Main.getStringFromLocalTime(inTime));


            }
        });

        Vview_outTimeAddBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                LocalTime inTime = LocalTime.now();
                Vview_outTime.setText(Main.getStringFromLocalTime(inTime));

            }
        });

        Vview_resetBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                resetDisplay();
            }
        });

        Vview_searchButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ArrayList<Visitor> foundVisitors =VisitorAction.searchVisitorRecords(Vview_idSearch.getText(),null);

                System.out.println("search term : "+Vview_idSearch.getText()+" found Records : "+foundVisitors);
                ObservableList<Visitor> observableList =FXCollections.observableList(foundVisitors);
                setTable();
                Vview_mainTable.setItems(observableList);
            }
        });

        Vview_updateBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                VisitorAction.updateVisitorRecord(getVisitorRecord());
            }
        });

        Vview_deleteBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                VisitorAction.deleteVisitorRecord(getVisitorRecord());
                resetDisplay();
            }
        });

        Vview_mainTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                resetRecords();
                Visitor selectedVisitor = Vview_mainTable.getSelectionModel().getSelectedItem();
                if (selectedVisitor.isVisitorIn()){
                    setCurrentVisitor(selectedVisitor);
                    displayVisitor(selectedVisitor);
                    displayVisitorDetails();
                }

            }
        });

        Vview_inVisitorsBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                resetRecords();
                ArrayList<Visitor> getINVisitorRecords = VisitorAction.getInVisitorRecords();
                ObservableList<Visitor> visitorObservableList =FXCollections.observableList(getINVisitorRecords);
                setTable();
                Vview_mainTable.setItems(visitorObservableList);

            }
        });
    }

    public Visitor getInitialVisitorRecord(){
        Visitor visitor =new Visitor();

        currentVisitor.setVisitorIn(Vview_inTime.getText()!=null);

        visitor.setVisitorOut(false);
        visitor.setDate(Vview_date.getValue());
        visitor.setVisitorIn(currentVisitor.isVisitorIn());
        visitor.setVisitorID(Main.getVisitorID());
        visitor.setIdNumber(Vview_idNumber.getText());
        visitor.setName(Vview_vName.getText());
        visitor.setPurpose(Vview_purpose.getText());
        visitor.setPhoneNumber(Vview_phoneNumber.getText());
        visitor.setGender(Vview_gender.getValue());
        visitor.setNote(Vview_note.getText());
        visitor.setInTime(Main.getLocalTimeFromString(Vview_inTime.getText()));

        return visitor;
    }

    public Visitor getVisitorRecord(){

        Visitor visitor =new Visitor();
        currentVisitor.setVisitorIn(Vview_inTime.getText() != null);
        currentVisitor.setVisitorOut(Vview_outTime.getText() != null);

        visitor.setVisitorIn(currentVisitor.isVisitorIn());
        visitor.setVisitorOut(currentVisitor.isVisitorOut());
        visitor.setVisitorID(currentVisitor.getVisitorID());
        visitor.setIdNumber(Vview_idNumber.getText());
        visitor.setDate(Vview_date.getValue());
        visitor.setName(Vview_vName.getText());
        visitor.setPurpose(Vview_purpose.getText());
        visitor.setPhoneNumber(Vview_phoneNumber.getText());
        visitor.setGender(Vview_gender.getValue());
        visitor.setNote(Vview_note.getText());
        visitor.setInTime(Main.getLocalTimeFromString(Vview_inTime.getText()));
        visitor.setOutTime(Main.getLocalTimeFromString(Vview_outTime.getText()));

        System.out.println("Returned visitor : from getVisitorRecord() "+visitor.toString());

        return visitor;
    }

    public void resetDisplay(){
        Vview_gender.setValue(null);
        Vview_date.setValue(null);
        Vview_displayArea.setText(null);
        Vview_idNumber.setText(null);
        Vview_idSearch.setText(null);
        Vview_mainTable.setItems(null);
        Vview_inTime.setText(null);
        Vview_outTime.setText(null);
        Vview_note.setText(null);
        Vview_vName.setText(null);
        Vview_phoneNumber.setText(null);
        Vview_purpose.setText(null);
        Vview_recordID.setText(null);
        currentVisitor=new Visitor();
    }

    public void resetRecords(){
        Vview_gender.setValue(null);
        Vview_date.setValue(null);
        Vview_displayArea.setText(null);
        Vview_idNumber.setText(null);
        Vview_idSearch.setText(null);
        Vview_inTime.setText(null);
        Vview_outTime.setText(null);
        Vview_note.setText(null);
        Vview_phoneNumber.setText(null);
        Vview_purpose.setText(null);
        Vview_recordID.setText(null);
        currentVisitor =new Visitor();
    }

    public void displayVisitorDetails(){
        String strVisitor="\n---Visitor Details---\n"+
                "\nVisitor ID \t: "+currentVisitor.getVisitorID()+"\n"+
                "Date  \t: "+currentVisitor.getDate()+"\n"+
                "Name \t: "+currentVisitor.getName()+"\n"+
                "ID Number \t: "+currentVisitor.getIdNumber()+"\n"+
                "Phone Number \t: "+currentVisitor.getPhoneNumber()+"\n"+
                "Gender  \t: "+currentVisitor.getGender()+"\n"+
                "Note \t: "+currentVisitor.getNote()+"\n"+
                "IN Time \t: "+currentVisitor.getInTime()+"\n"

                ;

        Vview_displayArea.setText(strVisitor);
    }

    public void displayVisitor(Visitor visitor){
        Vview_recordID.setText(String.valueOf(visitor.getVisitorID()));
        Vview_date.setValue(visitor.getDate());
        Vview_idNumber.setText(visitor.getIdNumber());
        Vview_vName.setText(visitor.getName());
        Vview_purpose.setText(visitor.getPurpose());
        Vview_phoneNumber.setText(visitor.getPhoneNumber());
        Vview_gender.setValue(visitor.getGender());
        Vview_note.setText(visitor.getNote());
        Vview_inTime.setText(Main.getStringFromLocalTime(visitor.getInTime()));
        if (!(visitor.getOutTime()==(null))){
            Vview_outTime.setText(Main.getStringFromLocalTime(visitor.getOutTime()));
        }





    }

    public void setTable(){

        if (!isMainTableSet){
            isMainTableSet =true;


            Vview_TvisitorID.setCellValueFactory(new PropertyValueFactory<Visitor,Integer>("visitorID"));
            Vview_TidNumberCol.setCellValueFactory(new PropertyValueFactory<Visitor, String>("idNumber"));
            Vview_TidNameCol.setCellValueFactory(new PropertyValueFactory<Visitor, String>("name"));
            Vview_TGender.setCellValueFactory(new PropertyValueFactory<Visitor, Gender>("gender"));
            Vview_TreasonCol.setCellValueFactory(new PropertyValueFactory<Visitor, String>("purpose"));
            Vview_TiphoneCol.setCellValueFactory(new PropertyValueFactory<Visitor, String>("phoneNumber"));
            Vview_TarrivalCol.setCellValueFactory(new PropertyValueFactory<Visitor, LocalTime>("inTime"));
            Vview_ToutTimeCol.setCellValueFactory(new PropertyValueFactory<Visitor, LocalTime>("outTime"));
            Vview_TdateCol.setCellValueFactory(new PropertyValueFactory<Visitor, LocalDate>("date"));
            Vview_TnotesCol.setCellValueFactory(new PropertyValueFactory<Visitor, String>("note"));

        }
    }

    public void setCurrentVisitor(Visitor currentVisitor) {
        this.currentVisitor = currentVisitor;
    }
}
