package com.shaio.his.consentimientoInformado;

import com.shaio.his.ResponseJson;

public class ResponseListDocumento extends ResponseJson {
	private StructListConsentimiento data = new StructListConsentimiento();

	public StructListConsentimiento getData() {
		return data;
	}

	public void setData(StructListConsentimiento data) {
		this.data = data;
	}
     

}
