package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryResponse;

public interface CategoryService {
	
	public CategoryResponse getAllCategories();

	void createCategory(Category category);

	public String deleteCategory(Long categoryId);

	public Category updateCategory(Category category, Long categoryId);
}
