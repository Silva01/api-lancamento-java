package silva.daniel.project.app.domain.client;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "address")
public class Address {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String street;
    private String number;
    private String complement;
    private String neighborhood;
    private String state;
    private String city;
    private String zipCode;
}
