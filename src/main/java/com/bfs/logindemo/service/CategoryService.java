package com.bfs.logindemo.service;
import com.bfs.logindemo.dao.CategoryDao;
import com.bfs.logindemo.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryDao categoryDao;

    @Autowired
    public CategoryService(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public List<Category> getAllCategories() {
        return categoryDao.findAll();
    }

    public Category getCategoryById(int id) {
        return categoryDao.findById(id);
    }

    public void saveCategory(Category category) {
        categoryDao.save(category);
    }

    public void updateCategory(Category category) {
        categoryDao.update(category);
    }

    public void deleteCategory(int id) {
        categoryDao.delete(id);
    }
}
