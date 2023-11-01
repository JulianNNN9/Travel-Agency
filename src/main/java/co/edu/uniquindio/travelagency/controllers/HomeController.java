package co.edu.uniquindio.travelagency.controllers;

import co.edu.uniquindio.travelagency.exceptions.EmptyAttributeException;
import co.edu.uniquindio.travelagency.exceptions.UserNoExistingException;
import co.edu.uniquindio.travelagency.exceptions.WrongPasswordException;
import co.edu.uniquindio.travelagency.model.TravelAgency;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;

public class HomeController {

    private final TravelAgency travelAgency = TravelAgency.getInstance();

    @FXML
    public TextField txtFldID, txtFldPassword;
    @FXML
    private ImageView cerrarVentanaImgv;
    @FXML
    private Button homeBtn,paquetesBtn,guiasBtn,ayudasBtn,iniciaSecionBtn,btnLogIn,btnRegister;
    @FXML
    private AnchorPane homePane,nuestrosPaquetesPane,nuestrosGuiasPane,ayudaPane,iniciarsesionPane;

    public void initialize() {

        File file1 = new File("src/main/resources/icons/cerrarVentana.png");
        Image exitButton = new Image(String.valueOf(file1.toURI()));

        cerrarVentanaImgv.setImage(exitButton);

    }

    @FXML
    private void handleButtonAction(ActionEvent event){

        if (event.getTarget() == homeBtn){visibilities(true,false,false,false,false);}
        if (event.getTarget() == paquetesBtn){visibilities(false,true,false,false,false);}
        if (event.getTarget() == guiasBtn) {visibilities(false,false,true,false,false);}
        if (event.getTarget() == ayudasBtn) {visibilities(false, false, false, true, false);}
        if (event.getTarget() == iniciaSecionBtn) {visibilities(false,false,false,false,true);}

    }

    public void visibilities(boolean pane1, boolean pane2 , boolean pane3, boolean pane4, boolean pane5 ){

        homePane.setVisible(pane1);
        nuestrosPaquetesPane.setVisible(pane2);
        nuestrosGuiasPane.setVisible(pane3);
        ayudaPane.setVisible(pane4);
        iniciarsesionPane.setVisible(pane5);

    }

    public void onExitButtonClick() {

        Stage stage = (Stage) cerrarVentanaImgv.getScene().getWindow();
        stage.close();

    }

    public void onLogInButtonClick() throws UserNoExistingException, WrongPasswordException, EmptyAttributeException, IOException {

        String u = travelAgency.LogIn(txtFldID.getText(), txtFldPassword.getText());
        
        
        if(u.equals("Client")){
            travelAgency.generateWindow("src/main/resources/views/Client.fxml",cerrarVentanaImgv);
        } else if (u.equals("Admin")) {
            travelAgency.generateWindow("src/main/resources/views/adminView.fxml",cerrarVentanaImgv);
        }

    }

    public void registroExit(MouseEvent mouseEvent) {


    }
}
