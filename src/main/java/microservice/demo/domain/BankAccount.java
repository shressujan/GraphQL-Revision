package microservice.demo.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BankAccount {

    @Id
    UUID id;
    String name;
    Currency currency;
}
