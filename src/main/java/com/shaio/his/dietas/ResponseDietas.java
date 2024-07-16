package com.shaio.his.dietas;

import com.shaio.his.ResponseJson;
import java.util.ArrayList;
import java.util.List;

public class ResponseDietas extends ResponseJson{
	
	
 List<struct_dietas> data = new ArrayList<struct_dietas>();
 
 
 public List<struct_dietas> getData() {
		return data;
	}

	public void setData(List<struct_dietas> data) {
		this.data = data;
	}


}
