package co.edu.uniquindio.travelagency.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;

public class LogInController {

    @FXML
    public TextField txtFldID;
    @FXML
    public TextField txtFldPassword;
    @FXML
    public Button btnCloseView;
    @FXML
    public Button btnLogIn;
    @FXML
    public Button btnRegister;

    public void onLogInClick() throws IOException {

        File url = new File("src/main/resources/co/edu/uniquindio/travelagency/clientView.fxml");
        FXMLLoader loader = new FXMLLoader(url.toURL());
        Parent parent = loader.load();

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        stage.setResizable(false);
        stage.show();

        Stage stage1 = (Stage) this.btnLogIn.getScene().getWindow();
        stage1.close();

    }

    public void onRegisterClick() {
    }

    public void onCloseViewClick() {

        Stage stage = (Stage) this.btnCloseView.getScene().getWindow();
        stage.close();

    }

}