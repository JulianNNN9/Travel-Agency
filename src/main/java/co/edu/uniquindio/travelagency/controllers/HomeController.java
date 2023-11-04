package co.edu.uniquindio.travelagency.controllers;

import co.edu.uniquindio.travelagency.exceptions.*;
import co.edu.uniquindio.travelagency.model.TouristPackage;
import co.edu.uniquindio.travelagency.model.TravelAgency;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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

public class HomeController {

    private final TravelAgency travelAgency = TravelAgency.getInstance();

    //----------------------Modificar perfil----------------------
    @FXML
    private HBox hboxCliente;
    @FXML
    public Pane perfilPane;
    @FXML
    private ImageView PerfilImgv;
    @FXML
    public TextField txtFldNombre;
    @FXML
    public TextField txtFldMail;
    @FXML
    public TextField txtFldNumero;
    @FXML
    public TextField txtFldResidencia;
    @FXML
    public Button confirmarEdicionButton;

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

    public void initialize() {

        File file1 = new File("src/main/resources/icons/cerrarVentana.png");
        Image exitButton = new Image(String.valueOf(file1.toURI()));

        cerrarVentanaImgvPrincipal.setImage(exitButton);

        //----------------------Modificar perfil----------------------

        txtFldNombre.setEditable(false);
        txtFldMail.setEditable(false);
        txtFldNumero.setEditable(false);
        txtFldResidencia.setEditable(false);
        confirmarEdicionButton.setVisible(false);
    }

    public void onModificarPerfilClick(ActionEvent actionEvent) {
        txtFldNombre.setEditable(true);
        txtFldMail.setEditable(true);
        txtFldNumero.setEditable(true);
        txtFldResidencia.setEditable(true);
        confirmarEdicionButton.setVisible(true);
    }

    public void onConfirmarEdicionClick(ActionEvent actionEvent) {
    }

    public void onPerfilClick(MouseEvent mouseEvent) {
        visibilitiesClient(true);
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

    public void visibilitiesClient(boolean pane1){
        perfilPane.setVisible(pane1);
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


        if(sesion.equals("Client")){
            hboxPanePrincipal.setVisible(false);
            hboxCliente.setVisible(true);
            visibilitiesPrincipal(true, false, false, false);
        } else if (sesion.equals("Admin")) {
            travelAgency.generateWindow("src/main/resources/views/adminView.fxml",cerrarVentanaImgvPrincipal);
        } else {
            travelAgency.createAlertError("El usuario ingresado no existe", "Verifique los datos");
        }

    }
    public void registroExit(MouseEvent e) {
        visibilitiesRegister(true,true,false);}
    public void onRegisterButtonClck(ActionEvent e) {
        visibilitiesRegister(false,false,true);
    }

    public void buscador1(KeyEvent keyEvent) {

        infoTA.setText("");
        String cadenaInformativa = "";
        List<TouristPackage> jeje = travelAgency.getTouristPackages();

        for(int i=0; i< jeje.size();i++){
            if(travelAgency.empiezaPor(barraBusquedaTF.getText()))
                cadenaInformativa += jeje.get(i).toString()+"\n\n";
        infoTA.setText(cadenaInformativa);

        }


    }
}
