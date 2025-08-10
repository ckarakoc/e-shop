package nl.ckarakoc.eshop.service;

import nl.ckarakoc.eshop.model.Category;

import java.util.List;


public interface CategoryService {
	List<Category> getAllCategories();

	Category createCategory(Category category);

	Category getCategoryById(Long categoryId);

	Category updateCategory(Long categoryId, Category category);

	void deleteCategory(Long categoryId);
}
