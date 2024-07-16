package com.shaio.his.anestesiaperioperatorio;

import java.time.LocalDate;
import java.time.LocalTime;

public class StructAnestesiaguardar {
	private String validar ="";
	private int ingreso=0;
	private String tipoidenpaciente="";
	private int identificacionpaciente=0;
	private int consecutivocita=0;
	private String especialidadmedico="";
	private String procedimiento=""; 
	private String registromedicorealiza="";
	private int nitingreso=0;
	private String viaingreso="";
	private String planingreso = "";
	private String habitacionpaciente = "";
	private String posnopos = "";
	private String cobrable = "";
	private String usuariocreacion = "";
	private String programacreacion = "";
	private String descripcion = "";

	public String getValidar() {
		return validar;
	}
	public void setValidar(String validar) {
		this.validar = validar;
	}
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
	public int getIdentificacionpaciente() {
		return identificacionpaciente;
	}
	public void setIdentificacionpaciente(int identificacionpaciente) {
		this.identificacionpaciente = identificacionpaciente;
	}
	public int getConsecutivocita() {
		return consecutivocita;
	}
	public void setConsecutivocita(int consecutivocita) {
		this.consecutivocita = consecutivocita;
	}
	public String getEspecialidadmedico() {
		return especialidadmedico;
	}
	public void setEspecialidadmedico(String especialidadmedico) {
		this.especialidadmedico = especialidadmedico;
	}
	public String getProcedimiento() {
		return procedimiento;
	}
	public void setProcedimiento(String procedimiento) {
		this.procedimiento = procedimiento;
	}
	public String getRegistromedicorealiza() {
		return registromedicorealiza;
	}
	public void setRegistromedicorealiza(String registromedicorealiza) {
		this.registromedicorealiza = registromedicorealiza;
	}
	public int getNitingreso() {
		return nitingreso;
	}
	public void setNitingreso(int nitingreso) {
		this.nitingreso = nitingreso;
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
	public String getPosnopos() {
		return posnopos;
	}
	public void setPosnopos(String posnopos) {
		this.posnopos = posnopos;
	}
	public String getCobrable() {
		return cobrable;
	}
	public void setCobrable(String cobrable) {
		this.cobrable = cobrable;
	}
	public String getUsuariocreacion() {
		return usuariocreacion;
	}
	public void setUsuariocreacion(String usuariocreacion) {
		this.usuariocreacion = usuariocreacion;
	}
	public String getProgramacreacion() {
		return programacreacion;
	}
	public void setProgramacreacion(String programacreacion) {
		this.programacreacion = programacreacion;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public int getFechaActual() {
     	LocalDate now = LocalDate.now();
        int fechaactual = now.getYear() * 10000 + now.getMonthValue() * 100 + now.getDayOfMonth();
        return fechaactual;
    }
    
	public int getHoraActual() {
        int horaactual = LocalTime.now().getHour()*10000 + LocalTime.now().getMinute()*100 + LocalTime.now().getSecond();
        return horaactual;
    }
	    
}
