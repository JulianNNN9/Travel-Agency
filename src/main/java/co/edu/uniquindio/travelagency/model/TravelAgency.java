package co.edu.uniquindio.travelagency.model;

import co.edu.uniquindio.travelagency.exceptions.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;
import lombok.extern.java.Log;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
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
    List<Destino> destinos;
    List<Client> clients;
    List<Admin> admins;

    @Getter
    private final ResourceBundle resourceBundle;

    private static TravelAgency travelAgency;

    private TravelAgency() {

        this.resourceBundle = ResourceBundle.getBundle("textos");

        try {

            FileHandler fh = new FileHandler("logs.log", true);
            fh.setFormatter(new SimpleFormatter());
            log.addHandler(fh);

        } catch (IOException e) {
            log.severe(e.getMessage());
        }

        //Cargar guía

        ArrayList<TouristGuide> aux = (ArrayList<TouristGuide>) archiveUtils.deserializerObjet("src/main/resources/persistencia/touristGuides.ser");

        this.touristGuides = Objects.requireNonNullElseGet(aux, ArrayList::new);

        for (TouristGuide guide : touristGuides) {
            if (guide.getLanguages() == null) {
                guide.setLanguages(new ArrayList<>());
            }
        }

        //Cargar reservaciones

        ArrayList<Reservation> aux1 = (ArrayList<Reservation>) archiveUtils.deserializerObjet("src/main/resources/persistencia/reservations.ser");

        this.reservations = Objects.requireNonNullElseGet(aux1, ArrayList::new);

        for (Reservation reservation : reservations){
            if (reservation.getTouristPackages() == null){
                reservation.setTouristPackages(new ArrayList<>());
            }
        }

        //Cargar paquetes

        ArrayList<TouristPackage> aux2 = (ArrayList<TouristPackage>) archiveUtils.deserializerObjet("src/main/resources/persistencia/touristPackages.ser");

        this.touristPackages = Objects.requireNonNullElseGet(aux2, ArrayList::new);

        for (TouristPackage aPackage : touristPackages){
            if (aPackage.getDestinosName() == null){
                aPackage.setDestinosName(new ArrayList<>());
            }
        }

        List<String> dest = new ArrayList<>();
        dest.add("AAA");
        dest.add("BBB");

        TouristPackage touristPackage = TouristPackage.builder()
                .destinosName(dest)
                .name("AAA")
                .price(1.0)
                .quota(2)
                .startDate(LocalDate.now())
                .endDate(LocalDate.of(2024, 12, 1))
                .duration(10)
                .clientID("456")
                .build();

        touristPackages.add(touristPackage);

        //Cargar destinos

        ArrayList<Destino> aux3 = (ArrayList<Destino>) archiveUtils.deserializerObjet("src/main/resources/persistencia/destinos.ser");

        this.destinos = Objects.requireNonNullElseGet(aux3, ArrayList::new);

        for (Destino destino : destinos){
            if (destino.getImagesHTTPS() == null){
                destino.setImagesHTTPS(new ArrayList<>());
            }
        }

        Destino destino = Destino.builder()
                .name("AAA")
                .city("AAA")
                .imagesHTTPS(new ArrayList<>())
                .description("AAA")
                .weather("TEMPLADO")
                .build();

        destino.getImagesHTTPS().add("AAA");

        Destino destino1 = Destino.builder()
                .name("BBB")
                .city("BBB")
                .imagesHTTPS(new ArrayList<>())
                .description("BBB")
                .weather("TEMPLADO")
                .build();

        destinos.add(destino);
        destinos.add(destino1);

        //Cargar clientes

        ArrayList<Client> aux4 = (ArrayList<Client>) archiveUtils.deserializerObjet("src/main/resources/persistencia/clients.ser");

        this.clients = Objects.requireNonNullElseGet(aux4, ArrayList::new);

        //Cargar admins

        ArrayList<Admin> aux5 = (ArrayList<Admin>) archiveUtils.deserializerObjet("src/main/resources/persistencia/admins.ser");

        this.admins = Objects.requireNonNullElseGet(aux5, ArrayList::new);

        Admin admin = Admin.builder()
                .userId("admin")
                .password("123")
                .build();

        admins.add(admin);

        Client cl1 = Client.builder()
                .userId("user1")
                .password("user1")
                .build();

        clients.add(cl1);

    }

    public static TravelAgency getInstance(){

        if (travelAgency == null){
            travelAgency = new TravelAgency();
        }

        log.info("Se ha creado una instancia de Travel Agency");

        return travelAgency;

    }

    public void agregarGuia(ObservableList<TouristGuide> touristGuideObservableList, TouristGuide nuevoGuia) throws AtributoVacioException, RepeatedInformationException {

        if (nuevoGuia.getId() == null || nuevoGuia.getId().isEmpty() ||
                nuevoGuia.getFullName() == null || nuevoGuia.getFullName().isEmpty() ||
                nuevoGuia.getExperience() == null || nuevoGuia.getExperience().isEmpty() ||
                nuevoGuia.getRating() == null){

            createAlertError("Campos obligatorios", "Los campos marcados con (*) son oblogatorios");
            log.info("Se ha intentado agregar un destino con campos vacios.");
            throw new AtributoVacioException("Se ha intentado agregar un destino con campos vacios.");
        }

        if (touristGuideObservableList.stream().anyMatch(touristGuide -> touristGuide.getId().equals(nuevoGuia.getId()))){

            createAlertError("Paquete existente", "El paquete que trataba de agregar ya se encuentra registrado.");
            log.severe("Se ha intentado crear un paquete existente.");
            throw new RepeatedInformationException("Se ha intentado crear un paquete existente.");
        }

        touristGuideObservableList.add(nuevoGuia);
        travelAgency.touristGuides.add(nuevoGuia);

        log.info("Se ha registrado un nuevo guia.");

    }

    public void agregarPaquete(ObservableList<TouristPackage> packageObservableList, TouristPackage nuevoPaquete) throws AtributoVacioException, RepeatedInformationException {

        if ( nuevoPaquete.getName() == null || nuevoPaquete.getName().isEmpty() ||
                nuevoPaquete.getPrice() == null || nuevoPaquete.getPrice().isNaN() ||
                nuevoPaquete.getQuota() == null ||
                nuevoPaquete.getStartDate() == null ||
                nuevoPaquete.getEndDate() == null ||
                nuevoPaquete.getDuration() < 0 ||
                nuevoPaquete.getClientID() == null || nuevoPaquete.getClientID().isEmpty()){

            createAlertError("Campos obligatorios", "Los campos marcados con (*) son oblogatorios");
            log.info("Se ha intentado agregar un destino con campos vacios.");
            throw new AtributoVacioException("Se ha intentado agregar un destino con campos vacios.");
        }

        if (packageObservableList.stream().anyMatch(destination -> destination.getName().equals(nuevoPaquete.getName()))){

            createAlertError("Paquete existente", "El paquete que trataba de agregar ya se encuentra registrado.");
            log.severe("Se ha intentado crear un paquete existente.");
            throw new RepeatedInformationException("Se ha intentado crear un paquete existente.");
        }

        packageObservableList.add(nuevoPaquete);
        travelAgency.touristPackages.add(nuevoPaquete);
        //archiveUtils.serializerObjet("src/main/resources/persistencia/touristPackages.ser", touristPackages);

        log.info("Se ha creado un nuevo paquete.");

    }

    public void agregarDestino(ObservableList<Destino> destinoObservableList, Destino nuevoDestino) throws RepeatedInformationException, AtributoVacioException {

        if (nuevoDestino.getName().isEmpty() ||
                nuevoDestino.getCity().isEmpty() ||
                nuevoDestino.getDescription().isEmpty() ||
                nuevoDestino.getWeather().isEmpty()) {

            createAlertError("Campos obligatorios", "Los campos marcados con (*) son oblogatorios");
            log.info("Se ha intentado agregar un destino con campos vacios.");
            throw new AtributoVacioException("Se ha intentado agregar un destino con campos vacios.");
        }

        if (destinoObservableList.stream().anyMatch(destination -> destination.getName().equals(nuevoDestino.getName()))){

            createAlertError("Destino existente", "El destino que trataba de agregar ya se encuentra registrado.");
            log.severe("Se ha intentado crear un Destino existente.");
            throw new RepeatedInformationException("Se ha intentado crear un Destino existente.");
        }

        destinoObservableList.add(nuevoDestino);
        travelAgency.destinos.add(nuevoDestino);
        //archiveUtils.serializerObjet("src/main/resources/persistencia/destinos.ser", destinos);

        log.info("Se ha creado un nuevo destino.");

        }

    public void agregarImagenDestino(ObservableList<String> observableListRutas, String ruta, Destino destino) {

        if (observableListRutas != null){
            observableListRutas.add(ruta);
        }

        for (Destino d : destinos) {
            if (d.equals(destino)) {
                if (d.getImagesHTTPS() == null){
                    d.setImagesHTTPS(new ArrayList<>());
                    d.getImagesHTTPS().add(ruta);
                } else {
                    d.getImagesHTTPS().add(ruta);
                }

                break;
            }
        }

    }


    public void agregarLeaguajeGuia(ObservableList<String> observableListLenguajes, String lenguaje, TouristGuide touristGuide) {

        if (observableListLenguajes != null){
            observableListLenguajes.add(lenguaje);
        }

        for (TouristGuide t : touristGuides) {
            if (t.equals(touristGuide)) {
                if (t.getLanguages() == null){
                    t.setLanguages(new ArrayList<>());
                    t.getLanguages().add(lenguaje);
                } else {
                    t.getLanguages().add(lenguaje);
                }

                break;
            }
        }

    }

    public void agregarDestinoEnPaquete(ObservableList<String> observableListDestinationName, String selectedItem, TouristPackage touristPackage) {

        if (observableListDestinationName != null){
            observableListDestinationName.add(selectedItem);
        }

        for (TouristPackage t : touristPackages) {
            if (t.equals(touristPackage)) {
                if (t.getDestinosName() == null){
                    t.setDestinosName(new ArrayList<>());
                    t.getDestinosName().add(selectedItem);
                } else {
                    t.getDestinosName().add(selectedItem);
                }

                break;
            }
        }
    }

    public String LogIn(String id, String password) throws EmptyAttributeException, WrongPasswordException, UserNoExistingException {

        if (id == null || id.isBlank() || password == null || password.isBlank()){
            createAlertError(this.getResourceBundle().getString("textoTituloAlertaErrorAtributoVacio"), this.getResourceBundle().getString("textoContenidoAlertaErrorAtributoVacio"));
            log.info("Se ha hecho un intento de registro de cliente con campos vacios.");
            throw new EmptyAttributeException(this.getResourceBundle().getString("textoAtributoVacioException"));
        }

        if (clients.stream().anyMatch(client -> client.getUserId().equals(id))){
            validateLogInDataUser(id, password, 0);
           return "Client";
        } else if (admins.stream().anyMatch(client -> client.getUserId().equals(id))) {
            validateLogInDataAdmin(id, password, 0);
            return  "Admin";
        }

        return "";
    }
    public void generateWindow(String path, ImageView close) throws IOException {

        File url = new File(path);
        FXMLLoader loader = new FXMLLoader(url.toURL());
        Parent parent = loader.load();

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        stage.setResizable(false);
        stage.show();

        Stage stage1 = (Stage) close.getScene().getWindow();
        stage1.close();
    }

    public void registrarCliente(String userId,String passeord,String fullname,String mail ,String phoneNumber,String residence) throws AtributoVacioException, RepeatedInformationException {

        if(userId == null || userId.isBlank() || fullname == null || fullname.isBlank() || phoneNumber == null || phoneNumber.isBlank() ){
            //createAlertError(this.getResourceBundle().getString);
            log.info("se ha intentado registrar n cliente con campo ecenciales vacios");
            throw new AtributoVacioException("campos obligatorios sin copletar");
        }

        if(clients.stream().anyMatch(cliente -> cliente.getUserId().equals(userId))){
            //createAlertError();
            log.info("se intento registrar un cliente que ya existe");
            throw new RepeatedInformationException("el cliente ya exite");
        }

        archiveUtils.serializerObjet("src/main/resources/persistencia/clients.ser",clients);

        Client client = Client.builder()
                .userId(userId)
                .password(passeord)
                .fullName(fullname)
                .mail(mail)
                .phoneNumber(phoneNumber)
                .residence(residence)
                .build();

        clients.add(client);

        log.info("se ha registrado un cliente con el user ID " + userId);

    }


    private void validateLogInDataAdmin(String id, String password, int i) throws UserNoExistingException, WrongPasswordException {

        if (i >= admins.size()) {

            createAlertError(this.getResourceBundle().getString("textTitleAlertErrorNoExistingException"), this.getResourceBundle().getString("textContentAlertErrorNoExistingException"));
            log.info("Se ha hecho un intento de registro con informacion incorrecta.");
            throw new UserNoExistingException(this.getResourceBundle().getString("textUserNoExistingException"));

        }

        Admin currentAdmin = admins.get(i);

        if (currentAdmin.getUserId().equals(id)) {

            if (currentAdmin.getPassword().equals(password)) {

                log.info("El admin con el id " + id + " ha hecho un inicio de sesión.");

            } else {

                createAlertError(travelAgency.getResourceBundle().getString("textTitleAlertErrorWrongPasswordException"), travelAgency.getResourceBundle().getString("textContentAlertErrorWrongPasswordException"));
                log.info("Se ha intentado un inicio de sesión con contraseña incorrecta.");
                throw new WrongPasswordException(travelAgency.getResourceBundle().getString("textWrongPasswordException"));

            }

        } else {

            validateLogInDataAdmin(id, password, ++i);
        }

    }

    public void validateLogInDataUser(String id, String password, int i) throws UserNoExistingException, WrongPasswordException {

        if (i >= clients.size()) {

            createAlertError(this.getResourceBundle().getString("textTitleAlertErrorNoExistingException"), this.getResourceBundle().getString("textContentAlertErrorNoExistingException"));
            log.info("Se ha hecho un intento de registro con informacion incorrecta.");
            throw new UserNoExistingException(this.getResourceBundle().getString("textUserNoExistingException"));

        }

        Client currentClient = clients.get(i);

        if (currentClient.getUserId().equals(id)) {
            if (currentClient.getPassword().equals(password)) {
                log.info("El cliente con el id " + id + " ha hecho un inicio de sesión.");
            } else {
                createAlertError(travelAgency.getResourceBundle().getString("textTitleAlertErrorWrongPasswordException"), travelAgency.getResourceBundle().getString("textContentAlertErrorWrongPasswordException"));
                log.info("Se ha intentado un inicio de sesión con contraseña incorrecta.");
                throw new WrongPasswordException(travelAgency.getResourceBundle().getString("textWrongPasswordException"));
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
