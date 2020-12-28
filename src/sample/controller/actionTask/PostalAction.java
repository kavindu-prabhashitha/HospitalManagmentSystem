package sample.controller.actionTask;

import sample.model.DispatchPostal;
import sample.model.PostalType;
import sample.model.ReceivedPostal;
import sample.model.UserRoll;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static sample.controller.actionTask.UserAction.*;

//create a postalAction class

public class PostalAction<searchedReceived> {

    //received postal data recoded in receivedData.txt file
    public static String receivedFilePath = "src/sample/fileStorage/moduleData/postalData/receivedData.txt";

    //dispatch postal data recoded in dispatch .txt file
    public static String dispatchFilePath = "src/sample/fileStorage/moduleData/postalData/dispatchData.txt";

    //get PostalType enum object with the String type input of the pName
    public static PostalType getPostalType(String pName) {
        PostalType postalType = null;
        switch (pName) {
            case "RECEIVED":
                postalType = PostalType.RECEIVED;
                break;

            case "DISPATCH":
                postalType = PostalType.DISPATCH;
                break;
        }
        return postalType;
    }

     /* =============================================================================================================
       RECEIVED Action tasks
      =============================================================================================================
    */

    public static void addReceived(ReceivedPostal receivedPostal, UserRoll roll) {

        if (roll.equals(UserRoll.ADMIN) || roll.equals(UserRoll.RECEPTIONIST)) {
            saveReceived(receivedPostal);
        }

    }


