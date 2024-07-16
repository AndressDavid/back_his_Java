package com.shaio.his.habitaciones;

import com.shaio.his.ResponseJson;
import java.util.ArrayList;
import java.util.List;

public class ResponseDatosHab extends ResponseJson{
	
	
 List<Struct_datosHab> data = new ArrayList<Struct_datosHab>();
 
 
 public List<Struct_datosHab> getData() {
		return data;
	}

	public void setData(List<Struct_datosHab> data) {
		this.data = data;
	}


}
