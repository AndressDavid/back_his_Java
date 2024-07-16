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

import com.shaio.his.ResponseJson;
import com.shaio.his.consentimientoInformado.IConsentimientoInformado;
import com.shaio.his.consentimientoInformado.ResponseDocumentExist;
import com.shaio.his.consentimientoInformado.ResponseDocumento;
import com.shaio.his.consentimientoInformado.ResponseListConsentimientosPac;
import com.shaio.his.consentimientoInformado.ResponseListPlantillas;
import com.shaio.his.token.Token;



@RestController
@RequestMapping("consentimiento")
public class CConsetimientoInformado {

	
	@Autowired
	private IConsentimientoInformado consentimiento;
	
	
	
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/documentExist")
	public ResponseDocumentExist documentExist(
			@RequestParam(name = "token", defaultValue = "") String token,
			@RequestParam(name = "idDocument", defaultValue = "") String idDocument
			) {	
				
		if (!new Token().validar(token)) {
			throw new ResponseStatusException(
					  HttpStatus.FORBIDDEN, "token error!"
					);
		}
		
		ResponseDocumentExist doc = new ResponseDocumentExist();		
		doc.setData(consentimiento.documentExist(idDocument));		
		return doc;
	}
	
	
	
	
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/listPlantillas")
	public ResponseListPlantillas ListarPlantillas(
			@RequestParam(name = "token", defaultValue = "") String token
			) {	
			
		if (!new Token().validar(token)) {
			throw new ResponseStatusException(
					  HttpStatus.FORBIDDEN, "token error!"					  
					);
		}   		
		return consentimiento.ListarPlantillas();					
	}
	
	
	@CrossOrigin(origins = "*")
	@PostMapping(value = "/setPlantillas")
	public ResponseJson SetPlantillas(
			@RequestParam(name = "token", defaultValue = "") String token, @RequestBody  String body
			) {
		if (!new Token().validar(token)) {
			throw new ResponseStatusException(
					  HttpStatus.FORBIDDEN, "token error!"					  
					);
			}
		
		consentimiento.setLplantillas(body);
		return new ResponseJson("",0);
	}
	
	
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/list")
	public ResponseListConsentimientosPac Listar(
			@RequestParam(name = "token", defaultValue = "") String token
			,@RequestParam(name = "nroIngreso",required = true) String nroIngreso	
			) {	
			
		if (!new Token().validar(token)) {
			throw new ResponseStatusException(
					  HttpStatus.FORBIDDEN, "token error!"					  
					);
		}   		
		return consentimiento.listConsentimientosPacientes(nroIngreso);					
	}
	
	
	
	
	@CrossOrigin(origins = "*")
	@PostMapping(value = "/save")
	public ResponseJson saveData(
			@RequestParam(name = "token", defaultValue = "") String token
			,@RequestBody  String body 			
			) {
	
		if (!new Token().validar(token)) {
			throw new ResponseStatusException(
					  HttpStatus.FORBIDDEN, "token error!"					  
					);
		}   		
		String r = consentimiento.saveData(body);
		if (r.equals(""))
			return new ResponseJson("",0);
		else
			return new ResponseJson(r,500);
	}
	
	
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/get")
	public ResponseDocumento getData(
			@RequestParam(name = "token", defaultValue = "") String token
			,@RequestParam(name = "idDocumento",required = true) String idDocumento	
			) {
	
		if (!new Token().validar(token)) {
			throw new ResponseStatusException(
					  HttpStatus.FORBIDDEN, "token error!"					  
					);
		}   		
		
		return consentimiento.getData(idDocumento);
	}
	
	
}
