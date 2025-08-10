package nl.ckarakoc.eshop.service;

import nl.ckarakoc.eshop.model.Category;

import java.util.List;


public interface CategoryService {
	List<Category> getAllCategories();

	void createCategory(Category category);

	Category getCategoryById(Long categoryId);

	void updateCategory(Category category);

	void deleteCategory(Long categoryId);
}
