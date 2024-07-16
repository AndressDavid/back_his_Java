package com.shaio.his.visitors;

import java.util.List;

public class StructSummaryData {
	String nameSection;
	int countBySection;
	List<StructVisitors> visitors;

	public String getNameSection() {
		return nameSection;
	}

	public void setNameSection(String nameSection) {
		this.nameSection = nameSection;
	}

	public int getCountBySection() {
		return countBySection;
	}

	public void setCountBySection(int countBySection) {
		this.countBySection = countBySection;
	}

	public List<StructVisitors> getVisitors() {
		return visitors;
	}

	public void setVisitors(List<StructVisitors> visitors) {
		this.visitors = visitors;
	}

}
