package com.shaio.his.listadoshc;

import com.shaio.his.ResponseJson;
import java.util.ArrayList;
import java.util.List;

public class ResponseProfesionalesSalud extends ResponseJson{
	List<struct_profesionales> dataprofesionales = new ArrayList<struct_profesionales>();

	public List<struct_profesionales> getData() {
		return dataprofesionales;
	}

	public void setDataMedico(List<struct_profesionales> dataprofesionales) {
		this.dataprofesionales = dataprofesionales;
	}

}

