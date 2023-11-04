package co.edu.uniquindio.travelagency.controllers;

import co.edu.uniquindio.travelagency.exceptions.*;
import co.edu.uniquindio.travelagency.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.security.PublicKey;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.Optional;

public class HomeController {

    private final TravelAgency travelAgency = TravelAgency.getInstance();


    //----------------------Modificar perfil----------------------
    @FXML
    private HBox hboxCliente;
    @FXML
    public TableView<TouristPackage> tblPq;
    @FXML
    private ImageView cerrarVentanaImgv;
    @FXML
    public Pane perfilPane;
    @FXML
    private ImageView PerfilImgv;
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
    private TableColumn<Reservation, String> packageColumn;
    @FXML
    private TableColumn<Reservation, String> startDateColumn;
    @FXML
    private TableColumn<Reservation, String> endDateColumn;
    @FXML
    private TableColumn<Reservation, String> numberOfPeopleColumn;
    @FXML
    private ObservableList<Reservation> reservations = FXCollections.observableArrayList();

    //----------------------Reservar----------------------
    @FXML
    public Pane reservarPane;
    @FXML
    public Button reservarButton;
    @FXML
    public TextField txtFldNombrePaquete;
    @FXML
    public TableView<TouristPackage> packagesTable;
    @FXML
    ObservableList<TouristPackage> packageObservableList;
    @FXML
    public TableColumn<TouristPackage, TouristPackage> namePackageCol, priceCol, quotaCol, startDateCol, durationCol;

    //----------------------Registrar cliente----------------------
    @FXML
    private AnchorPane registroPanee;
    @FXML
    public TextField nombreTF, idTF ,passTF,mailTF ,telefonoTF,residenciaTF;
    @FXML
    private ImageView registroExit;
    @FXML
    private Button confirRegistroButtom;

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

    //----------------------Guias pane----------------------
    @FXML
    private AnchorPane nuestrosGuiasPane;
    @FXML
    private Button guiasBtn;

    //----------------------Iniciar Sesion Pane----------------------
    @FXML
    public TextField txtFldID, txtFldPassword;
    @FXML
    private Button iniciaSecionBtn,btnLogIn,btnRegister;
    @FXML
    private AnchorPane iniciarsesionPane;
    @FXML
    private TextArea infoTA;
    @FXML
    private HBox hboxPanePrincipal;

    @FXML private TableView<String> tblDe;

    @FXML private TableColumn<String,String>  colDeNombre, colDeCiudad, colDeDescription, colDeClima;

    @FXML
    ObservableList<String> dE = FXCollections.observableArrayList();
    @FXML
             ObservableList<TouristPackage>  tP = FXCollections.observableArrayList();



    String  clientID, passwordID,fullName, mail, phoneNumber, residence;

    public void initialize() {
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

        //Cargas historial de reservaciones

        packageColumn.setCellValueFactory(new PropertyValueFactory<>("touristPackageString"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDateString"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDateString"));
        numberOfPeopleColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfPeopleString"));

        // Llama a travelagency.getReservations para obtener las reservaciones
        List<Reservation> reservationsData = travelAgency.getReservations();

        reservations.addAll(reservationsData);

        historialReservacionesTable.setItems(reservations);

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

    public void onReservarClick(ActionEvent actionEvent) {
        visibilitiesClient(false, true);
    }

    private void seleccionDestinos() {
        dE = tblDe.getItems();

        tblPq.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            boolean seleccionado = newValue != null;



            if (seleccionado) {
                if (Objects.requireNonNull(newValue).getDestinosName() != null) {
                    dE.setAll( newValue.getDestinosName());
                }
            }

            this.colDeNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()));

        });
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

    public void onModificarPerfilClick(ActionEvent actionEvent) {
        txtFldNombre.setEditable(true);
        txtFldMail.setEditable(true);
        txtFldNumero.setEditable(true);
        txtFldResidencia.setEditable(true);
        confirmarEdicionButton.setVisible(true);
    }

    public void onConfirmarEdicionClick(ActionEvent actionEvent) throws AtributoVacioException {

        travelAgency.modificarPerfil(clientID, txtFldNombre.getText(), txtFldMail.getText(), txtFldNumero.getText(), txtFldResidencia.getText());

        txtFldNombre.setEditable(false);
        txtFldMail.setEditable(false);
        txtFldNumero.setEditable(false);
        txtFldResidencia.setEditable(false);
        confirmarEdicionButton.setVisible(false);
    }

    public void onPerfilClick(MouseEvent mouseEvent) {
        visibilitiesClient(true, false);
    }

    public void onConfiRegistrarClienteClick() throws RepeatedInformationException, AtributoVacioException {
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

    public void visibilitiesClient(boolean pane1, boolean pane2){
        perfilPane.setVisible(pane1);
        reservarPane.setVisible(pane2);
    }

    public void visibilitiesRegister(boolean pan1, boolean pan2, boolean pan3){
        hboxPanePrincipal.setVisible(pan1);
        iniciarsesionPane.setVisible(pan2);
        registroPanee.setVisible(pan3);
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
            visibilitiesPrincipal(true, false, false, false);

            optionalClient.ifPresent(this::sesionIniciada);

            cargarDatos();

        } else if (sesion.equals("Admin")) {

            travelAgency.generateWindow("src/main/resources/views/adminView.fxml",cerrarVentanaImgvPrincipal);

        } else {
            travelAgency.createAlertError("El usuario ingresado no existe", "Verifique los datos");
        }

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
        barraBusquedaTF.textProperty().addListener((observable, oldValue, newValue) -> {
            filterData.setPredicate(touristPackage ->{
                if(newValue.isEmpty() || newValue.isBlank()){
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                if(touristPackage.getName().toLowerCase().indexOf(searchKeyword)> -1){
                    return true;

                }else if(touristPackage.getPrice().toString().indexOf(searchKeyword)>-1){
                    return true;

                }else if(touristPackage.getQuota().toString().indexOf(searchKeyword)>-1){
                    return true;

                }else if(touristPackage.getStartDate().toString().indexOf(searchKeyword)>-1){
                    return true;

                }else if(touristPackage.getEndDate().toString().indexOf(searchKeyword)>-1){
                    return true;

                }else
                    return false;

            });

        });

        SortedList<TouristPackage> sortedData = new SortedList<>(filterData);
        sortedData.comparatorProperty().bind(tblPq.comparatorProperty());

        tblPq.setItems(sortedData);

        dE = tblDe.getItems();
    }
    public void registroExit(MouseEvent e) {
        visibilitiesRegister(true,true,false);}
    public void onRegisterButtonClck(ActionEvent e) {
        visibilitiesRegister(false,false,true);
    }
}
