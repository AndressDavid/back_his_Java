package com.shaio.his.consentimientoInformado;


import java.sql.Timestamp;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

public class StructListConsentimiento {
	
	
	private String fecha;
    private String idDocumento="";
    private String plantilla;
    private String nombrePlantilla;
    private String tipoDocumento = "consentimiento";
    private String rmMedico = "";
    private String nombreMedico = "";
 
    
    public static StructListConsentimiento fromJson(String jsonString) throws JsonSyntaxException {
    	Gson gson = new Gson();
    	return gson.fromJson(jsonString, StructListConsentimiento.class);
    }
	
	  
		public String getFecha() {
			return fecha;
		}

		public void setFecha(String fecha) {
			this.fecha = fecha;
		}


		public String getPlantilla() {
			return plantilla;
		}

		public void setPlantilla(String plantilla) {
			this.plantilla = plantilla;
		}

		

	



		public String getNombrePlantilla() {
			return nombrePlantilla;
		}


		public void setNombrePlantilla(String nombrePlantilla) {
			this.nombrePlantilla = nombrePlantilla;
		}


		public String getIdDocumento() {
			return idDocumento;
		}


		public void setIdDocumento(String idDocumento) {
			this.idDocumento = idDocumento;
		}


		public String getTipoDocumento() {
			return tipoDocumento;
		}


		public void setTipoDocumento(String tipoDocumento) {
			this.tipoDocumento = tipoDocumento;
		}


		public String getRmMedico() {
			return rmMedico;
		}


		public void setRmMedico(String rmMedico) {
			this.rmMedico = rmMedico;
		}


		public String getNombreMedico() {
			return nombreMedico;
		}


		public void setNombreMedico(String nombreMedico) {
			this.nombreMedico = nombreMedico;
		}








	    
}