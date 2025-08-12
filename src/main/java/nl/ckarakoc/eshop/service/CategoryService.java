package nl.ckarakoc.eshop.service;

import nl.ckarakoc.eshop.payload.CategoryDTO;
import nl.ckarakoc.eshop.payload.CategoryResponse;


/**
 * Service interface for managing categories in the application.
 * Provides methods to perform CRUD operations on {@code Category} entities.
 */
public interface CategoryService {

	/**
	 * Retrieves a list of all categories available in the system.
	 *
	 * @return a {@code CategoryResponse} representing all the categories in the system.
	 */
	CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

	/**
	 * Creates a new category in the system.
	 *
	 * @param category the {@code CategoryDTO} object containing details of the category to be created
	 * @return the newly created {@code CategoryDTO} object
	 */
	CategoryDTO createCategory(CategoryDTO category);

	/**
	 * Retrieves the category details for the given category ID.
	 *
	 * @param categoryId the unique identifier of the category to be retrieved
	 * @return a {@code CategoryDTO} object containing the details of the specified category
	 */
	CategoryDTO getCategoryById(Long categoryId);

	/**
	 * Updates an existing category with the specified ID using the provided updated category details.
	 * The method allows modifying the category's attributes such as its name.
	 *
	 * @param categoryId the unique identifier of the category to be updated
	 * @param category the {@code CategoryDTO} object containing the new details for the category
	 *                 to be updated
	 * @return the updated {@code CategoryDTO} object representing the modified category
	 */
	CategoryDTO updateCategory(Long categoryId, CategoryDTO category);

	/**
	 * Deletes a category identified by its unique ID.
	 *
	 * @param categoryId the unique identifier of the category to be deleted
	 */
	void deleteCategory(Long categoryId);
}
