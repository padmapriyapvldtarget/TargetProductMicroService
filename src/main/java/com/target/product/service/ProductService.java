package com.target.product.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.target.product.entity.CurrentPrice;
import com.target.product.entity.ProductEntity;
import com.target.product.exception.NoContentException;
import com.target.product.model.Product;
import com.target.product.model.RedSky;

import com.target.product.repository.ProductRepository;

@Service
public class ProductService {

	@Value("${redSkyUrl}")
	private String redSkyUrl;

	@Autowired
	private ProductRepository productRepository;

	public Product saveIntoProductTable(Product product) {

		return productRepository.save(product);

	}

	public ResponseEntity<RedSky> getRedSkyDetails(Integer productId) throws NoContentException {

		RestTemplate restTemplate = new RestTemplate();
		String fooResourceUrl = redSkyUrl + productId;
		ResponseEntity<RedSky> response = restTemplate.getForEntity(fooResourceUrl, RedSky.class);

		return response;

	}

	public Product updateRecordIntoProductTable(ProductEntity productEntity) {

		Product productDetails = new Product();
		productDetails.setProductId(productEntity.getProductId());
		CurrentPrice currentPrice = new CurrentPrice();
		currentPrice.setValue(productEntity.getCurrentprice().getValue());
		currentPrice.setCurrencyCode(productEntity.getCurrentprice().getCurrencyCode());
		productDetails.setCurrentprice(currentPrice);

		Product entity = productRepository.save(productDetails);

		return entity;

	}

	public ProductEntity fetchRecordFromProductTable(Integer productId, ResponseEntity<RedSky> redSky)
			throws NoContentException {

		ProductEntity productEntity = new ProductEntity();

		Optional<Product> productDetails = productRepository.findById(productId);
		if (productDetails.isPresent()) {

			productEntity.setProductId(productDetails.get().getProductId());
			productEntity.setName(redSky.getBody().getName());
			CurrentPrice price = new CurrentPrice();
			price.setValue(productDetails.get().getCurrentprice().getValue());
			price.setCurrencyCode(productDetails.get().getCurrentprice().getCurrencyCode());
			productEntity.setCurrentprice(price);
			return productEntity;

		} else {
			throw new NoContentException(HttpStatus.NO_CONTENT);
		}

	}

}