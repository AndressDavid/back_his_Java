package com.shaio.his.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.shaio.his.listadoshc.IListadoshc;
import com.shaio.his.listadoshc.ResponseFinalidades;
import com.shaio.his.listadoshc.ResponseTipoDiagnostico;
import com.shaio.his.listadoshc.ResponseProfesionalesSalud;
import com.shaio.his.listadoshc.ResponseDiagnosticos;
import com.shaio.his.token.Token;

@RestController
@RequestMapping("listadoshc")
public class CListadosHC {
	
	@Autowired
	private IListadoshc listadoshc;
	
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/tipodiagnostico")
	public ResponseTipoDiagnostico ListarTipoDiagnostico(
			@RequestHeader("Authorization") String authorizationheader		
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
		return listadoshc.ListarTipoDiagnostico();					
	}
	

	@CrossOrigin(origins = "*")
	@PostMapping(value = "/finalidades")
	public ResponseFinalidades ListarFinalidades(
			
			@RequestBody Map<String, String> requestdata, @RequestHeader("Authorization") String authorizationheader	
			) {
		String tipofin=requestdata.get("tipofin");
		String genero=requestdata.get("genero");
				
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
		
		return listadoshc.ListarFinalidades(tipofin, genero);

	}
	
	
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/profesionalessalud")
	public ResponseProfesionalesSalud ListarProfesionalesSalud(
			@RequestHeader("Authorization") String authorizationheader		
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
		return listadoshc.ListarProfesionalesSalud();
	}
	
	
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/diagnosticos")
	public ResponseDiagnosticos ListadoDiagnosticos(
			@RequestHeader("Authorization") String authorizationheader		
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
		return listadoshc.ListadoDiagnosticos();
	}
	
	
	
	
}
