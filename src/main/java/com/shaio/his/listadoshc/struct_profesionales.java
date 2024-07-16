package com.shaio.his.listadoshc;

public class struct_profesionales {
	private String registro = "";
	private String nombre = "";
	private String usuario = "";
	private String especialidad = "";
	private String descripcionespecialidad = "";
	private long tipousuario = 0;
	
	public String getCodRegistro() {
		return registro;
	}
	public void setCodRegistro(String registro) {
		this.registro = registro;
	}
	
	public String getDesRegistro() {
		return nombre;
	}
	public void setDesRegistro(String nombre) {
		this.nombre = nombre;
	}

	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getEspecialidad() {
		return especialidad;
	}
	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}
	
	public String getDescripcionEspecialidad() {
		return descripcionespecialidad;
	}
	public void setDescripcionEspecialidad(String descripcionespecialidad) {
		this.descripcionespecialidad = descripcionespecialidad;
	}
	
	public long getTipoProfesional() {
		return tipousuario;
	}
	public void setTipoProfesional(long tipousuario) {
		this.tipousuario = tipousuario;
	}
	
}