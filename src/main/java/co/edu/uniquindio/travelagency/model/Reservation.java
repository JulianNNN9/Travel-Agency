package co.edu.uniquindio.travelagency.model;

import co.edu.uniquindio.travelagency.enums.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor @Builder

public class Reservation {

    private List<TouristPackage> touristPackages;
    private LocalDate requestDate;
    private ReservationStatus reservationStatus;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean touristGuide;
    private Integer numberOfPeople;

}
