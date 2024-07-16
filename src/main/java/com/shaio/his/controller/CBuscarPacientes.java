package com.shaio.his.controller;

import java.util.List;
//import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.shaio.his.BusPaciente.IBusPaciente;
import com.shaio.his.BusPaciente.ResponseCitaPaciente;
import com.shaio.his.BusPaciente.ResponseHistoryPatient;
import com.shaio.his.BusPaciente.ResponseListarPaciente;
import com.shaio.his.BusPaciente.struct_paciente;
//import com.shaio.his.BusPaciente.struct_paciente_filter;
import com.shaio.his.pagination.StructPagination;
import com.shaio.his.token.Token;



@RestController
@RequestMapping("paciente")
public class CBuscarPacientes {

	
	@Autowired
	private IBusPaciente buscarPaciente;

	
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/listarActivos")
	public ResponseListarPaciente ListarPacientesActivos(
			@RequestParam(name = "token", defaultValue = "") String token				
			) {				
		if (!new Token().validar(token)) {
			throw new ResponseStatusException(
					  HttpStatus.FORBIDDEN, "token error!"
					);
		}   		
		return buscarPaciente.listadoPacientesActivos();					
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping(value = "/filtrarActivos")
	public ResponseListarPaciente FiltrarPacientesActivos(
			@RequestParam(name = "token") String token,
			@RequestBody StructPagination bodyfilter 
			) {				
		if (!new Token().validar(token)) {
			throw new ResponseStatusException(
					  HttpStatus.FORBIDDEN, "token error!"
		);
		}   	
		
		return buscarPaciente.filtradoPacientesActivos(bodyfilter);					
	}
	

	@CrossOrigin(origins = "*")
	@GetMapping(value = "/find")
	public ResponseListarPaciente buscarPaciente(
			@RequestParam(name = "token", defaultValue = "") String token,
			@RequestParam(name = "numero_ingreso", defaultValue = "") String numero_ingreso,
			@RequestParam(name = "primer_nombre", defaultValue = "") String primer_nombre,
			@RequestParam(name = "segundo_nombre", defaultValue = "") String segundo_nombre,
			@RequestParam(name = "primer_apellido", defaultValue = "") String primer_apellido,
			@RequestParam(name = "segundo_apellido", defaultValue = "") String segundo_apellido,
			@RequestParam(name = "tipo_documento", defaultValue = "") String tipo_documento	,
			@RequestParam(name = "documento", defaultValue = "") String documento,
			@RequestParam(name = "en_habitacion", required = false) boolean en_habitacion
			) {				
		if (!new Token().validar(token)) {
			throw new ResponseStatusException(
					  HttpStatus.FORBIDDEN, "token error!"
					);
		}   		
		return buscarPaciente.buscarPaciente( numero_ingreso, primer_nombre, segundo_nombre, primer_apellido, segundo_apellido,tipo_documento, documento, en_habitacion );					
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/censoHabitaciones")
	public List<struct_paciente> buscarPaciente(
			@RequestParam(name = "token", defaultValue = "") String token					
			) {	
			
		if (!new Token().validar(token)) {
			throw new ResponseStatusException(
					  HttpStatus.FORBIDDEN, "token error!"
					);
		}   		
		return buscarPaciente.censoHabitaciones();					
	}
	

	
	/*
	 * Method: getPatientHistory
	 * Description: Metodo encargado de obtener los antecedentes de un paciente  
	 * Create Date: 18/09/2023
	 * @author: Joan Oliveros
	 * **/
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/getPatientHistory")
	public ResponseHistoryPatient getPatientHistory(
			@RequestParam(name = "token", defaultValue = "") String token,
			@RequestParam(name = "addmissionNumber", defaultValue = "") String addmissionNumber, 
			@RequestParam(name = "typeDocument", defaultValue =  "") String typeDocument,
			@RequestParam(name = "document", defaultValue =  "") String document
			
			) {	
			
	
		return buscarPaciente.getPatientHistory(addmissionNumber, typeDocument,  document);					
	}	
	
	
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/consecutivocita")
	public ResponseCitaPaciente consecutivoCitaPaciente(
			@RequestHeader("Authorization") String authorizationheader,
			@RequestParam(name = "typeDocument", defaultValue = "") String tipo_documento,
			@RequestParam(name = "document", defaultValue = "") String documento
			) {				
			String token=authorizationheader.substring(7); 
		
			if (!new Token().validar(token)) {
				throw new ResponseStatusException(
						  HttpStatus.FORBIDDEN, "token error!"
						);
			}     		
		return buscarPaciente.consecutivoCitaPaciente( tipo_documento, documento );					
	}
	
}
