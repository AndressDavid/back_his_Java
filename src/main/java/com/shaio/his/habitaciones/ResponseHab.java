package com.shaio.his.habitaciones;

import com.shaio.his.ResponseJson;
import java.util.ArrayList;
import java.util.List;

public class ResponseHab extends ResponseJson{
	
	
 List<Struct_hab> data = new ArrayList<Struct_hab>();
 
 
 public List<Struct_hab> getData() {
		return data;
	}

	public void setData(List<Struct_hab> data) {
		this.data = data;
	}


}
