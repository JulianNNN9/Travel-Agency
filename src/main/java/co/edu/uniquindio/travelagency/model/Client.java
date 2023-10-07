package co.edu.uniquindio.travelagency.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data @NoArgsConstructor @AllArgsConstructor @SuperBuilder

public class Client extends User{

    private String fullName;
    private String mail;
    private String phoneNumber;
    private String residence;

}
