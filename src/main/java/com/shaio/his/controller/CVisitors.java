package com.shaio.his.controller;

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

import com.shaio.his.token.Token;
import com.shaio.his.visitors.IVisitors;
import com.shaio.his.visitors.ResponseSummary;
import com.shaio.his.visitors.ResponseVisitors;
import com.shaio.his.visitors.StructAdmissionUser;

@RestController
@RequestMapping("visitors")
public class CVisitors {

	@Autowired
	private IVisitors visitor;

	
	/*
	 * Method: searchVisitorByPatient
	 * Description: Metodo encargado de buscar los acompañantes o visitantes de un paciente
	 * Create Date: 10/10/2023
	 * @author: Joan Oliveros
	 * **/
	@CrossOrigin(origins = "*")
	@GetMapping(path = "/searchVisitorByPatient")
	public ResponseVisitors searchVisitorByPatient(
			@RequestParam(name = "typeDocumentPatient")  String typeDocumentPatient,
			@RequestParam(name = "documentPatient") String documentPatient,			
			@RequestParam(name = "token", defaultValue = "") String token) {

		if (!new Token().validar(token)) {
			throw new ResponseStatusException(
					  HttpStatus.FORBIDDEN, "token error!"
					);
		} 
		return visitor.searchVisitorsByPatient(typeDocumentPatient, documentPatient);
	}
	
	
	/*
	 * Method: getSummaryByDate
	 * Description: Metodo encargado de traer la cantidad de visitantes activos o egresados de la clinica, y listarlos por sección 
	 * Create Date: 18/10/2023
	 * @author: Joan Oliveros
	 * **/
	@CrossOrigin(origins = "*")
	@GetMapping(path = "/getSummaryByDate")
	public ResponseSummary getSummaryByDate(
			@RequestParam(name = "token", defaultValue = "") String token
			) {
		if (!new Token().validar(token)) {
			throw new ResponseStatusException(
					  HttpStatus.FORBIDDEN, "token error!"
					);
		} 
		return visitor.getSummaryByDate();
	}
	
	
	/*
	 * Method: managmentVisitorByPatient
	 * Description: Metodo encargado de realizar la gestion de un visitante, ya sea crear el ingreso, o actualizar el estado del mismo 
	 * Create Date: 18/10/2023
	 * @author: Joan Oliveros
	 * **/
	@CrossOrigin(origins = "*")
	@PostMapping(value = "/managmentVisitorByPatient")
	public ResponseVisitors managmentVisitorByPatient(
			@RequestBody StructAdmissionUser dataVisitor, 
			@RequestParam(name = "user") String user,
			@RequestParam(name = "program") String program,
			@RequestParam(name = "typeDocumentPatient")  String typeDocumentPatient,
			@RequestParam(name = "documentPatient") String documentPatient,
			@RequestParam(name = "token", defaultValue = "") String token
			) {
	if (!new Token().validar(token)) {
			throw new ResponseStatusException(
					  HttpStatus.FORBIDDEN, "token error!"
					);
		} 
		return visitor.managmentVisitorByPatient(typeDocumentPatient, documentPatient, dataVisitor, user, program);
	}

}
