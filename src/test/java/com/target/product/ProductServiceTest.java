package com.target.product;

//https://memorynotfound.com/unit-test-spring-mvc-rest-service-junit-mockito/
import org.cassandraunit.spring.CassandraDataSet;

import org.cassandraunit.spring.CassandraUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.target.product.constants.JsonConstants;
import com.target.product.controller.ProductController;
import com.target.product.entity.ProductEntity;
import com.target.product.model.Product;
import com.target.product.model.RedSky;
import com.target.product.service.ProductService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.reflect.Type;
import java.util.ArrayList;

import org.springframework.test.context.ContextConfiguration;

import static org.junit.Assert.assertEquals;

@RunWith(JUnitPlatform.class)
@SpringBootTest({ "spring.data.cassandra.port=9042", "spring.data.cassandra.keyspace-name=product1" })
@EnableAutoConfiguration
@ComponentScan
@ContextConfiguration
@CassandraDataSet(value = { "cassandra-init.sh" }, keyspace = "product1")
@CassandraUnit
public class ProductServiceTest {

	private MockMvc mockMvc;

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductController productController;
	private ProductService mockProductService = null;

	@BeforeEach
	public void init() {
		productController.setProductService(productService);

		this.mockMvc = MockMvcBuilders.standaloneSetup(productController).build();

	}

	@Test
	@DisplayName("saveProduct")
	public void saveProduct() throws Exception {

		ProductEntity productResult = null;

		String mockRedSkyJson = JsonConstants.mockRedSkyJson;
		String mockProductJson = JsonConstants.mockProductJson;

		Gson gson = new Gson();

		Type productListType = new TypeToken<ArrayList<Product>>() {
		}.getType();

		ArrayList<Product> productList = gson.fromJson(mockProductJson, productListType);

		Type redSkyListType = new TypeToken<ArrayList<RedSky>>() {
		}.getType();

		ArrayList<RedSky> redSkyList = gson.fromJson(mockRedSkyJson, redSkyListType);

		for (int i = 0; i < productList.size() && i < redSkyList.size(); i++) {

			ObjectMapper mapper = new ObjectMapper();
			Product product = productList.get(i);
			ResponseEntity<RedSky> redSky = new ResponseEntity<>(redSkyList.get(i), HttpStatus.OK);

			mockProductService = Mockito.mock(ProductService.class);

			productResult = productService.saveIntoProductTable(product, redSky);

			assertEquals(productResult.getProductId(), product.getProductId());
		}

	}

	@Test
	@DisplayName("getProductById")
	public void getProductById() throws Exception {

		String mockProductJson = JsonConstants.mockProductJson;
		Gson gson = new Gson();
		Type productListType = new TypeToken<ArrayList<Product>>() {
		}.getType();

		ArrayList<Product> productList = gson.fromJson(mockProductJson, productListType);

		mockProductService = Mockito.mock(ProductService.class);
		for (int i = 0; i < productList.size(); i++) {

			productService.fetchRecordFromProductTable(productList.get(i).getProductId());
			mockMvc.perform(get("/product/{id}", productList.get(i).getProductId())).andExpect(status().isOk())
					.andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(productList.get(i).getProductId()));

		}
	}

	@Test
	@DisplayName("getAllProducts")
	public void getAllProducts() throws Exception {
		String mockProductJson = JsonConstants.mockProductJson;
		Gson gson = new Gson();
		Type productListType = new TypeToken<ArrayList<Product>>() {
		}.getType();

		ArrayList<Product> productList = gson.fromJson(mockProductJson, productListType);

		mockProductService = Mockito.mock(ProductService.class);

		mockMvc.perform(get("/product/getAllProducts").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].productId").value(productList.get(0).getProductId()));

	}

	@Test
	public void updateProductAPI() throws Exception {

		String mockProductJson = JsonConstants.mockProductJson;

		Gson gson = new Gson();

		Type productListType = new TypeToken<ArrayList<ProductEntity>>() {
		}.getType();

		ArrayList<ProductEntity> productList = gson.fromJson(mockProductJson, productListType);

		mockProductService = Mockito.mock(ProductService.class);

		mockMvc.perform(MockMvcRequestBuilders.put("/product/{id}", "1").content(asJsonString(productList.get(0)))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

	}

	@Test

	public void deleteProductAPI() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/product/deleteProduct/{id}", "1"))
				.andExpect(status().isNoContent());
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}