package com.shaio.his.consentimientoInformado;

import java.util.ArrayList;
import java.util.List;

import com.shaio.his.ResponseJson;

public class ResponseListPlantillas extends ResponseJson {
    List<StructPLantillaConsentimiento> lplantillas = new ArrayList<StructPLantillaConsentimiento>();

	public List<StructPLantillaConsentimiento> getLplantillas() {
		return lplantillas;
	}

	public void setLplantillas(String plantillas) {
		//this.lplantillas = List<plantillas>;
	}
    
    
    
    
}
