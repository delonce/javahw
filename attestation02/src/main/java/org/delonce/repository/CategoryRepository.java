package org.delonce.repository;

import org.delonce.entity.Category;

import java.util.List;

public interface CategoryRepository {
    void deleteCategory(int id);
    void deleteAllCategories();
    List<Category> findAll();
    Category findById(int id);
    void createCategory(String name);
    void updateCategory(int id, String name);
    List<Category> findStartsWithCategory(String startsWithString);
}
