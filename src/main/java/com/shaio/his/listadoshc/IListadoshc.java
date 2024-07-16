package com.shaio.his.listadoshc;


public interface IListadoshc {
    public  ResponseTipoDiagnostico ListarTipoDiagnostico();
    
    public  ResponseFinalidades ListarFinalidades(String tipofin, String genero);
    
    public  ResponseProfesionalesSalud ListarProfesionalesSalud();
    public  ResponseDiagnosticos ListadoDiagnosticos();
	
    
}

