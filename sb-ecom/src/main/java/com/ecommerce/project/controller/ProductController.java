package com.ecommerce.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.service.ProductService;

@RestController
@RequestMapping("/api")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@PostMapping("/admin/categories/{categoryId}/product")
	public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO, @PathVariable Long categoryId){
		return new ResponseEntity<ProductDTO>(productService.addProduct(categoryId, productDTO), HttpStatus.CREATED);
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
	
	@GetMapping("/public/products/keyword/{keyword}")
	public ResponseEntity<ProductResponse> getProductsByKeyword(@PathVariable String keyword){
		ProductResponse productResponse = productService.searchProductByKeyword(keyword);
		return new ResponseEntity<>(productResponse, HttpStatus.OK);
	}
	
	@PutMapping("/admin/products/{productid}")
	public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO, @PathVariable Long productId){
		ProductDTO updatedProductDTO = productService.updateProduct(productDTO, productId);
		return new ResponseEntity<>(updatedProductDTO, HttpStatus.OK);
	}
	
	@DeleteMapping("/admin/products/{productId}")
	public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId){
		ProductDTO deletedProduct = productService.deleteProduct(productId);
		return new ResponseEntity<>(deletedProduct, HttpStatus.OK);
	}
}
