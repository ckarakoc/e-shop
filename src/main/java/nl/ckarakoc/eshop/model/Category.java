package nl.ckarakoc.eshop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Setter
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity(name = "categories")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long categoryId;

	@NotBlank
	@Size(min = 5, message = "Category name must be at least 5 characters long")
	private String categoryName;

	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	private List<Product> products;

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Category category)) return false;
		return Objects.equals(categoryId, category.categoryId);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(categoryId);
	}
}
