package com.shaio.his.doctores;

import com.shaio.his.ResponseJson;
import java.util.ArrayList;
import java.util.List;

public class ResponseDoctores extends ResponseJson{
	
	
 List<struct_doctores> data = new ArrayList<struct_doctores>();
 
 
 public List<struct_doctores> getData() {
		return data;
	}

	public void setData(List<struct_doctores> data) {
		this.data = data;
	}


}
