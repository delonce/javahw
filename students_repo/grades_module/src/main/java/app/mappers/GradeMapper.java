package app.mappers;

import app.models.Grade;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GradeMapper implements RowMapper<Grade> {

    @Override
    public Grade mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Grade(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getDate("start_date").toLocalDate(),
                rs.getBoolean("is_active")
        );
    }
}
