package org.delonce.repository;

import org.delonce.entity.Category;

import java.util.List;

public interface CategoryRepository {
    List<Category> findAll();
    void addCategory(String name);
    void deleteCategory(int id);
}
