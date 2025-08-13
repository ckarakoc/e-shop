package nl.ckarakoc.eshop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Table(name = "addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long addressId;

	@NotBlank
	@Size(min = 5, message = "street name must be at least 5 characters long.")
	private String street;

	@NotBlank
	@Size(min = 5, message = "building name must be at least 5 characters long.")
	private String buildingName;

	@NotBlank
	@Size(min = 5, message = "state name must be at least 5 characters long.")
	private String city;

	@NotBlank
	@Size(min = 2, message = "state name must be at least 2 characters long.")
	private String state;

	@NotBlank
	@Size(min = 2, message = "country name must be at least 2 characters long.")
	private String country;

	@NotBlank
	@Size(min = 6, message = "pincode name must be at least 6 characters long.")
	private String pincode;

	public Address(String street, String buildingName, String city, String state, String country, String pincode) {
		this.street = street;
		this.buildingName = buildingName;
		this.city = city;
		this.state = state;
		this.country = country;
		this.pincode = pincode;
	}

	@ToString.Exclude
	@ManyToMany(mappedBy = "addresses")
	private List<User> users = new ArrayList<>();
}
