package nl.ckarakoc.eshop.repository;

import nl.ckarakoc.eshop.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

	@Query("SELECT ci FROM CartItem ci WHERE ci.cart.cartId =?1 AND ci.product.productId = ?2")
	CartItem findCartItemByProductIdAndCartId(Long cartId, Long productId);

	@Modifying
	@Query("DELETE FROM CartItem ci WHERE ci.cart.cartId = ?2 and ci.product.productId = ?1")
	void deleteCartItemByProductIdAndCartId(Long productId, Long cartId);
}
