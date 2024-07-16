package com.shaio.his.BusPaciente;

import java.util.List;

public class struct_patient_history {

	String weight = "";
	String height = "";
	String age = "";
	List<struct_description> historyGeneral;
	List<struct_description> historyphysicalActivity;
	List<struct_description> historyPediatric;
	List<struct_description> historyVaccinations;

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public List<struct_description> getHistoryGeneral() {
		return historyGeneral;
	}

	public void setHistoryGeneral(List<struct_description> historyGeneral) {
		this.historyGeneral = historyGeneral;
	}

	public List<struct_description> getHistoryphysicalActivity() {
		return historyphysicalActivity;
	}

	public void setHistoryPhysicalActivity(List<struct_description> historyphysicalActivity) {
		this.historyphysicalActivity = historyphysicalActivity;
	}

	public List<struct_description> getHistoryPediatric() {
		return historyPediatric;
	}

	public void setHistoryPediatric(List<struct_description> historyPediatric) {
		this.historyPediatric = historyPediatric;
	}

	public List<struct_description> getHistoryVaccinations() {
		return historyVaccinations;
	}

	public void setHistoryVaccinations(List<struct_description> historyVaccinations) {
		this.historyVaccinations = historyVaccinations;
	}
}







