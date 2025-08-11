package nl.ckarakoc.eshop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

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
