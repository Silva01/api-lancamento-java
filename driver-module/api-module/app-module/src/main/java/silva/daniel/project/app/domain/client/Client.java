package silva.daniel.project.app.domain.client;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "client")
public class Client {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String aggregateId;
    private String cpf;
    private String name;
    private String telephone;
    private boolean active;
    @OneToOne(cascade = CascadeType.ALL) @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;
}
