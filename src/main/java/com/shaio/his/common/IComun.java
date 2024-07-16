package com.shaio.his.common;

public interface IComun {
	
	
	public StructResponseCommon extraerComunesEspecialidades(StructFiltroEspecialidades datosfiltro);
	public StructResponseCommon extraerSalas(String tipo);
	public StructResponseCommon extraerOtrasSalas();
	public StructResponseCommon extaerTipoCirugia();
	public StructResponseCommon extaerListProfesional(StructFiltrosTipoProfesional filtros);
	public StructResponseCommon extaerTipoAnestesia();
	public StructResponseCommon extaerDiagnostico( StructFiltroDiagnostico body );
	public StructResponseCommon extraerEspecialidadesMedicos( StructFiltroEspecialidadMedicos body );
	public StructResponseCommon extaerMedicosXespecialidades( StructFiltroMedicoXespecialidad body );
	public StructResponseCommon extraerCupsPatologias();
	public StructResponseCommon extraerlistaMedicamentos(StructFiltroMedicamentos body);
}
