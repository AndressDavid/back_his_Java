package com.shaio.his.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.shaio.his.BusPaciente.IIngreso;
import com.shaio.his.BusPaciente.ResponseIngreso;
import com.shaio.his.token.Token;



@RestController
@RequestMapping("ingreso")
public class CIngreso {

	
	@Autowired
	private IIngreso ingreso;
	
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/validar")
	public ResponseIngreso buscarIngreso(
			@RequestParam(name = "ingreso") long idingreso, @RequestHeader("Authorization") String authorizationheader		
	){				
		if (authorizationheader==null || !authorizationheader.startsWith("Bearer ")) {
			throw new ResponseStatusException(
					HttpStatus.UNAUTHORIZED, "falta el token de autorización");
		}
		
		String token=authorizationheader.substring(7); 
		
		if (!new Token().validar(token)) {
			throw new ResponseStatusException(
					  HttpStatus.FORBIDDEN, "token error!"
					);
		}   		
		return ingreso.buscarIngreso(idingreso);					
	}
	
	
}
