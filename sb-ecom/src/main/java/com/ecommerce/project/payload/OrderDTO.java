package com.ecommerce.project.payload;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
	
	private Long orderId;
	private String email;
	private List<OrderItemDTO> orderItems;
	private LocalDate orderDate;
	private PaymentDTO payment;
	private Double totatAmount;
	private String orderStatus;
	private Long addressId;
}
