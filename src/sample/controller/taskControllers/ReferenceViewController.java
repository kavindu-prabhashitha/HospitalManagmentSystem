package sample.controller.taskControllers;
import sample.model.BloodGroup;
import sample.model.UserRoll;
import java.util.ArrayList;

public class ReferenceViewController {

   public static ArrayList<UserRoll> userRolls = new ArrayList<>();

   public static ArrayList<String> doctorSpeciality = new ArrayList<>();

   public static ArrayList<String> gender = new ArrayList<>();

   public static ArrayList<BloodGroup> bloogGroup = new ArrayList<>();

    public static ArrayList<String> maritalStatus = new ArrayList<>();

    public static void setUserRolls(UserRoll userRolls) {
        ReferenceViewController.userRolls.add(userRolls);
    }

    public static ArrayList<UserRoll> getUserRolls() {
        return userRolls;
    }

    public static void setDoctorSpeciality(String speciality) {
        doctorSpeciality.add(speciality);
    }

    public static ArrayList<String> getDoctorSpeciality() {
        return doctorSpeciality;
    }

    public static ArrayList<String> getGender() {
        return gender;
    }

    public static void setGender(String gender) {
        ReferenceViewController.gender.add(gender);
    }

    public static void setBloogGroup(BloodGroup bloogGroup) {
        ReferenceViewController.bloogGroup.add(bloogGroup);
    }

    public static ArrayList<BloodGroup> getBloogGroup() {
        return bloogGroup;
    }
    public static void setMaritalStatus(String status) {
        ReferenceViewController.maritalStatus.add(status);
    }

    public static ArrayList<String> getMaritalStatus() {
        return maritalStatus;
    }
}