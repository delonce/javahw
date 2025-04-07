package app.mappers;

import app.models.Student;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentMapper implements RowMapper<Student> {

    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Student(
                rs.getInt("id"),
                rs.getString("full_name"),
                rs.getInt("age"),
                rs.getString("email"),
                (Integer[]) rs.getArray("grades_list").getArray()
        );
    }
}
