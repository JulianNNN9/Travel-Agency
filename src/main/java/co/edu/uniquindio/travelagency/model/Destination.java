package co.edu.uniquindio.travelagency.model;

import co.edu.uniquindio.travelagency.enums.Weather;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor @Builder

public class Destination {

    private String name;
    private String city;
    private String description;
    //private List<String> imagesHTTPS;
    private Weather weather;

}
