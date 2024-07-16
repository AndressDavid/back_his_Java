package com.shaio.his.visitors;

import java.util.ArrayList;
import java.util.List;

import com.shaio.his.ResponseJson;

public class ResponseVisitors extends ResponseJson {

	List<StructVisitors> data = new ArrayList<StructVisitors>();

	public List<StructVisitors> getData() {
		return data;
	}

	public void setData(List<StructVisitors> data) {
		this.data = data;
	}

}
