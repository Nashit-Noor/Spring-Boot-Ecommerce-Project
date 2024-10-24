package com.ecommerce.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public List<Category> getAllCategories() {
		List<Category> categories = categoryRepository.findAll();
		if(categories.isEmpty()) {
			throw new APIException("No category created till now.");
		}
		return categories;
	}

	@Override
	public void createCategory(Category category) {
		Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
		if(savedCategory != null) {
			throw new APIException("Category with the name '" + category.getCategoryName() + "' already exists !!!");
		}		
		categoryRepository.save(category);
	}

	@Override
	public String deleteCategory(Long categoryId) {
		Category category = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "categoryId", categoryId));		
		categoryRepository.delete(category);
		return "Category with categoryId: "+categoryId+" deleted successfully !!";
	}

	@Override
	public Category updateCategory(Category category, Long categoryId) {
		Category savedCategory = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "categoryId", categoryId));
		
		savedCategory.setCategoryName(category.getCategoryName());
		categoryRepository.save(savedCategory);
		return savedCategory;
	}
	
}
