package nl.ckarakoc.eshop.service;

import nl.ckarakoc.eshop.payload.CartDTO;

import java.util.List;

public interface CartService {
	public CartDTO addProductToCart(Long productId, Integer quantity);

	List<CartDTO> getAllCarts();

	CartDTO getCart(String emailId, Long cartId);

	CartDTO updateProductQuantityInCart(Long productId, Integer quantity);

	String deleteProductFromCart(Long cartId, Long productId);

	void updateProductInCarts(Long cartId, Long productId);
}
