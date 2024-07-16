package com.shaio.his;

public class ResponseJson {
	private String errorMessage = "";
	private int errorCode = 0;

	public ResponseJson(String errorM, int code) {
		this.errorMessage = errorM;
		this.errorCode = code;
	}

	public ResponseJson() {
	};

	public void setValues(String errorM, int code) {
		this.errorMessage = errorM;
		this.errorCode = code;
	}
  
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

 
 
}
