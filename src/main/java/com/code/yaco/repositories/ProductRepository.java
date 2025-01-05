package com.code.yaco.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.code.yaco.models.Product;

public interface ProductRepository extends JpaRepository<Product,Long> {

}
