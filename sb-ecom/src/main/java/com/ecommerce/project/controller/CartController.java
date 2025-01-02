package com.ecommerce.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.project.payload.CartDTO;
import com.ecommerce.project.repository.CartRepository;
import com.ecommerce.project.service.CartService;
import com.ecommerce.project.util.AuthUtil;

@RestController
@RequestMapping("/api")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private AuthUtil authUtil;
	
	@Autowired
	private CartRepository cartRepository;
	
	@PostMapping("/carts/products/{productId}/quantity/{quantity}")
	public ResponseEntity<CartDTO> addProductToCart(@PathVariable Long productId, @PathVariable Integer quantity){
		CartDTO cartDTO = cartService.addProductToCart(productId, quantity);
		return new ResponseEntity<>(cartDTO, HttpStatus.CREATED);
	}
	
	@GetMapping("/carts")
	public ResponseEntity<List<CartDTO>> getCarts(){
		List<CartDTO> cartDTOs = cartService.getAllCarts();
		return new ResponseEntity<List<CartDTO>>(cartDTOs, HttpStatus.FOUND);
	}
	
	@GetMapping("/carts/users/cart")
	public ResponseEntity<CartDTO> getCartById(){
		String emailId = authUtil.loggedInEmail();
		Long cartId = cartRepository.findCartByEmail(emailId).getCartId();
		CartDTO cartDTO = cartService.getCart(emailId, cartId);
		return new ResponseEntity<CartDTO>(cartDTO, HttpStatus.OK);
	}
	
	@PutMapping("/cart/products/{productId}/quantity/{operation}")
	public ResponseEntity<CartDTO> updateCartProduct(@PathVariable Long productId, @PathVariable String operation){
		CartDTO cartDTO = cartService.updateProductQuantityInCart(productId, operation.equalsIgnoreCase("delete")?-1:1);
		return new ResponseEntity<CartDTO>(cartDTO, HttpStatus.OK);
	}
	
	@DeleteMapping("/carts/{cartId}/product/{productId}")
	public ResponseEntity<String> deleteProductFromCart(@PathVariable Long cartId, @PathVariable Long productId){
		String status = cartService.deleteProductFromCart(cartId, productId);
		return new ResponseEntity<String>(status, HttpStatus.OK);
	}
}
