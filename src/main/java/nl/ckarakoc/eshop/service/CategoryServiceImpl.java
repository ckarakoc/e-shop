package nl.ckarakoc.eshop.service;

import nl.ckarakoc.eshop.expection.EntityNotFoundException;
import nl.ckarakoc.eshop.model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements the {@link CategoryService} to manage Category entities.
 * This service provides methods to create, retrieve, update, and delete categories.
 */
@Service
public class CategoryServiceImpl implements CategoryService {
	private final List<Category> categories = new ArrayList<>();

	/**
	 * Retrieves a list of all existing categories.
	 *
	 * @return a list containing all the {@code Category} objects managed by the service.
	 */
	@Override
	public List<Category> getAllCategories() {
		return categories;
	}

	/**
	 * Creates a new category, assigns it a unique identifier, and adds it to the list of categories.
	 *
	 * @param category the {@code Category} object to be created and added to the list
	 * @return the newly created {@code Category} object with the assigned identifier
	 */
	@Override
	public Category createCategory(Category category) {
		category.setCategoryId(categories.size() + 1L);
		categories.add(category);
		return category;
	}

	/**
	 * Retrieves a {@code Category} object by its unique identifier.
	 *
	 * @param categoryId the unique identifier of the category to be retrieved
	 * @return the {@code Category} object matching the specified identifier
	 * @throws EntityNotFoundException if no category is found with the specified identifier
	 */
	@Override
	public Category getCategoryById(Long categoryId) {
		return categories.stream()
				.filter(category -> category.getCategoryId().equals(categoryId))
				.findFirst()
				.orElseThrow(() -> new EntityNotFoundException("Category not found"));
	}

	/**
	 * Updates the category with the specified ID using the provided updated {@code Category} object.
	 * If the category with the specified ID is not found, a 404 error is thrown.
	 *
	 * @param categoryId the ID of the category to be updated
	 * @param category   the {@code Category} object containing the updated details
	 * @return the updated {@code Category} object
	 */
	@Override
	public Category updateCategory(Long categoryId, Category category) {
		Category cat = getCategoryById(categoryId); // throws 404 if not found
		cat.setCategoryName(category.getCategoryName());
		return cat;
	}

	/**
	 * Deletes a category identified by its unique ID. If the category does not exist, an
	 * {@code EntityNotFoundException} is thrown.
	 *
	 * @param categoryId the unique identifier of the category to be deleted
	 * @throws EntityNotFoundException if the category with the specified ID does not exist
	 */
	@Override
	public void deleteCategory(Long categoryId) {
		boolean removed = categories.removeIf(category -> category.getCategoryId().equals(categoryId));
		if (!removed) {
			throw new EntityNotFoundException("Category does not exist");
		}
	}
}
