package silva.daniel.project.app.domain.client;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "address")
public class Address {
    private @NotNull @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    @NotNull
    @NotEmpty
    private String street;

    @NotNull
    @NotEmpty
    private String number;

    private String complement;

    @NotNull
    @NotEmpty
    private String neighborhood;

    @NotNull
    @NotEmpty
    private String state;

    @NotNull
    @NotEmpty
    private String city;

    @NotNull
    @NotEmpty
    private String zipCode;
}
