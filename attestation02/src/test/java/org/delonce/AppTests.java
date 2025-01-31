package org.delonce;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.delonce.entity.Author;
import org.delonce.exception.ObjectByIdNotFoundException;
import org.delonce.repository.AuthorRepository;
import org.delonce.repository.BookRepository;
import org.delonce.repository.CategoryRepository;
import org.delonce.repository.impl.AuthorRepositoryJdbcTemplateImpl;
import org.delonce.repository.impl.BookRepositoryJdbcTemplateImpl;
import org.delonce.repository.impl.CategoryRepositoryJdbcTemplateImpl;
import org.junit.jupiter.api.Test;

class AppTests {
    @Test
    void testBookRepo() {
        BookRepository bookRepository = new BookRepositoryJdbcTemplateImpl();
        assertEquals(1, bookRepository.findById(1).getBookId());

        bookRepository.createBook("test", 1, 1, "1869-01-01");
        bookRepository.updateBook(1, "test", 1, 1, "1869-01-01");
        bookRepository.deleteBook(2);

        assertEquals(6, bookRepository.findAll().size());
        assertEquals(2, bookRepository.findBookByAuthorId(1).size());

        bookRepository.deleteAllBooks();
        assertThrows(ObjectByIdNotFoundException.class, () -> bookRepository.findById(1));
        assertThrows(ObjectByIdNotFoundException.class, () -> bookRepository.updateBook(1, "test", 1, 1, "1869-01-01"));
        assertThrows(ObjectByIdNotFoundException.class, () -> bookRepository.deleteBook(2));
    }

    @Test
    void testCategoryRepo() {
        CategoryRepository categoryRepository = new CategoryRepositoryJdbcTemplateImpl();
        assertEquals(1, categoryRepository.findById(1).getCategoryId());

        categoryRepository.createCategory("new category");
        categoryRepository.updateCategory(2, "first category");
        categoryRepository.deleteCategory(2);

        assertEquals(6, categoryRepository.findAll().size());
        assertEquals(2, categoryRepository.findStartsWithCategory("Р").size());

        categoryRepository.deleteAllCategories();
        assertThrows(ObjectByIdNotFoundException.class, () -> categoryRepository.findById(1));
        assertThrows(ObjectByIdNotFoundException.class, () -> categoryRepository.updateCategory(2, "first category"));
        assertThrows(ObjectByIdNotFoundException.class, () -> categoryRepository.deleteCategory(2));
    }

    @Test
    void testAuthorRepo() {
        AuthorRepository authorRepository = new AuthorRepositoryJdbcTemplateImpl();
        Author firstAuthor = authorRepository.findById(1);
        assertEquals(1, firstAuthor.getAuthorId());

        authorRepository.createAuthor("Roman", "2024-01-01");
        authorRepository.updateAuthor(1, "first Author", firstAuthor.getBirthDate());
        authorRepository.deleteAuthor(2);

        assertEquals(6, authorRepository.findAll().size());
        assertEquals(1, authorRepository.findAuthorWithFixedLengthName(11).size());

        authorRepository.deleteAllAuthors();
        assertThrows(ObjectByIdNotFoundException.class, () -> authorRepository.findById(1));
        assertThrows(ObjectByIdNotFoundException.class, () -> authorRepository.updateAuthor(1, "first Author", firstAuthor.getBirthDate()));
        assertThrows(ObjectByIdNotFoundException.class, () -> authorRepository.deleteAuthor(2));
    }
}
