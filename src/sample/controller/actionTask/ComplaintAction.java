package sample.controller.actionTask;

import sample.Main;
import sample.controller.SystemDataReader;
import sample.controller.taskControllers.SystemDataWriter;
import sample.model.Complaint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ComplaintAction {

    private static String complaintFilePath= "src/sample/fileStorage/moduleData/complaintData.txt";

    public static void addComplaintRecord(Complaint complaint){
        addComplaint(complaint);
    }

    public static void updateComplaintRecord(Complaint complaint){
        editDeleteComplaint(complaint,1);
    }

    public static void deleteComplaintRecord(Complaint complaint){
        editDeleteComplaint(complaint,10);
    }

    public static Complaint searchComplaintRecord(String complaintID,String complaintBy){
        return searchComplaint(complaintID,complaintBy);
    }

    public static ArrayList<Complaint> getAllComplaintRecords(){
        return getAllComplaints();
    }

    private static Complaint searchComplaint(String complaintID, String vName){
        ArrayList<Complaint> allComplaint =getAllComplaints();
        Complaint foundComplaint =null;
        for (int i=0;i<allComplaint.size();i++){
            if (String.valueOf(allComplaint.get(i).getComplaintID()).equals(complaintID) ||
                    allComplaint.get(i).getComplaintBy().equals(vName)){
                foundComplaint=allComplaint.get(i);
                System.out.println("Complaint Record Found in Complaint file");
            }
        }

        return foundComplaint;
    }


    private static void addComplaint(Complaint complaint){
        SystemDataWriter systemDataWriter =new SystemDataWriter();
        systemDataWriter.writeDataToFile(complaint.toString(),complaintFilePath,5);
    }

    //operation =1 will update record any other integer will delete record
    private static void editDeleteComplaint(Complaint complaint,int operationType){
        ArrayList<Complaint> finalEditedDataArray = new ArrayList<>();
        ArrayList<Complaint> allComplaints =getAllComplaints();

        for (int i=0;i<allComplaints.size();i++){
            Complaint tempComplaint =allComplaints.get(i);
            if (tempComplaint.getComplaintID() == complaint.getComplaintID()){
                if(operationType==1){
                    finalEditedDataArray.add(complaint);
                    System.out.println("Complaint Update Record added Success");
                }else {
                    System.out.println("Complaint Deleted Success");
                }
            }
            finalEditedDataArray.add(allComplaints.get(i));
        }

        SystemDataWriter systemDataWriter = new SystemDataWriter();
        ArrayList<String> stringComplaintArray =getStringComplaintArray(finalEditedDataArray);
        systemDataWriter.writeDataToFile(stringComplaintArray,complaintFilePath,10);

    }

    private static ArrayList<String> getStringComplaintArray(ArrayList<Complaint> finalEditedDataArray) {
        ArrayList<String> stringArrayList = new ArrayList<>();
        for (int i=0;i<finalEditedDataArray.size();i++){
            stringArrayList.add(finalEditedDataArray.get(i).toString());
        }

        return stringArrayList;
    }

    private static ArrayList<Complaint> getAllComplaints() {
        SystemDataReader systemDataReader =new SystemDataReader();
        ArrayList<String> coplaintStrngArray=systemDataReader.getTempDataArray(complaintFilePath);
        ArrayList<Complaint> complaintArrayList = getComplaintArrayByStringArray(coplaintStrngArray);

        return  complaintArrayList;
    }

    private static ArrayList<Complaint> getComplaintArrayByStringArray(ArrayList<String> complaintStrngArray) {
        ArrayList<Complaint> complaintArrayList = new ArrayList<>();

        for (int i=0;i<complaintStrngArray.size();i++){
            String line =complaintStrngArray.get(i);
            Complaint complaint =getComplaintByStringLine(line);
            complaintArrayList.add(complaint);
        }
        System.out.println("complaint Arrya passed from getComplaintArrayByStringArray()---->");
        return complaintArrayList;
    }

    private static Complaint getComplaintByStringLine(String line) {
        Complaint complaint =new Complaint();
        List<String> tempComplant = Arrays.asList(line.split("[~\n]"));

        complaint.setComplaintID(Integer.parseInt(tempComplant.get(0)));
        complaint.setComplaintDate(Main.getLocalDatefromString(tempComplant.get(1)));
        complaint.setComplaintType(tempComplant.get(2));
        complaint.setComplaintBy(tempComplant.get(3));
        complaint.setPhoneNumber(tempComplant.get(4));
        complaint.setDescription(tempComplant.get(5));
        complaint.setNote(tempComplant.get(6));
        complaint.setActiontaken(tempComplant.get(7));

        return complaint;
    }
}
