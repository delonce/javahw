package app.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "comments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "student_id")
    private Integer studentId;

    @Column(name = "grade_id")
    private Integer gradeId;

    @Column(name = "text")
    private String text;
}