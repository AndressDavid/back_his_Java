package com.shaio.his.nutricion;

import java.util.ArrayList;
import java.util.List;
import com.shaio.his.ResponseJson;

public class ResponseNutricion extends ResponseJson {
	
	List<struct_Nutricion> dataNutricion = new ArrayList<struct_Nutricion>();
	
	public List<struct_Nutricion> getData(){
		return dataNutricion;
	}
	
	public void setData(List<struct_Nutricion> dataNutricion) {
		this.dataNutricion = dataNutricion;
	}

}
