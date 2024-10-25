package com.ecommerce.project.controller;

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

import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class CategoryController {

	private CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping("/public/categories")
	public ResponseEntity<CategoryResponse> getAllCategories() {
		return new ResponseEntity<CategoryResponse>(categoryService.getAllCategories(), HttpStatus.OK);
	}

	@PostMapping("/public/categories")
	public ResponseEntity<String> createCategory(@Valid @RequestBody Category category) {
		categoryService.createCategory(category);
		return new ResponseEntity<String>("Category added successfully", HttpStatus.CREATED);
	}

	@DeleteMapping("/admin/categories/{categoryId}")
	public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
		return new ResponseEntity<String>(categoryService.deleteCategory(categoryId), HttpStatus.OK);
	}
	
	@PutMapping("/public/categories/{categoryId}")
	public ResponseEntity<String> updateCategory(@Valid @RequestBody Category category, @PathVariable Long categoryId){
		Category updatedCategory = categoryService.updateCategory(category, categoryId);
		return new ResponseEntity<String>("Category with category id: "+updatedCategory.getCategoryId()+" is updated", HttpStatus.OK);
	}
}
