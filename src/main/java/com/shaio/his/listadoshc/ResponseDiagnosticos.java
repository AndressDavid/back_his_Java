package com.shaio.his.listadoshc;

import com.shaio.his.ResponseJson;
import java.util.ArrayList;
import java.util.List;

public class ResponseDiagnosticos extends ResponseJson{
	List<struct_diagnostico> datadiagnosticos = new ArrayList<struct_diagnostico>();

	public List<struct_diagnostico> getData() {
		return datadiagnosticos;
	}

	public void setDataDiagnostico(List<struct_diagnostico> datadiagnosticos) {
		this.datadiagnosticos = datadiagnosticos;
	}

}