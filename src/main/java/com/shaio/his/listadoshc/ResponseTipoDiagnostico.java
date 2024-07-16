package com.shaio.his.listadoshc;

import com.shaio.his.ResponseJson;
import java.util.ArrayList;
import java.util.List;

public class ResponseTipoDiagnostico extends ResponseJson{
	List<struct_tipodiagnostico> datatipodiagnostico = new ArrayList<struct_tipodiagnostico>();

	public List<struct_tipodiagnostico> getData() {
		return datatipodiagnostico;
	}

	public void setData(List<struct_tipodiagnostico> datatipodiagnostico) {
		this.datatipodiagnostico = datatipodiagnostico;
	}


}

