package com.shaio.his.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.shaio.his.dietas.IDietas;
import com.shaio.his.dietas.ResponseDietas;
import com.shaio.his.token.Token;



@RestController
@RequestMapping("dietas")
public class CDietas {

	
	@Autowired
	private IDietas dietas;
	
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/list")
	public ResponseDietas listarDietas(
			@RequestParam(name = "token", defaultValue = "") String token
					
			) {				
		if (!new Token().validar(token)) {
			throw new ResponseStatusException(
					  HttpStatus.FORBIDDEN, "token error!"
					);
		}   
		
		
		return dietas.ListarDietas();					
	}
	
	
}
