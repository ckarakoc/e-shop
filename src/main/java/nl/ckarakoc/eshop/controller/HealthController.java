package nl.ckarakoc.eshop.controller;

import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Data
public class HealthController {
	final String healthMessage ="test";

	@GetMapping("health")
	public String health() {
		return "test";
	}
}
