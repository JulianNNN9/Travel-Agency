package co.edu.uniquindio.travelagency.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainViewController {
    @FXML
    public Pane contentPane;
    @FXML
    public Button btnCloseView;

    public void initialize(){

    }

    public void onCloseViewClick() {

        Stage stage = (Stage) this.btnCloseView.getScene().getWindow();
        stage.close();

    }
}
