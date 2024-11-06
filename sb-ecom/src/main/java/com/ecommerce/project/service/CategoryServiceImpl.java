package com.ecommerce.project.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize) {
		Pageable pageDetails = PageRequest.of(pageNumber, pageSize);
		Page<Category> categoryPage = categoryRepository.findAll(pageDetails);
		List<Category> categories = categoryPage.getContent();
		if(categories.isEmpty()) {
			throw new APIException("No category created till now.");
		}
		List<CategoryDTO> categoryDTO = categories.stream()
				.map(category->modelMapper.map(category, CategoryDTO.class)).toList();
		CategoryResponse categoryResponse = new CategoryResponse();
		categoryResponse.setContent(categoryDTO);
		categoryResponse.setPageNumber(categoryPage.getNumber());
		categoryResponse.setPageSize(categoryPage.getSize());
		categoryResponse.setTotalElements(categoryPage.getTotalElements());
		categoryResponse.setTotalPages(categoryPage.getTotalPages());
		categoryResponse.setLastpage(categoryPage.isLast());
		return categoryResponse;
	}

	@Override
	public CategoryDTO createCategory(CategoryDTO categoryDTO) {
		Category category = modelMapper.map(categoryDTO, Category.class);
		Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
		if(savedCategory != null) {
			throw new APIException("Category with the name '" + categoryDTO.getCategoryName() + "' already exists !!!");
		}
		return modelMapper.map(categoryRepository.save(category), CategoryDTO.class);
	}

	@Override
	public CategoryDTO deleteCategory(Long categoryId) {
		Category category = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "categoryId", categoryId));
		categoryRepository.delete(category);
		return modelMapper.map(category, CategoryDTO.class);
	}

	@Override
	public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
		Category savedCategory = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "categoryId", categoryId));
		Category category = modelMapper.map(categoryDTO, Category.class);
		category.setCategoryName(categoryDTO.getCategoryName());
		return modelMapper.map(categoryRepository.save(savedCategory), CategoryDTO.class);
	}
	
}
