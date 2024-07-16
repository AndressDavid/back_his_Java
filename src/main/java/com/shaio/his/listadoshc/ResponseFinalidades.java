package com.shaio.his.listadoshc;

import com.shaio.his.ResponseJson;
import java.util.ArrayList;
import java.util.List;

public class ResponseFinalidades extends ResponseJson{
	List<struct_finalidades> datafinalidad = new ArrayList<struct_finalidades>();

	public List<struct_finalidades> getData() {
		return datafinalidad;
	}

	public void setDataFinalidad(List<struct_finalidades> datafinalidad) {
		this.datafinalidad = datafinalidad;
	}

	
}




