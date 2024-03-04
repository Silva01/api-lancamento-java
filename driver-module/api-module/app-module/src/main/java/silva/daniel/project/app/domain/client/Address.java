package silva.daniel.project.app.domain.client;

import jakarta.persistence.*;

@Entity
@Table(name = "address")
public record Address(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id,
        String street,
        String number,
        String complement,
        String neighborhood,
        String state,
        String city,
        String zipCode
) {
}
