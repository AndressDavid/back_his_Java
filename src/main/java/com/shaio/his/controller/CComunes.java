package com.shaio.his.controller;

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


import com.shaio.his.common.IComun;
import com.shaio.his.common.StructFiltroDiagnostico;
import com.shaio.his.common.StructFiltroEspecialidadMedicos;
import com.shaio.his.common.StructFiltroEspecialidades;
import com.shaio.his.common.StructFiltroMedicamentos;
import com.shaio.his.common.StructFiltroMedicoXespecialidad;
import com.shaio.his.common.StructResponseCommon;
import com.shaio.his.common.StructSalas;
import com.shaio.his.common.StructFiltrosTipoProfesional;
import com.shaio.his.token.Token;

@RestController
@RequestMapping("comunes")
public class CComunes {
	
	@Autowired
	private IComun iComun;
	
	
	@CrossOrigin(origins = "*")
	@PostMapping(path = "/especialidades")
	public StructResponseCommon extraerEspecialidad(
		@RequestHeader("Authorization") String authorizationHeader,
		@RequestBody StructFiltroEspecialidades datosFiltro
	) {
		
		if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {

			throw new ResponseStatusException(                

			HttpStatus.UNAUTHORIZED, "Falta el token de autenticación o no está en el formato correcto!"

			);

			}
		String token = authorizationHeader.substring(7);
		
		if (!new Token().validar(token)) {
			throw new ResponseStatusException(
					  HttpStatus.FORBIDDEN, "token error!"
			);
		}
		
		return iComun.extraerComunesEspecialidades(datosFiltro);
		
	}
	
	
	@CrossOrigin(origins = "*")
	@PostMapping(path = "/salas")
	public StructResponseCommon extaerSalas(
		@RequestHeader("Authorization") String authorizationHeader,
		@RequestParam(name = "tipo", defaultValue = "") String tipoSala
	) {
		
		if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {

			throw new ResponseStatusException(                

			HttpStatus.UNAUTHORIZED, "Falta el token de autenticación o no está en el formato correcto!"

			);

			}
		String token = authorizationHeader.substring(7);
		
		if (!new Token().validar(token)) {
			throw new ResponseStatusException(
					  HttpStatus.FORBIDDEN, "token error!"
			);
		}
		
		return iComun.extraerSalas(tipoSala);
		
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping(path = "/otrasSalas")
	public StructResponseCommon extaerOtrasSalas(
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
		
		return iComun.extraerOtrasSalas();
		
	}
	
	
	@CrossOrigin(origins = "*")
	@PostMapping(path = "/tipoCirugia")
	public StructResponseCommon extaertipoCirugia(
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
		
		return iComun.extaerTipoCirugia();
		
	}
	
	
	@CrossOrigin(origins = "*")
	@PostMapping(path = "/ListProfesional")
	public StructResponseCommon extaerListProfesional(
		@RequestHeader("Authorization") String authorizationHeader,
		@RequestBody StructFiltrosTipoProfesional filtros
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
		
		return iComun.extaerListProfesional(filtros);
		
	}
	
	
	@CrossOrigin(origins = "*")
	@PostMapping(path = "/tipoAnestesia")
	public StructResponseCommon extaertipoAnestesia(
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
		
		return iComun.extaerTipoAnestesia();
		
	}
	
	
	
	@CrossOrigin(origins = "*")
	@PostMapping(path = "/diagnosticos")
	public StructResponseCommon extaerDiagnosticos(
		@RequestHeader("Authorization") String authorizationHeader,
		@RequestBody StructFiltroDiagnostico body
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
		
		return iComun.extaerDiagnostico(body);
		
	}
	
	
	@CrossOrigin(origins = "*")
	@PostMapping(path = "/especialidadesMedicos")
	public StructResponseCommon EspecialidadesMedicos(
		@RequestHeader("Authorization") String authorizationHeader,
		@RequestBody StructFiltroEspecialidadMedicos body
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
		
		return iComun.extraerEspecialidadesMedicos(body);
		
	}
	
	
	@CrossOrigin(origins = "*")
	@PostMapping(path = "/medicosXespecialidades")
	public StructResponseCommon medicosXespecialidades(
		@RequestHeader("Authorization") String authorizationHeader,
		@RequestBody StructFiltroMedicoXespecialidad body
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
		
		return iComun.extaerMedicosXespecialidades(body);
		
	}
	
	
	@CrossOrigin(origins = "*")
	@PostMapping(path = "/listaPatologias")
	public StructResponseCommon listaPatologias(
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
		
		return iComun.extraerCupsPatologias();
		
	}
	
	
	@CrossOrigin(origins = "*")
	@PostMapping(path = "/listaMedicamentos")
	public StructResponseCommon listaMedicamentos(
		@RequestHeader("Authorization") String authorizationHeader,
		@RequestBody StructFiltroMedicamentos body
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
		
		return iComun.extraerlistaMedicamentos(body);
		
	}
	

}
