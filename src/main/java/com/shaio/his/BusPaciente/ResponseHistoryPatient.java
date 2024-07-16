package com.shaio.his.BusPaciente;

import java.util.ArrayList;
import java.util.List;

import com.shaio.his.ResponseJson;


public class ResponseHistoryPatient extends ResponseJson {

	List<struct_patient_history> data = new ArrayList<struct_patient_history>();
	
	public List<struct_patient_history> getData() {
		return data;
	}
	public void setData(List<struct_patient_history> data) {
		this.data = data;
	}
}
