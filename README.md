# TargetProductService
Target Product MicroService



Download docker in MAC below link have the steps 
https://runnable.com/docker/install-docker-on-macos

Execute below commands to pull latest cassandra image 
sudo docker pull cassandra
The version of the downloaded Cassandra image can be checked with following command:
(dockerhost)$ sudo docker run -it --rm --name cassandra cassandra -v


sudo docker run -it --rm --name docker-cassandra -p7000:7000 -p7001:7001 -p9042:9042 -p9160:9160 cassandra:latest

sudo docker exec -it docker-cassandra bash

cqlsh 127.0.0.1 9042 -u cassandra -p cassandra


CREATE KEYSPACE product1
WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 3};

CREATE TABLE product1."product" ( productid varint, name varchar, price double, PRIMARY KEY (productid) );


CREATE TYPE product1."current_price"
(value double,
currencycode text);


CREATE TABLE product1."product_data"
(productid int,
name text,
currentprice current_price,
PRIMARY KEY (productid));

http://localhost:8083/swagger-ui.html
https://dzone.com/articles/spring-boot-restful-api-documentation-with-swagger
http://localhost:8083/v2/api-docs

Run Below sequence 
1. RedSky Microservice
2.Target Registry 
3.Target API Gateway
4.Target Product MicroService


Jacoco Tool been used to see the test coverage of test cases 
it can be seen in the below folder
/target/site/index.html --> open index.html with browser 
















--------------------------------------------------------------------------------------
// If third party api redsky runs than only we can test below code

		
		  for (int i = 0; i < productList.size() && i < redSkyList.size(); i++) {
		  Product product = productList.get(i); ResponseEntity<RedSky> redSky = new
		  ResponseEntity<>(redSkyList.get(i), HttpStatus.OK);
		  
		  
		  mockMvc.perform(post("/product/save")
		  .contentType(MediaType.APPLICATION_JSON) .content(asJsonString(product)))
		  .andExpect(status().isCreated());
		  
		  verify(productService, times(1)).saveIntoProductTable(product, redSky);
		  verifyNoMoreInteractions(productService); }
		 /
