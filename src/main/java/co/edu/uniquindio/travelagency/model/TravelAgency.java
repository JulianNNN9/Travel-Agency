package co.edu.uniquindio.travelagency.model;

import co.edu.uniquindio.travelagency.exceptions.*;
import javafx.scene.control.Alert;
import lombok.Getter;
import lombok.extern.java.Log;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import co.edu.uniquindio.travelagency.utils.*;

@Log @Getter

public class TravelAgency {

    List<TouristGuide> touristGuides;
    List<Reservation> reservations;
    List<TouristPackage> touristPackages;
    List<Destination> destinations;
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

        ArrayList<TouristGuide> aux5 = (ArrayList<TouristGuide> ) archiveUtils.deserializerObjet("src/main/resources/persistencia/touristGuides.ser");

        this.touristGuides = Objects.requireNonNullElseGet(aux5, ArrayList::new);

        ArrayList<Reservation> aux4 = (ArrayList<Reservation> ) archiveUtils.deserializerObjet("src/main/resources/persistencia/reservations.ser");

        this.reservations = Objects.requireNonNullElseGet(aux4, ArrayList::new);

        ArrayList<TouristPackage> aux3 = (ArrayList<TouristPackage> ) archiveUtils.deserializerObjet("src/main/resources/persistencia/touristPackeages.ser");

        this.touristPackages = Objects.requireNonNullElseGet(aux3, ArrayList::new);

        ArrayList<Destination> aux2 = (ArrayList<Destination> ) archiveUtils.deserializerObjet("src/main/resources/persistencia/destinations.ser");

        this.destinations = Objects.requireNonNullElseGet(aux2, ArrayList::new);

        ArrayList<Client> aux1 = (ArrayList<Client> ) archiveUtils.deserializerObjet("src/main/resources/persistencia/clients.ser");

        this.clients = Objects.requireNonNullElseGet(aux1, ArrayList::new);

        ArrayList<Admin> aux = (ArrayList<Admin> ) archiveUtils.deserializerObjet("src/main/resources/persistencia/admins.ser");

        this.admins = Objects.requireNonNullElseGet(aux, ArrayList::new);

        Admin admin = Admin.builder()
                .userId("admin")
                .password("123")
                .build();

        admins.add(admin);

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

        if (clients.stream().anyMatch(client -> client.getUserId().equals(id))){
            validateLogInDataUser(id, password, 0);
        }

        validateLogInDataAdmin(id, password, 0);



    }

    private void validateLogInDataAdmin(String id, String password, int i) throws userNoExistingException, wrongPasswordException {

        if (i >= admins.size()) {

            createAlertError(this.getResourceBundle().getString("textTitleAlertErrorNoExistingException"), this.getResourceBundle().getString("textContentAlertErrorNoExistingException"));
            log.info("Se ha hecho un intento de registro con informacion incorrecta.");
            throw new userNoExistingException(this.getResourceBundle().getString("textUserNoExistingException"));

        }

        Admin currentAdmin = admins.get(i);

        if (currentAdmin.getUserId().equals(id)) {

            if (currentAdmin.getPassword().equals(password)) {

                log.info("El admin con el id " + id + " ha hecho un inicio de sesión.");

            } else {

                createAlertError(travelAgency.getResourceBundle().getString("textTitleAlertErrorWrongPasswordException"), travelAgency.getResourceBundle().getString("textContentAlertErrorWrongPasswordException"));
                log.info("Se ha intentado un inicio de sesión con contraseña incorrecta.");
                throw new wrongPasswordException(travelAgency.getResourceBundle().getString("textWrongPasswordException"));

            }

        } else {

            validateLogInDataAdmin(id, password, ++i);
        }

    }

    public void validateLogInDataUser(String id, String password, int i) throws userNoExistingException, wrongPasswordException {

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
            validateLogInDataUser(id, password, ++i);
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
