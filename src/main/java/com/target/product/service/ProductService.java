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

	public ProductEntity saveIntoProductTable(Product product, ResponseEntity<RedSky> redSky) {

		ProductEntity entity = new ProductEntity();
		entity.setProductId(product.getProductId());
		entity.setName(redSky.getBody().getName());
		CurrentPrice price = new CurrentPrice();
		price.setValue(product.getCurrentprice().getValue());
		price.setCurrencyCode(product.getCurrentprice().getCurrencyCode());
		entity.setCurrentprice(price);

		return productRepository.save(entity);

	}

	public ProductEntity fetchRecordFromProductTable(Integer id) throws NoContentException {
		Optional<ProductEntity> entity = productRepository.findById(id);
		if (!entity.isPresent()) {
			throw new NoContentException(HttpStatus.NO_CONTENT);
		}
		return entity.get();

	}

	public ResponseEntity<RedSky> getRedSkyDetails(Product product) throws NoContentException {

		RestTemplate restTemplate = new RestTemplate();
		String fooResourceUrl = redSkyUrl + product.getProductId();
		ResponseEntity<RedSky> response = restTemplate.getForEntity(fooResourceUrl, RedSky.class);

		return response;

	}

	public ProductEntity updateRecordIntoProductTable(ProductEntity productEntity) {

		ProductEntity productData = new ProductEntity();
		productData.setProductId(productEntity.getProductId());
		productData.setName(productEntity.getName());
		CurrentPrice currentPrice = new CurrentPrice();
		currentPrice.setValue(productEntity.getCurrentprice().getValue());
		currentPrice.setCurrencyCode(productEntity.getCurrentprice().getCurrencyCode());
		productData.setCurrentprice(currentPrice);

		ProductEntity entity = productRepository.save(productData);

		return entity;

	}

}