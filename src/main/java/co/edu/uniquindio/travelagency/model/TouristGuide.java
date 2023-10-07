package co.edu.uniquindio.travelagency.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor @Builder

public class TouristGuide {

    private String id;
    private String fullName;
    private List<String> languages;
    private String experience;
    private Integer rating;

}
