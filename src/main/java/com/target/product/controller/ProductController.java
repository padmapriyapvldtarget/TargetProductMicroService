package com.target.product.controller;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.target.product.entity.CurrentPrice;
import com.target.product.entity.ProductEntity;
import com.target.product.exception.NoContentException;
import com.target.product.model.Product;
import com.target.product.model.RedSky;
import com.target.product.repository.ProductRepository;
import com.target.product.service.ProductService;

@RestController

@RequestMapping("/product")
public class ProductController {

	private ProductService productService;

	@Autowired
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	@Autowired
	private ProductRepository productRepository;

	@PostMapping("/save")
	public ResponseEntity<?> saveIntoProductTable(@RequestBody Product product) {

		
			return new ResponseEntity<>(productService.saveIntoProductTable(product), HttpStatus.OK);
		

	}

	@GetMapping(path = "{id}")
	public ResponseEntity<?> fetchRecordFromProductTable(@PathVariable("id") Integer productId) {
		ProductEntity productEntity = null;
		Product productDetails = null;

		ResponseEntity<RedSky> redSky = null;

		try {
			redSky = productService.getRedSkyDetails(productId);
			productEntity = productService.fetchRecordFromProductTable(productId, redSky);

			return new ResponseEntity<>(productEntity, HttpStatus.OK);

		} catch (NoContentException e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

	}

	@PutMapping("/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable("id") Integer productId,
			@RequestBody ProductEntity productEntity) {
		Optional<Product> productData = productRepository.findById(productId);

		if (productData.isPresent()) {

			return new ResponseEntity<>(productService.updateRecordIntoProductTable(productEntity), HttpStatus.OK);

		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/deleteProduct/{id}")
	public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") Integer productId) {
		try {

			productRepository.deleteById(productId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

	@GetMapping("/getAllProducts")
	public ResponseEntity<List<Product>> getAllProducts() {
		try {
			List<Product> products = new ArrayList<Product>();

			productRepository.findAll().forEach(products::add);

			if (products.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(products, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
