package org.delonce.repository;

import org.delonce.exception.ObjectByIdNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Arrays;
import java.util.List;

public interface CRUDRepository {
    default <T> List<T> findAll(JdbcTemplate jdbcTemplate, String query, RowMapper<T> mapper) {
        return jdbcTemplate.query(query, mapper);
    }

    default <T> T findById(JdbcTemplate jdbcTemplate, String query, int id, RowMapper<T> mapper, Class<T> clazz) {
        try {
            return jdbcTemplate.queryForObject(query, mapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new ObjectByIdNotFoundException("Object of type " + clazz.getName() + " with ID " + id + " is not found", e);
        }
    }

    default <T> void update(JdbcTemplate jdbcTemplate, String query, Class<T> clazz, Object... args) {
        if (jdbcTemplate.update(query, args) == 0) {
            throw new ObjectByIdNotFoundException("Cannot update object of type " + clazz.getName() + " with args " + Arrays.stream(args).toList(), null);
        }
    }
}
