package com.shaio.his.Cirugia;

public class StructuraBusqueda {
	
	private int ingreso =0;
	private String tipoDocumento ="";
	private String documento ="";
	private String via ="";
	private String especialidad ="";
	private String fecha = "";
	private boolean todas = false;
	
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	public boolean isTodas() {
		return todas;
	}
	public void setTodas(boolean todas) {
		this.todas = todas;
	}
	
	public int getIngreso() {
		return ingreso;
	}
	public void setIngreso(int ingreso) {
		this.ingreso = ingreso;
	}
	
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	
	public String getVia() {
		return via;
	}
	public void setVia(String via) {
		this.via = via;
	}
	
	public String getEspecialidad() {
		return especialidad;
	}
	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}
	
	
}
