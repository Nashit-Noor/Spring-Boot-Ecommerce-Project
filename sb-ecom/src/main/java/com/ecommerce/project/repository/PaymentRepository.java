package com.ecommerce.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.project.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>{

}
