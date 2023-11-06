module co.edu.uniquindio.travelagency {

    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires javafx.graphics;
    requires static lombok;
    requires java.mail;


    opens co.edu.uniquindio.travelagency.app to javafx.fxml;
    opens co.edu.uniquindio.travelagency.controllers to javafx.fxml;

    exports co.edu.uniquindio.travelagency.app;
    exports co.edu.uniquindio.travelagency.controllers;
    exports co.edu.uniquindio.travelagency.model;
    exports co.edu.uniquindio.travelagency.exceptions;
    exports co.edu.uniquindio.travelagency.enums;
}