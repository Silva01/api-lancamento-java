package silva.daniel.project.app.domain.account.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class AccountKey implements Serializable {
    private Integer number;
    private Integer bankAgencyNumber;
}
