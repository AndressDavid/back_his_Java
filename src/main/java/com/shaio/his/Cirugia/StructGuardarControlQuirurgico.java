package com.shaio.his.Cirugia;

import java.util.ArrayList;

public class StructGuardarControlQuirurgico {
	private String ingreso;
	private StructActoQuirurgico actoQuirurgico;
	private ArrayList<StructProcedimientos> procedimientos;
	private StructDescripcionQuirurgica descripcionQuirurgica;
	private StructPerfusion perfucion;
	private ArrayList<StructMedicamentos> medicamentos;
	private StructDatosPaciente informacionPaciente;
	private String programa;
	private String usuario;
	private String tipoUsuario;
	private String especialidadUsuario;

	public String getIngreso() {
		return ingreso;
	}

	public void setIngreso(String ingreso) {
		this.ingreso = ingreso;
	}

	public StructActoQuirurgico getActoQuirurgico() {
		return actoQuirurgico;
	}

	public void setActoQuirurgico(StructActoQuirurgico actoQuirurgico) {
		this.actoQuirurgico = actoQuirurgico;
	}

	public ArrayList<StructProcedimientos> getProcedimientos() {
		return procedimientos;
	}

	public void setProcedimientos(ArrayList<StructProcedimientos> procedimientos) {
		this.procedimientos = procedimientos;
	}

	public StructDescripcionQuirurgica getDescripcionQuirurgica() {
		return descripcionQuirurgica;
	}

	public void setDescripcionQuirurgica(StructDescripcionQuirurgica descripcionQuirurgica) {
		this.descripcionQuirurgica = descripcionQuirurgica;
	}

	
	public StructPerfusion getPerfucion() {
		return perfucion;
	}

	public void setPerfucion(StructPerfusion perfucion) {
		this.perfucion = perfucion;
	}

	public ArrayList<StructMedicamentos> getMedicamentos() {
		return medicamentos;
	}

	public void setMedicamentos(ArrayList<StructMedicamentos> medicamentos) {
		this.medicamentos = medicamentos;
	}

	public StructDatosPaciente getInformacionPaciente() {
		return informacionPaciente;
	}

	public void setInformacionPaciente(StructDatosPaciente informacionPaciente) {
		this.informacionPaciente = informacionPaciente;
	}

	public String getPrograma() {
		return programa;
	}

	public void setPrograma(String programa) {
		this.programa = programa;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public String getEspecialidadUsuario() {
		return especialidadUsuario;
	}

	public void setEspecialidadUsuario(String especialidadUsuario) {
		this.especialidadUsuario = especialidadUsuario;
	}

	
}
