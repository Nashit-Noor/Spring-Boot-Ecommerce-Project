package com.ecommerce.project.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.repository.CategoryRepository;
import com.ecommerce.project.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public ProductDTO addProduct(Long categoryId, ProductDTO productDTO) {
		Category category = categoryRepository
				.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId", categoryId));
		Product product = modelMapper.map(productDTO, Product.class);
		product.setImage("default.png");
		product.setCategory(category);
		product.setSpecialPrice(product.getPrice() - ((product.getDiscount()*0.01)*product.getPrice()));
		Product savedProduct = productRepository.save(product);
		return modelMapper.map(savedProduct, ProductDTO.class);
	}

	@Override
	public ProductResponse getAllProducts() {
		List<Product> products = productRepository.findAll();
		List<ProductDTO> productDTOS = products.stream().map(product -> modelMapper.map(product, ProductDTO.class)).toList();
		
		ProductResponse productResponse = new ProductResponse();
		productResponse.setContent(productDTOS);
		return productResponse;
	}

	@Override
	public ProductResponse searchByCategory(Long categoryId) {
		Category category = categoryRepository
				.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId", categoryId));
		List<Product> products = productRepository.findByCategoryOrderByPriceAsc(category);
		List<ProductDTO> productDTOS = products.stream().map(product -> modelMapper.map(product, ProductDTO.class)).toList();
		
		ProductResponse productResponse = new ProductResponse();
		productResponse.setContent(productDTOS);
		return productResponse;
	}

	@Override
	public ProductResponse searchProductByKeyword(String keyword) {
		List<Product> products = productRepository.findByProductNameLikeIgnoreCase('%'+keyword+'%');
		List<ProductDTO> productDTOS = products.stream().map(product -> modelMapper.map(product, ProductDTO.class)).toList();
		
		ProductResponse productResponse = new ProductResponse();
		productResponse.setContent(productDTOS);
		return productResponse;
	}

	@Override
	public ProductDTO updateProduct(ProductDTO productDTO, Long productId) {
		Product productFromDb = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product", "ProductId", productId));
		Product product = modelMapper.map(productDTO, Product.class);
		productFromDb.setProductName(product.getProductName());
		productFromDb.setDescription(product.getDescription());
		productFromDb.setQuantity(product.getQuantity());
		productFromDb.setDiscount(product.getDiscount());
		productFromDb.setSpecialPrice(product.getSpecialPrice());
		
		Product savedProduct = productRepository.save(productFromDb);
		
		return modelMapper.map(savedProduct, ProductDTO.class);
	}

	@Override
	public ProductDTO deleteProduct(Long productId) {
		Product productFromDb = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product", "ProductId", productId));
		productRepository.delete(productFromDb);
		return modelMapper.map(productFromDb, ProductDTO.class);
	}

}
