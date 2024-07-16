package com.shaio.his.visitors;

import com.shaio.his.ResponseJson;

public class ResponseSummary extends ResponseJson {
	StructSummary data = new StructSummary();

	public StructSummary getData() {
		return data;
	}

	public void setData(StructSummary data) {
		this.data = data;
	}
}