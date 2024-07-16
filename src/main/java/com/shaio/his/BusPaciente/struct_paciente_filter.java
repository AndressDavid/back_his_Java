package com.shaio.his.BusPaciente;

import com.shaio.his.pagination.StructPagination;

public class struct_paciente_filter {


	private int maxPages;
	private StructPagination data;
	
	
	public int getMaxPages() {
		return maxPages;
	}
	public void setMaxPages(int maxPages) {
		this.maxPages = maxPages;
	}
	
	public StructPagination getData() {
		return data;
	}
	public void setData(StructPagination data) {
		this.data = data;
	}

}
