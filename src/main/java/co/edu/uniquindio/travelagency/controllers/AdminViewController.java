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
    public Button addButtonGuides, modifyButtonGuides, deleteButtonGuide, addLenguajeButton, deleteLenguajeButton;
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
    private List<Reservation> reservations = TravelAgency.getInstance().getReservations();

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
                } else {
                    observableListRutas.clear();
                }
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
                } else {
                    observableListDestinationName.clear();
                }
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
                } else {
                    observableListLenguajes.clear();
                }
            }

            this.lenguajeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()));

        });

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

        List<Reservation> reservations = travelAgency.getReservations();

        // Crear un mapa para contar las repeticiones de los nombres de destinos
        Map<String, Integer> cuentaReservasPorDestino = new HashMap<>();

        for (Reservation reservation : reservations) {
            List<TouristPackage> touristPackages = reservation.getTouristPackages();
            for (TouristPackage touristPackage : touristPackages) {
                List<String> destinos = touristPackage.getDestinosName();
                for (String destino : destinos) {
                    cuentaReservasPorDestino.put(destino, cuentaReservasPorDestino.getOrDefault(destino, 0) + 1);
                }
            }
        }

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

        guides.sort((g1, g2) -> Integer.compare(g2.getRating(), g1.getRating()));

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

    private Map<String, Integer> getStringIntegerMap() {
        Map<String, Integer> packageReservationCounts = new HashMap<>();

        for (Reservation reservation : reservations) {
            List<TouristPackage> packages = reservation.getTouristPackages();
            for (TouristPackage aPackage : packages) {
                String packageName = aPackage.getName();
                packageReservationCounts.put(packageName, packageReservationCounts.getOrDefault(packageName, 0) + 1);
            }
        }
        return packageReservationCounts;
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

        imagesRoutesTable.refresh();
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

            imagesRoutesTable.refresh();

            System.out.println(selectedDestino);
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

            imagesRoutesTable.refresh();
        }
    }

    @FXML
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

    @FXML
    private Stage getStage() {
        return (Stage) txtFldRuta.getScene().getWindow();
    }

    @FXML
    public void agregarRutaImagenDestinations(ActionEvent actionEvent) throws RepeatedInformationException, AtributoVacioException, RutaInvalidaException {

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

    @FXML
    public void eliminarRuta(Optional<Destino> destino, String rutaABorrar){

        List<String> rutasSinEliminar = new ArrayList<>();

        if (destino.isPresent()){
            rutasSinEliminar = destino.get().getImagesHTTPS().stream().filter(s -> !s.equals(rutaABorrar)).toList();
        }

        destino.get().getImagesHTTPS().clear();
        destino.get().getImagesHTTPS().addAll(rutasSinEliminar);
    }

    @FXML
    private void limpiarCamposDestinations() {
        txtFldName.clear();
        txtFldCity.clear();
        choiceBoxClima.setValue(null);
        txtFldDescription.clear();
    }

    //----------------------------Packages-----------------------------

    @FXML
    private void agregarElementoPackages(ActionEvent event) throws RepeatedInformationException, AtributoVacioException, ErrorEnIngresoFechasException {

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
    private void modificarElementoPackages(ActionEvent event) {

        if (packagesTable.getSelectionModel().getSelectedIndex() >= 0) {
            TouristPackage selectedPackage = packagesTable.getSelectionModel().getSelectedItem();

            selectedPackage.setName(txtFldPackageName.getText());
            selectedPackage.setPrice(Double.parseDouble(txtFldPrice.getText()));
            selectedPackage.setQuota(Integer.parseInt(txtFldQuota.getText()));
            selectedPackage.setStartDate(datePckrStartDate.getValue());
            selectedPackage.setEndDate(datePckrEndDate.getValue());

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

            destinationsNameTable.refresh();
        }
    }

    @FXML
    public void agregarDestinoEnPaquete(ActionEvent actionEvent) throws RepeatedInformationException, AtributoVacioException, ErrorEnIngresoFechasException {

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

        choiceBoxDestinationName.setValue("");

    }

    @FXML
    public void eliminarDestinoName(Optional<TouristPackage> touristPackage, String destinoABorrar){

        List<String> destinosSinEliminar = touristPackage.get().getDestinosName().stream().filter(s -> !s.equals(destinoABorrar)).toList();

        touristPackage.get().getDestinosName().clear();
        touristPackage.get().getDestinosName().addAll(destinosSinEliminar );

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

    @FXML
    public void agregarGuiaButton(ActionEvent actionEvent) throws RepeatedInformationException, AtributoVacioException {

        TouristGuide nuevoGuia = TouristGuide.builder()
                .id(txtFldGuideId.getText())
                .fullName(txtFldFullNameGuide.getText())
                .languages(new ArrayList<>())
                .experience(txtFldExperience.getText())
                .rating(Integer.valueOf(txtFldRating.getText()))
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
    public void modificarGuiaButton(ActionEvent actionEvent) {

        if (guidesTable.getSelectionModel().getSelectedIndex() >= 0) {

            TouristGuide selectedGuia = guidesTable.getSelectionModel().getSelectedItem();

            selectedGuia.setId(txtFldGuideId.getText());
            selectedGuia.setFullName(txtFldFullNameGuide.getText());
            selectedGuia.setExperience(txtFldExperience.getText());
            selectedGuia.setRating(Integer.valueOf(txtFldRating.getText()));

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
    public void eliminarGuiaButton(ActionEvent actionEvent) {
        if (guidesTable.getSelectionModel().getSelectedIndex() >= 0) {

            TouristGuide selectedGuia = guidesTable.getSelectionModel().getSelectedItem();
            touristGuideObservableList.remove(selectedGuia);

            limpiarCamposGuias();

            guidesLenguajeTable.setDisable(true);
            addLenguajeButton.setDisable(true);
            deleteLenguajeButton.setDisable(true);
            txtFldLenguaje.setDisable(true);

            guidesLenguajeTable.refresh();
        }
    }

    @FXML
    public void agregarLenguajeGuia(ActionEvent actionEvent) throws RepeatedInformationException, AtributoVacioException {

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
                    .rating(Integer.valueOf(txtFldRating.getText()))
                    .build();

            travelAgency.agregarLeaguajeGuia(observableListLenguajes, txtFldLenguaje.getText(), nuevoGuia);

            travelAgency.agregarGuia(touristGuideObservableList, nuevoGuia);
        }

        txtFldLenguaje.clear();
    }

    @FXML
    public void elimiarLenguajeGuia(ActionEvent actionEvent) {

        if (guidesLenguajeTable.getSelectionModel().getSelectedIndex() >= 0) {

            String selectedLenguaje = guidesLenguajeTable.getSelectionModel().getSelectedItem();
            observableListLenguajes.remove(selectedLenguaje);

            String guideID = txtFldGuideId.getText();

            Optional<TouristGuide> guideSeleccionadoOpcional = travelAgency.getTouristGuides().stream()
                    .filter(touristGuide -> touristGuide.getId().equals(guideID))
                    .findFirst();

            eliminarLenguaje(guideSeleccionadoOpcional, selectedLenguaje);

        }

        choiceBoxDestinationName.setValue("");



    }

    @FXML
    public void eliminarLenguaje(Optional<TouristGuide> touristGuide, String lenguajeABorrar){

        List<String> languajeSinEliminar = touristGuide.get().getLanguages().stream().filter(s -> !s.equals(lenguajeABorrar)).toList();

        touristGuide.get().getLanguages().clear();
        touristGuide.get().getLanguages().addAll(languajeSinEliminar);

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
