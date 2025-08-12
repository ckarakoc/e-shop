package nl.ckarakoc.eshop.controller;

import jakarta.validation.Valid;
import nl.ckarakoc.eshop.config.AppConstants;
import nl.ckarakoc.eshop.payload.CategoryDTO;
import nl.ckarakoc.eshop.payload.CategoryResponse;
import nl.ckarakoc.eshop.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for managing categories in the application.
 * Provides RESTful endpoints to handle the CRUD operations for {@code Category} entities.
 */
@RestController
@RequestMapping("api")
public class CategoryController {

	private CategoryService categoryService;

	@Autowired
	private ModelMapper mapper;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	/**
	 * Retrieves a list of all existing categories.
	 *
	 * @return a list containing all {@code Category} objects.
	 */
	@GetMapping("public/categories")
	public ResponseEntity<CategoryResponse> getAllCategories(
			@RequestParam(name = "pageNumber", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer pageNumber,
			@RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer pageSize,
			@RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_CATEGORIES_BY) String sortBy,
			@RequestParam(name = "sortOrder", defaultValue = AppConstants.DEFAULT_SORT_DIR) String sortOrder) {
		return new ResponseEntity<>(categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortOrder), HttpStatus.OK);
	}

	/**
	 * Retrieves a category by its unique ID.
	 *
	 * @param categoryId the unique identifier of the category to retrieve
	 * @return the {@code Category} object corresponding to the specified ID
	 * or throws an exception if the category is not found
	 */
	@GetMapping("public/categories/{categoryId}")
	public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long categoryId) {
		return new ResponseEntity<>(categoryService.getCategoryById(categoryId), HttpStatus.OK);
	}

	/**
	 * Creates a new category in the system.
	 *
	 * @param categoryDto the {@code CategoryDTO} object containing details of the category to be created
	 * @return a {@code ResponseEntity} containing the newly created {@code CategoryDTO} object
	 * and an HTTP status of {@code CREATED}
	 */
	@PostMapping("public/categories")
	public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDto) {
		return new ResponseEntity<>(categoryService.createCategory(categoryDto), HttpStatus.CREATED);
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
	public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long categoryId, @Valid @RequestBody CategoryDTO category) {
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