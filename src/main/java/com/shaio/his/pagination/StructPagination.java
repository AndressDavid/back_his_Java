package com.shaio.his.pagination;

import java.util.ArrayList;

public class StructPagination {
	
	private int page = 0;
	private ArrayList<StructFilters> filter ;
	private ArrayList<StructOrder> orden;
	private String select = "";
	private int maxPage = 30;
	
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	
	public ArrayList<StructFilters> getFilter() {
		return filter;
	}
	
	public void setFilter(ArrayList<StructFilters> filter) {
		this.filter = filter;
	}
	
	public ArrayList<StructOrder> getOrden() {
		return orden;
	}
	public void setOrden(ArrayList<StructOrder> orden) {
		this.orden = orden;
	}
	
	public String getSelect() {
		return select;
	}
	public void setSelect(String select) {
		this.select = select;
	}
	public int getMaxPage() {
		return maxPage;
	}
	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}

}
