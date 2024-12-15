package org.delonce.repository;

import org.delonce.entity.Book;
import org.delonce.entity.BookWithDesc;

import java.util.List;

public interface BookRepository {
    List<Book> findAll();
    List<BookWithDesc> findAllByCategoriesAndAuthors();

}
