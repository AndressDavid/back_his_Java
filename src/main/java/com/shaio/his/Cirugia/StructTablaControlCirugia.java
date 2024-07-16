package com.shaio.his.Cirugia;

public class StructTablaControlCirugia {
	private String nombres;
	private String apellidos;
	private String fecha;
	private int hora;
	private String tipoAtencion; //
	private String via;
	private String cama; 
	private String estado; //
	private String especialidad;
	private int cita; // 
	
	private String FechaIngreso;
	private int ingreso;
	
	private String documento;
	private String tipoDocumento;
	
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	
	public int getHora() {
		return hora;
	}
	public void setHora(int hora) {
		this.hora = hora;
	}
	
	public String getTipoAtencion() {
		return tipoAtencion;
	}
	public void setTipoAtencion(String tipoAtencion) {
		this.tipoAtencion = tipoAtencion;
	}
	
	public String getVia() {
		return via;
	}
	public void setVia(String via) {
		this.via = via;
	}
	
	public String getCama() {
		return cama;
	}
	public void setCama(String cama) {
		this.cama = cama;
	}
	
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public String getEspecialidad() {
		return especialidad;
	}
	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}
	
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	
	public int getCita() {
		return cita;
	}
	public void setCita(int cita) {
		this.cita = cita;
	}
	
	
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	
	public String getFechaIngreso() {
		return FechaIngreso;
	}
	public void setFechaIngreso(String fechaIngreso) {
		FechaIngreso = fechaIngreso;
	}
	public int getIngreso() {
		return ingreso;
	}
	public void setIngreso(int ingreso) {
		this.ingreso = ingreso;
	}
	
	
}
