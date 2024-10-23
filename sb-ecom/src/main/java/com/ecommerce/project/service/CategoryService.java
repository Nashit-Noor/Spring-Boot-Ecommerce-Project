package com.ecommerce.project.service;

import java.util.List;

import com.ecommerce.project.model.Category;

public interface CategoryService {
	public List<Category> getAllCategories();

	void createCategory(Category category);

	public String deleteCategory(Long categoryId);

	public Category updateCategory(Category category, Long categoryId);
}
