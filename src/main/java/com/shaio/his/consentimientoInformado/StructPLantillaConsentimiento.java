package com.shaio.his.consentimientoInformado;

import org.json.JSONObject;


public class StructPLantillaConsentimiento {
  private String active = "";
  private String text = "";
  private String title = "";
  private String id_document = "";
  private String formulario="";
  private String type = "";
  private String subType = "";
  private String version = "";
  private String code = "";
  
  public StructPLantillaConsentimiento fromJson(String json) {
	   JSONObject jo = new JSONObject(json); 
	   StructPLantillaConsentimiento cons = new StructPLantillaConsentimiento();
	   cons.active =  (String) jo.get("active");
	   cons.text = (String) jo.get("text");
	   cons.title = (String) jo.get("title");
	   cons.id_document = (String) jo.get("id_document");	
	   cons.type = (String) jo.get("type");
	   cons.subType =(String) jo.get("subType");
	   cons.version = (String) jo.get("version");
	   cons.code = (String) jo.get("code");
	   
	   try {
		   cons.setFormulario((String) jo.get("formulario"));
	   } catch(Exception e) {}
	   return cons;
  }
  
  
  
public String getActive() {
	return active;
}
public void setActive(String active) {
	this.active = active;
}
public String getText() {
	return text;
}
public void setText(String text) {
	this.text = text;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getId_document() {
	return id_document;
}
public void setId_document(String id_document) {
	this.id_document = id_document;
}



public String getFormulario() {
	return formulario;
}



public void setFormulario(String formulario) {
	this.formulario = formulario;
}



public String getType() {
	return type;
}



public void setType(String type) {
	this.type = type;
}



public String getSubType() {
	return subType;
}



public void setSubType(String subType) {
	this.subType = subType;
}



public String getVersion() {
	return version;
}



public void setVersion(String version) {
	this.version = version;
}



public String getCode() {
	return code;
}



public void setCode(String code) {
	this.code = code;
}
  
}
