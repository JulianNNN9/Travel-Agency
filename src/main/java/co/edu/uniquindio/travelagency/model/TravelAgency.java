package co.edu.uniquindio.travelagency.model;

import co.edu.uniquindio.travelagency.exceptions.*;
import javafx.scene.control.Alert;
import lombok.Getter;
import lombok.extern.java.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

@Log @Getter

public class TravelAgency {

    List<TouristGuide> touristGuides;
    List<Reservation> reservations;
    List<TouristPackage> touristPackages;
    List<Client> clients;
    List<Admin> admins;

    @Getter
    private final ResourceBundle resourceBundle;

    private static TravelAgency travelAgency;

    private TravelAgency() {

        this.resourceBundle = ResourceBundle.getBundle("textos");

        try {

            FileHandler fh = new FileHandler("logs.log", true);
            fh.setFormatter( new SimpleFormatter());
            log.addHandler(fh);

        }catch (IOException e){
            log.severe(e.getMessage());
        }

        this.touristGuides = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.touristPackages = new ArrayList<>();
        this.clients = new ArrayList<>();
        this.admins = new ArrayList<>();

    }

    public static TravelAgency getInstance(){

        if (travelAgency == null){
            travelAgency = new TravelAgency();
        }

        log.info("Se ha creado una instancia de Travel Agency");

        return travelAgency;

    }

    public void LogIn(String id, String password) throws emptyAttributeException, wrongPasswordException, userNoExistingException {

        if (id == null || id.isBlank() || password == null || password.isBlank()){
            createAlertError(this.getResourceBundle().getString("textoTituloAlertaErrorAtributoVacio"), this.getResourceBundle().getString("textoContenidoAlertaErrorAtributoVacio"));
            log.info("Se ha hecho un intento de registro de cliente con campos vacios.");
            throw new emptyAttributeException(this.getResourceBundle().getString("textoAtributoVacioException"));
        }

        validateLogInData(id, password, 0);

    }

    public void validateLogInData(String id, String password, int i) throws userNoExistingException, wrongPasswordException {
        if (i >= clients.size()) {

            createAlertError(this.getResourceBundle().getString("textTitleAlertErrorNoExistingException"), this.getResourceBundle().getString("textContentAlertErrorNoExistingException"));
            log.info("Se ha hecho un intento de registro con informacion incorrecta.");
            throw new userNoExistingException(this.getResourceBundle().getString("textUserNoExistingException"));

        }

        Client currentClient = clients.get(i);

        if (currentClient.getUserId().equals(id)) {
            if (currentClient.getPassword().equals(password)) {
                log.info("El cliente con el id " + id + " ha hecho un inicio de sesión.");
            } else {
                createAlertError(travelAgency.getResourceBundle().getString("textTitleAlertErrorWrongPasswordException"), travelAgency.getResourceBundle().getString("textContentAlertErrorWrongPasswordException"));
                log.info("Se ha intentado un inicio de sesión con contraseña incorrecta.");
                throw new wrongPasswordException(travelAgency.getResourceBundle().getString("textWrongPasswordException"));
            }
        } else {
            validateLogInData(id, password, ++i);
        }

    }

    public void createAlertError(String titleError, String contentError){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titleError);
        alert.setContentText(contentError);
        alert.show();
    }

    public void createAlertInfo(String titleError, String headerError, String contentError){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titleError);
        alert.setHeaderText(headerError);
        alert.setContentText(contentError);
        alert.show();
    }

}
