package nl.ckarakoc.eshop.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import nl.ckarakoc.eshop.model.Address;
import nl.ckarakoc.eshop.validators.annotations.ValidAddress;


public class AddressValidator implements ConstraintValidator<ValidAddress, Address> {
	@Override
	public boolean isValid(Address address, ConstraintValidatorContext context) {
		if (address == null) {
			return true; // Let @NotNull handle null validation if needed
		}

		boolean isValid = true;

		// Disable default constraint violation
		context.disableDefaultConstraintViolation();

		// Validate street
		if (isBlankOrNull(address.getStreet())) {
			context.buildConstraintViolationWithTemplate("Street name cannot be blank")
				.addPropertyNode("street")
				.addConstraintViolation();
			isValid = false;
		} else if (address.getStreet().length() < 5) {
			context.buildConstraintViolationWithTemplate("Street name must be at least 5 characters long")
				.addPropertyNode("street")
				.addConstraintViolation();
			isValid = false;
		}

		// Validate building name
		if (isBlankOrNull(address.getBuildingName())) {
			context.buildConstraintViolationWithTemplate("Building name cannot be blank")
				.addPropertyNode("buildingName")
				.addConstraintViolation();
			isValid = false;
		} else if (address.getBuildingName().length() < 5) {
			context.buildConstraintViolationWithTemplate("Building name must be at least 5 characters long")
				.addPropertyNode("buildingName")
				.addConstraintViolation();
			isValid = false;
		}

		// Validate city
		if (isBlankOrNull(address.getCity())) {
			context.buildConstraintViolationWithTemplate("City name cannot be blank")
				.addPropertyNode("city")
				.addConstraintViolation();
			isValid = false;
		} else if (address.getCity().length() < 5) {
			context.buildConstraintViolationWithTemplate("City name must be at least 5 characters long")
				.addPropertyNode("city")
				.addConstraintViolation();
			isValid = false;
		}

		// Validate state
		if (isBlankOrNull(address.getState())) {
			context.buildConstraintViolationWithTemplate("State name cannot be blank")
				.addPropertyNode("state")
				.addConstraintViolation();
			isValid = false;
		} else if (address.getState().length() < 2) {
			context.buildConstraintViolationWithTemplate("State name must be at least 2 characters long")
				.addPropertyNode("state")
				.addConstraintViolation();
			isValid = false;
		}

		// Validate country
		if (isBlankOrNull(address.getCountry())) {
			context.buildConstraintViolationWithTemplate("Country name cannot be blank")
				.addPropertyNode("country")
				.addConstraintViolation();
			isValid = false;
		} else if (address.getCountry().length() < 2) {
			context.buildConstraintViolationWithTemplate("Country name must be at least 2 characters long")
				.addPropertyNode("country")
				.addConstraintViolation();
			isValid = false;
		}

		// Validate pincode
		if (isBlankOrNull(address.getPincode())) {
			context.buildConstraintViolationWithTemplate("Pincode cannot be blank")
				.addPropertyNode("pincode")
				.addConstraintViolation();
			isValid = false;
		} else if (address.getPincode().length() < 4) {
			context.buildConstraintViolationWithTemplate("Pincode must be at least 4 characters long")
				.addPropertyNode("pincode")
				.addConstraintViolation();
			isValid = false;
		}

		return isValid;
	}

	private boolean isBlankOrNull(String value) {
		return value == null || value.trim().isEmpty();
	}
}
