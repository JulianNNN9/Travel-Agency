package co.edu.uniquindio.travelagency.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class Client extends User{

    private String fullName;
    private String mail;
    private String phoneNumber;
    private String residence;

}
