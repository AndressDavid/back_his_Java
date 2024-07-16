package com.shaio.his.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.shaio.his.BusPaciente.IBusPaciente;
import com.shaio.his.BusPaciente.struct_paciente;
import com.shaio.his.cups.ICups;
import com.shaio.his.cups.ResponseCups;
import com.shaio.his.cups.StructFiltrosCupsQuirurgicos;
import com.shaio.his.cups.struct_cups;
import com.shaio.his.token.Token;



@RestController
@RequestMapping("cups")
public class CCups {

	
	@Autowired
	private ICups cups;
	
	
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/list")
	public ResponseCups listarCups(
			@RequestParam(name = "token", defaultValue = "") String token,
			@RequestParam(name = "filter", defaultValue = "") String filter
					
			) {				
		if (!new Token().validar(token)) {
			throw new ResponseStatusException(
					  HttpStatus.FORBIDDEN, "token error!"
					);
		}   		
		return cups.ListarCups(filter);					
	}
	
	
	@CrossOrigin(origins = "*")
	@PostMapping(value = "/listaProcedimientosQuirurgicos")
	public ResponseCups listaProcedimientos(
			@RequestHeader("Authorization") String authorizationHeader,	
			@RequestBody StructFiltrosCupsQuirurgicos filter
			) {		
		
		if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			throw new ResponseStatusException(                
			HttpStatus.UNAUTHORIZED, "Falta el token de autenticación o no está en el formato correcto!");

		}
		
		String token = authorizationHeader.substring(7);
		if (!new Token().validar(token)) {
			throw new ResponseStatusException(
					  HttpStatus.FORBIDDEN, "token error!"
					);
		}   		
		return cups.ListaCupsQuirurgicos(filter);					
	}
	
}
