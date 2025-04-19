package bus_app.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@Entity
@Table(name = "buses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SQLDelete(sql = "update buses set is_deleted = true where number = ?")
@SQLRestriction("is_deleted = false")
public class Bus {

    @Id
    private String number;

    @OneToOne
    @JoinColumn(name = "path_number", referencedColumnName = "number")
    private Path path;

    @OneToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;

    @ManyToMany
    @JoinTable(
            name = "buses_drivers",
            joinColumns = @JoinColumn(name = "bus_number"),
            inverseJoinColumns = @JoinColumn(name = "driver_id")
    )
    private List<Driver> drivers;

    private Integer seatsNumber;

    private String type;

    private Boolean isActive;

    private Boolean isDeleted;
}