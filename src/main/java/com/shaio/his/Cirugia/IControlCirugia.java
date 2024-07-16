package com.shaio.his.Cirugia;

import com.shaio.his.ResponseJson;

public interface IControlCirugia {
	
	public ResponseListaCirugia extraerListaCirugiasPaciente(StructuraBusqueda datosBusqueda);
	public ResponseListaCirugia extaerConsumoSalaCirugia(StructFiltroConsumoSala body);
	public ResponseJsonGuardadoCirugia guardarControlQuirurgico(StructGuardarControlQuirurgico body);
	public StructValidarProcedimientos validarProcedimientos(StructFiltroValidarProcedimiento procedimiento);
	public StructResponseMiPres datosMipres(StructFiltroMiPres filtros);
	
}
