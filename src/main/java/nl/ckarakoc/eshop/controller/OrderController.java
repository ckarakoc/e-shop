package nl.ckarakoc.eshop.controller;

import nl.ckarakoc.eshop.payload.OrderDTO;
import nl.ckarakoc.eshop.payload.OrderRequestDTO;
import nl.ckarakoc.eshop.service.OrderService;
import nl.ckarakoc.eshop.util.AuthUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class OrderController {

	private final AuthUtil authUtil;
	private final OrderService orderService;

	public OrderController(AuthUtil authUtil, OrderService orderService) {
		this.authUtil = authUtil;
		this.orderService = orderService;
	}

	@PostMapping("/order/users/payments/{paymentMethod}")
	public ResponseEntity<OrderDTO> orderProducts(@PathVariable String paymentMethod, @RequestBody OrderRequestDTO orderRequestDTO) {
		OrderDTO dto = orderService.placeOrder(
			authUtil.loggedInEmail(),
			orderRequestDTO.getAddressId(),
			paymentMethod,
			orderRequestDTO.getPgName(),
			orderRequestDTO.getPgPaymentId(),
			orderRequestDTO.getPgStatus(),
			orderRequestDTO.getPgResponseMessage()
		);
		return ResponseEntity.ok(dto);
	}
}
