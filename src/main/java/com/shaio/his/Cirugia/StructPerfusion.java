package com.shaio.his.Cirugia;

public class StructPerfusion {
	private boolean envio = false; 
	private boolean preoperatorio = false;
	private boolean interoperatorio = false;
	private boolean posoperatorio = false;
	private boolean cardioplejia = false;
	private int anterogrado = 0;
	private int retrograda =0;
	private int simultanea =0;
	private int pinzaAortica =0;
	private int perfusion =0;
	private int paroTotal =0;
	private float temperaturaRectal =0;
	
	
	public boolean isEnvio() {
		return envio;
	}
	public void setEnvio(boolean envio) {
		this.envio = envio;
	}
	
	public boolean isPreoperatorio() {
		return preoperatorio;
	}
	public void setPreoperatorio(boolean preoperatorio) {
		this.preoperatorio = preoperatorio;
	}
	public boolean isInteroperatorio() {
		return interoperatorio;
	}
	public void setInteroperatorio(boolean interoperatorio) {
		this.interoperatorio = interoperatorio;
	}
	public boolean isPosoperatorio() {
		return posoperatorio;
	}
	public void setPosoperatorio(boolean posoperatorio) {
		this.posoperatorio = posoperatorio;
	}
	public boolean isCardioplejia() {
		return cardioplejia;
	}
	public void setCardioplejia(boolean cardioplejia) {
		this.cardioplejia = cardioplejia;
	}
	public int getAnterogrado() {
		return anterogrado;
	}
	public void setAnterogrado(int anterogrado) {
		this.anterogrado = anterogrado;
	}
	public int getRetrograda() {
		return retrograda;
	}
	public void setRetrograda(int retrograda) {
		this.retrograda = retrograda;
	}
	public int getSimultanea() {
		return simultanea;
	}
	public void setSimultanea(int simultanea) {
		this.simultanea = simultanea;
	}
	public int getPinzaAortica() {
		return pinzaAortica;
	}
	public void setPinzaAortica(int pinzaAortica) {
		this.pinzaAortica = pinzaAortica;
	}
	public int getPerfusion() {
		return perfusion;
	}
	public void setPerfusion(int perfusion) {
		this.perfusion = perfusion;
	}
	public int getParoTotal() {
		return paroTotal;
	}
	public void setParoTotal(int paroTotal) {
		this.paroTotal = paroTotal;
	}
	public float getTemperaturaRectal() {
		return temperaturaRectal;
	}
	public void setTemperaturaRectal(float temperaturaRectal) {
		this.temperaturaRectal = temperaturaRectal;
	}
}
