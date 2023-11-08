package co.edu.uniquindio.travelagency.controllers;

import co.edu.uniquindio.travelagency.exceptions.AtributoVacioException;
import co.edu.uniquindio.travelagency.exceptions.ErrorEnIngresoFechasException;
import co.edu.uniquindio.travelagency.exceptions.RepeatedInformationException;
import co.edu.uniquindio.travelagency.exceptions.RutaInvalidaException;
import co.edu.uniquindio.travelagency.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.File;
import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.*;

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
    public Button limpiarCamposDestinos, examinarRutaButton, deleteButtonDestination, modifyButtonDestination, addButtonDestination, deleteButtonImageDestination, addButtonImageDestination;

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
    public TableColumn<TouristPackage, TouristPackage> namePackageCol, priceCol, quotaCol, startDateCol, durationCol;
    @FXML
    public ImageView imgViewBackPackagesButton;
    @FXML
    public Button limpiarCamposPaquetes, addButtonPackages, modifyButtonPackages, deleteButtonPackages, addButtonDestinationName, deleteButtonDestinationName;
    @FXML
    public TextField txtFldPackageName,txtFldPrice, txtFldQuota;
    @FXML
    public DatePicker datePckrStartDate, datePckrEndDate;

    //Ventana gestionar guias
    @FXML
    public TableView<String> guidesLenguajeTable;
    @FXML
    ObservableList<String> observableListLenguajes;
    @FXML
    public TableColumn<String, String> lenguajeCol;
    @FXML
    public Pane manageGuidesPane;
    @FXML
    public TableView<TouristGuide> guidesTable;
    @FXML
    ObservableList<TouristGuide> touristGuideObservableList;
    @FXML
    public TableColumn<TouristGuide, TouristGuide> idGuideCol, fullNameGuideCol, experienceCol, ratingCol;
    @FXML
    public ImageView imgViewBackGuidesButton;
    @FXML
    public Button limpiarCamposGuides, addButtonGuides, modifyButtonGuides, deleteButtonGuide, addLenguajeButton, deleteLenguajeButton;
    @FXML
    public TextField txtFldGuideId, txtFldFullNameGuide, txtFldExperience, txtFldRating, txtFldLenguaje;

    //Ventana estadísticas

    @FXML
    public Pane statisticsPane;
    @FXML
    public ImageView imgViewBackStatisticsButton;
    @FXML
    private BarChart<String, Number> destinationsChart;
    @FXML
    private CategoryAxis destinationsXAxis;
    @FXML
    private NumberAxis destinationsYAxis;
    @FXML
    private BarChart<String, Number> guidesChart;
    @FXML
    private CategoryAxis guidesXAxis;
    @FXML
    private NumberAxis guidesYAxis;
    private List<TouristGuide> guides = TravelAgency.getInstance().getTouristGuides();
    @FXML
    private BarChart<String, Number> packagesChart;
    @FXML
    private CategoryAxis packagesXAxis;
    @FXML
    private NumberAxis packagesYAxis;
    private List<Reservation> reservations = travelAgency.getReservations();

    public void initialize(){

        //Imagenes

        File file = new File("src/main/resources/icons/4103083.png");
        Image backButton = new Image(String.valueOf(file.toURI()));

        imgViewBackDestinationsButton.setImage(backButton);
        imgViewBackPackagesButton.setImage(backButton);
        imgViewBackGuidesButton.setImage(backButton);
        imgViewBackStatisticsButton.setImage(backButton);

        File file1 = new File("src/main/resources/icons/LogOut.png");
        Image exitButton = new Image(String.valueOf(file1.toURI()));

        imgViewExitButton.setImage(exitButton);

        //txtFields de numeros

        txtFldQuota.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("[0-9]")) {
                event.consume(); // Bloquea la entrada no numérica
            }
        });

        txtFldPrice.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("[0-9]")) {
                event.consume(); // Bloquea la entrada no numérica
            }
        });

        txtFldRating.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("[1-5]")) {
                event.consume(); // Bloquea la entrada no numérica o fuera del rango
            }
        });

        // Limita el campo de texto a un solo dígito
        txtFldRating.lengthProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue.intValue() > oldValue.intValue()) {
                if (!txtFldRating.getText().isEmpty()) {
                    txtFldRating.setText(txtFldRating.getText().substring(0, 1));
                }
            }
        });

        //------------------------DESTINOS----------------------------

        //Tabla de rutas de imagenes

        imagesRoutesTable.setDisable(true);
        examinarRutaButton.setDisable(true);
        addButtonImageDestination.setDisable(true);
        deleteButtonImageDestination.setDisable(true);
        txtFldRuta.setDisable(true);
        modifyButtonDestination.setDisable(true);

        observableListRutas = imagesRoutesTable.getItems();

        destinationsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            boolean seleccionado = newValue != null;

            imagesRoutesTable.setDisable(!seleccionado);
            examinarRutaButton.setDisable(!seleccionado);
            addButtonImageDestination.setDisable(!seleccionado);
            deleteButtonImageDestination.setDisable(!seleccionado);
            txtFldRuta.setDisable(!seleccionado);
            modifyButtonDestination.setDisable(!seleccionado);

            if (seleccionado) {
                if (Objects.requireNonNull(newValue).getImagesHTTPS() != null) {
                    observableListRutas.setAll(newValue.getImagesHTTPS());
                }
            }

            this.rutasCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()));

        });

        //Tabla de destinos

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

        //Destinos en paquetes

        destinationsNameTable.setDisable(true);
        addButtonDestinationName.setDisable(true);
        deleteButtonDestinationName.setDisable(true);
        choiceBoxDestinationName.setDisable(true);
        modifyButtonPackages.setDisable(true);

        observableListDestinationName = destinationsNameTable.getItems();

        packagesTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            boolean seleccionado = newValue != null;

            destinationsNameTable.setDisable(!seleccionado);
            addButtonDestinationName.setDisable(!seleccionado);
            deleteButtonDestinationName.setDisable(!seleccionado);
            choiceBoxDestinationName.setDisable(!seleccionado);
            modifyButtonPackages.setDisable(!seleccionado);

            if (seleccionado) {
                if (Objects.requireNonNull(newValue).getDestinosName() != null) {
                    observableListDestinationName.setAll(newValue.getDestinosName());
                }
            }

            this.destinosNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()));

        });

        //Paquetes

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
                txtFldPackageName.setText(newSelection.getName());
                txtFldPrice.setText(String.valueOf(newSelection.getPrice()));
                txtFldQuota.setText(String.valueOf(newSelection.getQuota()));
                datePckrStartDate.setValue(newSelection.getStartDate());
                datePckrEndDate.setValue(newSelection.getEndDate());
            }
        });

        //------------------------GUIAS----------------------------

        //Lenguajes del guia

        guidesLenguajeTable.setDisable(true);
        addLenguajeButton.setDisable(true);
        deleteLenguajeButton.setDisable(true);
        txtFldLenguaje.setDisable(true);

        observableListLenguajes = guidesLenguajeTable.getItems();

        guidesTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            boolean seleccionado = newValue != null;

            guidesLenguajeTable.setDisable(!seleccionado);
            addLenguajeButton.setDisable(!seleccionado);
            deleteLenguajeButton.setDisable(!seleccionado);
            txtFldLenguaje.setDisable(!seleccionado);

            if (seleccionado) {
                if (Objects.requireNonNull(newValue).getLanguages() != null) {
                    observableListLenguajes.setAll(newValue.getLanguages());
                }
            }

            this.lenguajeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()));

        });

        //Guias

        touristGuideObservableList = guidesTable.getItems();

        if (travelAgency.getTouristGuides() != null) {
            touristGuideObservableList.addAll(travelAgency.getTouristGuides());
        }

        this.idGuideCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.fullNameGuideCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        this.experienceCol.setCellValueFactory(new PropertyValueFactory<>("experience"));
        this.ratingCol.setCellValueFactory(new PropertyValueFactory<>("rating"));

        guidesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtFldGuideId.setText(newSelection.getId());
                txtFldFullNameGuide.setText(String.valueOf(newSelection.getFullName()));
                txtFldExperience.setText(newSelection.getExperience());
                txtFldRating.setText(String.valueOf(newSelection.getRating()));
            }
        });

        //------------------------ESTADISTICAS-------------------------

        // Obtener las reservas desde travelAgency

        Map<String, Integer> cuentaReservasPorDestino = getIntegerMap();

        // Ordenar el mapa por la cantidad de reservas en orden descendente
        List<Map.Entry<String, Integer>> listaOrdenada = cuentaReservasPorDestino.entrySet()
                .stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .toList();

        // Crear listas observables a partir de los datos ordenados
        ObservableList<String> destinations = FXCollections.observableArrayList();
        ObservableList<Integer> reservationCounts = FXCollections.observableArrayList();

        for (Map.Entry<String, Integer> entry : listaOrdenada) {
            destinations.add(entry.getKey());
            reservationCounts.add(entry.getValue());
        }

        // Configura el gráfico de destinos más reservados
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (int i = 0; i < destinations.size(); i++) {
            series.getData().add(new XYChart.Data<>(destinations.get(i), reservationCounts.get(i)));
        }
        destinationsChart.getData().add(series);
        destinationsChart.setTitle("Destinos Más Reservados");
        destinationsXAxis.setLabel("Destinos");
        destinationsYAxis.setLabel("Reservas");



        // Ordenar los guías por rating en orden descendente

        guides.sort((g1, g2) -> Double.compare(g2.getRating(), g1.getRating()));

        // Tomar los 5 guías mejor puntuados o el número que desees
        List<TouristGuide> topGuides = guides.subList(0, Math.min(5, guides.size()));

        // Configurar el gráfico de guías mejor puntuados
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        for (TouristGuide guide : topGuides) {
            series1.getData().add(new XYChart.Data<>(guide.getFullName(), guide.getRating()));
        }

        guidesChart.getData().add(series1);
        guidesChart.setTitle("Guías Mejor Puntuados");
        guidesXAxis.setLabel("Guías");
        guidesYAxis.setLabel("Puntuación");



        //Crear un mapa para contabilizar las reservas por nombre de paquete

        Map<String, Integer> packageReservationCounts = getStringIntegerMap();

        // Convertir el mapa en una lista de pares (nombre del paquete, número de reservas)
        ObservableList<XYChart.Data<String, Number>> packageData = FXCollections.observableArrayList();

        for (String packageName : packageReservationCounts.keySet()) {
            int reservationCount = packageReservationCounts.get(packageName);
            packageData.add(new XYChart.Data<>(packageName, reservationCount));
        }

        // Configurar el gráfico de paquetes más reservados
        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setData(packageData);
        packagesChart.getData().add(series2);
        packagesChart.setTitle("Paquetes Más Reservados");
        packagesXAxis.setLabel("Paquetes");
        packagesYAxis.setLabel("Número de Reservas");
    }

    private Map<String, Integer> getIntegerMap() {
        List<Reservation> reservations = travelAgency.getReservations();

        // Crear un mapa para contar las repeticiones de los nombres de destinos
        Map<String, Integer> cuentaReservasPorDestino = new HashMap<>();

        for (Reservation reservation : reservations) {
            TouristPackage touristPackage = reservation.getTouristPackage();

            if (touristPackage != null) {
                List<String> destinos = touristPackage.getDestinosName();

                for (String destino : destinos) {
                    cuentaReservasPorDestino.put(destino, cuentaReservasPorDestino.getOrDefault(destino, 0) + 1);
                }
            }
        }

        return cuentaReservasPorDestino;
    }

    private Map<String, Integer> getStringIntegerMap() {
        Map<String, Integer> packageReservationCounts = new HashMap<>();

        for (Reservation reservation : reservations) {
            TouristPackage touristPackage = reservation.getTouristPackage();

            if (touristPackage != null) {
                String packageName = touristPackage.getName();
                packageReservationCounts.put(packageName, packageReservationCounts.getOrDefault(packageName, 0) + 1);
            }
        }

        return packageReservationCounts;
    }

    //----------------------------Destinations-----------------------------

    public void onLimpiarCamposDestinoClick() {
        limpiarCamposDestinations();
        imagesRoutesTable.setDisable(true);
        imagesRoutesTable.getItems().clear();
        examinarRutaButton.setDisable(true);
        addButtonImageDestination.setDisable(true);
        deleteButtonImageDestination.setDisable(true);
        txtFldRuta.setDisable(true);
    }

    @FXML
    private void agregarElementoDestinations() throws RepeatedInformationException, AtributoVacioException {

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

        imagesRoutesTable.refresh();
    }

    @FXML
    private void modificarElementoDestinations() throws AtributoVacioException {
        if (destinationsTable.getSelectionModel().getSelectedIndex() >= 0) {

            Destino selectedDestino = destinationsTable.getSelectionModel().getSelectedItem();

            travelAgency.modificarDestino(selectedDestino, txtFldName.getText(), txtFldCity.getText(), txtFldDescription.getText(), choiceBoxClima.getValue());

            limpiarCamposDestinations();

            destinationsTable.refresh();

            imagesRoutesTable.setDisable(true);
            examinarRutaButton.setDisable(true);
            addButtonImageDestination.setDisable(true);
            deleteButtonImageDestination.setDisable(true);
            txtFldRuta.setDisable(true);
            modifyButtonDestination.setDisable(true);

            imagesRoutesTable.refresh();

            System.out.println(selectedDestino);
        }

    }

    @FXML
    private void eliminarElementoDestinations() {
        if (destinationsTable.getSelectionModel().getSelectedIndex() >= 0) {

            Destino selectedDestino = destinationsTable.getSelectionModel().getSelectedItem();

            travelAgency.eliminarDestino(destinoObservableList, selectedDestino);

            limpiarCamposDestinations();

            imagesRoutesTable.setDisable(true);
            examinarRutaButton.setDisable(true);
            addButtonImageDestination.setDisable(true);
            deleteButtonImageDestination.setDisable(true);
            txtFldRuta.setDisable(true);
            modifyButtonDestination.setDisable(true);

            imagesRoutesTable.refresh();
        }
    }

    @FXML
    public void seleccionarImagen() {
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

    @FXML
    private Stage getStage() {
        return (Stage) txtFldRuta.getScene().getWindow();
    }

    @FXML
    public void agregarRutaImagenDestinations() throws RepeatedInformationException, AtributoVacioException, RutaInvalidaException {

        String destinoName = txtFldName.getText();

        Optional<Destino> destinoSeleccionadoOpcional = travelAgency.getDestinos().stream()
                .filter(destino -> destino.getName().equals(destinoName))
                .findFirst();

        if (destinoSeleccionadoOpcional.isPresent()) {

            travelAgency.agregarImagenDestino(observableListRutas, txtFldRuta.getText(), destinoSeleccionadoOpcional.get());

        } else {

            Destino nuevoDestino = Destino.builder()
                    .name(txtFldName.getText())
                    .city(txtFldCity.getText())
                    .imagesHTTPS(new ArrayList<>())
                    .description(txtFldDescription.getText())
                    .weather(choiceBoxClima.getValue())
                    .build();

            travelAgency.agregarImagenDestino(observableListRutas, txtFldRuta.getText(), nuevoDestino);

            travelAgency.agregarDestino(destinoObservableList, nuevoDestino);
        }

        txtFldRuta.clear();
    }

    @FXML
    public void eliminarRutaImagenDestinations() {

        if (imagesRoutesTable.getSelectionModel().getSelectedIndex() >= 0) {

            String selectedRuta = imagesRoutesTable.getSelectionModel().getSelectedItem();
            observableListRutas.remove(selectedRuta);

            String destinoName = txtFldName.getText();

            Optional<Destino> destinoSeleccionadoOpcional = travelAgency.getDestinos().stream()
                    .filter(destino -> destino.getName().equals(destinoName))
                    .findFirst();

            travelAgency.eliminarRuta(destinoSeleccionadoOpcional, selectedRuta);

        }

        txtFldRuta.clear();
    }

    @FXML
    private void limpiarCamposDestinations() {
        txtFldName.clear();
        txtFldCity.clear();
        choiceBoxClima.setValue(null);
        txtFldDescription.clear();
    }

    //----------------------------Packages-----------------------------

    public void onLimpiarCamposPaquetesClick() {
        limpiarCamposPackages();
        destinationsNameTable.setDisable(true);
        destinationsNameTable.getItems().clear();
        addButtonDestinationName.setDisable(true);
        deleteButtonDestinationName.setDisable(true);
        choiceBoxDestinationName.setDisable(true);
    }

    @FXML
    private void agregarElementoPackages() throws RepeatedInformationException, AtributoVacioException, ErrorEnIngresoFechasException {

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
                .build();

        travelAgency.agregarPaquete(packageObservableList, nuevoPaquete);

        limpiarCamposPackages();

        destinationsNameTable.setDisable(true);
        addButtonDestinationName.setDisable(true);
        deleteButtonDestinationName.setDisable(true);
        choiceBoxDestinationName.setDisable(true);
        modifyButtonPackages.setDisable(true);

        destinationsNameTable.refresh();
    }

    @FXML
    private void modificarElementoPackages() throws AtributoVacioException {

        if (packagesTable.getSelectionModel().getSelectedIndex() >= 0) {

            TouristPackage selectedPackage = packagesTable.getSelectionModel().getSelectedItem();

            travelAgency.modificarPaquete(selectedPackage, txtFldPackageName.getText(), Double.parseDouble(txtFldPrice.getText()), Integer.parseInt(txtFldQuota.getText()), datePckrStartDate.getValue(), datePckrEndDate.getValue());

            limpiarCamposPackages();

            packagesTable.refresh();

            destinationsNameTable.setDisable(true);
            addButtonDestinationName.setDisable(true);
            deleteButtonDestinationName.setDisable(true);
            choiceBoxDestinationName.setDisable(true);
            modifyButtonPackages.setDisable(true);

            destinationsNameTable.refresh();

            System.out.println(selectedPackage);
        }

    }

    @FXML
    private void eliminarElementoPackages() {
        if (packagesTable.getSelectionModel().getSelectedIndex() >= 0) {
            TouristPackage selectedPackage = packagesTable.getSelectionModel().getSelectedItem();

            travelAgency.eliminarPaquete(packageObservableList, selectedPackage);

            limpiarCamposPackages();
            destinationsNameTable.setDisable(true);
            addButtonDestinationName.setDisable(true);
            deleteButtonDestinationName.setDisable(true);
            choiceBoxDestinationName.setDisable(true);
            modifyButtonPackages.setDisable(true);

            destinationsNameTable.refresh();
        }
    }

    @FXML
    public void agregarDestinoEnPaquete() throws RepeatedInformationException, AtributoVacioException, ErrorEnIngresoFechasException {

        String paqueteName = txtFldPackageName.getText();

        Optional<TouristPackage> paqueteSeleccionadoOpcional = travelAgency.getTouristPackages().stream()
                .filter(touristPackage -> touristPackage.getName().equals(paqueteName))
                .findFirst();

        if (paqueteSeleccionadoOpcional.isPresent()){

            travelAgency.agregarDestinoEnPaquete(observableListDestinationName, choiceBoxDestinationName.getSelectionModel().getSelectedItem(), paqueteSeleccionadoOpcional.get());

        } else {

            long duration = 0;

            if (datePckrStartDate.getValue() != null && datePckrEndDate.getValue() != null){
                duration = datePckrStartDate.getValue().until(datePckrEndDate.getValue(), ChronoUnit.DAYS);
            }

            TouristPackage nuevoPaquete = TouristPackage.builder()
                    .name(txtFldPackageName.getText())
                    .price(Double.valueOf(txtFldPrice.getText()))
                    .quota(Integer.parseInt(txtFldQuota.getText()))
                    .startDate(datePckrStartDate.getValue())
                    .endDate(datePckrEndDate.getValue())
                    .duration(duration)
                    .build();

            travelAgency.agregarDestinoEnPaquete(observableListDestinationName, choiceBoxDestinationName.getSelectionModel().getSelectedItem(), nuevoPaquete);

            travelAgency.agregarPaquete(packageObservableList, nuevoPaquete);
        }

    }

    @FXML
    public void eliminarDestinoEnPaquete() {

        if (destinationsNameTable.getSelectionModel().getSelectedIndex() >= 0) {

            String selectedDestino = destinationsNameTable.getSelectionModel().getSelectedItem();
            observableListDestinationName.remove(selectedDestino);

            String packageName = txtFldPackageName.getText();

            Optional<TouristPackage> packageSeleccionadoOpcional = travelAgency.getTouristPackages().stream()
                    .filter(touristPackage -> touristPackage.getName().equals(packageName))
                    .findFirst();

            travelAgency.eliminarDestinoName(packageSeleccionadoOpcional, selectedDestino);

        }

        choiceBoxDestinationName.getSelectionModel().clearSelection();

    }

    @FXML
    private void limpiarCamposPackages() {
        txtFldPackageName.clear();
        txtFldPrice.clear();
        txtFldQuota.clear();
        datePckrEndDate.setValue(null);
        datePckrStartDate.setValue(null);
    }

    //----------------------------Guides-----------------------------

    public void onLimpiarCamposGuiasClick() {
        limpiarCamposGuias();
        guidesLenguajeTable.setDisable(true);
        guidesLenguajeTable.getItems().clear();
        addLenguajeButton.setDisable(true);
        deleteLenguajeButton.setDisable(true);
        txtFldLenguaje.setDisable(true);
    }

    @FXML
    public void agregarGuiaButton() throws RepeatedInformationException, AtributoVacioException {

        TouristGuide nuevoGuia = TouristGuide.builder()
                .id(txtFldGuideId.getText())
                .fullName(txtFldFullNameGuide.getText())
                .languages(new ArrayList<>())
                .experience(txtFldExperience.getText())
                .rating(Double.valueOf(txtFldRating.getText()))
                .build();

        travelAgency.agregarGuia(touristGuideObservableList, nuevoGuia);

        limpiarCamposGuias();

        guidesLenguajeTable.setDisable(true);
        addLenguajeButton.setDisable(true);
        deleteLenguajeButton.setDisable(true);
        txtFldLenguaje.setDisable(true);

        guidesLenguajeTable.refresh();

    }

    @FXML
    public void modificarGuiaButton() throws AtributoVacioException {

        if (guidesTable.getSelectionModel().getSelectedIndex() >= 0) {

            TouristGuide selectedGuia = guidesTable.getSelectionModel().getSelectedItem();

            travelAgency.modificarGuia(selectedGuia, txtFldGuideId.getText(), txtFldFullNameGuide.getText(), txtFldExperience.getText(), txtFldRating.getText());

            limpiarCamposGuias();

            guidesTable.refresh();

            guidesLenguajeTable.setDisable(true);
            addLenguajeButton.setDisable(true);
            deleteLenguajeButton.setDisable(true);
            txtFldLenguaje.setDisable(true);

            guidesLenguajeTable.refresh();
        }

    }

    @FXML
    public void eliminarGuiaButton() {
        if (guidesTable.getSelectionModel().getSelectedIndex() >= 0) {

            TouristGuide selectedGuia = guidesTable.getSelectionModel().getSelectedItem();

            travelAgency.eliminarGuia(touristGuideObservableList, selectedGuia);

            limpiarCamposGuias();

            guidesLenguajeTable.setDisable(true);
            addLenguajeButton.setDisable(true);
            deleteLenguajeButton.setDisable(true);
            txtFldLenguaje.setDisable(true);

            guidesLenguajeTable.refresh();
        }
    }

    @FXML
    public void agregarLenguajeGuia() throws RepeatedInformationException, AtributoVacioException {

        String guiaID = txtFldGuideId.getText();

        Optional<TouristGuide> guiaSeleccionadoOpcional = travelAgency.getTouristGuides().stream()
                .filter(touristGuide -> touristGuide.getId().equals(guiaID))
                .findFirst();

        if (guiaSeleccionadoOpcional.isPresent()){
            travelAgency.agregarLeaguajeGuia(observableListLenguajes, txtFldLenguaje.getText(), guiaSeleccionadoOpcional.get());
        } else {

            TouristGuide nuevoGuia = TouristGuide.builder()
                    .id(txtFldGuideId.getText())
                    .fullName(txtFldFullNameGuide.getText())
                    .experience(txtFldExperience.getText())
                    .rating(Double.valueOf(txtFldRating.getText()))
                    .build();

            travelAgency.agregarLeaguajeGuia(observableListLenguajes, txtFldLenguaje.getText(), nuevoGuia);

            travelAgency.agregarGuia(touristGuideObservableList, nuevoGuia);
        }

        txtFldLenguaje.clear();
    }

    @FXML
    public void elimiarLenguajeGuia() {

        if (guidesLenguajeTable.getSelectionModel().getSelectedIndex() >= 0) {

            String selectedLenguaje = guidesLenguajeTable.getSelectionModel().getSelectedItem();
            observableListLenguajes.remove(selectedLenguaje);

            String guideID = txtFldGuideId.getText();

            Optional<TouristGuide> guideSeleccionadoOpcional = travelAgency.getTouristGuides().stream()
                    .filter(touristGuide -> touristGuide.getId().equals(guideID))
                    .findFirst();

            travelAgency.eliminarLenguaje(guideSeleccionadoOpcional, selectedLenguaje);

        }

        choiceBoxDestinationName.getSelectionModel().clearSelection();

    }

    private void limpiarCamposGuias() {
        txtFldGuideId.clear();
        txtFldFullNameGuide.clear();
        txtFldExperience.clear();
        txtFldRating.clear();
    }


    @FXML
    private void handleButtonAction(ActionEvent event){

        if (event.getTarget() == manageDestinationsButton){visibilities(false,true,false,false,false,false);}
        if (event.getTarget() == managePackagesButton){

            List<String> destinationNames = travelAgency.getDestinos().stream()
                    .map(Destino::getName)
                    .toList();

            if (!destinationNames.equals(choiceBoxDestinationName.getItems())) {
                choiceBoxDestinationName.getItems().setAll(destinationNames);
            }

            visibilities(false,false,false,true,false,false);
        }
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

        if (mouseEvent.getTarget() == imgViewBackDestinationsButton){

            limpiarCamposDestinations();

            imagesRoutesTable.setDisable(true);
            examinarRutaButton.setDisable(true);
            addButtonImageDestination.setDisable(true);
            deleteButtonImageDestination.setDisable(true);
            txtFldRuta.setDisable(true);
            modifyButtonDestination.setDisable(true);

            visibilities(true, false, false, false, false, false);

        }
        if (mouseEvent.getTarget() == imgViewBackPackagesButton){

            limpiarCamposPackages();

            destinationsNameTable.setDisable(true);
            addButtonDestinationName.setDisable(true);
            deleteButtonDestinationName.setDisable(true);
            choiceBoxDestinationName.setDisable(true);
            modifyButtonPackages.setDisable(true);

            visibilities(true, false,false, false, false, false);
        }
        if (mouseEvent.getTarget() == imgViewBackGuidesButton){

            limpiarCamposGuias();

            guidesLenguajeTable.setDisable(true);
            addLenguajeButton.setDisable(true);
            deleteLenguajeButton.setDisable(true);
            txtFldLenguaje.setDisable(true);

            visibilities(true, false,false, false, false, false);
        }
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
