package nl.ckarakoc.eshop.repository;

import nl.ckarakoc.eshop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
