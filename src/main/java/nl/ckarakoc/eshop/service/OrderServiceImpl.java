package nl.ckarakoc.eshop.service;

import nl.ckarakoc.eshop.exception.APIException;
import nl.ckarakoc.eshop.exception.ResourceNotFoundException;
import nl.ckarakoc.eshop.model.*;
import nl.ckarakoc.eshop.payload.OrderDTO;
import nl.ckarakoc.eshop.payload.OrderItemDTO;
import nl.ckarakoc.eshop.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

	private final CartRepository cartRepository;
	private final AddressRepository addressRepository;
	private final ModelMapper modelMapper;
	private final PaymentRepository paymentRepository;
	private final OrderRepository orderRepository;
	private final OrderItemRepository orderItemRepository;
	private final ProductRepository productRepository;
	private final CartService cartService;

	public OrderServiceImpl(CartRepository cartRepository, AddressRepository addressRepository, ModelMapper modelMapper, PaymentRepository paymentRepository, OrderRepository orderRepository, OrderItemRepository orderItemRepository, ProductRepository productRepository, CartService cartService) {
		this.cartRepository = cartRepository;
		this.addressRepository = addressRepository;
		this.modelMapper = modelMapper;
		this.paymentRepository = paymentRepository;
		this.orderRepository = orderRepository;
		this.orderItemRepository = orderItemRepository;
		this.productRepository = productRepository;
		this.cartService = cartService;
	}

	@Transactional
	@Override
	public OrderDTO placeOrder(String email, Long addressId, String paymentMethod, String pgName, String pgPaymentId, String pgStatus, String pgResponseMessage) {
		Cart cart = cartRepository.findCartByEmail(email);
		if (cart == null) throw new ResourceNotFoundException("Cart", "email", email);

		Address address = addressRepository.findById(addressId)
			.orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", addressId));

		Order order = new Order();
		order.setEmail(email);
		order.setOrderDate(LocalDate.now());
		order.setTotalAmount(cart.getTotalPrice());
		order.setOrderStatus("Order Accepted");
		order.setAddress(address);

		Payment payment = new Payment(paymentMethod, pgPaymentId, pgStatus, pgResponseMessage, pgName);
		payment.setOrder(order);
		payment = paymentRepository.save(payment);
		order.setPayment(payment);

		Order savedOrder = orderRepository.save(order);

		List<CartItem> cartItems = cart.getCartItems();
		if (cartItems.isEmpty()) {
			throw new APIException("Cart is empty");
		}

		List<OrderItem> orderItems = new ArrayList<>();
		for (CartItem cartItem : cartItems) {
			OrderItem orderItem = new OrderItem();
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setDiscount(cartItem.getDiscount());
			orderItem.setOrderedProductPrice(cartItem.getPrice());
			orderItem.setOrder(savedOrder);
			orderItems.add(orderItem);
		}

		orderItems = orderItemRepository.saveAll(orderItems);

		cart.getCartItems().forEach(item -> {
			int quantity = item.getQuantity();
			Product product = item.getProduct();

			// Reduce stock quantity
			product.setQuantity(product.getQuantity() - quantity);

			// Save product back to the database
			productRepository.save(product);

			// Remove items from cart
			cartService.deleteProductFromCart(cart.getCartId(), item.getProduct().getProductId());
		});

		OrderDTO orderDTO = modelMapper.map(savedOrder, OrderDTO.class);
		orderItems.forEach(item -> orderDTO.getOrderItems().add(modelMapper.map(item, OrderItemDTO.class)));

		orderDTO.setAddressId(addressId);

		return orderDTO;
	}
}
