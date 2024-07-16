package com.shaio.his.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.shaio.his.BusPaciente.IBusPaciente;
import com.shaio.his.BusPaciente.struct_paciente;
import com.shaio.his.BusPaciente.struct_revista;
import com.shaio.his.revistaMedica.IRevistaMedica;
import com.shaio.his.token.Token;

@RestController
@RequestMapping("revistaM")
public class CRevistaMedica {

	
	@Autowired
	private IRevistaMedica revistaMedica;
	
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/ultimoDX")	
	public struct_revista ultimoDX(
			@RequestParam(name = "token", defaultValue = "") String token,
			@RequestParam(name = "idingreso") long idingreso,
			@RequestParam(name = "usuario") String usuario	
			) {	
			
		if (!new Token().validar(token)) {
			throw new ResponseStatusException(
					  HttpStatus.FORBIDDEN, "token error!"
					);
		}   		
		return revistaMedica.ultimoDx(idingreso,usuario);					
	}
	
	
	@CrossOrigin(origins = "*")
	@PostMapping(value = "/save")
	public void save(
			@RequestParam(name = "token", defaultValue = "") String token,
			@RequestBody String Jsondatos		
			) {	
			
		if (!new Token().validar(token)) {
			throw new ResponseStatusException(
					  HttpStatus.FORBIDDEN, "token error!"
					);
		}   		
		revistaMedica.saveData(Jsondatos);					
	}
	
	
	
	
	
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/list")	
	public List<struct_revista> listar(
			@RequestParam(name = "token", defaultValue = "") String token,
			@RequestParam(name = "usuario", defaultValue = "") String usuario,
			@RequestParam(name = "numeroDocumento", defaultValue = "") long numeroDocumento
			) {	
			
		if (!new Token().validar(token)) {
			throw new ResponseStatusException(
					  HttpStatus.FORBIDDEN, "token error!"
					);
		}   		
		return revistaMedica.listar(usuario,numeroDocumento);					
	}
	
	
}
