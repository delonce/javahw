package org.delonce.repository.impl;

import org.delonce.config.JdbcTemplateConfig;
import org.delonce.entity.Book;
import org.delonce.entity.BookWithDesc;
import org.delonce.repository.BookRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class BookRepositoryJdbcTemplateImpl implements BookRepository {

    //language=SQL
    private static final String SQL_SELECT_ALL = "SELECT * FROM books ORDER BY id";

    //language=SQL
    private static final String SQL_SELECT_WITH_AUTHOR_AND_CATEGORY = "SELECT " +
            "b.title, " +
            "a.name, " +
            "c.category_name, " +
            "b.published_date " +
            "FROM books b " +
            "JOIN authors a ON b.fk_author_id = a.id " +
            "JOIN categories c ON b.fk_category_id = c.id;";

    private final JdbcTemplate jdbcTemplate = JdbcTemplateConfig.jdbcTemplate();

    private static final RowMapper<Book> bookRowMapper = (row, rowNumber) -> {
        int bookId = row.getInt("id");;
        String title = row.getString("title");
        int authorId = row.getInt("fk_author_id");
        int categoryId = row.getInt("fk_author_id");
        String publishedDate = row.getString("published_date");

        return new Book(bookId, title, authorId, categoryId, publishedDate);
    };

    private static final RowMapper<BookWithDesc> bookWithDescRowMapper = (row, rowNumber) -> {
        String title = row.getString("title");
        String author = row.getString("name");
        String category = row.getString("category_name");
        String published_date = row.getString("published_date");

        return new BookWithDesc(title, author, category, published_date);
    };

    @Override
    public List<BookWithDesc> findAllByCategoriesAndAuthors() {
        return jdbcTemplate.query(SQL_SELECT_WITH_AUTHOR_AND_CATEGORY, bookWithDescRowMapper);
    }

    @Override
    public List<Book> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, bookRowMapper);
    }
}
