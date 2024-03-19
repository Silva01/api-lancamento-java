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
@Table(name = "client", uniqueConstraints = @UniqueConstraint(columnNames = "cpf"))
public class Client {
    @Id
    @NotEmpty
    @NotNull
    @Column(nullable = false)
    private String cpf;

    @NotEmpty
    @NotNull
    @Column(nullable = false)
    private String aggregateId;

    @NotEmpty
    @NotNull
    @Column(nullable = false)
    private String name;

    private String telephone;

    private boolean active;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;
}
