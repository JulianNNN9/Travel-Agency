package co.edu.uniquindio.travelagency.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor @Builder

public class TouristPackage {

    private List<String> destinosName;
    private String name;
    private Double price;
    private Integer quota;
    private LocalDate startDate;
    private LocalDate endDate;
    private long duration;
    private String clientID;

}
