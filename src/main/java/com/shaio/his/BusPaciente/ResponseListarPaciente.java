package com.shaio.his.BusPaciente;

import java.util.List;
import java.util.ArrayList;
import com.shaio.his.ResponseJson;

public class ResponseListarPaciente extends ResponseJson {
  private List<struct_paciente> listPacientes = new ArrayList<struct_paciente>();
  private int paginas = 1;

	public List<struct_paciente> getListPacientes() {
		return listPacientes;
	}
	
	public void setListPacientes(List<struct_paciente> listPacientes) {
		this.listPacientes = listPacientes;
	}
	
	public int getPaginas() {
		return paginas;
	}
	
	public void setPaginas(int paginas) {
		this.paginas = paginas;
	} 
}
