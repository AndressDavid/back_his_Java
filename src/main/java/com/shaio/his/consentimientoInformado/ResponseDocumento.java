package com.shaio.his.consentimientoInformado;

import com.shaio.his.ResponseJson;

public class ResponseDocumento extends ResponseJson {
	private StructConsentimiento data = new StructConsentimiento();

	public StructConsentimiento getData() {
		return data;
	}

	public void setData(StructConsentimiento data) {
		this.data = data;
	}
     

}
