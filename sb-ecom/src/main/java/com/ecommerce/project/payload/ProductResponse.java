package com.ecommerce.project.payload;

import java.util.List;

import com.ecommerce.project.model.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
	private List<ProductDTO> content;
}
