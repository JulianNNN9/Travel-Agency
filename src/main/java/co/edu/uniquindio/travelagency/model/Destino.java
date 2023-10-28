package co.edu.uniquindio.travelagency.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor @Builder

public class Destino {

    private String name;
    private String city;
    private String description;
    //private List<String> imagesHTTPS;
    private String weather;

}
