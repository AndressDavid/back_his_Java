package com.shaio.his.controller;

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

import com.shaio.his.anestesiaperioperatorio.IAnestesiaPerioperatorio;
import com.shaio.his.anestesiaperioperatorio.ResponseAnestesiaGuardar;
import com.shaio.his.anestesiaperioperatorio.ResponseAnestesiaPerioperatorio;
import com.shaio.his.anestesiaperioperatorio.ResponseAnestesiaValidar;
import com.shaio.his.anestesiaperioperatorio.StructAnestesiaguardar;
import com.shaio.his.anestesiaperioperatorio.StructAnestesiavalidar;
import com.shaio.his.token.Token;

@RestController
@RequestMapping("anestesiaperioperatorio")
public class CAnestesiaPerioperatorio {
	
	@Autowired
	private IAnestesiaPerioperatorio anestesiaperioperatorio;
	
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/list")
	public ResponseAnestesiaPerioperatorio ListarCupsAnestesia(
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
		return anestesiaperioperatorio.ListarCupsAnestesia();					
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping(value = "/validar")
	public ResponseAnestesiaValidar ValidarDatosAnestesia(
			@RequestHeader("Authorization") String authorizationheader,
			@RequestBody StructAnestesiavalidar cJsonData
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
		return anestesiaperioperatorio.ValidarDatosAnestesia(cJsonData);					
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping(value = "/guardar")
	public ResponseAnestesiaGuardar GuardarDatosAnestesia(
			@RequestHeader("Authorization") String authorizationheader,
			@RequestBody StructAnestesiaguardar cJsonData
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
		return anestesiaperioperatorio.GuardarDatosAnestesia(cJsonData);					
	}
	
}
