package bus_app.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "paths")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SQLDelete(sql = "update paths set is_deleted = true where number = ?")
@SQLRestriction("is_deleted = false")
public class Path {

    @Id
    private String number;

    @OneToOne
    @JoinColumn(name = "begin_station", referencedColumnName = "id")
    private Station beginStation;

    @OneToOne
    @JoinColumn(name = "end_station", referencedColumnName = "id")
    private Station endStation;

    @OneToMany(mappedBy = "path", cascade = CascadeType.ALL)
    private List<PathStation> stations = new ArrayList<>();

    private Integer duration;

    private Boolean isDeleted;
}