package co.edu.uniquindio.travelagency.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class Home2Controller  {


   @FXML
   private ImageView cerrarVentanaImgv;

    @FXML
    private Button homeBtn,paquetesBtn,guiasBtn,ayudasBtn,iniciaSecionBtn;
    @FXML
    private AnchorPane homePane,nuestrosPaquetesPane,nuestrosGuiasPane,ayudaPane,iniciarsesionPane;

    public void initialize() {

    }

    @FXML
    private void handleButtonAction(MouseEvent event){
        if(event.getTarget() == homeBtn){
           visibilidad(true,false,false,false,false);
        }else if (event.getTarget() == paquetesBtn){
            visibilidad(false,true,false,false,false);
        } else if (event.getTarget() == guiasBtn) {
            visibilidad(false,false,true,false,false);
        } else if (event.getTarget() == ayudasBtn) {
            visibilidad(false,false,false,true,false);
        } else if (event.getTarget() == iniciaSecionBtn) {
            visibilidad(false,false,false,false,true);
        }

    }

    public void visibilidad ( boolean pane1,boolean pane2 , boolean pane3, boolean pane4, boolean pane5 ){
        homePane.setVisible(pane1);
        nuestrosPaquetesPane.setVisible(pane2);
        nuestrosGuiasPane.setVisible(pane3);
        ayudaPane.setVisible(pane4);
        iniciarsesionPane.setVisible(pane5);




    }


}
