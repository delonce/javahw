package app.models.dto;

import app.models.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentForOtherUserDTO {

    private String fullName;
    private String email;

    public StudentForOtherUserDTO(Student student) {
        this.fullName = student.getFullName();
        this.email = student.getEmail();
    }
}