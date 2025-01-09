package org.delonce.repository;

import org.delonce.entity.Book;

import java.util.List;

public interface BookRepository {
    void deleteBook(int id);
    void deleteAllBooks();
    List<Book> findAll();
    Book findById(int id);
    void createBook(String title, int authorId, int categoryId, String publishedDate);
    void updateBook(int id, String title, int authorId, int categoryId, String publishedDate);
    List<Book> findBookByAuthorId(int authorId);
}
