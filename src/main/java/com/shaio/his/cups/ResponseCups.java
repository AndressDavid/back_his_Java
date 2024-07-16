package com.shaio.his.cups;

import com.shaio.his.ResponseJson;
import java.util.ArrayList;
import java.util.List;

public class ResponseCups extends ResponseJson{
	
	
 List<struct_cups> data = new ArrayList<struct_cups>();
 
 
 public List<struct_cups> getData() {
		return data;
	}

	public void setData(List<struct_cups> data) {
		this.data = data;
	}


}
