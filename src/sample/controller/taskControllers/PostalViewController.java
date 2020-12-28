package sample.controller.taskControllers;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.util.StringConverter;
import sample.controller.actionTask.PostalAction;
import sample.controller.actionTask.ReferenceAction;
import sample.controller.actionTask.UserAction;
import sample.model.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static sample.model.UserRoll.ADMIN;

public class PostalViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label postalView_addressLable;

    @FXML
    private Label postalView_fromAddressLable;

    @FXML
    private TableView<?> postalView_userTable;

    @FXML
    private JFXButton postalView_addPostal;

    @FXML
    private JFXButton postalView_searchPostal;


    @FXML
    private JFXButton postalView_updatePostal;

    @FXML
    private JFXButton postalView_deletePostal;

    @FXML
    private JFXButton postalView_viewAll;

    @FXML
    private JFXButton postalView_reset;

    @FXML
    private JFXComboBox<PostalType> postalView_type;

    @FXML
    private JFXTextField postalView_refecenceNo;

    @FXML
    private JFXTextField postalView_refecenceNo2;

    @FXML
    private JFXTextField postalView_toName;

    @FXML
    private JFXTextField postalView_fromName;

    @FXML
    private JFXTextArea postalView_note;

    @FXML
    private JFXTextArea postalView_address;

    @FXML
    private JFXTextArea postalView_fromAddress;

    @FXML
    private DatePicker postalView_date;



    @FXML
    void initialize() {
        postalView_type.getItems().addAll( ReferenceAction.getPostalTypes());

        postalView_type.setOnAction( new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                switch (postalView_type.getValue()) {
                    case RECEIVED:
                        setViewForReceived();
                        break;
                    case DISPATCH:
                        setViewForDispatch();
                        break;


                    default:
                        break;
                }
            }
        } );

        postalView_addPostal.setOnAction( new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                postalView_date.setConverter( new StringConverter<LocalDate>() {
                    String pattern = "yyyy-MM-dd";
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern( pattern );

                    {
                        postalView_date.setPromptText( pattern.toLowerCase() );
                    }

                    @Override
                    public String toString(LocalDate date) {
                        if (date != null) {
                            return dateFormatter.format( date );
                        } else {
                            return "";
                        }
                    }

                    @Override
                    public LocalDate fromString(String string) {
                        if (string != null && !string.isEmpty()) {
                            return LocalDate.parse( string, dateFormatter );
                        } else {
                            return null;
                        }
                    }
                } );

                switch (postalView_type.getValue()) {
                    case RECEIVED:
                        PostalAction.addReceived( getReceived(), ADMIN );
                        break;

                    case DISPATCH:

                        PostalAction.addDispatch( getDispatch(), ADMIN );
                        break;


                    default:
                        throw new IllegalStateException("Unexpected value: " + postalView_type.getValue());
                }
            }
        } );

        postalView_searchPostal.setOnAction( new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String serachID = postalView_refecenceNo2.getText();
                switch (postalView_type.getValue()) {
                    case RECEIVED:
                        ReceivedPostal receivedPostal = PostalAction.searchReceived( serachID);
                        displayReceivedData( receivedPostal );
                        break;
                    case DISPATCH:
                        DispatchPostal dispatchPostal = PostalAction.searchDispatch( serachID);
                        displayDispatchData( dispatchPostal );
                        break;
                    default:
                        throw new IllegalStateException( "Unexpected value: " + postalView_type.getValue() );
                }

            }
        } );

        postalView_deletePostal.setOnAction( new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                PostalAction.deleteUserRecord( postalView_type.getValue(), postalView_refecenceNo2.getText(),
                        UserRoll.ADMIN);
            }
        } );

        postalView_updatePostal.setOnAction( new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                switch (postalView_type.getValue()) {
                    case RECEIVED:
                        PostalAction.updateReceivedRecord( UserRoll.RECEPTIONIST,
                                getReceived(),
                                postalView_refecenceNo2.getText()
                        );
                        break;
                    case DISPATCH:
                        PostalAction.updateDispachRecord( UserRoll.RECEPTIONIST, getDispatch(), postalView_refecenceNo2.getText() );
                        break;

                    default:
                        break;
                }

            }
        } );

        postalView_reset.setOnAction( new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                resetDisplay();
            }
        } );
    }

   /* private LoginUser getLoginUser(JFXBadge userView_userName, JFXBadge userView_userPassword) {
        LoginUser loginUser = new LoginUser();
        loginUser.setUserName( UserAction.encryptUserData( userView_userName.getText() ) );
        loginUser.setUserPassword( UserAction.encryptUserData( userView_userPassword.getText() ) );

        return loginUser;
    }*/

    public void displayReceivedData(ReceivedPostal receivedPostal) {
        postalView_type.setValue( receivedPostal.getPostalType() );
        postalView_refecenceNo.setText( receivedPostal.getReferenceNo() );
        postalView_toName.setText( receivedPostal.getToName() );
        postalView_fromName.setText( receivedPostal.getFromName() );
        postalView_fromAddress.setText( receivedPostal.getFromAddress() );
        postalView_date.setValue( receivedPostal.getDate() );
        postalView_note.setText( receivedPostal.getNote() );
    }

    public void displayDispatchData(DispatchPostal dispatchPostal) {
        postalView_type.setValue( dispatchPostal.getPostalType() );
        postalView_refecenceNo.setText( dispatchPostal.getReferenceNo() );
        postalView_toName.setText( dispatchPostal.getToName() );
        postalView_fromName.setText( dispatchPostal.getFromName() );
        postalView_address.setText( dispatchPostal.getAddress() );
        postalView_date.setValue( dispatchPostal.getDate() );
        postalView_note.setText( dispatchPostal.getNote() );

    }


    public ReceivedPostal getReceived() {
        ReceivedPostal receivedPostal = new ReceivedPostal();
        receivedPostal.setPostalType( postalView_type.getValue() );
        receivedPostal.setReferenceNo( postalView_refecenceNo.getText() );
        receivedPostal.setToName( postalView_toName.getText() );
        receivedPostal.setFromName( postalView_fromName.getText() );
        receivedPostal.setFromAddress( postalView_fromAddress.getText() );
        receivedPostal.setDate( postalView_date.getValue() );
        receivedPostal.setNote( postalView_note.getText() );


        return receivedPostal;

    }

    public DispatchPostal getDispatch() {
        DispatchPostal dispatchPostal = new DispatchPostal();
        dispatchPostal.setPostalType( postalView_type.getValue() );
        dispatchPostal.setReferenceNo( postalView_refecenceNo.getText() );
        dispatchPostal.setToName( postalView_toName.getText() );
        dispatchPostal.setFromName( postalView_fromName.getText() );
        dispatchPostal.setAddress( postalView_address.getText() );
        dispatchPostal.setDate( postalView_date.getValue() );
        dispatchPostal.setNote( postalView_note.getText() );

        return dispatchPostal;
    }


    public void resetDisplay() {
        postalView_type.setValue( null );
        postalView_refecenceNo.clear();
        postalView_refecenceNo2.clear();
        postalView_toName.clear();
        postalView_fromName.clear();
        postalView_address.clear();
        postalView_fromAddress.clear();
        postalView_date.setValue( null );
        postalView_note.clear();


    }


    public void setViewForReceived() {
        postalView_refecenceNo.setDisable( false );
        postalView_date.setDisable( false );
        postalView_note.setDisable( false );
        postalView_fromAddress.setDisable( false );
        postalView_toName.setDisable( false );
        postalView_fromName.setDisable( false );

        postalView_address.setDisable( true );
        postalView_addressLable.setDisable( true );

    }

    public void setViewForDispatch() {
        postalView_refecenceNo.setDisable( false );
        postalView_date.setDisable( false );
        postalView_note.setDisable( false );
        postalView_address.setDisable( false );
        postalView_toName.setDisable( false );
        postalView_fromName.setDisable( false );

        postalView_fromAddress.setDisable( true );
        postalView_fromAddressLable.setDisable( true );
    }


}
