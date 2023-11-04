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
import java.util.*;
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

        new Thread(() -> {

            ArrayList<TouristGuide> aux = (ArrayList<TouristGuide>) archiveUtils.deserializerObjet("src/main/resources/persistencia/touristGuides.ser");

            this.touristGuides = Objects.requireNonNullElseGet(aux, ArrayList::new);

            for (TouristGuide guide : touristGuides) {
                if (guide.getLanguages() == null) {
                    guide.setLanguages(new ArrayList<>());
                }
            }

        }).start();

        //Cargar reservaciones

        new Thread(() -> {
            ArrayList<Reservation> aux1 = (ArrayList<Reservation>) archiveUtils.deserializerObjet("src/main/resources/persistencia/reservations.ser");

            this.reservations = Objects.requireNonNullElseGet(aux1, ArrayList::new);

            for (Reservation reservation : reservations){
                if (reservation.getTouristPackages() == null){
                    reservation.setTouristPackages(new ArrayList<>());
                }
            }

        }).start();

        //Cargar paquetes

        new Thread(() -> {

            ArrayList<TouristPackage> aux2 = (ArrayList<TouristPackage>) archiveUtils.deserializerObjet("src/main/resources/persistencia/touristPackages.ser");

            this.touristPackages = Objects.requireNonNullElseGet(aux2, ArrayList::new);

            for (TouristPackage aPackage : touristPackages){
                if (aPackage.getDestinosName() == null){
                    aPackage.setDestinosName(new ArrayList<>());
                }
            }

        }).start();

        //Cargar destinos

        new Thread(() -> {

            ArrayList<Destino> aux3 = (ArrayList<Destino>) archiveUtils.deserializerObjet("src/main/resources/persistencia/destinos.ser");

            this.destinos = Objects.requireNonNullElseGet(aux3, ArrayList::new);

            for (Destino destino : destinos){
                if (destino.getImagesHTTPS() == null){
                    destino.setImagesHTTPS(new ArrayList<>());
                }
            }

        }).start();


        //Cargar clientes

        new Thread(() -> {

            ArrayList<Client> aux4 = (ArrayList<Client>) archiveUtils.deserializerObjet("src/main/resources/persistencia/clients.ser");

            this.clients = Objects.requireNonNullElseGet(aux4, ArrayList::new);

        }).start();



        //Cargar admins

        new Thread(() -> {

            ArrayList<Admin> aux5 = (ArrayList<Admin>) archiveUtils.deserializerObjet("src/main/resources/persistencia/admins.ser");

            this.admins = Objects.requireNonNullElseGet(aux5, ArrayList::new);

        }).start();



    }

    public static TravelAgency getInstance(){

        if (travelAgency == null){
            travelAgency = new TravelAgency();
        }

        log.info("Se ha creado una instancia de Travel Agency");

        return travelAgency;

    }

    public void serializarDestinos(){
        archiveUtils.serializerObjet("src/main/resources/persistencia/destinos.ser", destinos);
    }

    public void serializarGuias(){
        archiveUtils.serializerObjet("src/main/resources/persistencia/touristGuides.ser", touristGuides);
    }

    public void serializarPaquetes(){
        archiveUtils.serializerObjet("src/main/resources/persistencia/touristPackages.ser", touristPackages);
    }

    public void modificarDestino(Destino selectedDestino, String nuevoNombre, String nuevaCiudad, String nuevaDescrpcion, String nuevaLocalDate) {

        selectedDestino.setName(nuevoNombre);
        selectedDestino.setCity(nuevaCiudad);
        selectedDestino.setDescription(nuevaDescrpcion);
        selectedDestino.setWeather(nuevaLocalDate);

        serializarDestinos();

    }
    public void modificarPaquete(TouristPackage selectedPackage, String nuevoNombrePaquete, double nuevoPrecio, int nuevosCupos, LocalDate nuevaFechaInicio, LocalDate nuevaFechaFin) {

        selectedPackage.setName(nuevoNombrePaquete);
        selectedPackage.setPrice(nuevoPrecio);
        selectedPackage.setQuota(nuevosCupos);
        selectedPackage.setStartDate(nuevaFechaInicio);
        selectedPackage.setEndDate(nuevaFechaFin);

        serializarPaquetes();

    }

    public void modificarGuia(TouristGuide selectedGuia, String nuevoGuideID, String nuevoGuideName, String nuevaExperiencia, String nuevoRating) {

        selectedGuia.setId(nuevoGuideID);
        selectedGuia.setFullName(nuevoGuideName);
        selectedGuia.setExperience(nuevaExperiencia);
        selectedGuia.setRating(Integer.valueOf(nuevoRating));

        serializarGuias();
    }

    public void eliminarDestinoName(Optional<TouristPackage> touristPackage, String destinoABorrar){

        List<String> destinosSinEliminar = touristPackage.get().getDestinosName().stream().filter(s -> !s.equals(destinoABorrar)).toList();

        touristPackage.get().getDestinosName().clear();
        touristPackage.get().getDestinosName().addAll(destinosSinEliminar );

        serializarPaquetes();
    }

    public void eliminarRuta(Optional<Destino> destino, String rutaABorrar){

        List<String> rutasSinEliminar = new ArrayList<>();

        if (destino.isPresent()){
            rutasSinEliminar = destino.get().getImagesHTTPS().stream().filter(s -> !s.equals(rutaABorrar)).toList();
        }

        destino.get().getImagesHTTPS().clear();
        destino.get().getImagesHTTPS().addAll(rutasSinEliminar);

        serializarDestinos();
    }

    public void eliminarLenguaje(Optional<TouristGuide> touristGuide, String lenguajeABorrar){

        List<String> languajeSinEliminar = touristGuide.get().getLanguages().stream().filter(s -> !s.equals(lenguajeABorrar)).toList();

        touristGuide.get().getLanguages().clear();
        touristGuide.get().getLanguages().addAll(languajeSinEliminar);

        serializarGuias();

    }


    public void eliminarDestino(ObservableList<Destino> destinoObservableList, Destino selectedDestino) {
        destinoObservableList.remove(selectedDestino);
        destinos.removeIf(destino -> destino.equals(selectedDestino));
        serializarDestinos();
    }

    public void eliminarPaquete(ObservableList<TouristPackage> packageObservableList, TouristPackage selectedPackage) {
        packageObservableList.remove(selectedPackage);
        touristPackages.removeIf(touristPackage -> touristPackage.equals(selectedPackage));
        serializarPaquetes();
    }

    public void eliminarGuia(ObservableList<TouristGuide> touristGuideObservableList, TouristGuide selectedGuia) {
        touristGuideObservableList.remove(selectedGuia);
        touristGuides.removeIf(touristGuide -> touristGuide.equals(selectedGuia));
        serializarGuias();
    }

    public void agregarGuia(ObservableList<TouristGuide> touristGuideObservableList, TouristGuide nuevoGuia) throws AtributoVacioException, RepeatedInformationException {

        if (nuevoGuia.getId() == null || nuevoGuia.getId().isEmpty() ||
                nuevoGuia.getFullName() == null || nuevoGuia.getFullName().isEmpty() ||
                nuevoGuia.getExperience() == null || nuevoGuia.getExperience().isEmpty() ||
                nuevoGuia.getRating() == null){

            createAlertError("Campos obligatorios", "Los campos marcados con (*) son oblogatorios");
            log.info("Se ha intentado agregar un guia con campos vacios.");
            throw new AtributoVacioException("Se ha intentado agregar un guia con campos vacios.");
        }

        if (touristGuideObservableList.stream().anyMatch(touristGuide -> touristGuide.getId().equals(nuevoGuia.getId()))){

            createAlertError("Guia existente", "El guia que trataba de agregar ya se encuentra registrado.");
            log.severe("Se ha intentado registrar un guia existente.");
            throw new RepeatedInformationException("Se ha intentado registrar un guia existente.");
        }

        touristGuideObservableList.add(nuevoGuia);
        travelAgency.touristGuides.add(nuevoGuia);

        serializarGuias();

        log.info("Se ha registrado un nuevo guia.");

    }

    public void agregarPaquete(ObservableList<TouristPackage> packageObservableList, TouristPackage nuevoPaquete) throws AtributoVacioException, RepeatedInformationException, ErrorEnIngresoFechasException {

        if ( nuevoPaquete.getName() == null || nuevoPaquete.getName().isEmpty() ||
                nuevoPaquete.getPrice() == null || nuevoPaquete.getPrice().isNaN() ||
                nuevoPaquete.getQuota() == null ||
                nuevoPaquete.getStartDate() == null ||
                nuevoPaquete.getEndDate() == null){

            createAlertError("Campos obligatorios", "Los campos marcados con (*) son oblogatorios");
            log.info("Se ha intentado agregar un destino con campos vacios.");
            throw new AtributoVacioException("Se ha intentado agregar un destino con campos vacios.");
        }

        if (packageObservableList.stream().anyMatch(touristPackage -> touristPackage.getName().equals(nuevoPaquete.getName()))){

            createAlertError("Paquete existente", "El paquete que trataba de agregar ya se encuentra registrado.");
            log.severe("Se ha intentado crear un paquete existente.");
            throw new RepeatedInformationException("Se ha intentado crear un paquete existente.");
        }

        if (nuevoPaquete.getDuration() < 0){
            createAlertError("Error en el ingreso de fechas", "Las fechas que desea ingresar son inválidas, verifiquelas.");
            log.info("Las fechas fueron incorrectamente colocadas, la fecha de inicio no puede ser después de la fecha de fin.");
            throw new ErrorEnIngresoFechasException("Las fechas fueron incorrectamente colocadas, la fecha de inicio no puede ser después de la fecha de fin.");
        }

        packageObservableList.add(nuevoPaquete);
        travelAgency.touristPackages.add(nuevoPaquete);

        serializarPaquetes();

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

        serializarDestinos();

        log.info("Se ha creado un nuevo destino.");

        }

    public void agregarImagenDestino(ObservableList<String> observableListRutas, String ruta, Destino destino) throws RutaInvalidaException, RepeatedInformationException {

        File archivo = new File(ruta);
        boolean esRutaDeArchivo = archivo.exists() && archivo.isFile();

        if (!esRutaDeArchivo){
            createAlertError("Error en la ruta", "La ruta que trata de ingresar es inválida o inexistente.");
            log.severe("Se ha intentado agregar una imagen invalida a un destino.");
            throw new RutaInvalidaException("Se ha intentado agregar una imagen invalida a un destino.");
        }

        if (observableListRutas.stream().anyMatch(string -> string.equals(ruta))){

            createAlertError("Ruta existente", "La ruta que trataba de agregar ya se encuentra registrada.");
            log.severe("Se ha intentado crear una ruta existente.");
            throw new RepeatedInformationException("Se ha intentado crear una ruta existente.");
        }

        observableListRutas.add(ruta);

        for (Destino d : destinos) {
            if (d.equals(destino)) {
                d.getImagesHTTPS().add(ruta);
                break;
            }
        }

        serializarDestinos();
    }


    public void agregarLeaguajeGuia(ObservableList<String> observableListLenguajes, String lenguaje, TouristGuide touristGuide) throws RepeatedInformationException {

        if (observableListLenguajes.stream().anyMatch(string -> string.equals(lenguaje))){

            createAlertError("Lenguaje ya ingresado", "La lenguaje que trataba de agregar ya se encuentra agregado.");
            log.severe("Se ha intentado agregar un lenaguje existente.");
            throw new RepeatedInformationException("Se ha intentado agregar un lenaguje existente.");
        }

        observableListLenguajes.add(lenguaje);

        for (TouristGuide t : touristGuides) {
            if (t.equals(touristGuide)) {
                t.getLanguages().add(lenguaje);
                break;
            }
        }

        serializarGuias();
    }

    public void agregarDestinoEnPaquete(ObservableList<String> observableListDestinationName, String selectedItem, TouristPackage touristPackage) throws RepeatedInformationException {

        if (observableListDestinationName.stream().anyMatch(string -> string.equals(selectedItem))){

            createAlertError("Destino existente", "La destino que trataba de agregar ya se encuentra registrada.");
            log.severe("Se ha intentado agregar un destino existente.");
            throw new RepeatedInformationException("Se ha intentado agregar un destino existente.");
        }

        observableListDestinationName.add(selectedItem);

        for (TouristPackage t : touristPackages) {
            if (t.equals(touristPackage)) {
                t.getDestinosName().add(selectedItem);
                break;
            }
        }

        serializarPaquetes();
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

        Client client = Client.builder()
                .userId(userId.trim())
                .password(passeord.trim())
                .fullName(fullname.trim())
                .mail(mail.trim())
                .phoneNumber(phoneNumber.trim())
                .residence(residence.trim())
                .build();

        clients.add(client);

        archiveUtils.serializerObjet("src/main/resources/persistencia/clients.ser", clients);

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


    public boolean empiezaPor(String inicio){
//        if(inicio.isEmpty() || inicio.length().length())
//            return false;
//        for
      return  true;
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
