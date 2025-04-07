package app.models.dto;

import app.models.Grade;
import app.models.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentForUserDTO {

    private Integer id;
    private String fullName;
    private Integer age;
    private String email;
    private List<Grade> grades;

    public StudentForUserDTO(Student student, List<Grade> grades) {
        this.id = student.getId();
        this.fullName = student.getFullName();
        this.age = student.getAge();
        this.email = student.getEmail();
        this.grades = grades;
    }
}