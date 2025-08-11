package nl.ckarakoc.eshop.service;

import nl.ckarakoc.eshop.expection.APIException;
import nl.ckarakoc.eshop.expection.ResourceNotFoundException;
import nl.ckarakoc.eshop.model.Category;
import nl.ckarakoc.eshop.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<Category> getAllCategories() {
		List<Category> categories = categoryRepository.findAll();
		if (categories.isEmpty()) throw new APIException("No categories found");
		return categories;
	}

	@Override
	public Category createCategory(Category category) {
		Category saved = categoryRepository
				.findByCategoryName(category.getCategoryName());
		if (saved != null) throw new APIException("Category with name " + category.getCategoryName() + " already exists");

		categoryRepository.save(category);
		return category;
	}

	@Override
	public Category getCategoryById(Long categoryId) {
		return categoryRepository
				.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
	}

	@Override
	public Category updateCategory(Long categoryId, Category category) {
		Category cat = getCategoryById(categoryId);
		cat.setCategoryName(category.getCategoryName());
		categoryRepository.save(cat);
		return cat;
	}

	@Override
	public void deleteCategory(Long categoryId) {
		Category category = getCategoryById(categoryId);
		categoryRepository.delete(category);
	}
}
