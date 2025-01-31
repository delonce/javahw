package org.delonce.repository;

import org.delonce.entity.Author;

import java.util.List;

public interface AuthorRepository {
    void deleteAuthor(int id);
    void deleteAllAuthors();
    List<Author> findAll();
    List<Author> findAuthorWithFixedLengthName(int length);
    Author findById(int id);
    void createAuthor(String name, String birthDate);
    void updateAuthor(int id, String name, String birthDate);
}
