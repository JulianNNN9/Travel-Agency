package co.edu.uniquindio.travelagency.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data @SuperBuilder

public class Admin extends User implements Serializable {
}
