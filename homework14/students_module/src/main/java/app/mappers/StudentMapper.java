package app.mappers;

import app.models.Student;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class StudentMapper implements RowMapper<Student> {

    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Student(
                rs.getInt("id"),
                rs.getString("full_name"),
                rs.getInt("age"),
                rs.getString("email"),
                rs.getString("password"),
                (String[]) rs.getArray("roles").getArray(),
                (Integer[]) rs.getArray("grades_list").getArray()
        );
    }
}
