package sample.controller.taskControllers;

import com.jfoenix.controls.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import sample.Main;
import sample.controller.actionTask.ProfileAction;
import sample.controller.actionTask.ReferenceAction;
import sample.controller.actionTask.UserAction;
import sample.model.*;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileViewController {


    @FXML
    private ResourceBundle resources;

    @FXML private URL location;
    @FXML private Label profile_userNameLabel;
    @FXML private JFXButton profile_changePicButton;
    @FXML private GridPane profileView_userdataGrid;
    @FXML private JFXTextField profile_name;
    @FXML private JFXTextField profile_nicNumber;
    @FXML private JFXTextField profile_phoneNumber;
    @FXML private JFXComboBox<Gender> profile_genderDrop;
    @FXML private JFXTextArea profile_address;
    @FXML private DatePicker profile_dobDatePicker;
    @FXML private JFXTextField profile_userName;
    @FXML private JFXPasswordField profile_userPassword;
    @FXML private JFXButton profile_editProfileButton;
    @FXML private JFXButton profile_saveProfileButton;
    @FXML private GridPane profileView_patientExtraGrid;
    @FXML private JFXComboBox<BloodGroup> profile_bloodGropDrop;
    @FXML private JFXTextField profile_allergies;
    @FXML private GridPane profileView_empExtraGrid;
    @FXML private Label profile_staffID;
    @FXML private DatePicker profile_dateOfJoin;
    @FXML private JFXTextField profile_emailAddress;
    @FXML private GridPane profileView_doctorExtraGrid;
    @FXML private JFXComboBox<String> profile_specialityDrop;
    @FXML private JFXComboBox<String> profile_maritalDrop;


    @FXML void initialize() {

        profile_maritalDrop.getItems().addAll(ReferenceAction.getMaritalStatus());
        profile_genderDrop.getItems().addAll(ReferenceAction.getGender());
        profile_specialityDrop.getItems().addAll(ReferenceAction.getDocSpecialityStringArray());
        profile_bloodGropDrop.getItems().addAll(ReferenceAction.getBloogGroup());
        System.out.println("initialize() in profileViewController currentUser"+ Main.getCurrentSystemUser());
        setUserProfileData(Main.getCurrentSystemUser());

        profile_editProfileButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                switch (Main.getCurrentSystemUser().getUserRoll()){
                    case ADMIN :
                       profileView_userdataGrid.setDisable(false);
                       break;
                    case RECEPTIONIST:
                        profileView_userdataGrid.setDisable(false);
                        profileView_empExtraGrid.setDisable(false);
                        break;
                    case PATIENT:
                        profileView_userdataGrid.setDisable(false);
                        profileView_patientExtraGrid.setDisable(false);
                        break;
                    case MEDICALOFFICER:
                        profileView_userdataGrid.setDisable(false);
                        profileView_empExtraGrid.setDisable(false);
                        profileView_doctorExtraGrid.setDisable(false);
                        break;
                    default:
                        break;
                }
            }
        });

        profile_saveProfileButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                switch (Main.getCurrentSystemUser().getUserRoll()){
                    case ADMIN :
                        ProfileAction.updateCurrentUserData(getAdminDataFromView());
                        break;
                    case RECEPTIONIST:
                        ProfileAction.updateCurrentUserData(getReceptionistFromView());
                        break;
                    case PATIENT:
                        ProfileAction.updateCurrentUserData(getPatientDataFromView());
                        break;
                    case MEDICALOFFICER:
                        ProfileAction.updateCurrentUserData(getMedicalOffFromView());
                        break;
                    default:
                        break;
                }
            }
        });

    }

    public void setInitialView(){
        profileView_userdataGrid.setDisable(true);
        profileView_patientExtraGrid.setDisable(true);
        profileView_doctorExtraGrid.setDisable(true);
    }

    public void setUserProfileData(SystemUser systemUser){
        switch (systemUser.getUserRoll()){
            case ADMIN :
                setViewForAdmin();
                setAdminDataToView(systemUser.getAdmin());
                break;
            case RECEPTIONIST:
                setViewForReception();
                setReceptionDataToView(systemUser.getReceptionist());
                break;
            case PATIENT:
                System.out.println("setUser for patient called");
                setViewForPatient();
                setPatientDataToView(systemUser.getPatient());
                break;
            case MEDICALOFFICER:
                setViewForMedicalOfficer();
                setMedicalDataToView(systemUser.getMedicalOfficer());
                break;
            default:
                break;

        }
    }

    private void setAdminDataToView(Admin admin) {
        profile_name.setText(admin.getName());
        profile_nicNumber.setText(admin.getIdCardNumber());
        profile_phoneNumber.setText(admin.getPhoneNumber());
        profile_address.setText(admin.getAddress());
        profile_maritalDrop.setValue(admin.getMaritalStatus());
        profile_genderDrop.setValue(admin.getGender());
        profile_dobDatePicker.setValue(admin.getDob());
        profile_userName.setText(UserAction.decryptUserData(admin.getUserName()));
        profile_userPassword.setText(UserAction.decryptUserData(admin.getUserPassword()));
    }

    public void setPatientDataToView(Patient passPatient){
        System.out.println( "setViewForPatient();-----> method called");
        profile_name.setText(passPatient.getName());
        profile_nicNumber.setText(passPatient.getIdCardNumber());
        profile_phoneNumber.setText(passPatient.getPhoneNumber());
        profile_address.setText(passPatient.getAddress());
        profile_maritalDrop.setValue(passPatient.getMaritalStatus());
        profile_dobDatePicker.setValue(passPatient.getDob());
        profile_userName.setText(UserAction.decryptUserData(passPatient.getUserName()));
        profile_userPassword.setText(UserAction.decryptUserData(passPatient.getUserPassword()));
        profile_bloodGropDrop.setValue(passPatient.getBloodGroup());
        profile_allergies.setText(passPatient.getAllergies());
    }

    public void setReceptionDataToView(Receptionist currentReceptionist){
        profile_name.setText(currentReceptionist.getName());
        profile_nicNumber.setText(currentReceptionist.getIdCardNumber());
        profile_phoneNumber.setText(currentReceptionist.getPhoneNumber());
        profile_address.setText(currentReceptionist.getAddress());
        profile_dobDatePicker.setValue(currentReceptionist.getDob());
        profile_maritalDrop.setValue(currentReceptionist.getMaritalStatus());
        profile_userName.setText(UserAction.decryptUserData(currentReceptionist.getUserName()));
        profile_userPassword.setText(UserAction.decryptUserData(currentReceptionist.getUserPassword()));
        profile_staffID.setText(String.valueOf(currentReceptionist.getStaffID()));
        profile_emailAddress.setText(currentReceptionist.getStaffEmailAddress());
        profile_dateOfJoin.setValue(currentReceptionist.getDateOfJoining());
    }

    public void setMedicalDataToView(MedicalOfficer medicalOfficerData){
        profile_name.setText(medicalOfficerData.getName());
        profile_nicNumber.setText(medicalOfficerData.getIdCardNumber());
        profile_phoneNumber.setText(medicalOfficerData.getPhoneNumber());
        profile_address.setText(medicalOfficerData.getAddress());
        profile_maritalDrop.setValue(medicalOfficerData.getMaritalStatus());
        profile_dobDatePicker.setValue(medicalOfficerData.getDob());
        profile_userName.setText(UserAction.decryptUserData(medicalOfficerData.getUserName()));
        profile_userPassword.setText(UserAction.decryptUserData(medicalOfficerData.getUserPassword()));
        profile_staffID.setText(String.valueOf(medicalOfficerData.getStaffID()));
        profile_emailAddress.setText(medicalOfficerData.getStaffEmailAddress());
        profile_dateOfJoin.setValue(medicalOfficerData.getDateOfJoining());
        profile_specialityDrop.setValue(medicalOfficerData.getSpeciality());
    }

    private void setViewForAdmin() {
        profileView_patientExtraGrid.setVisible(false);
        profileView_doctorExtraGrid.setVisible(false);
        profileView_empExtraGrid.setVisible(false);
        profileView_userdataGrid.setDisable(true);
    }

    private void setViewForReception() {
        profileView_patientExtraGrid.setVisible(false);
        profileView_empExtraGrid.setDisable(true);
        profileView_doctorExtraGrid.setVisible(false);
        profileView_userdataGrid.setDisable(true);
    }

    private void setViewForPatient(){
        profileView_patientExtraGrid.setVisible(true);
        profileView_patientExtraGrid.setDisable(true);
        profileView_empExtraGrid.setVisible(false);
        profileView_doctorExtraGrid.setVisible(false);
        profileView_userdataGrid.setDisable(true);
        profileView_userdataGrid.setVisible(true);
    }

    private void setViewForMedicalOfficer(){
        profileView_patientExtraGrid.setVisible(false);
        profileView_empExtraGrid.setDisable(true);
        profileView_doctorExtraGrid.setDisable(true);
        profileView_userdataGrid.setDisable(true);
    }

    private SystemUser getPatientDataFromView(){
        SystemUser systemUser = new SystemUser();
        systemUser.setUserRoll(UserRoll.PATIENT);
        Patient patient=new Patient();
        patient.setUserRoll(Main.getCurrentSystemUser().getUserRoll());
        patient.setName(profile_name.getText());
        patient.setIdCardNumber(profile_nicNumber.getText());
        patient.setAddress(profile_address.getText());
        patient.setPhoneNumber(profile_phoneNumber.getText());
        patient.setMaritalStatus(profile_maritalDrop.getValue());
        patient.setUserName(UserAction.encryptUserData(profile_userName.getText()));
        patient.setUserPassword(UserAction.encryptUserData(profile_userPassword.getText()));
        patient.setBloodGroup(profile_bloodGropDrop.getValue());
        patient.setAllergies(profile_allergies.getText());

        systemUser.setPatient(patient);

        return systemUser;
    }

    private SystemUser getAdminDataFromView(){
        SystemUser adminUser =new SystemUser();
        Admin admin =new Admin();

        adminUser.setUserRoll(UserRoll.ADMIN);
        admin.setUserRoll(Main.getCurrentSystemUser().getUserRoll());
        admin.setGender(profile_genderDrop.getValue());
        admin.setDob(profile_dobDatePicker.getValue());
        admin.setName(profile_name.getText());
        admin.setIdCardNumber(profile_nicNumber.getText());
        admin.setPhoneNumber(profile_phoneNumber.getText());
        admin.setAddress(profile_address.getText());
        admin.setMaritalStatus(profile_maritalDrop.getValue());
        admin.setUserName(UserAction.encryptUserData(profile_userName.getText()));
        admin.setUserPassword(UserAction.encryptUserData(profile_userPassword.getText()));

        adminUser.setAdmin(admin);
        return adminUser;
    }

    private SystemUser getReceptionistFromView(){
        SystemUser systemUser=new SystemUser();
        Receptionist receptionist=new Receptionist();

        systemUser.setUserRoll(UserRoll.RECEPTIONIST);
        receptionist.setUserRoll(Main.getCurrentSystemUser().getUserRoll());
        receptionist.setGender(profile_genderDrop.getValue());
        receptionist.setDob(profile_dobDatePicker.getValue());
        receptionist.setName(profile_name.getText());
        receptionist.setIdCardNumber(profile_nicNumber.getText());
        receptionist.setAddress(profile_address.getText());
        receptionist.setPhoneNumber(profile_phoneNumber.getText());
        receptionist.setMaritalStatus(profile_maritalDrop.getValue());
        receptionist.setUserName(UserAction.encryptUserData(profile_userName.getText()));
        receptionist.setUserPassword(UserAction.encryptUserData(profile_userPassword.getText()));
        receptionist.setStaffID(Integer.parseInt(profile_staffID.getText()));
        receptionist.setStaffEmailAddress(profile_emailAddress.getText());
        receptionist.setDateOfJoining(profile_dateOfJoin.getValue());

        systemUser.setReceptionist(receptionist);

        return systemUser;

    }

    public SystemUser getMedicalOffFromView(){
        SystemUser systemUser =new SystemUser();
        MedicalOfficer medicalOfficer = new MedicalOfficer();

        systemUser.setUserRoll(UserRoll.MEDICALOFFICER);
        medicalOfficer.setUserRoll(Main.getCurrentSystemUser().getUserRoll());
        medicalOfficer.setName(profile_name.getText());
        medicalOfficer.setDob(profile_dobDatePicker.getValue());
        medicalOfficer.setGender(profile_genderDrop.getValue());
        medicalOfficer.setIdCardNumber(profile_nicNumber.getText());
        medicalOfficer.setAddress(profile_address.getText());
        medicalOfficer.setPhoneNumber(profile_phoneNumber.getText());
        medicalOfficer.setMaritalStatus(profile_maritalDrop.getValue());
        medicalOfficer.setUserName(UserAction.encryptUserData(profile_userName.getText()));
        medicalOfficer.setUserPassword(UserAction.encryptUserData(profile_userPassword.getText()));
        medicalOfficer.setStaffID(Integer.parseInt(profile_staffID.getText()));
        medicalOfficer.setStaffEmailAddress(profile_emailAddress.getText());
        medicalOfficer.setDateOfJoining(profile_dateOfJoin.getValue());
        medicalOfficer.setSpeciality(profile_specialityDrop.getValue());

        systemUser.setMedicalOfficer(medicalOfficer);

        return systemUser;
    }
}
