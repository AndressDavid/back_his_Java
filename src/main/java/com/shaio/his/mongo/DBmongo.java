package com.shaio.his.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DBmongo implements AutoCloseable{
	
	private String uri = "mongodb://dba:Shaio2023!@172.20.10.37:27017/?authSource=shaio_auth";
	private MongoDatabase shaioDB;
	private com.mongodb.client.MongoClient mongoClient;
	
	
	@Autowired
	private Environment env;
	
	public DBmongo () {
		

		String env_value = System.getenv("shaio.enviroment");
		
		if (env_value==null) {
			uri = "mongodb://172.20.10.49:27017/?authSource=shaio_auth";
		} else {
		switch(env_value) {
		   case "PRODUCCION" :
			   uri = "mongodb://dba:Shaio2023!@172.20.10.37:27017/?authSource=shaio_auth";
			break;
		   case "PRUEBAS" :
			   uri = "mongodb://172.20.10.49:27017/?authSource=shaio_auth";
			break;
			default : 
				uri = "mongodb://172.20.10.49:27017/?authSource=shaio_auth";
			break;
			}
		}

		//uri = env.getProperty("env.mongo");

		
		mongoClient=MongoClients.create(uri);
		shaioDB = mongoClient.getDatabase("shaio_auth");
		
	}

	public MongoDatabase getShaioDB() {
		return shaioDB;
	}

	public void setShaioDB(MongoDatabase shaioDB) {
		this.shaioDB = shaioDB;
	}

	
	public void close() {
		mongoClient.close();
	}



}
