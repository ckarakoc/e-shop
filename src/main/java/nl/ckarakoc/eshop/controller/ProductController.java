package nl.ckarakoc.eshop.controller;

import jakarta.validation.Valid;
import nl.ckarakoc.eshop.config.AppConstants;
import nl.ckarakoc.eshop.payload.ProductDTO;
import nl.ckarakoc.eshop.payload.ProductResponse;
import nl.ckarakoc.eshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api")
public class ProductController {

	@Autowired
	ProductService productService;

	@PostMapping("admin/categories/{categoryId}/product")
	public ResponseEntity<ProductDTO> addProduct(@Valid @RequestBody ProductDTO productDTO,
	                                             @PathVariable Long categoryId) {
		ProductDTO savedProductDTO = productService.addProduct(categoryId, productDTO);
		return new ResponseEntity<>(savedProductDTO, HttpStatus.CREATED);
	}

	@GetMapping("public/products")
	public ResponseEntity<ProductResponse> getAllProducts(
		@RequestParam(name = "pageNumber", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer pageNumber,
		@RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer pageSize,
		@RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_PRODUCTS_BY) String sortBy,
		@RequestParam(name = "sortOrder", defaultValue = AppConstants.DEFAULT_SORT_DIR) String sortOrder
	) {
		ProductResponse productResponse = productService.getAllProducts(pageNumber, pageSize, sortBy, sortOrder);
		return new ResponseEntity<>(productResponse, HttpStatus.OK);
	}

	@GetMapping("public/categories/{categoryId}/products")
	public ResponseEntity<ProductResponse> getProductsByCategory(
		@PathVariable Long categoryId,
		@RequestParam(name = "pageNumber", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer pageNumber,
		@RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer pageSize,
		@RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_PRODUCTS_BY) String sortBy,
		@RequestParam(name = "sortOrder", defaultValue = AppConstants.DEFAULT_SORT_DIR) String sortOrder) {
		ProductResponse productResponse = productService.searchByCategory(categoryId, pageNumber, pageSize, sortBy, sortOrder);
		return new ResponseEntity<>(productResponse, HttpStatus.OK);
	}

	@GetMapping("public/products/keyword/{keyword}")
	public ResponseEntity<ProductResponse> getProductsByKeyword(
		@PathVariable String keyword,
		@RequestParam(name = "pageNumber", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer pageNumber,
		@RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer pageSize,
		@RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_PRODUCTS_BY) String sortBy,
		@RequestParam(name = "sortOrder", defaultValue = AppConstants.DEFAULT_SORT_DIR) String sortOrder
	) {
		ProductResponse productResponse = productService.searchByKeyword(keyword, pageNumber, pageSize, sortBy, sortOrder);
		return new ResponseEntity<>(productResponse, HttpStatus.OK);
	}

	@PutMapping("admin/products/{productId}")
	public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO productDTO,
	                                                @PathVariable Long productId) {
		ProductDTO updateProductDTO = productService.updateProduct(productId, productDTO);
		return new ResponseEntity<>(updateProductDTO, HttpStatus.OK);
	}

	@DeleteMapping("admin/products/{productId}")
	public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId) {
		ProductDTO deleted = productService.deleteProduct(productId);
		return new ResponseEntity<>(deleted, HttpStatus.OK);
	}

	@PutMapping("products/{productId}/image")
	public ResponseEntity<ProductDTO> updateProductImage(@PathVariable Long productId,
	                                                     @RequestParam("image") MultipartFile image) {
		ProductDTO updatedProduct = productService.updateProductImage(productId, image);
		return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
	}
}
