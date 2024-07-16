package com.shaio.his.BusPaciente;

public class struct_paciente {
  private long numeroDocumento = 0l;
  private long numeroIngreso = 0l;
  private String tipoDocumento="";
  private String primerNombre = "";
  private String segundoNombre = "";
  private String primerApellido = "";
  private String segundoApellido = "";
  private String habitacion="";
  private String fechaIngreso = "";
  private String seccion="";
  private String mail="";
  
  
  
public long getNumeroDocumento() {
	return numeroDocumento;
}
public void setNumeroDocumento(long numeroDocumento) {
	this.numeroDocumento = numeroDocumento;
}
public String getPrimerNombre() {
	return primerNombre;
}
public void setPrimerNombre(String primerNombre) {
	this.primerNombre = primerNombre;
}
public String getSegundoNombre() {
	return segundoNombre;
}
public void setSegundoNombre(String segundoNombre) {
	this.segundoNombre = segundoNombre;
}
public String getPrimerApellido() {
	return primerApellido;
}
public void setPrimerApellido(String primerApellido) {
	this.primerApellido = primerApellido;
}
public String getSegundoApellido() {
	return segundoApellido;
}
public void setSegundoApellido(String segundoApellido) {
	this.segundoApellido = segundoApellido;
}
public String getHabitacion() {
	return habitacion;
}
public void setHabitacion(String habitacion) {
	this.habitacion = habitacion;
}
public String getFechaIngreso() {
	return fechaIngreso;
}
public void setFechaIngreso(String fechaIngreso) {
	this.fechaIngreso = fechaIngreso;
}
public long getNumeroIngreso() {
	return numeroIngreso;
}
public void setNumeroIngreso(long numeroIngreso) {
	this.numeroIngreso = numeroIngreso;
}
public String getSeccion() {
	return seccion;
}
public void setSeccion(String seccion) {
	this.seccion = seccion;
}
public String getTipoDocumento() {
	return tipoDocumento;
}
public void setTipoDocumento(String tipoDocumento) {
	this.tipoDocumento = tipoDocumento;
}
public String getMail() {
	return mail;
}
public void setMail(String mail) {
	this.mail = mail;
}
  
}
