package com.shaio.his.habitaciones;

public interface IHabitaciones {
    public  ResponseHab ListarHab(String filter);
    
    public  ResponseDatosHab ListarDatosHab(String seccion, String cama, float activo);

	public ResponseGuardar guardarDatosHabitaciones(Struct_guardar requestBody);
}
