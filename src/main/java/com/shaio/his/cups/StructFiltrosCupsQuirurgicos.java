package com.shaio.his.cups;

public class StructFiltrosCupsQuirurgicos {
	private String sexo;
	private boolean exacto = false; 
	private String programa = "";
	private String codigo;
	private String descripcion;

	
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

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public boolean isExacto() {
		return exacto;
	}

	public void setExacto(boolean exacto) {
		this.exacto = exacto;
	}

	public String getPrograma() {
		return programa;
	}

	public void setPrograma(String programa) {
		this.programa = programa;
	}
	
	
}
