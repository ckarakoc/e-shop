package nl.ckarakoc.eshop.validators.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import nl.ckarakoc.eshop.validators.AddressValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AddressValidator.class)
public @interface ValidAddress {
	String message() default "Invalid address";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}

