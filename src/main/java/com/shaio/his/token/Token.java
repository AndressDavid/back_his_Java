package com.shaio.his.token;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.json.JSONObject;

public class Token {
  
	
	
	public boolean validar(String token) {
		String env_value = System.getenv("shaio.enviroment");
		System.out.println(env_value);
		
		String strCon = "";
		
		if (env_value==null) {
			//strCon = "http://172.20.10.142:5080/shaio/user/token/validate?token="+token;
			strCon = "http://172.20.10.49:9080/shaio/user/token/validate?token="+token;
		}else {
			switch(env_value) {
			   case "PRODUCCION" :
				   strCon = "http://172.20.10.142:5080/shaio/user/token/validate?token="+token;
				   //strCon = "http://172.20.10.37:8080/shaio/user/token/validate?token="+token;
				break;
			   case "PRUEBAS" :
				   strCon = "http://172.20.10.49:9080/shaio/user/token/validate?token="+token;
				break;
			}

		}
		
		
	if (token.equals("")) return false;	
		System.out.println("Conectadose a "+strCon);
	 try { HttpClient client = HttpClient.newHttpClient();
	  HttpRequest getRequest = HttpRequest.newBuilder()
		      .uri(URI.create(strCon))
		      .build();
	  
	    HttpResponse<String> response;	
		response = client.send(getRequest, BodyHandlers.ofString());
		JSONObject obj = new JSONObject(response.body());
		String msg = obj.getString("data");
		if (msg.equals("valido")) return true;
	  
	  } catch (IOException | InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 return false;
  }
	
	
}
