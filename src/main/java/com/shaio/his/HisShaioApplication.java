package com.shaio.his;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import com.shaio.his.token.Token;


@SpringBootApplication
public class HisShaioApplication {

	public static void main(String[] args) {

		System.out.println("Version 20240320");
		String env_value = System.getenv("shaio.enviroment");
		System.out.println("Enviroment:"+env_value);
		
		

		System.setProperty("server.port", "8081");
		System.setProperty("server.servlet.context-path", "/shaio");
		SpringApplication.run(HisShaioApplication.class, args);
	}

}
