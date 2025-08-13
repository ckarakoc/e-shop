package nl.ckarakoc.eshop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long productId;

	@NotBlank
	@Size(min = 3, message = "Product name must be at least 3 characters long")
	private String productName;
	private String image;
	private String description;
	private Integer quantity;
	private Double price;
	private Double discount;
	private Double specialPrice;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
}
