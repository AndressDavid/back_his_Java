package com.shaio.his.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shaio.his.habitaciones.IHabitaciones;
import com.shaio.his.habitaciones.ResponseHab;
import com.shaio.his.habitaciones.ResponseDatosHab;
import com.shaio.his.habitaciones.ResponseGuardar;
import com.shaio.his.habitaciones.Struct_guardar;

	

@RestController
@RequestMapping("hab")
public class CHabitacionInteligente {

	
	@Autowired
	public IHabitaciones hab;
	
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/lista")
	public ResponseHab listarHab(
			@RequestParam(name = "filter", defaultValue = "") String filter,
			@RequestHeader("Authorization") String authorizationHeader) {
		
		return hab.ListarHab(filter);					
	}
	
	
	
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/tabla")
	public ResponseDatosHab listarDatosHab(
			@RequestParam(name = "seccion", defaultValue = "") String seccion,
			@RequestParam(name = "cama", defaultValue = "") String cama,
			@RequestParam(name = "activo", defaultValue = "-1") float activo,
			@RequestHeader("Authorization") String authorizationHeader) {
		
		return hab.ListarDatosHab(seccion, cama, activo);					
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping(value = "/guardarDatosHabitaciones")
    public ResponseGuardar guardarDatosHab(
            @RequestBody Struct_guardar requestBody,
            @RequestHeader("Authorization") String authorizationHeader) {
        
        System.out.println("habita: "+requestBody.getHabitacion());
        
        ResponseGuardar resultado = hab.guardarDatosHabitaciones(requestBody);
        
        return (ResponseGuardar) resultado;
    }
	
	
}
