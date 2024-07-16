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

import com.shaio.his.nutricion.Inutricion;
import com.shaio.his.nutricion.ResponseNutricion;
import com.shaio.his.token.Token;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("nutricion")
public class CNutricion {
	
	@Autowired
	private Inutricion ListNutricion;
		
	@GetMapping(value = "/listarnutricion")
	
	public ResponseNutricion ListarPacientesNutricion(
			@RequestParam(name = "numeroingreso", defaultValue =  "") String numeroingreso,	
			@RequestParam(name = "Fecha") long Fecha,
			@RequestParam(name = "todas", defaultValue = "") String todas,
			@RequestParam(name = "seccion", defaultValue = "") String seccion,
			@RequestHeader("Authorization") String authorizationHeader) {
		
		if(authorizationHeader==null || !authorizationHeader.startsWith("Bearer ")) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Token Incorrecto");
		}
		
		String token = authorizationHeader.substring(7);
		
		if (!new Token().validar(token)) {
			throw new ResponseStatusException(
					  HttpStatus.FORBIDDEN, "token error!"
					);
		} 	
		
		return ListNutricion.ListarPacientesNutricion(numeroingreso, Fecha, todas, seccion);	
	}
	
	
	
	

}
