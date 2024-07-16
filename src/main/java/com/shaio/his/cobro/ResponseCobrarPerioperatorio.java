package com.shaio.his.cobro;

import java.util.ArrayList;
import java.util.List;

import com.shaio.his.ResponseJson;

public class ResponseCobrarPerioperatorio extends ResponseJson{
	
	List<StructCobrarPerioperatorio> dataCobrarperioperatorio = new ArrayList<StructCobrarPerioperatorio>();

	public List<StructCobrarPerioperatorio> getData(StructCobrarPerioperatorio lingreso) {
		return dataCobrarperioperatorio;
	}

	public void setDataGuardar(List<StructCobrarPerioperatorio> dataCobrarperioperatorio) {
		this.dataCobrarperioperatorio = dataCobrarperioperatorio;
	}

}

