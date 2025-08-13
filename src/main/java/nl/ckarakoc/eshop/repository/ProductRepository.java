package nl.ckarakoc.eshop.repository;

import nl.ckarakoc.eshop.model.Category;
import nl.ckarakoc.eshop.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	Page<Product> findByCategoryOrderByPriceAsc(Category category, Pageable pageDetails);

	Page<Product> findByProductNameContainingIgnoreCase(String name, Pageable pageDetails);
}
