package com.shaio.his.listadoshc;

public class struct_diagnostico {
	private String codigo = "";
	private String descripcion = "";
	private String genero = "";
	private String edadminima = "";
	private String edadmaxima = "";
	
	public String getCodCie() {
		return codigo;
	}
	public void setCodCie(String codigo) {
		this.codigo = codigo;
	}
	public String getDesCie() {
		return descripcion;
	}
	public void setDesCie(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getGeneroCie() {
		return genero;
	}
	public void setGeneroCie(String genero) {
		this.genero = genero;
	}
	
	public String getEdadMinimaCie() {
		return edadminima;
	}
	public void setEdadMinimaCie(String edadminima) {
		this.edadminima = edadminima;
	}
	
	public String getEdadMaximaCie() {
		return edadmaxima;
	}
	public void setEdadMaximaCie(String edadmaxima) {
		this.edadmaxima = edadmaxima;
	}
	
}
