package co.edu.uniquindio.travelagency.model;

import co.edu.uniquindio.travelagency.enums.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data @AllArgsConstructor @NoArgsConstructor @Builder

public class Reservation {

    private LocalDate requestDate;
    private ReservationStatus reservationStatus;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean touristGuide;

}
