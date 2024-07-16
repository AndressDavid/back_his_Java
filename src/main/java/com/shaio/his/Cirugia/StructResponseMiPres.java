package com.shaio.his.Cirugia;

import com.shaio.his.ResponseJson;

public class StructResponseMiPres extends ResponseJson {
	
	private String ruta = "";
	private String isMipre;
	private String texto;
	private String subtitulo;
	private String alerta;
	private String confirmacion1;
	private String confirmacion2;
	
	
	public String getRuta() {
		return ruta;
	}
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	
	public String getIsMipre() {
		return isMipre;
	}
	public void setIsMipre(String isMipre) {
		this.isMipre = isMipre;
	}
	
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	
	public String getSubtitulo() {
		return subtitulo;
	}
	public void setSubtitulo(String subtitulo) {
		this.subtitulo = subtitulo;
	}
	
	public String getAlerta() {
		return alerta;
	}
	public void setAlerta(String alerta) {
		this.alerta = alerta;
	}
	
	public String getConfirmacion1() {
		return confirmacion1;
	}
	public void setConfirmacion1(String confirmacion1) {
		this.confirmacion1 = confirmacion1;
	}
	
	public String getConfirmacion2() {
		return confirmacion2;
	}
	public void setConfirmacion2(String confirmacion2) {
		this.confirmacion2 = confirmacion2;
	}
	
}
