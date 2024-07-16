package com.shaio.his.visitors;

public interface IVisitors {
	public ResponseVisitors searchVisitorsByPatient(String typeDocumentPatient, String documentPatient);
	
	public ResponseVisitors managmentVisitorByPatient(String documentTypePatient, String documentPatient,
			StructAdmissionUser visitorData, String userCreate, String program);
	
	public ResponseSummary getSummaryByDate();
}
