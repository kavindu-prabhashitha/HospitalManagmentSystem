package sample.controller.actionTask;

import sample.model.*;

public class ProfileAction {

    public static void updateCurrentUserData(SystemUser systemUser){
        editUserAccount(systemUser);
    }

    private static void editUserAccount(SystemUser systemUser){
        switch (systemUser.getUserRoll()){
            case ADMIN:
                editAdminRecord(systemUser.getAdmin());
                break;
            case PATIENT:
                editPatientRecord(systemUser.getPatient());
                break;
            case RECEPTIONIST:
                editReceptionistRecord(systemUser.getReceptionist());
                break;
            case MEDICALOFFICER:
                editMedicalOfficer(systemUser.getMedicalOfficer());
                break;
            default:
                break;
            }
        }

    private static void editMedicalOfficer(MedicalOfficer medicalOfficer) {
        LoginUser loginUser=new LoginUser();
        loginUser.setUserPassword(medicalOfficer.getUserPassword());
        loginUser.setUserName(medicalOfficer.getUserName());
        UserAction.updateMedicalOfficerRecord(medicalOfficer.getUserRoll(),medicalOfficer,medicalOfficer.getIdCardNumber(),loginUser);
    }

    private static void editReceptionistRecord(Receptionist receptionist) {
        LoginUser loginUser =new LoginUser(receptionist.getUserName(),receptionist.getUserPassword());
        UserAction.updateReceptionRecord(receptionist.getUserRoll(),receptionist,receptionist.getIdCardNumber(),loginUser);
    }

    private static void editPatientRecord(Patient patient) {
        LoginUser loginUser=new LoginUser(patient.getUserName(),patient.getUserPassword());
        UserAction.updatePatientRecord(patient.getUserRoll(),patient,patient.getIdCardNumber(),loginUser);
    }

    private static void editAdminRecord(Admin admin) {
        LoginUser loginUser =new LoginUser(admin.getUserName(),admin.getUserPassword());
        UserAction.updateAdmin(admin.getUserRoll(),admin,admin.getIdCardNumber(),loginUser);
    }

}


