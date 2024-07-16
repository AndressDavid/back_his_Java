package com.shaio.his.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.shaio.his.cobro.ICobro;
import com.shaio.his.cobro.ResponseCobrarPerioperatorio;
import com.shaio.his.cobro.StructCobrarPerioperatorio;
import com.shaio.his.token.Token;


@RestController
@RequestMapping("cobros")
public class CCobro {
	
	@Autowired
	private ICobro cobros;
	
	@CrossOrigin(origins = "*")
	@PostMapping(value = "/cobracups")
	public ResponseCobrarPerioperatorio CobrarPerioperatorio(
			@RequestHeader("Authorization") String authorizationheader,
			@RequestBody StructCobrarPerioperatorio cJsonData
			) {				
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
		return cobros.CobrarPerioperatorio(cJsonData);					
	}
	

	
}
