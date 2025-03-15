package app.repositories;

import app.exceptions.NoDataException;
import app.mappers.GradeMapper;
import app.models.Grade;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GradeRepository {

    private final JdbcTemplate template;

    public List<Grade> readAllGrades() {
        return template.query(
                "select * from grades",
                new GradeMapper()
        );
    }

    public Grade makeGradeActive(Integer id) {
        return template.queryForObject(
                "update grades set is_active = true where id = ? returning *",
                new GradeMapper(),
                id
        );
    }

    public Grade makeGradeNonActive(Integer id) {
        return template.queryForObject(
                "update grades set is_active = false where id = ? returning *",
                new GradeMapper(),
                id
        );
    }

    public Grade readGrade(Integer id) throws NoDataException {
        try {
            return template.queryForObject(
                    "select * from grades where id = ?",
                    new GradeMapper(),
                    id
            );
        } catch (EmptyResultDataAccessException ex) {
            throw new NoDataException("No record with id = " + id);
        }
    }
}
