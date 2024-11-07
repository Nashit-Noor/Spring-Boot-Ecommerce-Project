package com.ecommerce.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.service.ProductService;

@RestController
@RequestMapping("/api")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@PostMapping("/admin/categories/{categoryId}/product")
	public ResponseEntity<ProductDTO> addProduct(@RequestBody Product product, @PathVariable Long categoryId){
		return new ResponseEntity<ProductDTO>(productService.addProduct(categoryId, product), HttpStatus.CREATED);
	}
	
	@GetMapping("/public/products")
	public ResponseEntity<ProductResponse> getAllProducts(){
		return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
	}
	
	@GetMapping("/public/{categoryId}/product")
	public ResponseEntity<ProductResponse> getProductsByCategory(@PathVariable Long categoryId){
		ProductResponse productResponse = productService.searchByCategory(categoryId);
		return new ResponseEntity<>(productResponse, HttpStatus.OK);
	}
}
