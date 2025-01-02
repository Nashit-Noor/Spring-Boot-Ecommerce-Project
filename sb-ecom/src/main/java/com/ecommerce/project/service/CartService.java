package com.ecommerce.project.service;

import java.util.List;

import com.ecommerce.project.payload.CartDTO;

public interface CartService {

	CartDTO addProductToCart(Long productId, Integer quantity);

	List<CartDTO> getAllCarts();

	CartDTO getCart(String emailId, Long cartId);

	CartDTO updateProductQuantityInCart(Long productId, Integer quantity);

	String deleteProductFromCart(Long cartId, Long productId);

	void updateProductInCart(Long cartId, Long productId);

}
