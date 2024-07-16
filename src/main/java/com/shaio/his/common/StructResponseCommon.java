package com.shaio.his.common;

import java.util.ArrayList;
import java.util.List;

import com.shaio.his.ResponseJson;

public class StructResponseCommon  extends ResponseJson{
	
	public List listaComun = new ArrayList<>();

	public List getListaComun() {
		return listaComun;
	}

	public void setListaComun(List listaComun) {
		this.listaComun = listaComun;
	}


	
}
