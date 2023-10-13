package co.edu.uniquindio.travelagency.controllers;

import co.edu.uniquindio.travelagency.exceptions.emptyAttributeException;
import co.edu.uniquindio.travelagency.exceptions.userNoExistingException;
import co.edu.uniquindio.travelagency.exceptions.wrongPasswordException;
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

        Image exitButton = new Image("https://cdn-icons-png.flaticon.com/128/5735/5735775.png");
        cerrarVentanaImgv.setImage(exitButton);

    }

    @FXML
    private void handleButtonAction(MouseEvent event){

        if(event.getTarget() == homeBtn){
           visibilities(true,false,false,false,false);
        }else if (event.getTarget() == paquetesBtn){
            visibilities(false,true,false,false,false);
        } else if (event.getTarget() == guiasBtn) {
            visibilities(false,false,true,false,false);
        } else if (event.getTarget() == ayudasBtn) {
            visibilities(false,false,false,true,false);
        } else if (event.getTarget() == iniciaSecionBtn) {
            visibilities(false,false,false,false,true);
        }

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

    public void onLogInButtonClick() throws userNoExistingException, wrongPasswordException, emptyAttributeException, IOException {

        travelAgency.LogIn(txtFldID.getText(), txtFldPassword.getText());

        File url = new File("src/main/resources/co/edu/uniquindio/travelagency/adminView.fxml");
        FXMLLoader loader = new FXMLLoader(url.toURL());
        Parent parent = loader.load();

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        stage.setResizable(false);
        stage.show();

        Stage stage1 = (Stage) cerrarVentanaImgv.getScene().getWindow();
        stage1.close();

    }
}
