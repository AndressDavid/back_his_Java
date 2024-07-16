package com.shaio.his.anestesiaperioperatorio;

public class StructAnestesiavalidar {
	private int ingreso = 0;
	private String tipoidenpaciente = "";
	private String identificacionpaciente = "";
	private String nombrepaciente = "";
	private String procedimiento = "";
	private String especialidadmedico = "";
	private String registromedicorealiza = "";
	private String viaingreso = "";
	private String planingreso = "";
	private String cobrable = "";
	private String descripcion = "";
	private String habitacionpaciente = "";
	
	public int getIngreso() {
		return ingreso;
	}
	public void setIngreso(int ingreso) {
		this.ingreso = ingreso;
	}

	public String getTipoidenpaciente() {
		return tipoidenpaciente;
	}
	
	public void setTipoidenpaciente(String tipoidenpaciente) {
		this.tipoidenpaciente = tipoidenpaciente;
	}
	
	public String getIdentificacionpaciente() {
		return identificacionpaciente;
	}
	
	public void setIdentificacionpaciente(String identificacionpaciente) {
		this.identificacionpaciente = identificacionpaciente;
	}
	
	public String getNombrePaciente() {
		return nombrepaciente;
	}
	public void setNombrePaciente(String nombrepaciente) {
		this.nombrepaciente = nombrepaciente;
	}
	
	public String getProcedimiento() {
		return procedimiento;
	}
	public void setProcedimiento(String procedimiento) {
		this.procedimiento = procedimiento;
	}
	public String getEspecialidadmedico() {
		return especialidadmedico;
	}
	public void setEspecialidadmedico(String especialidadmedico) {
		this.especialidadmedico = especialidadmedico;
	}
	public String getRegistromedicorealiza() {
		return registromedicorealiza;
	}
	public void setRegistromedicorealiza(String registromedicorealiza) {
		this.registromedicorealiza = registromedicorealiza;
	}
	public String getViaingreso() {
		return viaingreso;
	}
	public void setViaingreso(String viaingreso) {
		this.viaingreso = viaingreso;
	}
	public String getPlaningreso() {
		return planingreso;
	}
	public void setPlaningreso(String planingreso) {
		this.planingreso = planingreso;
	}
	
	public String getCobrable() {
		return cobrable;
	}
	public void setCobrable(String cobrable) {
		this.cobrable = cobrable;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getHabitacionpaciente() {
		return habitacionpaciente;
	}
	public void setHabitacionpaciente(String habitacionpaciente) {
		this.habitacionpaciente = habitacionpaciente;
	}
	
	public String getSeccionString() {
	    if (habitacionpaciente != null && habitacionpaciente.length() >= 2) {
	        return habitacionpaciente.substring(0, 2);
	    } else {
	        return "";
	    }
	}
	
	public String getCamaString() {
	    if (habitacionpaciente != null && habitacionpaciente.length() >= 2) {
	        return habitacionpaciente.substring(2);
	    } else {
	        return "";
	    }
	}
	
}
