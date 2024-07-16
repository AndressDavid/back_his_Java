package com.shaio.his.common;

import java.util.ArrayList;
import java.util.List;

import com.shaio.his.cups.struct_cups;

public class StructListaCupsPatologia {
	private String tipoPatologia;
	public List listaComun = new ArrayList<struct_cups>();
	
	public String getTipoPatologia() {
		return tipoPatologia;
	}
	public void setTipoPatologia(String tipoPatologia) {
		this.tipoPatologia = tipoPatologia;
	}
	public List getListaComun() {
		return listaComun;
	}
	public void setListaComun(List listaComun) {
		this.listaComun = listaComun;
	}
	
	
}
