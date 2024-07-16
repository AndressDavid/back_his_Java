package com.shaio.his.habitaciones;

public class Struct_datosHab {
  private String seccion = "";
  private String cama = "";
  private Number ingreso = 0;
  private String tipo_documento_paciente = "";
  private String num_doc_paciente = "";
  private String primer_nombre = "";
  private String segundo_nombre = "";
  private String primer_apellido = "";
  private String segundo_apellido = "";
  private String ip_dispositivo = "";
  private String mac_dispositivo = "";
  private Number activo = 1;
  
  
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


public Number getIngreso() {
	return ingreso;
}
public void setIngreso(Number ingreso) {
	this.ingreso = ingreso;
}  


public String getTipoDocumentoPaciente() {
	return tipo_documento_paciente;
}
public void setTipoDocumentoPaciente(String tipo_documento_paciente) {
	this.tipo_documento_paciente = tipo_documento_paciente;
}  


public String getNumDocPaciente() {
	return num_doc_paciente;
}
public void setNumDocPaciente(String num_doc_paciente) {
	this.num_doc_paciente = num_doc_paciente;
}  


public String getPrimer_Nombre() {
	return primer_nombre;
}
public void setPrimerNombre(String primer_nombre) {
	this.primer_nombre = primer_nombre;
}


public String getSegundoNombre() {
	return segundo_nombre;
}
public void setSegundoNombre(String segundo_nombre) {
	this.segundo_nombre = segundo_nombre;
}


public String getPrimerApellido() {
	return primer_apellido;
}
public void setPrimerApellido(String primer_apellido) {
	this.primer_apellido = primer_apellido;
}


public String getSegundoApellido() {
	return segundo_apellido;
}
public void setSegundoApellido(String segundo_apellido) {
	this.segundo_apellido = segundo_apellido;
}


public String getIpDispositivo() {
	return ip_dispositivo;
}
public void setIpDispositivo(String ip_dispositivo) {
	this.ip_dispositivo = ip_dispositivo;
}


public String getMacDispositivo() {
	return mac_dispositivo;
}
public void setMacDispositivo(String mac_dispositivo) {
	this.mac_dispositivo = mac_dispositivo;
}


public Number getActivo() {
	return activo;
}
public void setActivo(Number activo) {
	this.activo = activo;
}
}
