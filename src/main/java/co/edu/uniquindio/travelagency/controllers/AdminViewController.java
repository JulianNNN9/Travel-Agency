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

public class AdminViewController {

    private final TravelAgency travelAgency = TravelAgency.getInstance();

    //Ventana principal

    @FXML
    public Pane principalPane;
    @FXML
    public ImageView imgViewExitButton;
    @FXML
    public Button manageDestinationsButton;
    @FXML
    public Button managePackagesButton;
    @FXML
    public Button manageGuidesButton;
    @FXML
    public Button statisticsButton;

    //Ventana gestionar destinos

    @FXML
    public Pane manageDestinationsPane;
    @FXML
    public ImageView imgViewBackDestinationsButton;
    @FXML
    public TextField txtFldName;
    @FXML
    public TextField txtFldCity;
    @FXML
    public TextField txtFldWeather;
    @FXML
    public TextField txtFldDescription;
    @FXML
    public TableView<Destination> destinationsTable;
    @FXML
    public TableColumn<Destination, Destination> nameDestinationCol;
    @FXML
    public TableColumn<Destination, Destination> cityCol;
    @FXML
    public TableColumn<Destination, Destination> descriptionCol;
    @FXML
    public TableColumn<Destination, Destination> weatherCol;
    @FXML
    public Button deleteButtonDestination;
    @FXML
    public Button modifyButtonDestination;
    @FXML
    public Button addButtonDestination;

    //Ventana gestionar paquetes

    @FXML
    public Pane managePackagesPane;
    @FXML
    public TableView<TouristPackage> packagesTable;
    @FXML
    public TableColumn<TouristPackage, TouristPackage> namePackageCol;
    @FXML
    public TableColumn<TouristPackage, TouristPackage> priceCol;
    @FXML
    public TableColumn<TouristPackage, TouristPackage> quotaCol;
    @FXML
    public TableColumn<TouristPackage, TouristPackage> startDateCol;
    @FXML
    public TableColumn<TouristPackage, TouristPackage> durationCol;
    @FXML
    public TableColumn<TouristPackage, TouristPackage> clientIdCol;
    @FXML
    public ImageView imgViewBackPackagesButton;
    @FXML
    public Button addButtonPackages;
    @FXML
    public Button modifyButtonPackages;
    @FXML
    public Button deleteButtonPackages;
    @FXML
    public TextField txtFldPackageName;
    @FXML
    public TextField txtFldPrice;
    @FXML
    public TextField txtFldQuota;
    @FXML
    public DatePicker datePckrStartDate;
    @FXML
    public TextField txtFldDuration;
    @FXML
    public TextField txtFldClientID;

    //Ventana gestionar guias
    @FXML
    public Pane manageGuidesPane;
    @FXML
    public TableView<TouristGuide> guidesTable;
    @FXML
    public TableColumn<TouristGuide, TouristGuide> idGuideCol;
    @FXML
    public TableColumn<TouristGuide, TouristGuide> fullNameGuideCol;
    @FXML
    public TableColumn<TouristGuide, TouristGuide> experienceCol;
    @FXML
    public TableColumn<TouristGuide, TouristGuide> ratingCol;
    @FXML
    public ImageView imgViewBackGuidesButton;
    @FXML
    public Button addButtonGuides;
    @FXML
    public Button modifyButtonGuides;
    @FXML
    public Button deleteButtonGuide;
    @FXML
    public TextField txtFldGuideId;
    @FXML
    public TextField txtFldFullNameGuide;
    @FXML
    public TextField txtFldExperience;
    @FXML
    public TextField txtFldRating;

    //Ventana estadísticas

    public void initialize() {

        Image backbuttonDestination = new Image("https://cdn-icons-png.flaticon.com/128/4103/4103083.png");
        imgViewBackDestinationsButton.setImage(backbuttonDestination);

        Image exitButton = new Image("https://cdn-icons-png.flaticon.com/128/5735/5735775.png");
        imgViewExitButton.setImage(exitButton);

        //Lista de destinos

        ObservableList<Destination> destinationObservableList = destinationsTable.getItems();
        //destinationObservableList.addAll(travelAgency.getDestinations());

        this.nameDestinationCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.cityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
        this.descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        this.weatherCol.setCellValueFactory(new PropertyValueFactory<>("weather"));

        destinationsTable.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {

        });

        //Lista de paquetes

        ObservableList<TouristPackage> packageObservableList = packagesTable.getItems();
        //packageObservableList.addAll(travelAgency.getTouristPackages());

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
        //touristGuideObservableList.addAll(travelAgency.getTouristGuides());

        this.idGuideCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.fullNameGuideCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        this.experienceCol.setCellValueFactory(new PropertyValueFactory<>("experience"));
        this.ratingCol.setCellValueFactory(new PropertyValueFactory<>("rating"));

        guidesTable.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {

        });

        //

    }

    public void onBackButtonClick(MouseEvent mouseEvent) {

    }

    public void onExitButtonClick(MouseEvent mouseEvent) {

    }
}
