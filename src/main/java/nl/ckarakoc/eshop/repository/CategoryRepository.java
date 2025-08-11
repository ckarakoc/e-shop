package nl.ckarakoc.eshop.repository;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import nl.ckarakoc.eshop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	Category findByCategoryName(@NotBlank @Size(min = 5, message = "Category name must be at least 5 characters long") String categoryName);
}
