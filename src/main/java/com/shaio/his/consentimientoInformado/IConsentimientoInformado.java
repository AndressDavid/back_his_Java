package com.shaio.his.consentimientoInformado;

import java.util.List;

public interface IConsentimientoInformado {
  
	public ResponseListPlantillas ListarPlantillas();
	public void setLplantillas(String body);
	public String saveData(String body); 
	public ResponseDocumento getData(String idDocumento); 
	public ResponseListConsentimientosPac listConsentimientosPacientes(String nroIngreso);
	public boolean documentExist(String idDocumento);
}
