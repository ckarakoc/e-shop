package nl.ckarakoc.eshop.repository;

import nl.ckarakoc.eshop.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
