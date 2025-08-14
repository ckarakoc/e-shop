package nl.ckarakoc.eshop.service;


import nl.ckarakoc.eshop.exception.APIException;
import nl.ckarakoc.eshop.exception.ResourceNotFoundException;
import nl.ckarakoc.eshop.model.Cart;
import nl.ckarakoc.eshop.model.CartItem;
import nl.ckarakoc.eshop.model.Product;
import nl.ckarakoc.eshop.payload.CartDTO;
import nl.ckarakoc.eshop.payload.ProductDTO;
import nl.ckarakoc.eshop.repository.CartItemRepository;
import nl.ckarakoc.eshop.repository.CartRepository;
import nl.ckarakoc.eshop.repository.ProductRepository;
import nl.ckarakoc.eshop.util.AuthUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

@Service
public class CartServiceImpl implements CartService {

	private final CartRepository cartRepository;
	private final ProductRepository productRepository;
	private final CartItemRepository cartItemRepository;
	private final AuthUtil authUtil;
	private final ModelMapper mapper;

	public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository, AuthUtil authUtil, CartItemRepository cartItemRepository, ModelMapper mapper) {
		this.cartRepository = cartRepository;
		this.productRepository = productRepository;
		this.authUtil = authUtil;
		this.cartItemRepository = cartItemRepository;
		this.mapper = mapper;
	}

	@Override
	public CartDTO addProductToCart(Long productId, Integer quantity) {
		Cart cart = createCart();
		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

		CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cart.getCartId(), productId);

		if (cartItem != null) throw new APIException("Product" + product.getProductName() + " already exists in cart");
		if (product.getQuantity() == 0) throw new APIException("Product" + product.getProductName() + " is out of stock");
		if (product.getQuantity() < quantity) throw new APIException("Product" + product.getProductName() + " quantity is less than requested");

		CartItem newCartItem = new CartItem();
		newCartItem.setCart(cart);
		newCartItem.setProduct(product);
		newCartItem.setQuantity(quantity);
		newCartItem.setDiscount(product.getDiscount());
		newCartItem.setPrice(product.getPrice());
		cartItemRepository.save(newCartItem);

		product.setQuantity(product.getQuantity());
		productRepository.save(product);

		cart.setTotalPrice(cart.getTotalPrice() + (quantity * product.getSpecialPrice()));
		cartRepository.save(cart);

		CartDTO cartDTO = mapper.map(cart, CartDTO.class);
		Stream<ProductDTO> productDTOStream = cart.getCartItems()
			.stream()
			.map(item -> {
				ProductDTO mapped = mapper.map(item.getProduct(), ProductDTO.class);
				mapped.setQuantity(item.getQuantity());
				return mapped;
			});

		cartDTO.setProducts(productDTOStream.toList());

		return cartDTO;
	}

	@Override
	public List<CartDTO> getAllCarts() {
		List<Cart> carts = cartRepository.findAll();
		if (carts.isEmpty()) throw new APIException("No carts found");

		return carts.stream().map((cart) -> {
			CartDTO dto = mapper.map(cart, CartDTO.class);
			List<ProductDTO> products = cart.getCartItems().stream().map(item -> mapper.map(item.getProduct(), ProductDTO.class)).toList();
			dto.setProducts(products);
			return dto;
		}).toList();
	}

	@Override
	public CartDTO getCart(String emailId, Long cartId) {
		Cart cart = cartRepository.findCartByEmailAndCartId(emailId, cartId);
		if (cart == null) throw new ResourceNotFoundException("Cart", "cartId", cartId);
		CartDTO cartDTO = mapper.map(cart, CartDTO.class);
		cart.getCartItems().forEach(item -> item.getProduct().setQuantity(item.getQuantity()));
		cartDTO.setProducts(cart.getCartItems().stream().map(item -> mapper.map(item.getProduct(), ProductDTO.class)).toList());
		return cartDTO;
	}

	@Transactional
	@Override
	public CartDTO updateProductQuantityInCart(Long productId, Integer quantity) {
		Cart cart = cartRepository.findCartByEmail(authUtil.loggedInEmail());

		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));


		if (product.getQuantity() == 0) throw new APIException("Product" + product.getProductName() + " is out of stock");
		if (product.getQuantity() < quantity) throw new APIException("Product" + product.getProductName() + " quantity is less than requested");

		CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cart.getCartId(), productId);
		if (cartItem == null) throw new APIException("Product" + product.getProductName() + " not found in cart");
		if (cartItem.getQuantity() + quantity < 0) throw new APIException("quantity cannot be negative");


		cartItem.setPrice(product.getSpecialPrice());
		cartItem.setQuantity(cartItem.getQuantity() + quantity);
		cartItem.setDiscount(product.getDiscount());
		cart.setTotalPrice(cart.getTotalPrice() + (quantity * product.getSpecialPrice()));
		cartRepository.save(cart);
		CartItem savedCartItem = cartItemRepository.save(cartItem);
		if (savedCartItem.getQuantity() == 0) cartItemRepository.deleteById(savedCartItem.getCartItemId());

		CartDTO cartDTO = mapper.map(cart, CartDTO.class);
		cartDTO.setProducts(
			cart.getCartItems().stream().map(item -> {
				ProductDTO prd = mapper.map(item.getProduct(), ProductDTO.class);
				prd.setQuantity(item.getQuantity());
				return prd;
			}).toList()
		);
		return cartDTO;
	}

	@Transactional
	@Override
	public String deleteProductFromCart(Long cartId, Long productId) {
		Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));

		CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cartId, productId);
		if (cartItem == null) throw new APIException("Product" + productId + " not found in cart");

		cart.setTotalPrice(cart.getTotalPrice() - (cartItem.getPrice() * cartItem.getQuantity()));
		cartItemRepository.deleteCartItemByProductIdAndCartId(productId, cartId);

		return "Product " + cartItem.getProduct().getProductName() + " removed from cart";
	}

	@Override
	public void updateProductInCarts(Long cartId, Long productId) {
		Cart cart = cartRepository.findById(cartId)
			.orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));
		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

		CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(productId, cartId);

		if (cartItem == null) throw new APIException("Product" + productId + " not found in cart");

		cartItem.setPrice(product.getSpecialPrice());
		cartItem.setQuantity(product.getQuantity());
		cartItem.setDiscount(product.getDiscount());
		double cartPrice = cart.getTotalPrice() - (cartItem.getPrice() * cartItem.getQuantity());
		cart.setTotalPrice(cartPrice + cartItem.getPrice() * cartItem.getQuantity());

		cartItemRepository.save(cartItem);
		cartRepository.save(cart);
	}

	private Cart createCart() {
		Cart userCart = cartRepository.findCartByEmail(authUtil.loggedInEmail());
		if (userCart != null) {
			return userCart;
		}

		Cart cart = new Cart();
		cart.setTotalPrice(0.0);
		cart.setUser(authUtil.loggedInUser());
		return cartRepository.save(cart);

	}
}
