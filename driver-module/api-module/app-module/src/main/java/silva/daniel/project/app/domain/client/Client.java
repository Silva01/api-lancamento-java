package silva.daniel.project.app.domain.client;

import jakarta.persistence.*;

@Entity
@Table(name = "client")
public class Client {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String aggregateId;
    private String cpf;
    private String name;
    private String telephone;
    private boolean active;
    private @OneToOne(cascade = CascadeType.ALL) @JoinColumn(name = "address_id", referencedColumnName = "id") Address address;

    public Client(Long id, String aggregateId, String cpf, String name, String telephone, boolean active, Address address) {
        this.id = id;
        this.aggregateId = aggregateId;
        this.cpf = cpf;
        this.name = name;
        this.telephone = telephone;
        this.active = active;
        this.address = address;
    }

    public Client() {

    }

    public Long id() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String aggregateId() {
        return aggregateId;
    }

    public void setAggregateId(String aggregateId) {
        this.aggregateId = aggregateId;
    }

    public String cpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String name() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String telephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public boolean active() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Address address() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
