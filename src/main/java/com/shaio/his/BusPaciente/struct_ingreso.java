package com.shaio.his.BusPaciente;

public class struct_ingreso {
	private long idingreso = 0;
	private String tipoIdentificacion = "";
	private long numeroIdentificacion = 0;
	private String estadoIngreso = "";
	private String planIngreso = "";
	
	public long getNumeroIngreso() {
		return idingreso;
	}
	public void setNumeroIngreso(long idingreso) {
		this.idingreso = idingreso;
	}
	
	public String getTipoIdentificacion() {
		return tipoIdentificacion;
	}
	public void setTipoIdentificacion(String tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}
	
	public long getNumeroIdentificacion() {
		return numeroIdentificacion;
	}
	public void setNumeroIdentificacion(long numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}
	
	public String getEstadoIngreso() {
		return estadoIngreso;
	}
	public void setEstadoIngreso(String estadoIngreso) {
		this.estadoIngreso = estadoIngreso;
	}
	
	public String getPlanIngreso() {
		return planIngreso;
	}
	public void setPlanIngreso(String planIngreso) {
		this.planIngreso = planIngreso;
	}
	  
}
