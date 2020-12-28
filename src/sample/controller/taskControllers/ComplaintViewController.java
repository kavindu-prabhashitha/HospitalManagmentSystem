package sample.controller.taskControllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.Main;
import sample.controller.actionTask.ComplaintAction;
import sample.controller.actionTask.ReferenceAction;
import sample.model.Complaint;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ComplaintViewController {

    Complaint currentComplaint;
    Boolean isTableSet =false;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane vedComplaintView;

    @FXML
    private JFXComboBox<String> Cview_complaintDropDown;

    @FXML
    private JFXTextArea Cview_note;

    @FXML
    private JFXTextField Cview_complaitBy;

    @FXML
    private JFXTextField Cview_phoneNumber;

    @FXML
    private TextField Cview_idSearchFiled;

    @FXML
    private JFXTextField Cview_description;

    @FXML
    private JFXTextArea Cview_actionTaken;

    @FXML
    private Label Cview_DateCurrent;

    @FXML
    private JFXButton Cview_searchButton;

    @FXML
    private JFXButton Cview_uploadFile;

    @FXML
    private JFXTextArea Cview_displayArea;

    @FXML
    private Label Cview_complaintID;

    @FXML
    private JFXButton Cview_addBtn;

    @FXML
    private JFXButton Cview_updateBtn;

    @FXML
    private JFXButton Cview_deleteBtn;

    @FXML
    private JFXButton Cview_viewAllBtn;

    @FXML
    private JFXButton Cview_resetBtn;

    @FXML
    private TableView<Complaint> Cview_mainTable;

    @FXML
    private TableColumn<Complaint, Integer> Cview_Tid;

    @FXML
    private TableColumn<Complaint, LocalDate> Cview_Tdate;

    @FXML
    private TableColumn<Complaint, String> Cview_Tcomplainttype;

    @FXML
    private TableColumn<Complaint, String> Cview_complaintBy;

    @FXML
    private TableColumn<Complaint, String> Cview_TphoneNumber;

    @FXML
    private TableColumn<Complaint, String> Cview_Tdescription;

    @FXML
    private TableColumn<Complaint, String> Cview_Tnote;

    @FXML
    private TableColumn<Complaint, String> Cview_Tattachment;

    @FXML
    private TableColumn<Complaint, String> Cview_TactionTaken;


    @FXML
    void initialize() {
        currentComplaint =new Complaint();
        Cview_DateCurrent.setText(Main.getStringfromLocalDate(LocalDate.now()));
        Cview_complaintDropDown.getItems().addAll(ReferenceAction.getComplaintStringArray());

        Cview_addBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ComplaintAction.addComplaintRecord(getInitialComplaintData());
                resetDisplay();
            }
        });

        Cview_updateBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ComplaintAction.updateComplaintRecord(getComplaintDetails());
                resetDisplay();
            }
        });

        Cview_deleteBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ComplaintAction.deleteComplaintRecord(getComplaintDetails());
                resetDisplay();
            }
        });

        Cview_viewAllBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ArrayList<Complaint> allComplaints = ComplaintAction.getAllComplaintRecords();
                setMainTable(allComplaints);
            }
        });

        Cview_resetBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                resetDisplay();
            }
        });

        Cview_mainTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Complaint selectedComplaint =Cview_mainTable.getSelectionModel().getSelectedItem();
                displayComplaintDetails(selectedComplaint);
                setComplaintViewData(selectedComplaint);

            }
        });

        Cview_searchButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String searchTerm =Cview_idSearchFiled.getText();
                Complaint foundComplaint = ComplaintAction.searchComplaintRecord(searchTerm,searchTerm);
                setComplaintViewData(foundComplaint);
                displayComplaintDetails(foundComplaint);
            }
        });
    }

    public Complaint getInitialComplaintData(){
        Complaint newComplaint = new Complaint();
        newComplaint.setComplaintID(Main.getComplaintID());
        newComplaint.setComplaintDate(Main.getLocalDatefromString(Cview_DateCurrent.getText()));
        newComplaint.setComplaintType(Cview_complaintDropDown.getValue());
        newComplaint.setComplaintBy(Cview_complaitBy.getText());
        newComplaint.setPhoneNumber(Cview_phoneNumber.getText());
        newComplaint.setDescription(Cview_description.getText());
        newComplaint.setNote(Cview_note.getText());
        newComplaint.setActiontaken("PENDING");

        return newComplaint;
    }

    public Complaint getComplaintDetails(){
        Complaint complaint = new Complaint();
        complaint.setComplaintID(Integer.parseInt(Cview_complaintID.getText()));
        complaint.setComplaintDate(Main.getLocalDatefromString(Cview_DateCurrent.getText()));
        complaint.setComplaintType(Cview_complaintDropDown.getValue());
        complaint.setPhoneNumber(Cview_phoneNumber.getText());
        complaint.setDescription(Cview_description.getText());
        complaint.setNote(Cview_note.getText());
        complaint.setActiontaken(Cview_actionTaken.getText());

        return complaint;
    }

    public void resetDisplay(){
        Cview_complaintDropDown.setValue(null);
        Cview_actionTaken.setText(null);
        Cview_complaintID.setText(null);
        Cview_idSearchFiled.setText(null);
        Cview_description.setText(null);
        Cview_displayArea.setText(null);
        Cview_phoneNumber.setText(null);
        Cview_complaitBy.setText(null);
        Cview_actionTaken.setText(null);
        Cview_note.setText(null);
    }

    public void setComplaintViewData(Complaint complaintRecord){
        Cview_complaintID.setText(String.valueOf(complaintRecord.getComplaintID()));
        Cview_DateCurrent.setText(Main.getStringfromLocalDate(complaintRecord.getComplaintDate()));
        Cview_complaitBy.setText(complaintRecord.getComplaintBy());
        Cview_complaintDropDown.setValue(complaintRecord.getComplaintType());
        Cview_phoneNumber.setText(complaintRecord.getPhoneNumber());
        Cview_description.setText(complaintRecord.getDescription());
        Cview_note.setText(complaintRecord.getNote());
        Cview_actionTaken.setText(complaintRecord.getActiontaken());
    }

    public void displayComplaintDetails(Complaint complaint){
        Cview_displayArea.setText(
                "\n-----Complaint Data-----\n"+
                        "Complaint ID : "+complaint.getComplaintID()+"\n"+
                        "Complaint Type : "+complaint.getComplaintType()+"\n"+
                        "Complaint By : "+complaint.getComplaintBy()+"\n"+
                        "Contact Number : "+complaint.getPhoneNumber()+"\n"+
                        "Description : "+complaint.getDescription()+"\n"+
                        "Note : "+complaint.getNote()+"\n"+
                        "Action Taken : "+complaint.getActiontaken()
        );
    }

    public void setMainTable(ArrayList<Complaint> complaints){

        ObservableList<Complaint> observableList = FXCollections.observableList(complaints);

        if (!isTableSet){
            Cview_Tid.setCellValueFactory(new PropertyValueFactory<Complaint,Integer>("complaintID"));
            Cview_Tdate.setCellValueFactory(new PropertyValueFactory<Complaint,LocalDate>("complaintDate"));
            Cview_Tcomplainttype.setCellValueFactory(new PropertyValueFactory<Complaint,String>("complaintType"));
            Cview_complaintBy.setCellValueFactory(new PropertyValueFactory<Complaint,String>("complaintBy"));
            Cview_TphoneNumber.setCellValueFactory(new PropertyValueFactory<Complaint,String>("phoneNumber"));
            Cview_Tdescription.setCellValueFactory(new PropertyValueFactory<Complaint,String>("description"));
            Cview_Tnote.setCellValueFactory(new PropertyValueFactory<Complaint,String>("note"));
            Cview_TactionTaken.setCellValueFactory(new PropertyValueFactory<Complaint,String>("actiontaken"));

        }

        Cview_mainTable.setItems(observableList);
    }
}
