package co.edu.uniquindio.travelagency.controllers;

import co.edu.uniquindio.travelagency.model.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class AdminViewController {

    private final TravelAgency travelAgency = TravelAgency.getInstance();

    //Ventana principal

    @FXML
    public Pane principalPane;
    @FXML
    public ImageView imgViewExitButton;
    @FXML
    public Button manageDestinationsButton, managePackagesButton, manageGuidesButton, statisticsButton;

    //Ventana gestionar destinos

    @FXML
    public Pane manageDestinationsPane;
    @FXML
    public ImageView imgViewBackDestinationsButton;
    @FXML
    public TextField txtFldName, txtFldCity, txtFldWeather, txtFldDescription;
    @FXML
    public TableView<Destination> destinationsTable;
    @FXML
    public TableColumn<Destination, Destination> nameDestinationCol, cityCol, descriptionCol,weatherCol;
    @FXML
    public Button deleteButtonDestination, modifyButtonDestination, addButtonDestination;

    //Ventana gestionar paquetes

    @FXML
    public Pane managePackagesPane;
    @FXML
    public TableView<TouristPackage> packagesTable;
    @FXML
    public TableColumn<TouristPackage, TouristPackage> namePackageCol, priceCol, quotaCol, startDateCol, durationCol, clientIdCol;
    @FXML
    public ImageView imgViewBackPackagesButton;
    @FXML
    public Button addButtonPackages, modifyButtonPackages, deleteButtonPackages;
    @FXML
    public TextField txtFldPackageName,txtFldPrice, txtFldQuota, txtFldDuration, txtFldClientID;
    @FXML
    public DatePicker datePckrStartDate;

    //Ventana gestionar guias
    @FXML
    public Pane manageGuidesPane;
    @FXML
    public TableView<TouristGuide> guidesTable;
    @FXML
    public TableColumn<TouristGuide, TouristGuide> idGuideCol, fullNameGuideCol, experienceCol, ratingCol;
    @FXML
    public ImageView imgViewBackGuidesButton;
    @FXML
    public Button addButtonGuides, modifyButtonGuides, deleteButtonGuide;
    @FXML
    public TextField txtFldGuideId, txtFldFullNameGuide, txtFldExperience, txtFldRating;

    //Ventana estadísticas

    @FXML
    public Pane statisticsPane;
    @FXML
    public ImageView imgViewBackStatisticsButton;

    public void initialize() {

        Image backButton = new Image("https://cdn-icons-png.flaticon.com/128/4103/4103083.png");
        imgViewBackDestinationsButton.setImage(backButton);
        imgViewBackPackagesButton.setImage(backButton);
        imgViewBackGuidesButton.setImage(backButton);
        imgViewBackStatisticsButton.setImage(backButton);

        Image exitButton = new Image("https://cdn-icons-png.flaticon.com/128/5735/5735775.png");
        imgViewExitButton.setImage(exitButton);

        //Lista de destinos

        ObservableList<Destination> destinationObservableList = destinationsTable.getItems();

        if (travelAgency.getDestinations() != null) {
            destinationObservableList.addAll(travelAgency.getDestinations());
        }

        this.nameDestinationCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.cityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
        this.descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        this.weatherCol.setCellValueFactory(new PropertyValueFactory<>("weather"));

        destinationsTable.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {

        });

        //Lista de paquetes

        ObservableList<TouristPackage> packageObservableList = packagesTable.getItems();

        if (travelAgency.getTouristPackages() != null) {
            packageObservableList.addAll(travelAgency.getTouristPackages());
        }

        this.namePackageCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        this.quotaCol.setCellValueFactory(new PropertyValueFactory<>("quota"));
        this.startDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        this.durationCol.setCellValueFactory(new PropertyValueFactory<>("duration"));
        this.clientIdCol.setCellValueFactory(new PropertyValueFactory<>("clientID"));

        destinationsTable.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {

        });

        //Lista de guias

        ObservableList<TouristGuide> touristGuideObservableList = guidesTable.getItems();

        if (travelAgency.getTouristGuides() != null) {
            touristGuideObservableList.addAll(travelAgency.getTouristGuides());
        }

        this.idGuideCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.fullNameGuideCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        this.experienceCol.setCellValueFactory(new PropertyValueFactory<>("experience"));
        this.ratingCol.setCellValueFactory(new PropertyValueFactory<>("rating"));

        guidesTable.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {

        });

        //

    }

    @FXML
    private void handleButtonAction(MouseEvent event){

        if(event.getTarget() == manageDestinationsButton){
            visibilities(false,true,false,false,false);
        }else if (event.getTarget() == managePackagesPane){
            visibilities(false,false,true,false,false);
        } else if (event.getTarget() == manageGuidesButton) {
            visibilities(false,false,false,true,false);
        } else if (event.getTarget() == statisticsButton) {
            visibilities(false,false,false,false,true);
        }

    }

    public void visibilities(boolean pane1, boolean pane2 , boolean pane3, boolean pane4, boolean pane5 ){

        principalPane.setVisible(pane1);
        manageDestinationsPane.setVisible(pane2);
        managePackagesPane.setVisible(pane3);
        manageGuidesPane.setVisible(pane4);
        statisticsPane.setVisible(pane5);

    }

    public void onBackButtonClick(MouseEvent mouseEvent) {

        if (mouseEvent.getTarget() == imgViewBackDestinationsButton){
            visibilities(true, false, false, false, false);
        }if (mouseEvent.getTarget() == imgViewBackPackagesButton){
            visibilities(true, false, false, false, false);
        }if (mouseEvent.getTarget() == imgViewBackGuidesButton){
            visibilities(true, false, false, false, false);
        }if (mouseEvent.getTarget() == imgViewBackStatisticsButton){
            visibilities(true, false, false, false, false);
        }

    }

    public void onExitButtonClick() {

        Stage stage = (Stage) imgViewExitButton.getScene().getWindow();
        stage.close();

    }

}
