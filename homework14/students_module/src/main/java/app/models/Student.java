package app.models;

import app.exceptions.IncorrectBodyException;
import app.models.dto.StudentRegistrationDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "students")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "age")
    private Integer age;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "roles")
    private String[] roles;

    @Column(name = "grades_list")
    private Integer[] gradesList;

    public Student(StudentRegistrationDTO registrationDTO) {
        this.id = null;
        this.fullName = registrationDTO.getFullName();
        this.age = registrationDTO.getAge();
        this.email = registrationDTO.getEmail();
        this.password = registrationDTO.getPassword();
        this.roles = new String[] {"USER"};
        this.gradesList = new Integer[] {};
    }

    public static void isStudentCorrect(Student student) throws IncorrectBodyException {
        if (student.getFullName() == null || student.email == null || student.gradesList == null
            || student.getAge() == null) {
            throw new IncorrectBodyException("Incorrect received body");
        }

        checkValidation(student);
    }

    public static void checkValidation(Student student) throws IncorrectBodyException {
        if (student.getFullName() != null && !student.getFullName().matches("[a-zA-Zа-яА-Я]* [a-zA-Zа-яА-Я]* [a-zA-Zа-яА-Я]*")) {
            throw new IncorrectBodyException("Incorrect received body");
        }

        if (student.getEmail() != null && !student.getEmail().matches("^\\S+@\\S+\\.\\S+$")) {
            throw new IncorrectBodyException("Incorrect received body");
        }

        // предположим, курсы доступны только совершеннолетним
        if (student.getAge() != null && student.getAge() < 18) {
            throw new IncorrectBodyException("Incorrect received body");
        }
    }
}
