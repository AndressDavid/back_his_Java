package com.shaio.his.Cirugia;

import com.shaio.his.ResponseJson;

public class ResponseJsonGuardadoCirugia extends ResponseJson{
	private String consecCita ="";
	private String consecCons ="";
	private String fechaHora ="";
	
	public String getConsecCita() {
		return consecCita;
	}
	public void setConsecCita(String consecCita) {
		this.consecCita = consecCita;
	}
	
	public String getConsecCons() {
		return consecCons;
	}
	public void setConsecCons(String consecCons) {
		this.consecCons = consecCons;
	}
	
	public String getFechaHora() {
		return fechaHora;
	}
	public void setFechaHora(String fechaHora) {
		this.fechaHora = fechaHora;
	}
	
	
	
}
