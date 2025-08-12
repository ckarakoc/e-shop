package nl.ckarakoc.eshop.service;

import nl.ckarakoc.eshop.exception.APIException;
import nl.ckarakoc.eshop.exception.ResourceNotFoundException;
import nl.ckarakoc.eshop.model.Category;
import nl.ckarakoc.eshop.payload.CategoryDTO;
import nl.ckarakoc.eshop.payload.CategoryResponse;
import nl.ckarakoc.eshop.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
		Pageable pageDetails = PageRequest.of(
				pageNumber,
				pageSize,
				sortOrder.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC,
				sortBy
		);
		Page<Category> categoryPage = categoryRepository.findAll(pageDetails);

		List<Category> categories = categoryPage.getContent();
		if (categories.isEmpty()) throw new APIException("No categories found");

		List<CategoryDTO> categoryDTOs = categories.stream()
				.map(category -> mapper.map(category, CategoryDTO.class))
				.toList();
		return new CategoryResponse(categoryDTOs, categoryPage.getNumber(), categoryPage.getSize(), categoryPage.getTotalElements(), categoryPage.getTotalPages(), categoryPage.isLast());
	}

	@Override
	public CategoryDTO createCategory(CategoryDTO categoryDto) {
		if (categoryRepository.findByCategoryName(categoryDto.getCategoryName()).isPresent())
			throw new APIException("Category with name " + categoryDto.getCategoryName() + " already exists");

		Category saved = categoryRepository.save(mapper.map(categoryDto, Category.class));
		return mapper.map(saved, CategoryDTO.class);
	}

	@Override
	public CategoryDTO getCategoryById(Long categoryId) {
		return mapper
				.map(categoryRepository
								.findById(categoryId)
								.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId)),
						CategoryDTO.class);
	}

	@Override
	public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDto) {
		CategoryDTO cat = getCategoryById(categoryId);
		cat.setCategoryName(categoryDto.getCategoryName());
		return mapper
				.map(categoryRepository
								.save(mapper.map(cat, Category.class)),
						CategoryDTO.class);
	}

	@Override
	public void deleteCategory(Long categoryId) {
		CategoryDTO category = getCategoryById(categoryId);
		categoryRepository.delete(mapper.map(category, Category.class));
	}
}
