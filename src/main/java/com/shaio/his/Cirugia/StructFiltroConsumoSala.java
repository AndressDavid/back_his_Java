package com.shaio.his.Cirugia;

public class StructFiltroConsumoSala {
	private String ingreso;
	private String sala;
	private String tipoconsumo = "500";
	private int fechainicio;
	private int horainicio;
	private int fechafinal;
	private int horafinal;
	
	public String getIngreso() {
		return ingreso;
	}
	public void setIngreso(String ingreso) {
		this.ingreso = ingreso;
	}
	
	public String getSala() {
		return sala;
	}
	public void setSala(String sala) {
		this.sala = sala;
	}

	
	public int getFechainicio() {
		return fechainicio;
	}
	public void setFechainicio(int fechainicio) {
		this.fechainicio = fechainicio;
	}
	
	public int getHorainicio() {
		return horainicio;
	}
	public void setHorainicio(int horainicio) {
		this.horainicio = horainicio;
	}
	
	public int getFechafinal() {
		return fechafinal;
	}
	public void setFechafinal(int fechafinal) {
		this.fechafinal = fechafinal;
	}
	
	public int getHorafinal() {
		return horafinal;
	}
	public void setHorafinal(int horafinal) {
		this.horafinal = horafinal;
	}
	
	public String getTipoconsumo() {
		return tipoconsumo;
	}
	public void setTipoconsumo(String tipoconsumo) {
		this.tipoconsumo = tipoconsumo;
	}
	
	
	
}	
