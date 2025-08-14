package nl.ckarakoc.eshop.controller;

import nl.ckarakoc.eshop.model.Cart;
import nl.ckarakoc.eshop.payload.CartDTO;
import nl.ckarakoc.eshop.repository.CartRepository;
import nl.ckarakoc.eshop.service.CartService;
import nl.ckarakoc.eshop.util.AuthUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CartController {

	private final CartService cartService;
	private final AuthUtil authUtil;
	private final CartRepository cartRepository;

	public CartController(CartService cartService, AuthUtil authUtil, CartRepository cartRepository) {
		this.cartService = cartService;
		this.authUtil = authUtil;
		this.cartRepository = cartRepository;
	}

	@PostMapping("/carts/products/{productId}/quantity/{quantity}")
	public ResponseEntity<CartDTO> addProductToCart(@PathVariable Long productId, @PathVariable Integer quantity) {
		CartDTO cartDTO = cartService.addProductToCart(productId, quantity);
		return new ResponseEntity<>(cartDTO, HttpStatus.CREATED);
	}

	@GetMapping("/carts")
	public ResponseEntity<List<CartDTO>> getCarts() {
		return new ResponseEntity<>(cartService.getAllCarts(), HttpStatus.OK);
	}

	@GetMapping("/carts/users/cart")
	public ResponseEntity<CartDTO> getCartById() {
		String emailId = authUtil.loggedInEmail();
		Cart cart = cartRepository.findCartByEmail(emailId);
		CartDTO cartDTO = cartService.getCart(emailId, cart.getCartId());
		return new ResponseEntity<>(cartDTO, HttpStatus.OK);
	}

	@PutMapping("/carts/products/{productId}/quantity/{operation}")
	public ResponseEntity<CartDTO> updateCartProduct(@PathVariable Long productId, @PathVariable String operation) {
		return new ResponseEntity<>(cartService.updateProductQuantityInCart(productId, operation.equalsIgnoreCase("delete") ? -1 : 1), HttpStatus.OK);
	}

	@DeleteMapping("/carts/{cartId}/products/{productId}")
	public ResponseEntity<String> deleteProductFromCart(@PathVariable Long cartId, @PathVariable Long productId) {
		String status = cartService.deleteProductFromCart(cartId, productId);
		return new ResponseEntity<>(status, HttpStatus.OK);
	}
}
