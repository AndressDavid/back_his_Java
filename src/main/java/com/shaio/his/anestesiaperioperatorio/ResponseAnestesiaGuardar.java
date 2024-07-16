package com.shaio.his.anestesiaperioperatorio;

import java.util.ArrayList;
import java.util.List;

import com.shaio.his.ResponseJson;

public class ResponseAnestesiaGuardar extends ResponseJson{
	List<StructAnestesiaguardar> dataGuardar = new ArrayList<StructAnestesiaguardar>();

	public List<StructAnestesiaguardar> getData() {
		return dataGuardar;
	}

	public void setDataGuardar(List<StructAnestesiaguardar> dataGuardar) {
		this.dataGuardar = dataGuardar;
	}
	
}


