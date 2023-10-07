package co.edu.uniquindio.travelagency.app;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;

public class TravelAgencyApp extends Application {

    private double xOffset = 0, yOffset = 0;
    @Override
    public void start(Stage stage) throws IOException {

          File url = new File("src/main/resources/co/edu/uniquindio/travelagency/home2View.fxml");
        FXMLLoader loader = new FXMLLoader(url.toURL());
        Parent parent = loader.load();
        stage.initStyle(StageStyle.TRANSPARENT);
        parent.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneX();
            }
        });
        parent.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getSceneX() - xOffset);
                stage.setY(event.getSceneY() - yOffset);
            }
        });
        Scene scene = new Scene(parent);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}