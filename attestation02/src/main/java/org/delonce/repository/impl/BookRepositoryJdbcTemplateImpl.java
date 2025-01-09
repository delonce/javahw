package org.delonce.repository.impl;

import org.delonce.config.JdbcTemplateConfig;
import org.delonce.entity.Book;
import org.delonce.entity.Category;
import org.delonce.repository.BookRepository;
import org.delonce.repository.CRUDRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.util.List;

public class BookRepositoryJdbcTemplateImpl implements BookRepository, CRUDRepository {

    //language=SQL
    private static final String SQL_SELECT_ALL = "SELECT * FROM books ORDER BY id";

    //language=SQL
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM books WHERE id = ?";

    //language=SQL
    private static final String SQL_INSERT_BOOK =  "INSERT INTO books (title, fk_author_id, fk_category_id, published_date) VALUES (?, ?, ?, ?);";

    //language=SQL
    private static final String SQL_DELETE_BOOK = "DELETE FROM books WHERE id = ?";

    //language=SQL
    private static final String SQL_DELETE_ALL_BOOKS = "DELETE FROM books";

    //language=SQL
    private static final String SQL_UPDATE_BOOK = "UPDATE books SET title = ?, fk_author_id = ?, fk_category_id = ?, published_date = ? WHERE id = ?";

    private final JdbcTemplate jdbcTemplate = JdbcTemplateConfig.JdbcTemplate();

    private static final RowMapper<Book> bookRowMapper = (row, rowNumber) -> {
        int bookId = row.getInt("id");;
        String title = row.getString("title");
        int authorId = row.getInt("fk_author_id");
        int categoryId = row.getInt("fk_author_id");
        String publishedDate = row.getString("published_date");

        return new Book(bookId, title, authorId, categoryId, publishedDate);
    };

    @Override
    public List<Book> findAll() {
        return CRUDRepository.super.findAll(jdbcTemplate, SQL_SELECT_ALL, bookRowMapper);
    }

    @Override
    public Book findById(int id) {
        return CRUDRepository.super.findById(jdbcTemplate, SQL_SELECT_BY_ID, id, bookRowMapper, Book.class);
    }

    @Override
    public void createBook(String title, int authorId, int categoryId, String publishedDate) {
        CRUDRepository.super.update(jdbcTemplate, SQL_INSERT_BOOK, Book.class, title, authorId, categoryId, Date.valueOf(publishedDate));
    }

    @Override
    public void deleteBook(int id) {
        CRUDRepository.super.update(jdbcTemplate, SQL_DELETE_BOOK, Book.class, id);
    }

    @Override
    public void deleteAllBooks() {
        CRUDRepository.super.update(jdbcTemplate, SQL_DELETE_ALL_BOOKS, Book.class);
    }

    @Override
    public void updateBook(int id, String title, int authorId, int categoryId, String publishedDate) {
        CRUDRepository.super.update(jdbcTemplate, SQL_UPDATE_BOOK, Book.class, title, authorId, categoryId, Date.valueOf(publishedDate), id);
    }

    @Override
    public List<Book> findBookByAuthorId(int authorId) {
        return CRUDRepository.super.findAll(jdbcTemplate, SQL_SELECT_ALL, bookRowMapper)
                .stream()
                .filter(book -> book.getAuthorId() == authorId)
                .toList();
    }
}
