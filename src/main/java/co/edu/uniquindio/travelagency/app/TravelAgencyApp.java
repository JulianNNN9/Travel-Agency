package co.edu.uniquindio.travelagency.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class TravelAgencyApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        File url = new File("src/main/resources/views/homeView.fxml");
        FXMLLoader loader = new FXMLLoader(url.toURL());
        Parent parent = loader.load();

        stage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(parent);
        //scene.getStylesheets().add(getClass().getClassLoader().getResource("../views/styles.css").toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();

    }


    public static void main(String[] args) {
        launch();
    }
}