package com.shaio.his.visitors;

import java.util.List;

public class StructSummary {
	String active;
	String inactive;
	List<StructSummaryData> summary;

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getInactive() {
		return inactive;
	}

	public void setInactive(String inactive) {
		this.inactive = inactive;
	}

	public List<StructSummaryData> getSummary() {
		return summary;
	}

	public void setSummary(List<StructSummaryData> summary) {
		this.summary = summary;
	}

}
