package com.ecommerce.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.project.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{

	@Query("SELECT ci from CartItem ci where ci.cart.id=?1 and ci.product.id=?2")
	CartItem findCartItemByProductIdAndCartId(Long cartId, Long productId);

	@Modifying
	@Query("DELETE from CartItem ci where ci.cart.id=?1 and ci.product.id=?2")
	void deleteCartItemByProductIdAndCartId(Long cartId, Long productId);

}
