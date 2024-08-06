package com.bfs.logindemo.service;
import com.bfs.logindemo.dao.CategoryDao;
import com.bfs.logindemo.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryDao categoryDao;

    @Autowired
    public CategoryService(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Transactional(readOnly = true)
    public List<Category> getAllCategories() {
        return categoryDao.findAllHibernate();
    }

    @Transactional(readOnly = true)
    public Category getCategoryById(int id) {
        return categoryDao.findByIdHibernate(id);
    }

    @Transactional
    public void saveCategory(Category category) {
        categoryDao.saveHibernate(category);
    }

    @Transactional
    public void updateCategory(Category category) {
        categoryDao.updateHibernate(category);
    }

    @Transactional
    public void deleteCategory(int id) {
        categoryDao.deleteHibernate(id);
    }
}
