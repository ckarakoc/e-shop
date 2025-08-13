package nl.ckarakoc.eshop.service;

import nl.ckarakoc.eshop.exception.APIException;
import nl.ckarakoc.eshop.exception.ResourceNotFoundException;
import nl.ckarakoc.eshop.model.Category;
import nl.ckarakoc.eshop.model.Product;
import nl.ckarakoc.eshop.payload.ProductDTO;
import nl.ckarakoc.eshop.payload.ProductResponse;
import nl.ckarakoc.eshop.repository.CategoryRepository;
import nl.ckarakoc.eshop.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private FileService fileService;

	@Value("${project.image}")
	private String imagePath;

	@Override
	public ProductDTO addProduct(Long categoryId, ProductDTO productDTO) {
		Category category = categoryRepository.findById(categoryId)
			.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

		boolean isProductNotPresent = true;

		List<Product> products = category.getProducts();
		for (Product value : products) {
			if (value.getProductName().equals(productDTO.getProductName())) {
				isProductNotPresent = false;
				break;
			}
		}

		if (isProductNotPresent) {
			Product product = mapper.map(productDTO, Product.class);
			product.setImage("default.png");
			product.setCategory(category);
			double specialPrice = product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice());
			product.setSpecialPrice(specialPrice);
			Product saved = productRepository.save(product);
			return mapper.map(saved, ProductDTO.class);
		} else {
			throw new APIException("Product with name " + productDTO.getProductName() + " already exists");
		}
	}

	@Override
	public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
		Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
		Page<Product> pageProducts = productRepository.findAll(pageDetails);

		List<Product> products = pageProducts.getContent();
		List<ProductDTO> productDTOS = products.stream().map(product -> mapper.map(product, ProductDTO.class)).toList();

		return new ProductResponse(
			productDTOS,
			pageProducts.getNumber(),
			pageProducts.getSize(),
			pageProducts.getTotalElements(),
			pageProducts.getTotalPages(),
			pageProducts.isLast()
		);
	}

	@Override
	public ProductResponse searchByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

		Category category = categoryRepository.findById(categoryId)
			.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

		Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
		Page<Product> pageProducts = productRepository.findByCategoryOrderByPriceAsc(category, pageDetails);

		List<Product> products = pageProducts.getContent();
		List<ProductDTO> productDTOS = products.stream().map(product -> mapper.map(product, ProductDTO.class)).toList();

		return new ProductResponse(
			productDTOS,
			pageProducts.getNumber(),
			pageProducts.getSize(),
			pageProducts.getTotalElements(),
			pageProducts.getTotalPages(),
			pageProducts.isLast()
		);
	}

	@Override
	public ProductResponse searchByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
		Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
		Page<Product> pageProducts = productRepository.findByProductNameContainingIgnoreCase(keyword, pageDetails);

		List<Product> products = pageProducts.getContent();
		List<ProductDTO> productDTOS = products.stream().map(product -> mapper.map(product, ProductDTO.class)).toList();

		return new ProductResponse(
			productDTOS,
			pageProducts.getNumber(),
			pageProducts.getSize(),
			pageProducts.getTotalElements(),
			pageProducts.getTotalPages(),
			pageProducts.isLast()
		);
	}

	@Override
	public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
		Product productFromDb = productRepository.findById(productId)
			.orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

		Product product = mapper.map(productDTO, Product.class);
		productFromDb.setProductName(product.getProductName());
		productFromDb.setDescription(product.getDescription());
		productFromDb.setQuantity(product.getQuantity());
		productFromDb.setDiscount(product.getDiscount());
		productFromDb.setPrice(product.getPrice());
		productFromDb.setSpecialPrice(product.getSpecialPrice());

		Product saved = productRepository.save(productFromDb);
		return mapper.map(saved, ProductDTO.class);
	}

	@Override
	public ProductDTO deleteProduct(Long productId) {
		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
		productRepository.delete(product);
		return mapper.map(product, ProductDTO.class);
	}

	@Override
	public ProductDTO updateProductImage(Long productId, MultipartFile image) {
		Product productFromDb = productRepository.findById(productId)
			.orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

		String filename = fileService.uploadImage(imagePath, image);

		productFromDb.setImage(filename);
		Product updated = productRepository.save(productFromDb);
		return mapper.map(updated, ProductDTO.class);
	}
}
