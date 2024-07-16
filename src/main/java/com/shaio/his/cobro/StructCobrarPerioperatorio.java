package com.shaio.his.cobro;

public class StructCobrarPerioperatorio {
	private int ingreso ;
	private String viaingreso = "";
	private String planingreso = "";
	private String registromedicorealiza = "";
	private String identificacionpaciente = "";
	private String procedimiento = "";
	private String habitacionpaciente = "";
	private String especialidadmedico = "";
	private String consecutivocita = "";
	private String centrocosto = "";
	private String usuariocreacion = "";
		
	public String getIngresoString() {
		return "0"+ingreso;
	}
	
	public int getIngreso() {
		return ingreso;
	}
	public void setIngreso(int ingreso) {
		this.ingreso = ingreso;
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
	
	public String getRegistromedicorealiza() {
		return registromedicorealiza;
	}
	public void setRegistromedicorealiza(String registromedicorealiza) {
		this.registromedicorealiza = registromedicorealiza;
	}
	
	public String getIdentificacionpaciente() {
		return identificacionpaciente;
	}
	public void setIdentificacionpaciente(String identificacionpaciente) {
		this.identificacionpaciente = identificacionpaciente;
	}
	
	public String getIdentificacionString() {
	    if (identificacionpaciente != null) {
	        return String.format("%0" + 13 + "d", Integer.parseInt(identificacionpaciente));
	    } else {
	        return String.format("%0" + 13 + "d", 0);
	    }
	}
	
	public String getProcedimiento() {
		return procedimiento;
	}

	public void setProcedimiento(String procedimiento) {
		this.procedimiento = procedimiento;
	}

	public String getHabitacionpaciente() {
		return habitacionpaciente;
	}

	public void setHabitacionpaciente(String habitacionpaciente) {
		this.habitacionpaciente = habitacionpaciente;
	}

	public String getEspecialidadmedico() {
		return especialidadmedico;
	}

	public void setEspecialidadmedico(String especialidadmedico) {
		this.especialidadmedico = especialidadmedico;
	}

	public String getConsecutivocita() {
		return consecutivocita;
	}

	public void setConsecutivocita(String consecutivocita) {
		this.consecutivocita = consecutivocita;
	}

	public String getCentrocosto() {
		return centrocosto;
	}

	public void setCentrocosto(String centrocosto) {
		this.centrocosto = centrocosto;
	}

	public String getUsuariocreacion() {
		return usuariocreacion;
	}

	public void setUsuariocreacion(String usuariocreacion) {
		this.usuariocreacion = usuariocreacion;
	}
	
}