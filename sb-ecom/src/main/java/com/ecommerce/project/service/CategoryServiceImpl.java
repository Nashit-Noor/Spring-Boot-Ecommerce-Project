package com.ecommerce.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	private Long nextId = 1L;
	
	@Override
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}

	@Override
	public void createCategory(Category category) {
		category.setCategoryId(nextId++);
		categoryRepository.save(category);
	}

	@Override
	public String deleteCategory(Long categoryId) {
		Category category = categoryRepository.findById(categoryId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Record not found"));		
		categoryRepository.delete(category);
		return "Category with categoryId: "+categoryId+" deleted successfully !!";
	}

	@Override
	public Category updateCategory(Category category, Long categoryId) {
		Category savedCategory = categoryRepository.findById(categoryId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Record not found"));
		
		savedCategory.setCategoryName(category.getCategoryName());
		categoryRepository.save(savedCategory);
		return savedCategory;
	}
	
}
