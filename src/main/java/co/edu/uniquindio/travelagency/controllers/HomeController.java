package co.edu.uniquindio.travelagency.controllers;

import co.edu.uniquindio.travelagency.enums.ReservationStatus;
import co.edu.uniquindio.travelagency.exceptions.*;
import co.edu.uniquindio.travelagency.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class HomeController {

    private final TravelAgency travelAgency = TravelAgency.getInstance();


    @FXML
    public Label bienvenidoLabel;
    @FXML
    public Pane bienvenidoPane;

    //----------------------Modificar perfil----------------------
    @FXML
    private HBox hboxCliente;
    @FXML
    public TableView<TouristPackage> tblPq;
    @FXML
    public Pane perfilPane;
    @FXML
    public TextField txtFldNombre;
    @FXML
    private TableColumn<TouristPackage,TouristPackage> colPqNombre, colPqPrecio,colPqCupo,colPqFechaInicio,colPqFechaFinal,colPqDuration;
    @FXML
    public TextField txtFldMail;
    @FXML
    public TextField txtFldNumero;
    @FXML
    public TextField txtFldResidencia;
    @FXML
    public Button confirmarEdicionButton;
    @FXML
    public TableView<Reservation> historialReservacionesTable;
    @FXML
    private TableColumn<Reservation, String> packageColumn, startDateColumn, endDateColumn, numberOfPeopleColumn, estadoReserva;
    @FXML
    private ObservableList<Reservation> reservations = FXCollections.observableArrayList();
    @FXML
    public Button cancelarReservaButton, confirmarReservaButton;
    private boolean isTableLoaded = false;
    private List<Reservation> reservationsData = new ArrayList<>();


    //----------------------Reservar----------------------
    @FXML
    public Pane reservarPane;
    @FXML
    public Button reservarButton;
    @FXML
    public TextField txtFldNombrePaquete, txtFldCuposDeseados;
    @FXML
    public TableView<TouristPackage> packagesTable;
    @FXML
    ObservableList<TouristPackage> packageObservableList = FXCollections.observableArrayList();
    @FXML
    public TableColumn<TouristPackage, TouristPackage> namePackageCol, priceCol, quotaCol, startDateCol, durationCol;
    @FXML
    public RadioButton radioBttonSI, radioBttonNO;
    @FXML
    public ChoiceBox<String> choiceBoxGuias;
    @FXML
    public Label seleccionarGuiaLabel;
    @FXML
    public Button hacerReservacionButton;
    ToggleGroup group = new ToggleGroup();

    //----------------------Registrar cliente----------------------

    @FXML
    private AnchorPane registroPanee;
    @FXML
    public TextField nombreTF, idTF ,passTF,mailTF ,telefonoTF,residenciaTF;

    //----------------------Home pane----------------------
    @FXML
    private AnchorPane homePane;
    @FXML
    private ImageView cerrarVentanaImgvPrincipal;
    @FXML
    private ImageView cerrarVentanaImgvCliente;
    @FXML
    private Button homeBtn;
    //----------------------Paquetes pane----------------------
    @FXML
    private AnchorPane nuestrosPaquetesPane;
    @FXML
    public TextField barraBusquedaTF;
    @FXML
    private Button paquetesBtn;
    @FXML
    public ImageView mostrarDestinoImg, imagenSiguienteImg, imagenAnteriorImg;

    //----------------------Guias pane----------------------
    @FXML
    private AnchorPane nuestrosGuiasPane;
    @FXML
    private Button guiasBtn;

    //----------------------Iniciar Sesion Pane----------------------
    @FXML
    public TextField txtFldID, txtFldPassword;
    @FXML
    private Button iniciaSecionBtn;
    @FXML
    private AnchorPane iniciarsesionPane;
    @FXML
    private TextArea infoTA;
    @FXML
    private HBox hboxPanePrincipal;
    @FXML
    private TableView<String> tblDe;
    @FXML
    private TableColumn<String,String>  colDeNombre, colDeCiudad, colDeDescription, colDeClima;
    @FXML
    ObservableList<String> dE = FXCollections.observableArrayList();
    @FXML
    ObservableList<TouristPackage>  tP = FXCollections.observableArrayList();
    String  clientID, passwordID,fullName, mail, phoneNumber, residence;

    public void initialize() {

        imagenAnteriorImg.setVisible(false);
        imagenSiguienteImg.setVisible(false);

        cancelarReservaButton.setVisible(false);
        confirmarReservaButton.setVisible(false);

        choiceBoxGuias.setVisible(false);
        seleccionarGuiaLabel.setVisible(false);

        radioBttonSI.setToggleGroup(group);
        radioBttonNO.setToggleGroup(group);

        radioBttonSI.setOnAction(event -> {
            choiceBoxGuias.setVisible(true);
            seleccionarGuiaLabel.setVisible(true);
        });

        radioBttonNO.setOnAction(event -> {
            choiceBoxGuias.getSelectionModel().clearSelection();
            choiceBoxGuias.setVisible(false);
            seleccionarGuiaLabel.setVisible(false);
        });

        elementosSheaarchBar();
        seleccionDestinos();

        //----------------------Modificar perfil----------------------

        txtFldNombre.setEditable(false);
        txtFldMail.setEditable(false);
        txtFldNumero.setEditable(false);
        txtFldResidencia.setEditable(false);
        confirmarEdicionButton.setVisible(false);

        //txtField numericos

        txtFldNumero.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("[0-9]")) {
                event.consume(); // Bloquea la entrada no numérica
            }
        });

        txtFldCuposDeseados.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("[0-9]")) {
                event.consume(); // Bloquea la entrada no numérica
            }
        });

        //Hacer reservación

        txtFldNombrePaquete.setEditable(false);

        packageObservableList = packagesTable.getItems();

        if (travelAgency.getTouristPackages() != null) {
            packageObservableList.addAll(travelAgency.getTouristPackages());
        }

        this.namePackageCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        this.quotaCol.setCellValueFactory(new PropertyValueFactory<>("quota"));
        this.startDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        this.durationCol.setCellValueFactory(new PropertyValueFactory<>("duration"));

        packagesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtFldNombrePaquete.setText(newSelection.getName());
            }
        });

    }

    private void cargarTablaHistoricoReservas() {

        if (!isTableLoaded) {

            isTableLoaded = true;

            reservations = historialReservacionesTable.getItems();

            historialReservacionesTable.setItems(reservations);

            packageColumn.setCellValueFactory(cellData -> {
                StringProperty property = new SimpleStringProperty();
                TouristPackage touristPackage = cellData.getValue().getTouristPackage();
                if (touristPackage != null) {
                    property.set(touristPackage.getName());
                }
                return property;
            });
            this.startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
            this.endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
            this.numberOfPeopleColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfPeople"));
            this.estadoReserva.setCellValueFactory(new PropertyValueFactory<>("reservationStatus"));

            historialReservacionesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    if (newSelection.getReservationStatus() == ReservationStatus.CONFIRMED) {
                        cancelarReservaButton.setVisible(true);
                        confirmarReservaButton.setVisible(false);

                        cancelarReservaButton.setOnAction(event -> {
                            travelAgency.cancelarReserva(newSelection);
                            cancelarReservaButton.setVisible(false);
                            historialReservacionesTable.refresh();
                        });

                    }
                    if (newSelection.getReservationStatus() == ReservationStatus.PENDING) {
                        cancelarReservaButton.setVisible(true);
                        confirmarReservaButton.setVisible(true);

                        cancelarReservaButton.setOnAction(event -> {
                            travelAgency.cancelarReserva(newSelection);
                            cancelarReservaButton.setVisible(false);
                            historialReservacionesTable.refresh();
                        });

                        confirmarReservaButton.setOnAction(actionEvent -> {
                            travelAgency.confirmarReserva(newSelection);
                            confirmarReservaButton.setVisible(false);
                            historialReservacionesTable.refresh();
                        });
                    }
                    if (newSelection.getReservationStatus() == ReservationStatus.CANCELED) {
                        cancelarReservaButton.setVisible(false);
                        confirmarReservaButton.setVisible(false);
                    }
                }
            });
        } else {

            Optional<Client> client = travelAgency.getClients().stream().filter(client1 -> client1.getUserId().equals(clientID)).findFirst();

            List<Reservation> reservationsData = new ArrayList<>();

            if (client.isPresent()) {
                reservationsData = client.get().getReservationList();
            }

            List<Reservation> existingReservations = new ArrayList<>(reservations);

            for (Reservation newReservation : reservationsData) {
                if (!existingReservations.contains(newReservation)) {
                    reservations.add(newReservation);
                    historialReservacionesTable.refresh();
                }
            }
        }
    }

    public void onHacerReservacionClick() throws AtributoVacioException, CuposInvalidosException {
        travelAgency.hacerReservacion(clientID, mail, group.getSelectedToggle(), radioBttonSI, radioBttonNO, choiceBoxGuias.getSelectionModel().getSelectedItem(), txtFldCuposDeseados.getText(), txtFldNombrePaquete.getText());
        group.getSelectedToggle().setSelected(false);
        choiceBoxGuias.getSelectionModel().clearSelection();
        txtFldCuposDeseados.clear();
        txtFldNombrePaquete.clear();
        seleccionarGuiaLabel.setVisible(false);
        choiceBoxGuias.setVisible(false);
    }

    public void onReservarClick() {

        List<String> guiasName = travelAgency.getTouristGuides().stream()
                .map(TouristGuide::getFullName)
                .toList();

        if (!guiasName.equals(choiceBoxGuias.getItems())) {
            choiceBoxGuias.getItems().setAll(guiasName);
        }

        visibilitiesClient(false, false, true);
    }

    private void seleccionDestinos() {

        dE = tblDe.getItems();

        tblPq.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            boolean seleccionado = newValue != null;

            if (seleccionado) {
                if (Objects.requireNonNull(newValue).getDestinosName() != null) {
                    dE.clear();
                    dE.addAll(newValue.getDestinosName());
                    }
                }
        });

        tblDe.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            boolean seleccionado = newValue != null;

            if (seleccionado){
                Optional<Destino> optionalDestino =  travelAgency.getDestinos().stream().filter(destino -> destino.getName().equals(newValue)).findFirst();

                List<String> listaRutas;

                if (optionalDestino.isPresent()){
                    listaRutas = optionalDestino.get().getImagesHTTPS();
                    if (!listaRutas.isEmpty()){
                        mostrarDestinoImg.setImage(cargarImagen(listaRutas.get(0)));
                        imagenAnteriorImg.setVisible(true);
                        imagenSiguienteImg.setVisible(true);
                    } else {
                        mostrarDestinoImg.setImage(null);
                        imagenAnteriorImg.setVisible(false);
                        imagenSiguienteImg.setVisible(false);
                    }
                } else {
                    listaRutas = null;
                }

                if (listaRutas != null){

                    final int[] i = {0};

                    imagenSiguienteImg.setOnMouseClicked(mouseEvent -> {
                        if (i[0] + 1 < listaRutas.size()) {
                            ++i[0]; // Incrementa i solo si no se desborda de la lista
                            mostrarDestinoImg.setImage(cargarImagen(listaRutas.get(i[0])));
                        }
                    });

                    imagenAnteriorImg.setOnMouseClicked(mouseEvent -> {
                        if (i[0] > 0) {
                            --i[0]; // Resta 1 a i solo si no se vuelve menor que 0
                            mostrarDestinoImg.setImage(cargarImagen(listaRutas.get(i[0])));
                        }
                    });
                }


            }

        });

        this.colDeNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()));

    }

    public Image cargarImagen(String ruta){
        File file = new File(ruta);
        return new Image(String.valueOf(file.toURI()));
    }

    public void cargarDatos(){
        txtFldNombre.setText(fullName);
        txtFldMail.setText(mail);
        txtFldNumero.setText(phoneNumber);
        txtFldResidencia.setText(residence);
    }

    public void sesionIniciada(Client client){
        clientID = client.getUserId();
        passwordID = client.getPassword();
        fullName = client.getFullName();
        mail = client.getMail();
        phoneNumber = client.getPhoneNumber();
        residence = client.getResidence();
    }

    public void onModificarPerfilClick() {
        txtFldNombre.setEditable(true);
        txtFldMail.setEditable(true);
        txtFldNumero.setEditable(true);
        txtFldResidencia.setEditable(true);
        confirmarEdicionButton.setVisible(true);
    }

    public void onConfirmarEdicionClick() throws AtributoVacioException {

        travelAgency.modificarPerfil(clientID, txtFldNombre.getText(), txtFldMail.getText(), txtFldNumero.getText(), txtFldResidencia.getText());

        txtFldNombre.setEditable(false);
        txtFldMail.setEditable(false);
        txtFldNumero.setEditable(false);
        txtFldResidencia.setEditable(false);
        confirmarEdicionButton.setVisible(false);
    }

    public void onPerfilClick() {
        cargarTablaHistoricoReservas();
        visibilitiesClient(false, true, false);
    }

    public void onConfiRegistrarClienteClick() throws RepeatedInformationException, AtributoVacioException {
        visibilitiesRegister(true, false);
        travelAgency.registrarCliente(idTF.getText(),passTF.getText(),nombreTF.getText(),mailTF.getText(),telefonoTF.getText(),residenciaTF.getText());
        travelAgency.createAlertInfo("Registro de cliente","Informacion","se ha registrado el cliente con la ID" + idTF.getText());
    }


    @FXML
    private void handleButtonAction(ActionEvent event){

        if (event.getTarget() == homeBtn) {
            visibilitiesPrincipal(true,false,false,false);}
        if (event.getTarget() == paquetesBtn) {
            visibilitiesPrincipal(false,true,false,false);}
        if (event.getTarget() == guiasBtn) {
            visibilitiesPrincipal(false,false,true,false);}
        if (event.getTarget() == iniciaSecionBtn) {
            visibilitiesPrincipal(false,false,false,true);}

    }

    public void visibilitiesPrincipal(boolean pane1, boolean pane2 , boolean pane3, boolean pane5 ){
        homePane.setVisible(pane1);
        nuestrosPaquetesPane.setVisible(pane2);
        nuestrosGuiasPane.setVisible(pane3);
        iniciarsesionPane.setVisible(pane5);
    }

    public void visibilitiesClient(boolean pane1,boolean pane2, boolean pane3){
        bienvenidoPane.setVisible(pane1);
        perfilPane.setVisible(pane2);
        reservarPane.setVisible(pane3);
    }

    public void visibilitiesRegister(boolean pan1, boolean pan2){
        iniciarsesionPane.setVisible(pan1);
        registroPanee.setVisible(pan2);
    }

    public void onExitButtonClick() {
        Stage stage = (Stage) cerrarVentanaImgvPrincipal.getScene().getWindow();
        stage.close();
    }

    public void onLogInButtonClick() throws UserNoExistingException, WrongPasswordException, IOException, AtributoVacioException {

        String sesion = travelAgency.LogIn(txtFldID.getText(), txtFldPassword.getText());


        Optional<Client> optionalClient = travelAgency.getClients().stream().filter(client -> client.getUserId().equals(txtFldID.getText())).findFirst();

        if(sesion.equals("Client")){

            hboxPanePrincipal.setVisible(false);
            hboxCliente.setVisible(true);
            visibilitiesClient(true, false, false);

            optionalClient.ifPresent(this::sesionIniciada);

            cargarDatos();

            String[] firstName = fullName.split(" ");

            bienvenidoLabel.setText("Bienvenido de nuevo, " + firstName[0]);

        } else if (sesion.equals("Admin")) {

            travelAgency.generateWindow("src/main/resources/views/adminView.fxml",cerrarVentanaImgvPrincipal);

        } else {
            travelAgency.createAlertError("El usuario ingresado no existe", "Verifique los datos");
        }

    }
    public void onConocerNuestrosPaquetesClick() {
        visibilitiesPrincipal(false,true,false,false);
    }

    private void elementosSheaarchBar() {

        this.colPqNombre.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.colPqPrecio.setCellValueFactory(new PropertyValueFactory<>("price"));
        this.colPqCupo.setCellValueFactory(new PropertyValueFactory<>("quota"));
        this.colPqFechaInicio.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        this.colPqFechaFinal.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        this.colPqDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));

        tP.addAll(travelAgency.getTouristPackages());

        tblPq.setItems(tP);

        FilteredList<TouristPackage> filterData = new FilteredList<>(tP,b->true);
        barraBusquedaTF.textProperty().addListener((observable, oldValue, newValue) -> filterData.setPredicate(touristPackage ->{

            if(newValue.isEmpty() || newValue.isBlank()){
                return true;
            }

            String searchKeyword = newValue.toLowerCase();

            if(touristPackage.getName().toLowerCase().contains(searchKeyword)){
                return true;

            }else if(touristPackage.getPrice().toString().contains(searchKeyword)){
                return true;

            }else if(touristPackage.getQuota().toString().contains(searchKeyword)){
                return true;

            }else if(touristPackage.getStartDate().toString().contains(searchKeyword)){
                return true;

            }else return touristPackage.getEndDate().toString().contains(searchKeyword);

        }));

        SortedList<TouristPackage> sortedData = new SortedList<>(filterData);
        sortedData.comparatorProperty().bind(tblPq.comparatorProperty());

        tblPq.setItems(sortedData);

        dE = tblDe.getItems();
    }

    public void registroExit() {visibilitiesRegister(true,false);}
    public void onRegisterButtonClck() {visibilitiesRegister(false,true);}

    public void onLogOutButtonClick() throws IOException {

        File url = new File("src/main/resources/views/homeView.fxml");
        FXMLLoader loader = new FXMLLoader(url.toURL());
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        stage.show();

        Stage stage1 = (Stage) cerrarVentanaImgvCliente.getScene().getWindow();
        stage1.close();
    }
}