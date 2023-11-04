package co.edu.uniquindio.travelagency.controllers;

import co.edu.uniquindio.travelagency.exceptions.*;
import co.edu.uniquindio.travelagency.model.TouristPackage;
import co.edu.uniquindio.travelagency.model.TravelAgency;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class HomeController {

    private final TravelAgency travelAgency = TravelAgency.getInstance();

    @FXML
    public TextField txtFldID, txtFldPassword,nombreTF, idTF ,passTF,mailTF ,telefonoTF,residenciaTF,barraBusquedaTF;
    @FXML
    public TableView<TouristPackage> tblPq;
    @FXML
    private ImageView cerrarVentanaImgv;
    @FXML
    private Button homeBtn,paquetesBtn,guiasBtn,ayudasBtn,iniciaSecionBtn,btnLogIn,btnRegister, confirRegistroButtom;
    @FXML
    private AnchorPane homePane,nuestrosPaquetesPane,nuestrosGuiasPane,ayudaPane,iniciarsesionPane,registroPanee;
    @FXML
    private TableColumn<TouristPackage,TouristPackage> colPqNombre, colPqPrecio,colPqCupo,colPqFechaInicio,colPqFechaFinal,colPqDuration;

    @FXML
     ObservableList<TouristPackage> touristPackagelist ;









    public void onConfiRegistrarClienteClick() throws RepeatedInformationException, AtributoVacioException {

        travelAgency.registrarCliente(idTF.getText(),passTF.getText(),nombreTF.getText(),mailTF.getText(),telefonoTF.getText(),residenciaTF.getText());
        travelAgency.createAlertInfo("Registro de cliente","Informacion","se ha registrado el cliente con la ID" + idTF.getText());

    }

    @FXML
    private HBox hboxPane;



    public void initialize() {

        touristPackagelist = tblPq.getItems();

        if (travelAgency.getTouristPackages() != null) {
            touristPackagelist.addAll(travelAgency.getTouristPackages());
        }

        this.colPqNombre.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.colPqPrecio.setCellValueFactory(new PropertyValueFactory<>("price"));
        this.colPqCupo.setCellValueFactory(new PropertyValueFactory<>("quota"));
        this.colPqFechaInicio.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        this.colPqFechaFinal.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        this.colPqDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));

//        tblPq.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
//            if (newSelection != null) {
//                txtFldPackageName.setText(newSelection.getName());
//                txtFldPrice.setText(String.valueOf(newSelection.getPrice()));
//                txtFldQuota.setText(String.valueOf(newSelection.getQuota()));
//                datePckrStartDate.setValue(newSelection.getStartDate());
//                datePckrEndDate.setValue(newSelection.getEndDate());
//            }
//        });

        File file1 = new File("src/main/resources/icons/cerrarVentana.png");
        Image exitButton = new Image(String.valueOf(file1.toURI()));

        searchFilter();

        cerrarVentanaImgv.setImage(exitButton);

    }

    private void searchFilter() {
//        FilteredList<TouristPackage> filterData= new FilteredList<>(touristPackagelist,e->true);
//        barraBusquedaTF.setOnKeyPressed(e ->{
//            barraBusquedaTF.textProperty().addListener((observable, oldValue, newVaue )->{
//                filterData.setPredicate((Predicate<? super TouristPackage>) cust ->{
//                    if (newVaue == null){
//                        return  true;
//                    }
//                    String toLowerCaseFilter =newVaue.toLowerCase();
//                    if(cust.getName().contains(newVaue)){
//                        return true;
//                    }else if(cust.)
//                } );
//            });
//
//        });
    }


    @FXML
    private void handleButtonAction(ActionEvent event){

        if (event.getTarget() == homeBtn)
        {visibilities(true,false,false,false,false);}
        if (event.getTarget() == paquetesBtn)
        {visibilities(false,true,false,false,false);}
        if (event.getTarget() == guiasBtn)
        {visibilities(false,false,true,false,false);}
        if (event.getTarget() == ayudasBtn)
        {visibilities(false, false, false, true, false);}
        if (event.getTarget() == iniciaSecionBtn)
        {visibilities(false,false,false,false,true);}

    }

    public void visibilities(boolean pane1, boolean pane2 , boolean pane3, boolean pane4, boolean pane5 ){

        homePane.setVisible(pane1);
        nuestrosPaquetesPane.setVisible(pane2);
        nuestrosGuiasPane.setVisible(pane3);
        ayudaPane.setVisible(pane4);
        iniciarsesionPane.setVisible(pane5);

    }

    public void visibilities2(boolean pan1,boolean pan2,boolean pan3){

        hboxPane.setVisible(pan1);
        iniciarsesionPane.setVisible(pan2);
        registroPanee.setVisible(pan3);

    }

    public void onExitButtonClick() {

        Stage stage = (Stage) cerrarVentanaImgv.getScene().getWindow();
        stage.close();

    }

    public void onLogInButtonClick() throws UserNoExistingException, WrongPasswordException, EmptyAttributeException, IOException {

        String u = travelAgency.LogIn(txtFldID.getText(), txtFldPassword.getText());


        if(u.equals("Client")){
            travelAgency.generateWindow("src/main/resources/views/Client.fxml",cerrarVentanaImgv);
        } else if (u.equals("Admin")) {
            travelAgency.generateWindow("src/main/resources/views/adminView.fxml",cerrarVentanaImgv);
        }

    }
    public void registroExit(MouseEvent e) {visibilities2(true,true,false);}
    public void onRegisterButtonClck(ActionEvent e) {
        visibilities2(false,false,true);
    }


}
