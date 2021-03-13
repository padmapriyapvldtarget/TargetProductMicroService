package com.target.product.repository;

import org.springframework.data.repository.CrudRepository;


import org.springframework.stereotype.Repository;

import com.target.product.model.Product;


@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

}
