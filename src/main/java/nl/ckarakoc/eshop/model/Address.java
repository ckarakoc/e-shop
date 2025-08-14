package nl.ckarakoc.eshop.model;

import jakarta.persistence.*;
import lombok.*;
import nl.ckarakoc.eshop.payload.AddressDTO;
import nl.ckarakoc.eshop.validators.annotations.ValidAddress;

import java.util.Objects;
import java.util.Optional;

@Table(name = "addresses")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@ValidAddress
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long addressId;

	//	@NotBlank
	//	@Size(min = 5, message = "street name must be at least 5 characters long.")
	private String street;

	//	@NotBlank
	//	@Size(min = 5, message = "building name must be at least 5 characters long.")
	private String buildingName;

	//	@NotBlank
	//	@Size(min = 5, message = "state name must be at least 5 characters long.")
	private String city;

	//	@NotBlank
	//	@Size(min = 2, message = "state name must be at least 2 characters long.")
	private String state;

	//	@NotBlank
	//	@Size(min = 2, message = "country name must be at least 2 characters long.")
	private String country;

	//	@NotBlank
	//	@Size(min = 4, message = "pincode name must be at least 6 characters long.")
	private String pincode;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Address address)) return false;
		return Objects.equals(addressId, address.addressId);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(addressId);
	}

	// Fluent Setters
	public Address street(String street) {
		setStreet(street);
		return this;
	}

	public Address buildingName(String buildingName) {
		setBuildingName(buildingName);
		return this;
	}

	public Address city(String city) {
		setCity(city);
		return this;
	}

	public Address state(String state) {
		setState(state);
		return this;
	}

	public Address country(String country) {
		setCountry(country);
		return this;
	}

	public Address pincode(String pincode) {
		setPincode(pincode);
		return this;
	}

	public Address updateFrom(AddressDTO dto) {
		Optional.ofNullable(dto.getStreet()).ifPresent(this::setStreet);
		Optional.ofNullable(dto.getBuildingName()).ifPresent(this::setBuildingName);
		Optional.ofNullable(dto.getCity()).ifPresent(this::setCity);
		Optional.ofNullable(dto.getState()).ifPresent(this::setState);
		Optional.ofNullable(dto.getCountry()).ifPresent(this::setCountry);
		Optional.ofNullable(dto.getPincode()).ifPresent(this::setPincode);
		return this;
	}
}
