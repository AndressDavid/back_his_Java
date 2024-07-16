package com.shaio.his.BusPaciente;

import com.shaio.his.ResponseJson;

public class ResponseIngreso extends ResponseJson {
  private struct_ingreso datos = new struct_ingreso();

	public struct_ingreso getData() {
		return datos;
	}
	
	public void setData(struct_ingreso idingreso) {
		this.datos = idingreso;
	} 
}





