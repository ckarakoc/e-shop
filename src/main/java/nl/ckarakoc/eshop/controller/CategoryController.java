package nl.ckarakoc.eshop.controller;

import jakarta.validation.Valid;
import nl.ckarakoc.eshop.model.Category;
import nl.ckarakoc.eshop.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for managing categories in the application.
 * Provides RESTful endpoints to handle the CRUD operations for {@code Category} entities.
 */
@RestController
@RequestMapping("api")
public class CategoryController {

	private CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	/**
	 * Retrieves a list of all existing categories.
	 *
	 * @return a list containing all {@code Category} objects.
	 */
	@GetMapping("public/categories")
	public List<Category> getAllCategories() {
		return categoryService.getAllCategories();
	}

	/**
	 * Retrieves a category by its unique ID.
	 *
	 * @param categoryId the unique identifier of the category to retrieve
	 * @return the {@code Category} object corresponding to the specified ID
	 *         or throws an exception if the category is not found
	 */
	@GetMapping("public/categories/{categoryId}")
	public Category getCategoryById(@PathVariable Long categoryId) {
		return categoryService.getCategoryById(categoryId);
	}

	/**
	 * Creates a new category in the system.
	 *
	 * @param category the {@code Category} object to be created
	 * @return a {@code ResponseEntity} containing the newly created {@code Category} object
	 * and an HTTP status of {@code CREATED}
	 */
	@PostMapping("public/categories")
	public ResponseEntity<Category> createCategory(@Valid @RequestBody Category category) {
		return new ResponseEntity<>(categoryService.createCategory(category), HttpStatus.CREATED);
	}

	/**
	 * Updates an existing category with the specified ID using the provided updated category details.
	 * If the category with the given ID does not exist, a 404 error is returned.
	 *
	 * @param categoryId the unique identifier of the category to be updated
	 * @param category   the Category object containing the new details for the category
	 * @return a ResponseEntity containing the updated Category object and an HTTP status of OK
	 */
	@PutMapping("public/categories/{categoryId}")
	public ResponseEntity<Category> updateCategory(@PathVariable Long categoryId, @Valid @RequestBody Category category) {
		return new ResponseEntity<>(categoryService.updateCategory(categoryId, category), HttpStatus.OK);
	}

	/**
	 * Deletes a category identified by its unique ID.
	 *
	 * @param categoryId the unique identifier of the category to be deleted
	 * @return a {@code ResponseEntity<Void>} with HTTP status of {@code NO_CONTENT} upon successful deletion
	 */
	@DeleteMapping("admin/categories/{categoryId}")
	public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {
		categoryService.deleteCategory(categoryId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}