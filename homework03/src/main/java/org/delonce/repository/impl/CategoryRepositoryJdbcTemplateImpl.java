package org.delonce.repository.impl;

import org.delonce.config.JdbcTemplateConfig;
import org.delonce.entity.Category;
import org.delonce.repository.CategoryRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class CategoryRepositoryJdbcTemplateImpl implements CategoryRepository {

    //language=SQL
    private static final String SQL_SELECT_ALL = "SELECT * FROM categories ORDER BY id";

    //language=SQL
    private static final String SQL_INSERT_CATEGORY = "INSERT INTO categories(category_name) VALUES (?);";

    //language=SQL
    private static final String SQL_DELETE_CATEGORY = "DELETE FROM categories WHERE id = ?";

    private final JdbcTemplate jdbcTemplate = JdbcTemplateConfig.jdbcTemplate();

    private static final RowMapper<Category> memberRowMapper = (row, rowNumber) -> {
        int id = row.getInt("id");
        String name = row.getString("category_name");

        return new Category(id, name);
    };

    @Override
    public List<Category> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, memberRowMapper);
    }

    @Override
    public void createCategory(String name) {
        jdbcTemplate.update(SQL_INSERT_CATEGORY, name);
    }

    @Override
    public void deleteCategory(int id) {
        jdbcTemplate.update(SQL_DELETE_CATEGORY, id);
    }
}
