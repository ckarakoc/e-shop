package nl.ckarakoc.eshop.service;

import nl.ckarakoc.eshop.expection.EntityNotFoundException;
import nl.ckarakoc.eshop.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Service interface for managing categories in the application.
 * Provides methods to perform CRUD operations on {@code Category} entities.
 */
public interface CategoryService {

	/**
	 * Retrieves all categories available in the system.
	 *
	 * @return a list of {@code Category} objects representing all categories.
	 */
	List<Category> getAllCategories();

	/**
	 * Creates a new category in the system.
	 *
	 * @param category the {@code Category} object to be created
	 * @return the newly created {@code Category} object
	 */
	Category createCategory(Category category);

	/**
	 * Retrieves a category by its unique identifier.
	 *
	 * @param categoryId the unique identifier of the category to retrieve
	 * @return the {@code Category} object corresponding to the given identifier
	 * @throws EntityNotFoundException if no category is found with the specified identifier
	 */
	Category getCategoryById(Long categoryId);

	/**
	 * Updates an existing category identified by its ID with new details.
	 *
	 * @param categoryId the unique identifier of the category to be updated
	 * @param category   the {@code Category} object containing the updated details for the category
	 * @return the updated {@code Category} object
	 */
	Category updateCategory(Long categoryId, Category category);

	/**
	 * Deletes a category identified by its unique ID.
	 *
	 * @param categoryId the unique identifier of the category to be deleted
	 * @throws EntityNotFoundException if no category is found with the specified identifier
	 */
	void deleteCategory(Long categoryId);
}
