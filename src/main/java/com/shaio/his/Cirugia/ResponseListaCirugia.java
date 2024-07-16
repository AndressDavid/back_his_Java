package com.shaio.his.Cirugia;

import java.util.ArrayList;
import java.util.List;

import com.shaio.his.ResponseJson;


public class ResponseListaCirugia extends ResponseJson {
	private List datos = new ArrayList<>();


	public List getDatos() {
		return datos;
	}

	public void setDatos(List datos) {
		this.datos = datos;
	}

	
}
