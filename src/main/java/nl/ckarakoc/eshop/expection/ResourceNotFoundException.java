package nl.ckarakoc.eshop.expection;

public class ResourceNotFoundException extends RuntimeException {
	String resourceName, field, fieldName;
	Long fieldId;

	public ResourceNotFoundException() {
		super();
	}

	public ResourceNotFoundException(String resourceName, String field, String fieldName) {
		super(String.format("Resource %s not found with %s %s", resourceName, field, fieldName));
		this.resourceName = resourceName;
		this.field = field;
		this.fieldName = fieldName;
	}

	public ResourceNotFoundException(String resourceName, String field, Long fieldId) {
		super(String.format("Resource %s not found with %s %d", resourceName, field, fieldId));
		this.resourceName = resourceName;
		this.field = field;
		this.fieldId = fieldId;
	}

	public ResourceNotFoundException(String message) {
		super(message);
	}
}
