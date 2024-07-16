package com.shaio.his.common;

public class StructFiltroDiagnostico {
	private String sexRips ="0";
	private String codigo;
	private String descripcion;
	private String tipmae;
	private String cl1tma;
	private String tipoFiltro = "like";
	
	
	public String getSexRips() {
		return sexRips;
	}
	public void setSexRips(String sexRips) {
		this.sexRips = sexRips;
	}
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	public String getTipmae() {
		return tipmae;
	}
	public void setTipmae(String tipmae) {
		this.tipmae = tipmae;
	}
	
	public String getCl1tma() {
		return cl1tma;
	}
	public void setCl1tma(String cl1tma) {
		this.cl1tma = cl1tma;
	}
	
	public String getTipoFiltro() {
		return tipoFiltro;
	}
	public void setTipoFiltro(String tipoFiltro) {
		this.tipoFiltro = tipoFiltro;
	}
	
}
