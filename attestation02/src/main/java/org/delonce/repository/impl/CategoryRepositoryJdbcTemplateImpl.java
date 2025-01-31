package org.delonce.repository.impl;

import org.delonce.config.JdbcTemplateConfig;
import org.delonce.entity.Category;
import org.delonce.repository.CRUDRepository;
import org.delonce.repository.CategoryRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryRepositoryJdbcTemplateImpl implements CRUDRepository, CategoryRepository {

    //language=SQL
    private static final String SQL_SELECT_ALL = "SELECT * FROM categories ORDER BY id";

    //language=SQL
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM categories WHERE id = ?";

    //language=SQL
    private static final String SQL_INSERT_CATEGORY = "INSERT INTO categories(category_name) VALUES (?);";

    //language=SQL
    private static final String SQL_UPDATE_CATEGORY = "UPDATE categories SET category_name = ? WHERE id = ?";

    //language=SQL
    private static final String SQL_DELETE_CATEGORY = "DELETE FROM categories WHERE id = ?";

    //language=SQL
    private static final String SQL_DELETE_ALL_CATEGORY = "DELETE FROM categories";

    private final JdbcTemplate jdbcTemplate = JdbcTemplateConfig.JdbcTemplate();

    private static final RowMapper<Category> categoryRowMapper = (row, rowNumber) -> {
        int id = row.getInt("id");
        String name = row.getString("category_name");

        return new Category(id, name);
    };

    @Override
    public List<Category> findAll() {
        return CRUDRepository.super.findAll(jdbcTemplate, SQL_SELECT_ALL, categoryRowMapper);
    }

    @Override
    public Category findById(int id) {
        return CRUDRepository.super.findById(jdbcTemplate, SQL_SELECT_BY_ID, id, categoryRowMapper, Category.class);
    }

    @Override
    public void createCategory(String name) {
        CRUDRepository.super.update(jdbcTemplate, SQL_INSERT_CATEGORY, Category.class, name);
    }

    @Override
    public void deleteCategory(int id) {
        CRUDRepository.super.update(jdbcTemplate, SQL_DELETE_CATEGORY, Category.class, id);
    }

    @Override
    public void deleteAllCategories() {
        CRUDRepository.super.update(jdbcTemplate, SQL_DELETE_ALL_CATEGORY, Category.class);
    }

    @Override
    public void updateCategory(int id, String name) {
        CRUDRepository.super.update(jdbcTemplate, SQL_UPDATE_CATEGORY, Category.class, name, id);
    }

    @Override
    public List<Category> findStartsWithCategory(String startsWithLetter) {
        return CRUDRepository.super.findAll(jdbcTemplate, SQL_SELECT_ALL, categoryRowMapper)
                .stream()
                .filter(category -> category.getCategoryName().startsWith(startsWithLetter))
                .toList();
    }
}
