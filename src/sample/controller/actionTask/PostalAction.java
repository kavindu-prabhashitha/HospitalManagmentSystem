package sample.controller.actionTask;

import sample.Main;
import sample.controller.SystemDataReader;
import sample.controller.taskControllers.SystemDataWriter;
import sample.model.Postal;
import sample.model.PostalType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PostalAction{

    private static String postalDataFile ="src/sample/fileStorage/moduleData/postalData.txt";


    public static void addPostaRecord(Postal postal){
        addPostal(postal);
    }

    public static void updatePostalRecord(Postal postal){
        System.out.println("passes postal from viewController "+postal);
        editDeletePostal(postal,1);
    }

    public static void deletePostalRecord(Postal postal){
        editDeletePostal(postal,5);
    }

    public static Postal searchPostalRecord(int postalReference){
        return searchPostal(postalReference);
    }

    public static  ArrayList<Postal>  getAllPostals(){
        return  getAllPostalFromFile();
    }



    private static void addPostal(Postal postal){
        SystemDataWriter systemDataWriter =new SystemDataWriter();
        systemDataWriter.writeDataToFile(postal.toString(),postalDataFile,5);
    }

    private static void editDeletePostal(Postal postal,int operation){
        SystemDataReader systemDataReader =new SystemDataReader();
        ArrayList<Postal> editedorDeletedArray =new ArrayList<>();
        ArrayList<String> allPostalStrinArray = systemDataReader.getTempDataArray(postalDataFile);
        ArrayList<Postal> getAllPostals =getAllPostalFromString(allPostalStrinArray);

        System.out.println("getAllPostalFromString()-->"+getAllPostals);

        for (int i=0;i<getAllPostals.size();i++){
            if(getAllPostals.get(i).getReferenceNo()==(postal.getReferenceNo())){
                if (operation==1){
                    editedorDeletedArray.add(postal);
                    System.out.println("postal updated success in --->  editDeletePostal() "+postal);
                }else {
                    System.out.println("postal deleted success");
                }
            }else {
                editedorDeletedArray.add(getAllPostals.get(i));
            }

        }

        SystemDataWriter systemDataWriter =new SystemDataWriter();
        ArrayList<String> strinPostalArray = getStringArrayfromPostals(editedorDeletedArray);
        systemDataWriter.writeDataToFile(strinPostalArray,postalDataFile,10);

    }

    private static Postal searchPostal(int searchTerm){
        ArrayList<Postal> allPostals = getAllPostalFromFile();
        Postal foundPostal =null;

        for (int i=0;i<allPostals.size();i++){
            if (allPostals.get(i).getReferenceNo()==(searchTerm)){
                foundPostal=allPostals.get(i);
                System.out.println("Record found for search ----->searchTerm "+foundPostal);
            }
        }

        return foundPostal;
    }

    private static ArrayList<Postal> getAllPostalFromFile(){
        ArrayList<Postal> allPostalRecords = new ArrayList<>();
        SystemDataReader systemDataReader=new SystemDataReader();

        ArrayList<String> stringArrayList =systemDataReader.getTempDataArray(postalDataFile);
        allPostalRecords=getAllPostalFromString(stringArrayList);

        return allPostalRecords;
    }

    private static ArrayList<String> getStringArrayfromPostals(ArrayList<Postal> editedorDeletedArray) {
        ArrayList<String> strpostalArrayList =new ArrayList<>();
        for (int i=0;i<editedorDeletedArray.size();i++){
            strpostalArrayList.add(editedorDeletedArray.get(i).toString());
        }

        return strpostalArrayList;
    }

    private static ArrayList<Postal> getAllPostalFromString(ArrayList<String> allPostalStrinArray) {
        ArrayList<Postal> finalPostalArray =new ArrayList<>();
        for (int i=0;i<allPostalStrinArray.size();i++){
            String line =allPostalStrinArray.get(i);
            Postal postal = getPostalByStringLine(line);
            finalPostalArray.add(postal);
        }

        System.out.println("getAllPostalFromString()--->"+finalPostalArray.toString());
        return finalPostalArray;
    }

    private static Postal getPostalByStringLine(String line) {
        List<String> tempPostal = Arrays.asList(line.split("[~\n]"));

        Postal postal =new Postal();

        postal.setPostalType(getPostalType(tempPostal.get(0)));
        postal.setReferenceNo(Integer.parseInt(tempPostal.get(1)));
        postal.setDate(Main.getLocalDatefromString(tempPostal.get(2)));
        postal.setName(tempPostal.get(3));
        postal.setAddress(tempPostal.get(4));
        postal.setNote(tempPostal.get(5));

        return postal;

    }

    private static PostalType getPostalType(String postalType){
        PostalType postal=null;
        switch (postalType){
            case "RECEIVED":
                postal=PostalType.RECEIVED;
                break;
            case "DISPATCH":
                postal=PostalType.DISPATCH;
                break;
            default:
                break;

        }
        return postal;
    }
}
