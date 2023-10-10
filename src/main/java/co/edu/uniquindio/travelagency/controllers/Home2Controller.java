package co.edu.uniquindio.travelagency.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class Home2Controller  {

    @FXML
    private ImageView cerrarVentanaImgv;
    @FXML
    private Button homeBtn,paquetesBtn,guiasBtn,ayudasBtn,iniciaSecionBtn;
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


}
