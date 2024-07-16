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

import com.shaio.his.ResponseJson;
import com.shaio.his.Cirugia.IControlCirugia;
import com.shaio.his.Cirugia.ResponseJsonGuardadoCirugia;
import com.shaio.his.Cirugia.ResponseListaCirugia;
import com.shaio.his.Cirugia.StructFiltroConsumoSala;
import com.shaio.his.Cirugia.StructFiltroMiPres;
import com.shaio.his.Cirugia.StructFiltroValidarProcedimiento;
import com.shaio.his.Cirugia.StructGuardarControlQuirurgico;
import com.shaio.his.Cirugia.StructResponseMiPres;
import com.shaio.his.Cirugia.StructValidarProcedimientos;
import com.shaio.his.Cirugia.StructuraBusqueda;
import com.shaio.his.token.Token;

@RestController
@RequestMapping("control-cirugia")
public class CControlCirugia {
	
	@Autowired
	private IControlCirugia iDescripcionCirugia;
	
	@CrossOrigin(origins = "*")
	@PostMapping(path = "/getCigurias")
	public ResponseListaCirugia extraerListaCirugias(
			@RequestBody StructuraBusqueda datosBusqueda,
			@RequestHeader("Authorization") String authorizationHeader	
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
		try {
			
			ResponseListaCirugia response = iDescripcionCirugia.extraerListaCirugiasPaciente(datosBusqueda);

			return response;
			
		} catch (Exception e) {
			throw new ResponseStatusException(
					  HttpStatus.BAD_REQUEST, e.toString());
		}

	}
	
	
	@CrossOrigin(origins = "*")
	@PostMapping(path = "/getConsumoSalas")
	public ResponseListaCirugia extraerConsumoSalas(
			@RequestBody StructFiltroConsumoSala datosBusqueda,
			@RequestHeader("Authorization") String authorizationHeader	
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

		return iDescripcionCirugia.extaerConsumoSalaCirugia(datosBusqueda);
	}
		
	
	@CrossOrigin(origins = "*")
	@PostMapping(path = "/guardarControlQuirurgico")
	public ResponseJsonGuardadoCirugia guardarControlQuirurgico(
			@RequestHeader("Authorization") String authorizationHeader,	
			@RequestBody StructGuardarControlQuirurgico body
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
		
		
		
		return iDescripcionCirugia.guardarControlQuirurgico(body);
	}
	
	
	@CrossOrigin(origins = "*")
	@PostMapping(path = "/validarProcedimiento")
	public StructValidarProcedimientos validarProcedimiento(
			@RequestHeader("Authorization") String authorizationHeader,
			@RequestBody StructFiltroValidarProcedimiento body
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
		
		return iDescripcionCirugia.validarProcedimientos(body);
	}
	
	
	@CrossOrigin(origins = "*")
	@PostMapping(path = "/obtenerMiPres")
	public StructResponseMiPres obtenerMiPres(
			@RequestHeader("Authorization") String authorizationHeader,
			@RequestBody StructFiltroMiPres body
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
		
		return iDescripcionCirugia.datosMipres(body);
	}
	
}
