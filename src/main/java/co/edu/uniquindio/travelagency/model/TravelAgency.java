package co.edu.uniquindio.travelagency.model;

import lombok.Getter;
import lombok.extern.java.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

@Log @Getter

public class TravelAgency {

    List<TouristGuide> touristGuides;
    List<Reservation> reservations;
    List<TouristPackage> touristPackages;

    @Getter
    private final ResourceBundle resourceBundle;

    private static TravelAgency travelAgency;

    private TravelAgency() {

        this.resourceBundle = ResourceBundle.getBundle("textos");

        try {

            FileHandler fh = new FileHandler("logs.log", true);
            fh.setFormatter( new SimpleFormatter());
            log.addHandler(fh);

        }catch (IOException e){
            log.severe(e.getMessage());
        }

        this.touristGuides = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.touristPackages = new ArrayList<>();

    }

    public static TravelAgency getInstance(){

        if (travelAgency == null){
            travelAgency = new TravelAgency();
        }

        log.info("Se ha creado una instancia de Travel Agency");

        return travelAgency;

    }

}
