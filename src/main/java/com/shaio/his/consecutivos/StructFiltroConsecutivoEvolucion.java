package com.shaio.his.consecutivos;

public class StructFiltroConsecutivoEvolucion {
	private int ingreso;
	private int estado;
	private String seccion;
	private String cama;
	private String usuario;
	private String programa;
	
	
	public int getIngreso() {
		return ingreso;
	}
	public int getEstado() {
		return estado;
	}
	
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public void setIngreso(int ingreso) {
		this.ingreso = ingreso;
	}


	public String getSeccion() {
		return seccion;
	}
	public void setSeccion(String seccion) {
		this.seccion = seccion;
	}
	public String getCama() {
		return cama;
	}
	public void setCama(String cama) {
		this.cama = cama;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getPrograma() {
		return programa;
	}
	public void setPrograma(String programa) {
		this.programa = programa;
	}

	
}
