package nl.ckarakoc.eshop.controller;

import jakarta.validation.Valid;
import nl.ckarakoc.eshop.model.User;
import nl.ckarakoc.eshop.payload.AddressDTO;
import nl.ckarakoc.eshop.service.AddressService;
import nl.ckarakoc.eshop.util.AuthUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AddressController {

	private final AuthUtil authUtil;
	private final AddressService addressService;

	public AddressController(AuthUtil authUtil, AddressService addressService) {
		this.authUtil = authUtil;
		this.addressService = addressService;
	}

	@GetMapping("/addresses")
	public ResponseEntity<List<AddressDTO>> getAddresses() {
		List<AddressDTO> saved = addressService.getAddresses();
		return new ResponseEntity<>(saved, HttpStatus.OK);
	}

	@GetMapping("/addresses/{addressId}")
	public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long addressId) {
		AddressDTO saved = addressService.getAddressById(addressId);
		return new ResponseEntity<>(saved, HttpStatus.OK);
	}

	@GetMapping("/users/addresses/")
	public ResponseEntity<List<AddressDTO>> getUserAddresses() {
		User user = authUtil.loggedInUser();
		List<AddressDTO> saved = addressService.getUserAddresses(user);
		return new ResponseEntity<>(saved, HttpStatus.OK);
	}

	@PostMapping("/addresses")
	public ResponseEntity<AddressDTO> createAddress(@Valid @RequestBody AddressDTO addressDTO) {
		User user = authUtil.loggedInUser();
		AddressDTO saved = addressService.createAddress(addressDTO, user);
		return new ResponseEntity<>(saved, HttpStatus.CREATED);
	}

	@PutMapping("/addresses/{addressId}")
	public ResponseEntity<AddressDTO> updateAddress(@PathVariable Long addressId, @RequestBody AddressDTO addressDTO) {
		return new ResponseEntity<>(addressService.updateAddress(addressId, addressDTO), HttpStatus.OK);
	}

	@DeleteMapping("/addresses/{addressId}")
	public ResponseEntity<String> updateAddress(@PathVariable Long addressId) {
		return new ResponseEntity<>(addressService.deleteAddress(addressId), HttpStatus.OK);
	}


}