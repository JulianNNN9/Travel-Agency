package co.edu.uniquindio.travelagency.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data @NoArgsConstructor @AllArgsConstructor @SuperBuilder

public class Client extends User implements Serializable {

    private String fullName;
    private String mail;
    private String phoneNumber;
    private String residence;

}
