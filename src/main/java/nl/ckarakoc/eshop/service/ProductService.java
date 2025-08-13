package nl.ckarakoc.eshop.service;

import nl.ckarakoc.eshop.payload.ProductDTO;
import nl.ckarakoc.eshop.payload.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {
	ProductDTO addProduct(Long categoryId, ProductDTO productDTO);

	ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

	ProductResponse searchByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

	ProductResponse searchByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

	ProductDTO updateProduct(Long productId, ProductDTO productDTO);

	ProductDTO deleteProduct(Long productId);

	ProductDTO updateProductImage(Long productId, MultipartFile image);
}
