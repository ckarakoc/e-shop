package nl.ckarakoc.eshop.service;

import nl.ckarakoc.eshop.expection.EntityNotFoundException;
import nl.ckarakoc.eshop.model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
	private List<Category> categories = new ArrayList<>();

	@Override
	public List<Category> getAllCategories() {
		return categories;
	}

	@Override
	public void createCategory(Category category) {
		category.setCategoryId(categories.size() + 1L);
		categories.add(category);
	}

	@Override
	public Category getCategoryById(Long categoryId) {
		return categories.stream().filter(category -> category.getCategoryId().equals(categoryId)).findFirst().orElse(null);
	}

	@Override
	public void updateCategory(Category category) {

	}

	@Override
	public void deleteCategory(Long categoryId) {
		boolean exist = categories.removeIf(category -> category.getCategoryId().equals(categoryId));
		if (!exist) {
			throw new EntityNotFoundException("Category does not exist");
		}
	}
}
