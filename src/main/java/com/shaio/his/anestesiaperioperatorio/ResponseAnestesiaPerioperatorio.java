package com.shaio.his.anestesiaperioperatorio;

import com.shaio.his.ResponseJson;
import java.util.ArrayList;
import java.util.List;

public class ResponseAnestesiaPerioperatorio extends ResponseJson{
	List<StructAnestesiaperioperatorio> data = new ArrayList<StructAnestesiaperioperatorio>();

	public List<StructAnestesiaperioperatorio> getData() {
		return data;
	}

	public void setData(List<StructAnestesiaperioperatorio> data) {
		this.data = data;
	}


}

