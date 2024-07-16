package com.shaio.his.anestesiaperioperatorio;

import com.shaio.his.ResponseJson;
import java.util.ArrayList;
import java.util.List;

public class ResponseAnestesiaValidar extends ResponseJson{
	List<StructAnestesiavalidar> dataValidar = new ArrayList<StructAnestesiavalidar>();

	public List<StructAnestesiavalidar> getData() {
		return dataValidar;
	}

	public void setDataValidar(List<StructAnestesiavalidar> dataValidar) {
		this.dataValidar = dataValidar;
	}

}


