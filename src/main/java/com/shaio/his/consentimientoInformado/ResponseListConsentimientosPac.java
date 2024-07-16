package com.shaio.his.consentimientoInformado;

import java.util.ArrayList;
import java.util.List;

import com.shaio.his.ResponseJson;

public class ResponseListConsentimientosPac extends ResponseJson {
	List<StructListConsentimiento> data = new ArrayList<StructListConsentimiento>();

	public List<StructListConsentimiento> getData() {
		return data;
	}

	public void setData(List<StructListConsentimiento> data) {
		this.data = data;
	}
}
