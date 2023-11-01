package co.edu.uniquindio.travelagency.controllers;

import co.edu.uniquindio.travelagency.model.TravelAgency;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class ClientController {

    private final TravelAgency travelAgency = TravelAgency.getInstance();

    @FXML
    public ImageView cerrarVentanaImgv;

    public ImageView perfilImgv;



    public void initialize(){

        File file1 = new File("src/main/resources/icons/cerrarVentana.png");
        Image exitButton = new Image(String.valueOf(file1.toURI()));

        cerrarVentanaImgv.setImage(exitButton);

    }


    public void onExitButtonClick() throws IOException {

       travelAgency.generateWindow("src/main/resources/views/homeView.fxml",cerrarVentanaImgv);

    }

}
