package co.edu.uniquindio.travelagency.controllers;

import co.edu.uniquindio.travelagency.exceptions.AtributoVacioException;
import co.edu.uniquindio.travelagency.exceptions.RepeatedInformationException;
import co.edu.uniquindio.travelagency.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

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
    public TextField txtFldName, txtFldCity, txtFldDescription;
    @FXML
    public ChoiceBox<String> choiceBoxClima;
    @FXML
    public TextArea rutaText;
    @FXML
    ObservableList<String> observableListRutas;
    @FXML
    public TableView<String> imagesRoutesTable;
    @FXML
    public TableColumn<String, String> rutasCol;
    @FXML
    public TableView<Destino> destinationsTable;
    @FXML
    ObservableList<Destino> destinoObservableList;
    @FXML
    public TableColumn<Destino, Destino> nameDestinationCol, cityCol, descriptionCol,weatherCol;
    public Button examinarRutaButton, deleteButtonDestination, modifyButtonDestination, addButtonDestination, deleteButtonImageDestination, addButtonImageDestination;

    //Ventana gestionar paquetes

    @FXML
    public Pane managePackagesPane;
    @FXML
    public TableView<TouristPackage> packagesTable;
    ObservableList<TouristPackage> packageObservableList;
    @FXML
    public TableColumn<TouristPackage, TouristPackage> namePackageCol, priceCol, quotaCol, startDateCol, durationCol, clientIdCol;
    @FXML
    public ImageView imgViewBackPackagesButton;
    @FXML
    public Button addButtonPackages, modifyButtonPackages, deleteButtonPackages;
    @FXML
    public TextField txtFldPackageName,txtFldPrice, txtFldQuota, txtFldClientID;
    @FXML
    public DatePicker datePckrStartDate, datePckrEndDate;

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

        //------------------------DESTINOS----------------------------

        imagesRoutesTable.setDisable(true);
        examinarRutaButton.setDisable(true);
        addButtonImageDestination.setDisable(true);
        deleteButtonImageDestination.setDisable(true);
        rutaText.setDisable(true);

        destinationsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            boolean seleccionado = newValue != null;
            imagesRoutesTable.setDisable(!seleccionado);
            examinarRutaButton.setDisable(!seleccionado);
            addButtonImageDestination.setDisable(!seleccionado);
            deleteButtonImageDestination.setDisable(!seleccionado);
            rutaText.setDisable(!seleccionado);

            observableListRutas = imagesRoutesTable.getItems();

            if (Objects.requireNonNull(newValue).getImagesHTTPS() != null){
                observableListRutas.addAll(newValue.getImagesHTTPS());
            }

            this.rutasCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()));


        });

        destinoObservableList = destinationsTable.getItems();

        if (travelAgency.getDestinos() != null) {
            destinoObservableList.addAll(travelAgency.getDestinos());
        }

        this.nameDestinationCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.cityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
        this.descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        this.weatherCol.setCellValueFactory(new PropertyValueFactory<>("weather"));

        destinationsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtFldName.setText(newSelection.getName());
                txtFldCity.setText(newSelection.getCity());
                txtFldDescription.setText(newSelection.getDescription());
                choiceBoxClima.setValue(newSelection.getWeather());
            }
        });

        //------------------------PAQUETES----------------------------

        packageObservableList = packagesTable.getItems();

        if (travelAgency.getTouristPackages() != null) {
            packageObservableList.addAll(travelAgency.getTouristPackages());
        }

        this.namePackageCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        this.quotaCol.setCellValueFactory(new PropertyValueFactory<>("quota"));
        this.startDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        this.durationCol.setCellValueFactory(new PropertyValueFactory<>("duration"));
        this.clientIdCol.setCellValueFactory(new PropertyValueFactory<>("clientID"));

        packagesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtFldPackageName.setText(newSelection.getName());
                txtFldPrice.setText(String.valueOf(newSelection.getPrice()));
                txtFldQuota.setText(String.valueOf(newSelection.getQuota()));
                datePckrStartDate.setValue(newSelection.getStartDate());
                datePckrEndDate.setValue(newSelection.getEndDate());
                txtFldClientID.setText(newSelection.getClientID());
            }
        });

        //------------------------GUIAS----------------------------

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

    }

    //----------------------------Destinations-----------------------------

    @FXML
    private void agregarElementoDestinations(ActionEvent event) throws RepeatedInformationException, AtributoVacioException {

        Destino nuevoDestino = Destino.builder()
                .name(txtFldName.getText())
                .city(txtFldCity.getText())
                .imagesHTTPS(new ArrayList<>())
                .description(txtFldDescription.getText())
                .weather(choiceBoxClima.getValue())
                .build();

        travelAgency.agregarDestino(destinoObservableList, nuevoDestino);

        limpiarCamposDestinations();
        imagesRoutesTable.setDisable(true);
        examinarRutaButton.setDisable(true);
        addButtonImageDestination.setDisable(true);
        deleteButtonImageDestination.setDisable(true);
        rutaText.setDisable(true);
    }

    @FXML
    private void modificarElementoDestinations(ActionEvent event) {
        if (destinationsTable.getSelectionModel().getSelectedIndex() >= 0) {
            Destino selectedDestino = destinationsTable.getSelectionModel().getSelectedItem();
            selectedDestino.setName(txtFldName.getText());
            selectedDestino.setCity(txtFldCity.getText());
            selectedDestino.setDescription(txtFldDescription.getText());
            selectedDestino.setWeather(choiceBoxClima.getValue());
            limpiarCamposDestinations();
            destinationsTable.refresh();
            imagesRoutesTable.setDisable(true);
            examinarRutaButton.setDisable(true);
            addButtonImageDestination.setDisable(true);
            deleteButtonImageDestination.setDisable(true);
            rutaText.setDisable(true);
        }

    }

    @FXML
    private void eliminarElementoDestinations(ActionEvent event) {
        if (destinationsTable.getSelectionModel().getSelectedIndex() >= 0) {
            Destino selectedDestino = destinationsTable.getSelectionModel().getSelectedItem();
            destinoObservableList.remove(selectedDestino);
            limpiarCamposDestinations();
            imagesRoutesTable.setDisable(true);
            examinarRutaButton.setDisable(true);
            addButtonImageDestination.setDisable(true);
            deleteButtonImageDestination.setDisable(true);
            rutaText.setDisable(true);
        }
    }

    public void seleccionarImagen(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes", "*.jpg", "*.jpeg", "*.png", "*.gif", "*.bmp")
        );

        File imagenSeleccionada = fileChooser.showOpenDialog(getStage());
        if (imagenSeleccionada != null) {
            String rutaImagen = imagenSeleccionada.getAbsolutePath();
            rutaText.setText(rutaImagen);
        }
    }
    private Stage getStage() {
        return (Stage) rutaText.getScene().getWindow();
    }


    public void agregarRutaImagenDestinations(ActionEvent actionEvent) {

        String destinoName = txtFldName.getText();

        Optional<Destino> destinoSeleccionadoOpcional = travelAgency.getDestinos().stream()
                .filter(destino -> destino.getName().equals(destinoName))
                .findFirst();

        travelAgency.agregarImagenDestino(observableListRutas, rutaText.getText(), destinoSeleccionadoOpcional.get());
    }

    public void eliminarRutaImagenDestinations(ActionEvent actionEvent) {
        if (imagesRoutesTable.getSelectionModel().getSelectedIndex() >= 0) {
            String selectedRuta = imagesRoutesTable.getSelectionModel().getSelectedItem();
            observableListRutas.remove(selectedRuta);
        }
    }


    private void limpiarCamposDestinations() {
        txtFldName.clear();
        txtFldCity.clear();
        choiceBoxClima.setValue(null);
        txtFldDescription.clear();
    }

    //----------------------------Packages-----------------------------

    @FXML
    private void agregarElementoPackages(ActionEvent event) {

        long duration = 0L;

        TouristPackage nuevoPaquete = TouristPackage.builder()
                .name(txtFldPackageName.getText())
                .price(Double.parseDouble(txtFldPrice.getText()))
                .quota(Integer.parseInt(txtFldQuota.getText()))
                .startDate(datePckrStartDate.getValue())
                .endDate(datePckrEndDate.getValue())
                .duration(duration)
                .clientID(txtFldClientID.getText())
                .build();

        travelAgency.agregarPaquete(packageObservableList, nuevoPaquete);

        limpiarCamposPackages();
    }

    @FXML
    private void modificarElementoPackages(ActionEvent event) {
        if (destinationsTable.getSelectionModel().getSelectedIndex() >= 0) {
            TouristPackage selectedPackage = packagesTable.getSelectionModel().getSelectedItem();
            selectedPackage.setName(txtFldPackageName.getText());
            selectedPackage.setPrice(Double.parseDouble(txtFldPrice.getText()));
            selectedPackage.setQuota(Integer.parseInt(txtFldQuota.getText()));
            selectedPackage.setStartDate(datePckrStartDate.getValue());
            selectedPackage.setEndDate(datePckrEndDate.getValue());
            selectedPackage.setClientID(txtFldClientID.getText());
            limpiarCamposPackages();
            destinationsTable.refresh();
        }

    }

    @FXML
    private void eliminarElementoPackages(ActionEvent event) {
        if (packagesTable.getSelectionModel().getSelectedIndex() >= 0) {
            TouristPackage selectedPackage = packagesTable.getSelectionModel().getSelectedItem();
            packageObservableList.remove(selectedPackage);
            limpiarCamposPackages();
        }
    }

    private void limpiarCamposPackages() {
        txtFldName.clear();
        txtFldPrice.clear();
        txtFldQuota.clear();
        txtFldClientID.clear();
        datePckrEndDate.setValue(null);
        datePckrStartDate.setValue(null);
    }

    //----------------------------Guides-----------------------------


    @FXML
    private void handleButtonAction(ActionEvent event){

        if (event.getTarget() == manageDestinationsButton){visibilities(false,true,false,false,false,false);}
        if (event.getTarget() == managePackagesButton){visibilities(false,false,false,true,false,false);}
        if (event.getTarget() == manageGuidesButton) {visibilities(false,false,false,false,true,false);}
        if (event.getTarget() == statisticsButton) {visibilities(false,false,false,false,false,true);}

    }

    public void visibilities(boolean pane1, boolean pane2 , boolean pane3, boolean pane4, boolean pane5, boolean pane6 ){

        principalPane.setVisible(pane1);
        manageDestinationsPane.setVisible(pane2);
        managePackagesPane.setVisible(pane4);
        manageGuidesPane.setVisible(pane5);
        statisticsPane.setVisible(pane6);

    }

    public void onBackButtonClick(MouseEvent mouseEvent) {

        if (mouseEvent.getTarget() == imgViewBackDestinationsButton){visibilities(true, false, false, false, false, false);}
        if (mouseEvent.getTarget() == imgViewBackPackagesButton){visibilities(true, false,false, false, false, false);}
        if (mouseEvent.getTarget() == imgViewBackGuidesButton){visibilities(true, false,false, false, false, false);}
        if (mouseEvent.getTarget() == imgViewBackStatisticsButton){visibilities(true, false,false, false, false, false);}

    }

    public void onExitButtonClick() throws IOException {

        File url = new File("src/main/resources/views/homeView.fxml");
        FXMLLoader loader = new FXMLLoader(url.toURL());
        Parent parent = loader.load();

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        stage.setResizable(false);
        stage.show();

        Stage stage1 = (Stage) imgViewExitButton.getScene().getWindow();
        stage1.close();

    }

}
