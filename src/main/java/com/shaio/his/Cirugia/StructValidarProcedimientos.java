package com.shaio.his.Cirugia;

import com.shaio.his.ResponseJson;

public class StructValidarProcedimientos extends ResponseJson {
	
	private String especialidad;
	private String pos;


	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}
	
}
