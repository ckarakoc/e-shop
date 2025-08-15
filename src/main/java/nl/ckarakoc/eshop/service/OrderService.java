package nl.ckarakoc.eshop.service;

import nl.ckarakoc.eshop.payload.OrderDTO;

public interface OrderService {
	OrderDTO placeOrder(String email, Long addressId, String paymentMethod, String pgName, String pgPaymentId, String pgStatus, String pgResponseMessage);
}
