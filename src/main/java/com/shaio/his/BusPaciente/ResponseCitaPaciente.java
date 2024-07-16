package com.shaio.his.BusPaciente;

import java.util.List;
import java.util.ArrayList;
import com.shaio.his.ResponseJson;

public class ResponseCitaPaciente extends ResponseJson {
  private List<struct_citapaciente> citaPaciente = new ArrayList<struct_citapaciente>();

	public List<struct_citapaciente> getCitaPacientes() {
		return citaPaciente;
	}
	
	public void setCitaPacientes(List<struct_citapaciente> citaPaciente) {
		this.citaPaciente = citaPaciente;
	} 
}