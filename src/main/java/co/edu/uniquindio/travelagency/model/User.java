package co.edu.uniquindio.travelagency.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data @AllArgsConstructor @NoArgsConstructor @SuperBuilder
public class User {

    private String userId;
    private String password;

}
