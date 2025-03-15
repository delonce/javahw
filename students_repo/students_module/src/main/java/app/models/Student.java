package app.models;

import app.exceptions.IncorrectBodyException;
import jakarta.persistence.*;
import lombok.*;

import java.util.Arrays;

@Entity
@Table(name = "students")
@Getter
@Setter
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

    @Column(name = "grades_list")
    private Integer[] gradesList;

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
