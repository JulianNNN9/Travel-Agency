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
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class AdminViewController {

    private final TravelAgency travelAgency = TravelAgency.getInstance();

    private boolean choiceBoxLoaded = false;

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
    public TextField txtFldRuta;
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
    public TableView<String> destinationsNameTable;
    @FXML
    ObservableList<String> observableListDestinationName;
    @FXML
    public TableColumn<String, String> destinosNameCol;
    @FXML
    public ChoiceBox<String> choiceBoxDestinationName;
    @FXML
    public Pane managePackagesPane;
    @FXML
    public TableView<TouristPackage> packagesTable;
    @FXML
    ObservableList<TouristPackage> packageObservableList;
    @FXML
    public TableColumn<TouristPackage, TouristPackage> namePackageCol, priceCol, quotaCol, startDateCol, durationCol, clientIdCol;
    @FXML
    public ImageView imgViewBackPackagesButton;
    @FXML
    public Button addButtonPackages, modifyButtonPackages, deleteButtonPackages, addButtonDestinationName, deleteButtonDestinationName;
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

    public void initialize(){

        File file = new File("src/main/resources/icons/4103083.png");
        Image backButton = new Image(String.valueOf(file.toURI()));

        imgViewBackDestinationsButton.setImage(backButton);
        imgViewBackPackagesButton.setImage(backButton);
        imgViewBackGuidesButton.setImage(backButton);
        imgViewBackStatisticsButton.setImage(backButton);

        File file1 = new File("src/main/resources/icons/cerrarVentana.png");
        Image exitButton = new Image(String.valueOf(file1.toURI()));

        imgViewExitButton.setImage(exitButton);

        //------------------------DESTINOS----------------------------

        imagesRoutesTable.setDisable(true);
        examinarRutaButton.setDisable(true);
        addButtonImageDestination.setDisable(true);
        deleteButtonImageDestination.setDisable(true);
        txtFldRuta.setDisable(true);
        modifyButtonDestination.setDisable(true);

        destinationsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            boolean seleccionado = newValue != null;

            imagesRoutesTable.setDisable(!seleccionado);
            examinarRutaButton.setDisable(!seleccionado);
            addButtonImageDestination.setDisable(!seleccionado);
            deleteButtonImageDestination.setDisable(!seleccionado);
            txtFldRuta.setDisable(!seleccionado);
            modifyButtonDestination.setDisable(!seleccionado);

            observableListRutas = imagesRoutesTable.getItems();

            if (Objects.requireNonNull(newValue).getImagesHTTPS() != null){
                observableListRutas.clear();
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

        if (!choiceBoxLoaded) {
            List<String> destinationNames = travelAgency.getDestinos().stream()
                    .map(Destino::getName)
                    .toList();

            choiceBoxDestinationName.getItems().addAll(destinationNames);
            choiceBoxLoaded = true;
        }

        destinationsNameTable.setDisable(true);
        addButtonDestinationName.setDisable(true);
        deleteButtonDestinationName.setDisable(true);
        choiceBoxDestinationName.setDisable(true);
        modifyButtonPackages.setDisable(true);

        packagesTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            boolean seleccionado = newValue != null;

            destinationsNameTable.setDisable(!seleccionado);
            addButtonDestinationName.setDisable(!seleccionado);
            deleteButtonDestinationName.setDisable(!seleccionado);
            choiceBoxDestinationName.setDisable(!seleccionado);
            modifyButtonPackages.setDisable(!seleccionado);

            observableListDestinationName = destinationsNameTable.getItems();

            if (Objects.requireNonNull(newValue).getDestinosName() != null){
                observableListDestinationName.clear();
                observableListDestinationName.addAll(newValue.getDestinosName());
            }

            this.destinosNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()));

        });

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
        txtFldRuta.setDisable(true);
        modifyButtonDestination.setDisable(true);

        imagesRoutesTable.setItems(null);
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
            txtFldRuta.setDisable(true);
            modifyButtonDestination.setDisable(true);

            imagesRoutesTable.setItems(null);
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
            txtFldRuta.setDisable(true);
            modifyButtonDestination.setDisable(true);

            imagesRoutesTable.setItems(null);
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
            txtFldRuta.setText(rutaImagen);
        }
    }
    private Stage getStage() {
        return (Stage) txtFldRuta.getScene().getWindow();
    }


    public void agregarRutaImagenDestinations(ActionEvent actionEvent) {

        String destinoName = txtFldName.getText();

        Optional<Destino> destinoSeleccionadoOpcional = travelAgency.getDestinos().stream()
                .filter(destino -> destino.getName().equals(destinoName))
                .findFirst();

        travelAgency.agregarImagenDestino(observableListRutas, txtFldRuta.getText(), destinoSeleccionadoOpcional.get());

        txtFldRuta.clear();
    }

    public void eliminarRutaImagenDestinations(ActionEvent actionEvent) {

        if (imagesRoutesTable.getSelectionModel().getSelectedIndex() >= 0) {
            String selectedRuta = imagesRoutesTable.getSelectionModel().getSelectedItem();
            observableListRutas.remove(selectedRuta);

            String destinoName = txtFldName.getText();

            Optional<Destino> destinoSeleccionadoOpcional = travelAgency.getDestinos().stream()
                    .filter(destino -> destino.getName().equals(destinoName))
                    .findFirst();

            eliminarRuta(destinoSeleccionadoOpcional, selectedRuta);
        }

        txtFldRuta.clear();
    }

    public void eliminarRuta(Optional<Destino> destino, String rutaABorrar){
        List<String> rutasSinEliminar = destino.get().getImagesHTTPS().stream().filter(s -> !s.equals(rutaABorrar)).toList();

        destino.get().getImagesHTTPS().clear();
        destino.get().getImagesHTTPS().addAll(rutasSinEliminar);
    }


    private void limpiarCamposDestinations() {
        txtFldName.clear();
        txtFldCity.clear();
        choiceBoxClima.setValue(null);
        txtFldDescription.clear();
    }

    //----------------------------Packages-----------------------------

    @FXML
    private void agregarElementoPackages(ActionEvent event) throws RepeatedInformationException, AtributoVacioException {

        long duration = 0;

        if (datePckrStartDate.getValue() != null && datePckrEndDate.getValue() != null){
            duration = datePckrStartDate.getValue().until(datePckrEndDate.getValue(), ChronoUnit.DAYS);
        }

        TouristPackage nuevoPaquete = new TouristPackage();

        if (txtFldPrice.getText().isEmpty() || txtFldPrice.getText() == null || txtFldQuota.getText().isEmpty() || txtFldQuota.getText() == null){
            travelAgency.agregarPaquete(packageObservableList, nuevoPaquete);
        }

        nuevoPaquete = TouristPackage.builder()
                .name(txtFldPackageName.getText())
                .price(Double.valueOf(txtFldPrice.getText()))
                .quota(Integer.parseInt(txtFldQuota.getText()))
                .startDate(datePckrStartDate.getValue())
                .endDate(datePckrEndDate.getValue())
                .duration(duration)
                .clientID(txtFldClientID.getText())
                .build();

        travelAgency.agregarPaquete(packageObservableList, nuevoPaquete);

        limpiarCamposPackages();

        destinationsNameTable.setDisable(true);
        addButtonDestinationName.setDisable(true);
        deleteButtonDestinationName.setDisable(true);
        choiceBoxDestinationName.setDisable(true);
        modifyButtonPackages.setDisable(true);
    }

    @FXML
    private void modificarElementoPackages(ActionEvent event) {
        if (packagesTable.getSelectionModel().getSelectedIndex() >= 0) {
            TouristPackage selectedPackage = packagesTable.getSelectionModel().getSelectedItem();

            selectedPackage.setName(txtFldPackageName.getText());
            selectedPackage.setPrice(Double.parseDouble(txtFldPrice.getText()));
            selectedPackage.setQuota(Integer.parseInt(txtFldQuota.getText()));
            selectedPackage.setStartDate(datePckrStartDate.getValue());
            selectedPackage.setEndDate(datePckrEndDate.getValue());
            selectedPackage.setClientID(txtFldClientID.getText());

            limpiarCamposPackages();

            packagesTable.refresh();

            destinationsNameTable.setDisable(true);
            addButtonDestinationName.setDisable(true);
            deleteButtonDestinationName.setDisable(true);
            choiceBoxDestinationName.setDisable(true);
            modifyButtonPackages.setDisable(true);
        }

    }

    @FXML
    private void eliminarElementoPackages(ActionEvent event) {
        if (packagesTable.getSelectionModel().getSelectedIndex() >= 0) {
            TouristPackage selectedPackage = packagesTable.getSelectionModel().getSelectedItem();
            packageObservableList.remove(selectedPackage);
            limpiarCamposPackages();
            destinationsNameTable.setDisable(true);
            addButtonDestinationName.setDisable(true);
            deleteButtonDestinationName.setDisable(true);
            choiceBoxDestinationName.setDisable(true);
            modifyButtonPackages.setDisable(true);
        }
    }

    public void agregarDestinoEnPaquete(ActionEvent actionEvent) {

        String paqueteName = txtFldPackageName.getText();

        Optional<TouristPackage> paqueteSeleccionadoOpcional = travelAgency.getTouristPackages().stream()
                .filter(touristPackage -> touristPackage.getName().equals(paqueteName))
                .findFirst();

        travelAgency.agregarDestinoEnPaquete(observableListDestinationName, choiceBoxDestinationName.getSelectionModel().getSelectedItem(), paqueteSeleccionadoOpcional.get());

        txtFldRuta.clear();
    }

    public void eliminarDestinoEnPaquete(ActionEvent actionEvent) {

        if (destinationsNameTable.getSelectionModel().getSelectedIndex() >= 0) {
            String selectedDestino = destinationsNameTable.getSelectionModel().getSelectedItem();
            observableListDestinationName.remove(selectedDestino);

            String packageName = txtFldPackageName.getText();

            Optional<TouristPackage> packageSeleccionadoOpcional = travelAgency.getTouristPackages().stream()
                    .filter(touristPackage -> touristPackage.getName().equals(packageName))
                    .findFirst();

            eliminarDestinoName(packageSeleccionadoOpcional, selectedDestino);

        }

        choiceBoxDestinationName.setValue(null);

    }

    public void eliminarDestinoName(Optional<TouristPackage> touristPackage, String destinoABorrar){

        List<String> destinosSinEliminar = touristPackage.get().getDestinosName().stream().filter(s -> !s.equals(destinoABorrar)).toList();

        touristPackage.get().getDestinosName().clear();
        touristPackage.get().getDestinosName().addAll(destinosSinEliminar );
    }

    private void limpiarCamposPackages() {
        txtFldPackageName.clear();
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
