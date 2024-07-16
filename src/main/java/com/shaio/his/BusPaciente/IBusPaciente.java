package com.shaio.his.BusPaciente;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import com.shaio.his.pagination.StructPagination;

public interface IBusPaciente {
  
	public ResponseListarPaciente buscarPaciente(String numero_ingreso,String primer_nombre,String segundo_nombre
		  ,String primer_apellido,String segundo_apellido,  String tipoDocumento, String documento, boolean en_habitacion);
    public List<struct_paciente> censoHabitaciones();
    public ResponseListarPaciente listadoPacientesActivos();    
    public ResponseHistoryPatient getPatientHistory(String addmissionNumber, String typeDocument, String document);


	public ResponseCitaPaciente consecutivoCitaPaciente(String tipoDocumento, String documento);

    public ResponseListarPaciente filtradoPacientesActivos( StructPagination bodyfilter );   
}
