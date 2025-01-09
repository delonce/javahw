package org.delonce.repository.impl;

import org.delonce.config.JdbcTemplateConfig;
import org.delonce.entity.Author;
import org.delonce.repository.AuthorRepository;
import org.delonce.repository.CRUDRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.util.List;

public class AuthorRepositoryJdbcTemplateImpl implements AuthorRepository, CRUDRepository {

    //language=SQL
    private static final String SQL_SELECT_ALL = "SELECT * FROM authors ORDER BY id";

    //language=SQL
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM authors WHERE id = ?";

    //language=SQL
    private static final String SQL_INSERT_AUTHOR = "INSERT INTO authors(name, birth_date) VALUES (?, ?);";

    //language=SQL
    private static final String SQL_UPDATE_AUTHOR = "UPDATE authors SET name = ?, birth_date = ? WHERE id = ?";

    //language=SQL
    private static final String SQL_DELETE_AUTHOR = "DELETE FROM authors WHERE id = ?";

    //language=SQL
    private static final String SQL_DELETE_ALL_AUTHORS = "DELETE FROM authors";


    private final JdbcTemplate jdbcTemplate = JdbcTemplateConfig.JdbcTemplate();

    private static final RowMapper<Author> authorRowMapper = (row, rowNumber) -> {
        int authorId = row.getInt("id");;
        String name = row.getString("name");
        String birthDate = row.getString("birth_date");

        return new Author(authorId, name, birthDate);
    };

    @Override
    public List<Author> findAll() {
        return CRUDRepository.super.findAll(jdbcTemplate, SQL_SELECT_ALL, authorRowMapper);
    }

    @Override
    public Author findById(int id) {
        return CRUDRepository.super.findById(jdbcTemplate, SQL_SELECT_BY_ID, id, authorRowMapper, Author.class);
    }

    @Override
    public void createAuthor(String name, String birthDate) {
        CRUDRepository.super.update(jdbcTemplate, SQL_INSERT_AUTHOR, Author.class, name, Date.valueOf(birthDate));
    }

    @Override
    public void deleteAuthor(int id) {
        CRUDRepository.super.update(jdbcTemplate, SQL_DELETE_AUTHOR, Author.class, id);
    }

    @Override
    public void deleteAllAuthors() {
        CRUDRepository.super.update(jdbcTemplate, SQL_DELETE_ALL_AUTHORS, Author.class);
    }

    @Override
    public void updateAuthor(int id, String name, String birthDate) {
        CRUDRepository.super.update(jdbcTemplate, SQL_UPDATE_AUTHOR, Author.class, name, Date.valueOf(birthDate), id);
    }

    @Override
    public List<Author> findAuthorWithFixedLengthName(int length) {
        return CRUDRepository.super.findAll(jdbcTemplate, SQL_SELECT_ALL, authorRowMapper)
                .stream()
                .filter(author -> author.getName().length() == length)
                .toList();
    }
}
