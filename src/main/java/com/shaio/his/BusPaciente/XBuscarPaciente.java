package com.shaio.his.BusPaciente;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.shaio.his.AS400.DBAS400;
import com.shaio.his.pagination.Pagination;
import com.shaio.his.pagination.StructCambioNombreDatos;
import com.shaio.his.pagination.StructFilters;
import com.shaio.his.pagination.StructOrder;
import com.shaio.his.pagination.StructPagination;



@Service
public class XBuscarPaciente implements IBusPaciente {

	@Override
	public ResponseListarPaciente listadoPacientesActivos() {
		ResponseListarPaciente returnResult = new ResponseListarPaciente(); 
		List<struct_paciente> lpacientes = new ArrayList<struct_paciente>();

		// String sql = " SELECT * FROM ALPHILDAT.RIAING ing INNER JOIN alphildat.RIAPAC
		// pac ON ing.TIDING = pac.TIDPAC AND ing.NIDING = pac.NIDPAC WHERE ESTING = '2'
		// ";

		String sql = "SELECT TIDING,NIDPAC,"
				+ "NM1PAC,NM2PAC,AP1PAC, AP2PAC,NIGING,MAIPAC, "
				+ "cast(Left(FEIING , 4)||'-'||substring(FEIING,5,2)||'-'||substring(FEIING, 7,2) as date) as fecha_ingreso "
				+ "FROM ALPHILDAT.RIAING ing INNER JOIN alphildat.RIAPAC pac ON ing.TIDING = pac.TIDPAC AND ing.NIDING = pac.NIDPAC  WHERE ESTING = '2'  AND ( "
				+ " (VIAING IN ('01','02') AND FEIING>REPLACE(SUBSTR(CHAR(NOW() - 8 DAYS),1,10),'-','')) OR "
				+ " (VIAING NOT IN ('01','02') AND FEIING>REPLACE(SUBSTR(CHAR(NOW() - 365 DAYS),1,10),'-','')) "
				+ " ) ";
		
		System.out.println(sql);
		
		try (DBAS400 db = new DBAS400();
				Connection con = db.getCon();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql);) {
			while (rs.next()) {
				
				String fechaingreso =(rs.getString("fecha_ingreso") == null ) ? "": rs.getDate("fecha_ingreso").toString();
				
				struct_paciente pac = new struct_paciente();
				pac.setTipoDocumento(rs.getString("TIDING"));
				pac.setNumeroDocumento(rs.getLong("NIDPAC"));
				pac.setPrimerNombre(filtrarCampo(rs.getString("NM1PAC")));
				pac.setSegundoNombre(filtrarCampo(rs.getString("NM2PAC")));
				pac.setPrimerApellido(filtrarCampo(rs.getString("AP1PAC")));
				pac.setSegundoApellido(filtrarCampo(rs.getString("AP2PAC")));
				// pac.setHabitacion(filtrarCampo(rs.getString("NUMHAB")));
				pac.setNumeroIngreso(rs.getLong("NIGING"));
				// pac.setSeccion(rs.getString("SECHAB"));
				pac.setMail(rs.getString("MAIPAC"));
				pac.setFechaIngreso(fechaingreso);
				lpacientes.add(pac);
			}
			rs.close();
		} catch (Exception e) {
			returnResult.setErrorCode(-1);
			returnResult.setErrorMessage("Error:"+e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		returnResult.setListPacientes(lpacientes);
		return returnResult;
	}

	
	
	@Override
	public ResponseListarPaciente buscarPaciente(String numero_ingreso, String primer_nombre, String segundo_nombre,
			String primer_apellido, String segundo_apellido, String tipoDocumento, String documento, boolean en_habitacion) {
		ResponseListarPaciente returnResult = new ResponseListarPaciente(); 
		List<struct_paciente> lpacientes = new ArrayList<struct_paciente>();
		String sql = "";
		
		String inner = (en_habitacion == true) ? "INNER JOIN" :"LEFT OUTER JOIN";

		sql = " SELECT  " + "SECHAB,NIDPAC,NM1PAC,NM2PAC,AP1PAC,AP2PAC,NUMHAB,TRIM(MAIPAC) as MAIPAC,TIDPAC"
				+ ",IFNULL(INGHAB,(SELECT NIGING FROM ALPHILDAT.RIAING WHERE NIDING =r.NIDPAC ORDER BY NIDING  DESC FETCH first 1 ROWS ONLY)) AS numero_ingreso, "
				 + "(SELECT cast(Left(FEIING , 4)||'-'||substring(FEIING,5,2)||'-'||substring(FEIING, 7,2) as date ) FROM ALPHILDAT.RIAING WHERE NIDING =r.NIDPAC ORDER BY NIDING  DESC FETCH first 1 ROWS ONLY) AS fecha_ingreso" 
				+ " FROM ALPHILDAT.RIAPAC r " + inner +" ALPHILDAT.FACHAB f ON r.NIDPAC = f.NIDHAB  "
				+ " WHERE NM1PAC LIKE '%" + primer_nombre + "%' AND NM2PAC LIKE '%" + segundo_nombre
				+ "%' AND  r.TIDPAC LIKE '%"+tipoDocumento+"%' AND VARCHAR( r.NIDPAC )LIKE '%"+documento+"%' and AP1PAC LIKE '%" + primer_apellido + "%' " + "  AND AP1PAC LIKE '%" + segundo_apellido
				+ "%' AND "
				+ "(SELECT NIGING FROM ALPHILDAT.RIAING WHERE NIDING = r.NIDPAC ORDER BY NIDING DESC FETCH first 1 ROWS ONLY) LIKE '%"
				+ numero_ingreso + "%'";
		
		System.out.println(sql);
		try (DBAS400 db = new DBAS400();
				Connection con = db.getCon();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql);
			) {
			while (rs.next()) {
					
				String fechaingreso =(rs.getString("fecha_ingreso") == null ) ? "": rs.getDate("fecha_ingreso").toString();
				
				struct_paciente pac = new struct_paciente();
				pac.setNumeroDocumento(rs.getLong("NIDPAC"));
				pac.setPrimerNombre(filtrarCampo(rs.getString("NM1PAC")));
				pac.setSegundoNombre(filtrarCampo(rs.getString("NM2PAC")));
				pac.setPrimerApellido(filtrarCampo(rs.getString("AP1PAC")));
				pac.setSegundoApellido(filtrarCampo(rs.getString("AP2PAC")));
				pac.setHabitacion(filtrarCampo(rs.getString("NUMHAB")));
				pac.setNumeroIngreso(rs.getLong("NUMERO_INGRESO"));
				pac.setSeccion(rs.getString("SECHAB"));
				pac.setMail(rs.getString("MAIPAC"));
				pac.setFechaIngreso(fechaingreso);
				pac.setTipoDocumento(rs.getString("TIDPAC"));
				lpacientes.add(pac);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			returnResult.setErrorCode(-1);
			returnResult.setErrorMessage("Error:"+e.getMessage());
			e.printStackTrace();
		}

		returnResult.setListPacientes(lpacientes);
		return returnResult;
	}

	String filtrarCampo(String valor) {
		if (valor == null)
			return "";
		return valor.trim();
	}

	@Override
	public List<struct_paciente> censoHabitaciones() {
		// TODO Auto-generated method stub


		List<struct_paciente> lpacientes = new ArrayList();
		String sql = "SELECT SECHAB,NUMHAB,INGHAB,NIDPAC,ESTHAB,NM1PAC,NM2PAC,AP1pac,Ap2pac,MAIPAC  FROM ALPHILDAT.FACHAB INNER JOIN alphildat.RIAPAC p ON tidhab=tidpac and NIDHAB=NIDPAC "
				+ " WHERE "
				+ " MAIPAC <>'' "				
				+ "ORDER BY NUMHAB ,NIDPAC";
		try (	DBAS400 db = new DBAS400();			
				Connection con = db.getCon();			
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql);
			) {	
			while (rs.next()) {
				struct_paciente pac = new struct_paciente();
				pac.setNumeroDocumento(rs.getLong("NIDPAC"));
				pac.setPrimerNombre(filtrarCampo(rs.getString("NM1PAC")));
				pac.setSegundoNombre(filtrarCampo(rs.getString("NM2PAC")));
				pac.setPrimerApellido(filtrarCampo(rs.getString("AP1PAC")));
				pac.setSegundoApellido(filtrarCampo(rs.getString("AP2PAC")));
				pac.setHabitacion(filtrarCampo(rs.getString("NUMHAB")));
				pac.setNumeroIngreso(rs.getLong("INGHAB"));
				pac.setSeccion(rs.getString("SECHAB"));
				pac.setMail(rs.getString("MAIPAC"));
				lpacientes.add(pac);

				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		

	  return lpacientes;
	}
		


	@Override
	public ResponseHistoryPatient getPatientHistory(String addmissionNumber, String typeDocument,
			String document) {
		ResponseHistoryPatient response = new ResponseHistoryPatient();
		List<struct_patient_history> result = new ArrayList<struct_patient_history>();

		String sql = "SELECT c.TIDANT, c.NIDANT, c.NINANT, c.DESANT,c.OP5ANT, DETAIL.DPRAND, DETAIL.DESAND "
				+ "		FROM ALPHILDAT.ANTPAC c "
				+ "		JOIN ALPHILDAT.ANTDES DETAIL ON DETAIL.IN2AND = c.SANANT AND c.CODANT = DETAIL.IN1AND  "
				+ "WHERE NINANT = '" + addmissionNumber + "' AND NIDANT  = '" + document + "' AND TIDANT  = '"
				+ typeDocument + "' AND CODANT IN (4,15,16)";

		try (DBAS400 db = new DBAS400();
				Connection con = db.getCon();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql);) {
			struct_patient_history patientDetail = new struct_patient_history();
			List<struct_description> dataGeneral = new ArrayList<>();
			List<struct_description> dataPediatric = new ArrayList<>();
			List<struct_description> dataPhysicalActivity = new ArrayList<>();
			List<struct_description> dataVaccinations = new ArrayList<>();

			while (rs.next()) {
				String dprand = rs.getString("DPRAND").trim();
				String desand = rs.getString("DESAND").trim();
				String desant = rs.getString("DESANT").trim();

				struct_description resultDescription = new struct_description();
				resultDescription.setCode(desand);
				resultDescription.setDescription(desant);

				switch (dprand) {
				case "GENERALES":
					dataGeneral.add(resultDescription);
					break;
				case "PEDIATRICAS":
					dataPediatric.add(resultDescription);
					break;
				case "ACTIVIDAD FÍSICA":
					dataPhysicalActivity.add(resultDescription);
					break;
				case "VACUNAS":
					dataVaccinations.add(resultDescription);
					break;
				}
			}
			

			patientDetail.setHistoryGeneral(dataGeneral);
			patientDetail.setHistoryPediatric(dataPediatric);
			patientDetail.setHistoryPhysicalActivity(dataPhysicalActivity);
			patientDetail.setHistoryVaccinations(dataVaccinations);
			result.add(patientDetail);
			struct_patient_history getWeightSize = getWeightSize(addmissionNumber);
			patientDetail.setHeight(getWeightSize.getHeight());
			patientDetail.setWeight(getWeightSize.getWeight());
			
			response.setErrorCode(0);
			response.setData(result);
			rs.close();
		} catch (Exception e) {
			response.setErrorCode(99);
			response.setErrorMessage(e.getMessage());
		}
		
		 if (result.isEmpty()) {
	            response.setErrorCode(90);
	            response.setErrorMessage("No se encontraron datos!");
	        }

		return response;
	}
	
	

	private struct_patient_history getWeightSize(String addmissionNumber) {

		struct_patient_history result = new struct_patient_history();

		String sql = "SELECT PESNEU AS PESO, TIPPES AS TIPO, TALNEU AS TALLA FROM  ALPHILDAT.ENNEUR WHERE INGNEU = '"
				+ addmissionNumber + "' AND PESNEU > 0 ORDER BY FECNEU DESC, HORNEU DESC";

		try (DBAS400 db = new DBAS400();
				Connection con = db.getCon();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql);) {

			if (rs.next()) {
				String weightType = rs.getString("TIPO").equals("Gramos") ? " g" : " kg";
				result.setWeight(rs.getString("PESO") + weightType);
				result.setHeight(rs.getString("TALLA") + " cm");
			} else {
				sql = "SELECT PSOEXF AS PESO, TLLEXF AS TALLA FROM ALPHILDAT.RIAEXF WHERE NIGEXF = '" + addmissionNumber
						+ "' AND PSOEXF > 0 ORDER BY FECEXF DESC, HOREXF DESC";
				con.createStatement();
				st.executeQuery(sql);
				if (rs.next()) {
					result.setWeight(rs.getString("PESO") + " Kg");
					result.setHeight(rs.getString("TALLA") + " cm");
				}
			}

			rs.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return result;
	}
	
	private  struct_patient_history getDemographicData(String typeDocument , String document) {

		struct_patient_history result = new struct_patient_history();

		String sql = "SELECT * FROM ALPHILDAT.RIAPAC WHERE TIP";

		try (DBAS400 db = new DBAS400();
				Connection con = db.getCon();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql);) {

			if (rs.next()) {
				String weightType = rs.getString("TIPO").equals("Gramos") ? " g" : " kg";
				result.setWeight(rs.getString("PESO") + weightType);
				result.setHeight(rs.getString("TALLA") + " cm");
			} else {
				sql = "SELECT PSOEXF AS PESO, TLLEXF AS TALLA FROM ALPHILDAT.RIAEXF WHERE NIGEXF = '" + document
						+ "' AND PSOEXF > 0 ORDER BY FECEXF DESC, HOREXF DESC";
				con.createStatement();
				st.executeQuery(sql);
				if (rs.next()) {
					result.setWeight(rs.getString("PESO") + " Kg");
					result.setHeight(rs.getString("TALLA") + " cm");
				}
			}

			rs.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return result;
	}
	
	
	@Override
	public ResponseCitaPaciente consecutivoCitaPaciente(String tipoDocumento, String documento) {
		
		List<struct_citapaciente> lcitapacientes = new ArrayList<struct_citapaciente>();
		ResponseCitaPaciente returnResult = new ResponseCitaPaciente(); 
		
		String sqlCita = "SELECT CCIPAC CITA FROM ALPHILDAT.RIAPAC r WHERE r.TIDPAC LIKE '%"+tipoDocumento+"%' AND VARCHAR( r.NIDPAC )LIKE '%"+documento+"%'";
		String sqlActualiza ="";
		struct_citapaciente rpaciente = new struct_citapaciente();
		 
		try (	DBAS400 db = new DBAS400();			
				Connection conexion = db.getCon();			
				Statement st = conexion.createStatement();
				ResultSet cstmt = st.executeQuery(sqlCita);
			) {	
			if (cstmt.next()) {
				long consecutivocita = cstmt.getLong("CITA") + 1;
				System.out.println("DATOS PACIENTE");

				sqlActualiza = "UPDATE ALPHILDAT.RIAPAC SET CCIPAC = " + consecutivocita + " WHERE TIDPAC LIKE '%"+tipoDocumento+"%' AND VARCHAR( NIDPAC )LIKE '%"+documento+"%'";
				System.out.println("Mensaje update reg RIAPAC "+ sqlActualiza);
				Statement statement = conexion.createStatement();
				int filasActualizadas = statement.executeUpdate(sqlActualiza);
				if (filasActualizadas > 0) {
	               System.out.println("Registros actualizados correctamente - RIAPAC");
				} else {
	               System.out.println("No se encontraron registros para actualizar - RIAPAC");
				}
				statement.close();
				System.out.println("ACTUALIZA DATOS PACIENTE");
				rpaciente.setNumeroCita(consecutivocita);		
				lcitapacientes.add(rpaciente);
				
				cstmt.close();
			}else {
				System.out.println("NO GUARDADO ");
			}
		} catch (Exception e) {
			returnResult.setErrorCode(500);
			returnResult.setErrorMessage(e.getMessage());
			e.printStackTrace();
		}
		returnResult.setCitaPacientes(lcitapacientes);	
		return returnResult;
	}



	@Override
	public ResponseListarPaciente filtradoPacientesActivos( StructPagination bodyfilter ) {
		
		
		ResponseListarPaciente returnResult = new ResponseListarPaciente(); 
		List<struct_paciente> lpacientes = new ArrayList<struct_paciente>();
		Pagination pagination = new Pagination();
		

		
		String select = " TIDPAC,NIDPAC,"
				+ "NM1PAC,NM2PAC,AP1PAC, AP2PAC,NIGING,MAIPAC, "
				+ "cast(Left(FEIING , 4)||'-'||substring(FEIING,5,2)||'-'||substring(FEIING, 7,2) as date) as fecha_ingreso "
				+ "FROM ALPHILDAT.RIAING ing INNER JOIN alphildat.RIAPAC pac ON ing.TIDING = pac.TIDPAC AND ing.NIDING = pac.NIDPAC";
		

		pagination.setPagination_data(bodyfilter);
		pagination.setCambiarNombreDatos(true);
		pagination.setsCambioNombre(cambiarNombresFiltroFlutter());
		
		String sql = pagination.sqlConsultaFilter(select, "NIGING DESC", "ESTING = '2'");
		
		String sqlCount = "COUNT(*) paginas FROM ALPHILDAT.RIAING ing INNER JOIN alphildat.RIAPAC pac ON ing.TIDING = pac.TIDPAC AND ing.NIDING = pac.NIDPAC";
		
		try (
				DBAS400 db = new DBAS400();
				Connection con = db.getCon();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql);
			) {
			while (rs.next()) {
					
				String fechaingreso =(rs.getString("fecha_ingreso") == null ) ? "": rs.getDate("fecha_ingreso").toString();
				
				struct_paciente pac = new struct_paciente();
				pac.setNumeroDocumento(rs.getLong("NIDPAC"));
				pac.setPrimerNombre(filtrarCampo(rs.getString("NM1PAC")));
				pac.setSegundoNombre(filtrarCampo(rs.getString("NM2PAC")));
				pac.setPrimerApellido(filtrarCampo(rs.getString("AP1PAC")));
				pac.setSegundoApellido(filtrarCampo(rs.getString("AP2PAC")));
				pac.setNumeroIngreso(rs.getLong("NIGING"));
				pac.setMail(rs.getString("MAIPAC").trim());
				pac.setFechaIngreso(fechaingreso);
				pac.setTipoDocumento(rs.getString("TIDPAC"));
				lpacientes.add(pac);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			returnResult.setErrorCode(-1);
			returnResult.setErrorMessage("Error:"+e.getMessage());
			e.printStackTrace();
		}
		

		returnResult.setPaginas(pagination.countPages(sqlCount, "ESTING = '2'"));
		returnResult.setListPacientes(lpacientes);
		
		
		return returnResult;

	}
	private ArrayList<StructCambioNombreDatos>cambiarNombresFiltroFlutter() {
		
		StructCambioNombreDatos sCambiarDoc = new StructCambioNombreDatos();
		StructCambioNombreDatos sCambiarTipoDoc = new StructCambioNombreDatos();
		StructCambioNombreDatos sCambiarPNombre = new StructCambioNombreDatos();
		StructCambioNombreDatos sCambiarSNombre = new StructCambioNombreDatos();
		StructCambioNombreDatos sCambiarPApellido = new StructCambioNombreDatos();
		StructCambioNombreDatos sCambiarSApellido = new StructCambioNombreDatos();
		StructCambioNombreDatos sCambiarIngreso = new StructCambioNombreDatos();
		
		ArrayList<StructCambioNombreDatos> cambio = new ArrayList<StructCambioNombreDatos>();
		
		
		sCambiarDoc.setDestinoColumna("NIDPAC");
		sCambiarDoc.setOrigenColumna("numeroDocumento");
		cambio.add(sCambiarDoc);
		
		
		sCambiarPNombre.setDestinoColumna("NM1PAC");
		sCambiarPNombre.setOrigenColumna("primerNombre");
		cambio.add(sCambiarPNombre);
		
		
		sCambiarSNombre.setDestinoColumna("NM2PAC");
		sCambiarSNombre.setOrigenColumna("segundoNombre");
		cambio.add(sCambiarSNombre);
		
		
		sCambiarPApellido.setDestinoColumna("AP1PAC");
		sCambiarPApellido.setOrigenColumna("primerApellido");
		cambio.add(sCambiarPApellido);
		
		sCambiarSApellido.setDestinoColumna("AP2PAC");
		sCambiarSApellido.setOrigenColumna("segundoApellido");
		cambio.add(sCambiarSApellido);
		
		sCambiarTipoDoc.setDestinoColumna("TIDPAC");
		sCambiarTipoDoc.setOrigenColumna("tipoDocumento");
		cambio.add(sCambiarTipoDoc);
		
		sCambiarIngreso.setDestinoColumna("NIGING");
		sCambiarIngreso.setOrigenColumna("numeroIngreso");
		cambio.add(sCambiarIngreso);

		
		return cambio;
	}
	 
}
