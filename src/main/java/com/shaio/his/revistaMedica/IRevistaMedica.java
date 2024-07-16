package com.shaio.his.revistaMedica;

import java.util.List;

import com.shaio.his.BusPaciente.struct_revista;

public interface IRevistaMedica {
	
	public void saveData(String body);	
	public List<struct_revista> listar(String usuario,long numeroDocumento);
	public struct_revista ultimoDx(long numeroIngreso,String usuario); 
	
}
