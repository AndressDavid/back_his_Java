package com.shaio.his.listadoshc;

public class struct_finalidades {
	private String codigo = "";
	private String descripcion = "";
	private String tipo = "";
	private String genero = "";
	private long edaddesde = 0;
	private long edadhasta = 0;
	
	public String getCodFinalidad() {
		return codigo;
	}
	public void setCodFinalidad(String codigo) {
		this.codigo = codigo;
	}
	public String getDesFinalidad() {
		return descripcion;
	}
	public void setDesFinalidad(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getTipoFinalidad() {
		return tipo;
	}
	public void setTipoFinalidad(String tipo) {
		this.tipo = tipo;
	}
	
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	
	public long getEdadDesde() {
		return edaddesde;
	}
	public void setEdadDesde(long edaddesde) {
		this.edaddesde = edaddesde;
	}
	
	public long getEdadHasta() {
		return edadhasta;
	}
	public void setEdadHasta(long edadhasta) {
		this.edadhasta = edadhasta;
	}
	
}