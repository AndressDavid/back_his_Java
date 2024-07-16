package com.shaio.his.BusPaciente;

import java.util.Map;

import org.json.JSONObject;

public class struct_revista {
   private String dx="";
   private String paraclinicos="";
   private String imagenes="";
   private String interconsultas="";
   private String pendientes="";
   private String planManejo="";
   private String cambiosRevista="";
   private String encargado="";
   private String usuario="";
   private long numeroDocumento=0;
   private long numeroIngreso=0;
   private String fecha="";
   
   
   
   public struct_revista fromJson(String json) {
	   JSONObject jo = new JSONObject(json); 
	     
	     
	   struct_revista rev = new struct_revista();
	   rev.dx =  (String) jo.get("dx");
	   rev.paraclinicos = (String) jo.get("paraclinicos");
	   rev.imagenes = (String) jo.get("imagenes");
	   rev.interconsultas = (String) jo.get("interconsultas");
	   rev.pendientes = (String) jo.getString("pendientes");
	   rev.planManejo = (String) jo.getString("planManejo");
	   rev.cambiosRevista = (String) jo.getString("cambiosRevista");
	   rev.encargado = (String) jo.getString("encargado");
	   rev.usuario = (String) jo.getString("usuario");
	   
	   
	   //JSONObject x = (JSONObject) jo.get("numeroDocumento");
	   //JSONObject x = (JSONObject) jo.get("numeroDocumento");	  
	   //rev.numeroDocumento =  new Long(x.getString("$numberLong"));
	   
	   //rev.numeroDocumento = jo.getInt("numeroDocumento");
	   rev.numeroDocumento = getNumber(jo,"numeroDocumento");
	   
	   
	   
	   
	   //JSONObject x1 = (JSONObject) jo.get("numeroDocumento");
	   //rev.numeroIngreso = new Long(x1.getString("$numberLong"));
	   rev.numeroIngreso = jo.getInt("numeroIngreso");
	   
	   
	   rev.fecha = (String) jo.getString("fecha");	   
	   return rev;
   }
   
   
   
   long getNumber(JSONObject jo,String name) {
	   boolean error = false;
	   try {
		   JSONObject x = (JSONObject) jo.get(name);
		   return  new Long(x.getString("$numberLong"));
	   } catch(Exception e) {error = true;}
	   
	   try {
		   long valor = jo.getLong(name);
		   return valor;
	   }catch(Exception e) {error = true;}
	   return (int) jo.get(name);
	   
   }
   
   
   
   
   
   
public String getDx() {
	return dx;
}
public void setDx(String dx) {
	this.dx = dx;
}
public String getParaclinicos() {
	return paraclinicos;
}
public void setParaclinicos(String paraclinicos) {
	this.paraclinicos = paraclinicos;
}
public String getImagenes() {
	return imagenes;
}
public void setImagenes(String imagenes) {
	this.imagenes = imagenes;
}
public String getInterconsultas() {
	return interconsultas;
}
public void setInterconsultas(String interconsultas) {
	this.interconsultas = interconsultas;
}
public String getPlanManejo() {
	return planManejo;
}
public void setPlanManejo(String planManejo) {
	this.planManejo = planManejo;
}
public String getCambiosRevista() {
	return cambiosRevista;
}
public void setCambiosRevista(String cambiosRevista) {
	this.cambiosRevista = cambiosRevista;
}
public String getEncargado() {
	return encargado;
}
public void setEncargado(String encargado) {
	this.encargado = encargado;
}
public String getUsuario() {
	return usuario;
}
public void setUsuario(String usuario) {
	this.usuario = usuario;
}
public long getNumeroDocumento() {
	return numeroDocumento;
}
public void setNumeroDocumento(long numeroDocumento) {
	this.numeroDocumento = numeroDocumento;
}
public long getNumeroIngreso() {
	return numeroIngreso;
}
public void setNumeroIngreso(long numeroIngreso) {
	this.numeroIngreso = numeroIngreso;
}
public String getFecha() {
	return fecha;
}
public void setFecha(String fecha) {
	this.fecha = fecha;
}
public String getPendientes() {
	return pendientes;
}
public void setPendientes(String pendientes) {
	this.pendientes = pendientes;
}




}
