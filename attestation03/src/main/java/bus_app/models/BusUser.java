package bus_app.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "bus_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusUser {

    @Id
    private String username;

    private String password;

    private List<String> roles;
}