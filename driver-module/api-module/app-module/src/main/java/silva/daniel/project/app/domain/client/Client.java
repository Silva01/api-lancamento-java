package silva.daniel.project.app.domain.client;

import jakarta.persistence.*;

@Entity
@Table(name = "client")
public record Client(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id,
        String aggregateId,
        String cpf,
        String name,
        String telephone,
        boolean active,
        @OneToOne @JoinColumn(name = "address_id", referencedColumnName = "id") Address address
) {

}
