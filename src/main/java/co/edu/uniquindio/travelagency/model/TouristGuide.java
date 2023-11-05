package co.edu.uniquindio.travelagency.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor @Builder

public class TouristGuide implements Serializable {

    private String id;
    private String fullName;
    private List<String> languages = new ArrayList<>();
    private String experience;
    private Integer rating;

}
