package app.repositories;

import app.exceptions.NoDataException;
import app.mappers.StudentMapper;
import app.models.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class StudentJdbcRepository {

    private final JdbcTemplate template;

    public Student createStudent(Student student) {
        return template.queryForObject(
                String.format("insert into students values(default, '%s', %d, '%s', ARRAY%s) returning *",
                        student.getFullName(),
                        student.getAge(),
                        student.getEmail(),
                        Arrays.toString(student.getGradesList())),
                new StudentMapper()
        );
    }

    public Student readStudent(Integer id) throws NoDataException {
        try {
            return template.queryForObject(
                    "select * from students where id = ?",
                    new StudentMapper(),
                    id
            );
        } catch (EmptyResultDataAccessException ex) {
            throw new NoDataException("No record with id = " + id);
        }
    }

    public Student updateStudent(Integer id, Student student) {
        return template.queryForObject(
                String.format("update students set full_name = %s, age = %s, email = %s, grades_list = %s where id = %d returning *",
                        (student.getFullName() != null) ? ("'" + student.getFullName() + "'") : ("full_name"),
                        (student.getAge() != null) ? (student.getAge()) : ("age"),
                        (student.getEmail() != null) ? ("'" + student.getEmail() + "'") : ("email"),
                        (student.getGradesList() != null) ? ("ARRAY" + Arrays.toString(student.getGradesList())) : ("grades_list"),
                        id),
                new StudentMapper()
        );
    }

    public Student addGrade(Integer id, Integer gradeId) {
        return template.queryForObject(
                "update students set grades_list = array_append(grades_list, ?) where id = ? returning *",
                new StudentMapper(),
                gradeId,
                id
        );
    }

    public boolean deleteStudent(Integer id) {
        template.execute(String.format("delete from students where id = %d", id));

        return true;
    }

    public List<Student> getAllStudentsByGrade(Integer id) {
        return template.query(
                "select * from students where ?=any(grades_list)",
                new StudentMapper(),
                id
        );
    }
}
