package co.edu.uniquindio.travelagency.model;

import co.edu.uniquindio.travelagency.enums.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor @Builder

public class Reservation implements Serializable {

    private TouristPackage touristPackage;
    private LocalDate requestDate;
    private ReservationStatus reservationStatus;
    private LocalDate startDate;
    private LocalDate endDate;
    private TouristGuide touristGuide;
    private Integer numberOfPeople;

}
