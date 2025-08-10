package nl.ckarakoc.eshop.controller;

import nl.ckarakoc.eshop.model.Category;
import nl.ckarakoc.eshop.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class CategoryController {

	private CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping("api/public/categories")
	public List<Category> getAllCategories() {
		return categoryService.getAllCategories();
	}

	@PostMapping("api/public/categories")
	public ResponseEntity<Category> createCategory(@RequestBody Category category) {
		categoryService.createCategory(category);
		return new ResponseEntity<>(category, HttpStatus.CREATED);
	}

	@DeleteMapping("api/admin/categories/{categoryId}")
	public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {
		categoryService.deleteCategory(categoryId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}