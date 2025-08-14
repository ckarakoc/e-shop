package nl.ckarakoc.eshop.repository;

import nl.ckarakoc.eshop.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
	@Query("SELECT c FROM Cart c WHERE c.user.email = ?1")
	Cart findCartByEmail(String email);

	@Query("SELECT c FROM Cart c WHERE c.user.email = ?1 AND c.cartId = ?2")
	Cart findCartByEmailAndCartId(String emailId, Long cartId);

	@Query("select c from Cart c join fetch c.cartItems ci join fetch ci.product p where p.productId = ?1")
	List<Cart> findCartsByProductId(Long productId);
}