    private static void saveReceived(ReceivedPostal receivedPostal) {
        File receivedfile = new File(receivedFilePath);

        try (FileWriter fileWriter = new FileWriter(receivedfile, true)) {
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(receivedPostal.toString());
            bufferedWriter.newLine();
            bufferedWriter.close();
            fileWriter.close();

            System.out.println("file path : " + receivedfile.getPath() + " Received Postal saved");
            //addUserLoginData(patientloginData, new LoginUser(user.getUserName(), user.getUserPassword()));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    public static void updateReceivedRecord(UserRoll userRoll, ReceivedPostal receivedPostal, String searchedID) {
        if (userRoll.equals(UserRoll.RECEPTIONIST) || userRoll.equals(UserRoll.ADMIN)) {
            editReceivedRecord(receivedFilePath, receivedPostal, searchedID);
        } else {

            System.out.println("acces denied(cannot update)");
        }
    }

    private static void editReceivedRecord(String filePath, ReceivedPostal receivedRecord, String searchReceivedid) {

        ArrayList<String> tempReceivedList = new ArrayList<>();

        File file = new File(filePath);
        boolean found = false;
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                List<String> tempList = Arrays.asList(line.split("~"));

                if (tempList.get(1).equals(searchReceivedid)) {
                    found = true;
                    line = receivedRecord.toString();
                    tempReceivedList.add(line);
                } else {
                    tempReceivedList.add(line);
                }
            }

            bufferedReader.close();
            fileReader.close();

            if (found) {
                try {

                    File fileNew = new File(receivedFilePath);
                    if (file.exists()) {

                        file.delete();
                    }
                    file.createNewFile();

                    FileWriter fileWriter = new FileWriter(fileNew);
                    BufferedWriter newbufferedWriter = new BufferedWriter(fileWriter);
                    for (int i = 0; i < tempReceivedList.size(); i++) {
                        newbufferedWriter.write(tempReceivedList.get(i));
                        newbufferedWriter.newLine();
                    }
                    newbufferedWriter.close();
                    fileWriter.close();
                    System.out.println("Received edited  success");
                    System.out.println(tempReceivedList.toString());
                    //  updateUserLoginData(patientloginData, loginUser);


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ReceivedPostal searchReceived(String seachTerm) {
        ReceivedPostal foundReceived = searchReceivedRecord(seachTerm);
        System.out.println("return Received : " + foundReceived.toString());
        return foundReceived;
    }

    private static ReceivedPostal searchReceivedRecord(String searchedItem ){

        boolean found = false;
        ReceivedPostal searchedReceived = new ReceivedPostal();
        List<String> temp = new ArrayList<>();

        File dispatchFile = new File(dispatchFilePath);
        if (dispatchFile != null) {
            try (FileReader fileReader = new FileReader(dispatchFile)) {
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    List<String> tempList = Arrays.asList(line.split("~"));

                    if (searchedItem.equals( tempList.get( 1 ) )&& !found) {


                        found = true;

                        temp = tempList;
                        System.out.println("received Record found in dispatchFilePath.txt");
                    }
                }
                if (found) {
                    System.out.println("received record found :" + temp.toString());
                    searchedReceived.setPostalType(getPostalType((temp.get(0))));
                    searchedReceived.setReferenceNo(temp.get(1));
                    searchedReceived.setToName(temp.get(2));
                    searchedReceived.setFromName(temp.get(3));
                    searchedReceived.setDate(getLocalDatefromString(temp.get(4)));
                    searchedReceived.setNote(temp.get(5));
                    searchedReceived.setFromAddress(temp.get(6));

                    System.out.println("search found :" + searchedReceived);
                }


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return searchedReceived;
    }



    public static ArrayList<ReceivedPostal> getAllReceived() {
        ArrayList<ReceivedPostal> allReceivedRecords = new ArrayList<>();
        File newReceivedFile = new File(receivedFilePath);
        String receivedRecord = null;
        try (FileReader adminFileReader = new FileReader(newReceivedFile)){
            BufferedReader receivedBufferedRedaer = new BufferedReader(adminFileReader);

            while ((receivedRecord = receivedBufferedRedaer.readLine()) != null) {
                List<String> tempReceivedList = Arrays.asList(receivedRecord.split("~"));
                System.out.println(tempReceivedList.toString());
                ReceivedPostal receivedPostal = getReceivedFromList(tempReceivedList);
                allReceivedRecords.add(receivedPostal);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return allReceivedRecords;

    }

    private static ReceivedPostal getReceivedFromList(List<String> tenpReceivedRec) {
        ReceivedPostal returnReceived = new ReceivedPostal();

        returnReceived.setPostalType(getPostalType(tenpReceivedRec.get(0)));
        returnReceived.setReferenceNo(tenpReceivedRec.get(1));
        returnReceived.setToName(tenpReceivedRec.get(2));
        returnReceived.setFromName(tenpReceivedRec.get(3));
        returnReceived.setFromAddress(tenpReceivedRec.get(4));
        returnReceived.setDate(getLocalDatefromString(tenpReceivedRec.get(5)));
        returnReceived.setNote(tenpReceivedRec.get(6));

        return returnReceived;
    }




    /* =============================================================================================================
       DISPATCH Action tasks
      =============================================================================================================
    */


    public static void addDispatch(DispatchPostal dispatchPostal, UserRoll roll) {

        if (roll.equals(UserRoll.ADMIN) || roll.equals(UserRoll.RECEPTIONIST)) {
            saveDispatch(dispatchPostal);
        }

    }


    private static void saveDispatch(DispatchPostal dispatchPostal) {
        File dispatchfile = new File(dispatchFilePath);

        try (FileWriter fileWriter = new FileWriter(dispatchfile, true)) {
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(dispatchPostal.toString());
            bufferedWriter.newLine();
            bufferedWriter.close();
            fileWriter.close();

            System.out.println("file path : " + dispatchfile.getPath() + " dispatch Postal saved");
            //addUserLoginData(patientloginData, new LoginUser(user.getUserName(), user.getUserPassword()));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    public static void updateDispachRecord(UserRoll userRoll, DispatchPostal dispatchPostal, String searchedID) {
        if (userRoll.equals(UserRoll.RECEPTIONIST) || userRoll.equals(UserRoll.ADMIN)) {
            editDispatchRecord(dispatchFilePath, dispatchPostal, searchedID);
        } else {

            System.out.println("acces denied(cannot update)");
        }
    }

    private static void editDispatchRecord(String filePath, DispatchPostal dispatchPostal, String searchDispatchid) {

        ArrayList<String> tempDispatchList = new ArrayList<>();

        File file = new File(filePath);
        boolean found = false;
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                List<String> tempList = Arrays.asList(line.split("~"));

                if (!tempList.get(1).equals(searchDispatchid)) {
                    tempDispatchList.add(line);
                } else {
                    found = true;
                    line = dispatchPostal.toString();
                    tempDispatchList.add(line);
                }
            }

            bufferedReader.close();
            fileReader.close();

            if (found) {
                try {

                    File fileNew = new File(dispatchFilePath);
                    if(file.exists()){

                        file.delete();
                    }
                    file.createNewFile();

                    FileWriter fileWriter = new FileWriter(fileNew);
                    BufferedWriter newbufferedWriter = new BufferedWriter(fileWriter);
                    for (int i = 0; i < tempDispatchList.size(); i++) {
                        newbufferedWriter.write(tempDispatchList.get(i));
                        newbufferedWriter.newLine();
                    }
                    newbufferedWriter.close();
                    fileWriter.close();
                    System.out.println("DispatchList edited  success");
                    System.out.println(tempDispatchList.toString());
                   // updateUserLoginData(patientloginData,loginUser);


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static DispatchPostal searchDispatch(String seachTerm) {
        DispatchPostal foundDispatch;
        foundDispatch = searchDispatchRecord( seachTerm );
        System.out.println("return Dispatch : " + foundDispatch.toString());
        return foundDispatch;
    }

    private static DispatchPostal searchDispatchRecord(String searchTerm) {


        boolean found = false;
        DispatchPostal searchedDispatch = new DispatchPostal();
        List<String> temp = new ArrayList<>();

        File dispatchFile = new File(dispatchFilePath);
        if (dispatchFile != null) {
            try (FileReader fileReader = new FileReader(dispatchFile)) {
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    List<String> tempList = Arrays.asList(line.split("~"));

                    if (searchTerm.equals( tempList.get( 1 ) )|| found) {
                        continue;
                    }
                    found = true;

                    temp = tempList;
                    System.out.println("dispatch Record found in dispatchFilePath.txt");
                }
                if (found) {
                    System.out.println("dispatch record found :" + temp.toString());
                    searchedDispatch.setPostalType(getPostalType((temp.get(0))));
                    searchedDispatch.setReferenceNo(temp.get(1));
                    searchedDispatch.setToName(temp.get(2));
                    searchedDispatch.setFromName(temp.get(3));
                    searchedDispatch.setDate(getLocalDatefromString(temp.get(4)));
                    searchedDispatch.setNote(temp.get(5));
                    searchedDispatch.setAddress(temp.get(6));

                    System.out.println("search found :" + searchedDispatch);
                }


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return searchedDispatch;
    }




    public static ArrayList<DispatchPostal> getAllDispatch() {
        ArrayList<DispatchPostal> allDispatchRecords = new ArrayList<>();
        File newDispatchFile = new File(dispatchFilePath);
        String dispatchRecord = null;
        try (FileReader adminFileReader = new FileReader(newDispatchFile)){
            BufferedReader dispatchBufferedRedaer = new BufferedReader(adminFileReader);

            while ((dispatchRecord = dispatchBufferedRedaer.readLine()) != null) {
                List<String> tempDispatchList = Arrays.asList(dispatchRecord.split("~"));
                System.out.println(tempDispatchList.toString());
                DispatchPostal dispatchPostal = getDispatchFromList(tempDispatchList);
                allDispatchRecords.add(dispatchPostal);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return allDispatchRecords;
    }

    private static DispatchPostal getDispatchFromList(List<String> tenpDispatchRec) {
        DispatchPostal returnDispatch = new DispatchPostal();

        returnDispatch.setPostalType(getPostalType(tenpDispatchRec.get(0)));
        returnDispatch.setReferenceNo(tenpDispatchRec.get(1));
        returnDispatch.setToName(tenpDispatchRec.get(2));
        returnDispatch.setFromName(tenpDispatchRec.get(3));
        returnDispatch.setAddress(tenpDispatchRec.get(4));
        returnDispatch.setDate(getLocalDatefromString(tenpDispatchRec.get(5)));
        returnDispatch.setNote(tenpDispatchRec.get(6));

        return returnDispatch;
    }
    public static void deleteUserRecord(PostalType postalType, String searchTerm, UserRoll currentUserRoll){
        if (postalType.equals(PostalType.RECEIVED)){
            removePostalRecord(receivedFilePath,searchTerm);

        }else
        {
            removePostalRecord(dispatchFilePath,searchTerm);
        }
    }

    private static void removePostalRecord(String filePath, String serachTerm){
        ArrayList<String> tempPatientList =new ArrayList<>();
        File file = new File(filePath);
        boolean found =false;
        try{
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line =null;
            while ((line = bufferedReader.readLine()) != null) {
                List<String> tempList = Arrays.asList(line.split("~"));
                if(!tempList.get(6).equals(serachTerm)){
                    tempPatientList.add(line);
                }else {
                    found = true;
                }
            }

            bufferedReader.close();
            fileReader.close();

            if (found){
                try {

                    File fileNew = new File(filePath);
                    if(file.exists()){
                        file.delete();
                    }
                    file.createNewFile();

                    FileWriter fileWriter = new FileWriter(fileNew);
                    BufferedWriter newbufferedWriter = new BufferedWriter(fileWriter);
                    newbufferedWriter.write("");
                    for (int i=0;i<tempPatientList.size();i++){
                        newbufferedWriter.write(tempPatientList.get(i));
                        newbufferedWriter.newLine();

                    }
                    newbufferedWriter.close();
                    fileWriter.close();
                    System.out.println("User deleted success");
                    System.out.println(tempPatientList.toString());
                    //  deleteUserLoginData(loginDataPath,loginUser);


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
