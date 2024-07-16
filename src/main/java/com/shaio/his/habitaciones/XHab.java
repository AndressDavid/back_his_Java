package com.shaio.his.habitaciones;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.shaio.his.AS400.DBAS400;




@Service
public class XHab implements IHabitaciones{

	
	@Override
	public ResponseHab ListarHab(String seccion) {
	 List<Struct_hab> lHab = new ArrayList();
	 ResponseHab response = new ResponseHab();
		 
		 String sql = "SELECT H.SECHAB AS SECCION, H.NUMHAB AS CAMA, H.INGHAB AS INGRESO, H.TIDHAB AS TIPO_DOC_PACIENTE, H.NIDHAB AS NUM_DOC_PACIENTE FROM ALPHILDAT.FACHAB H INNER JOIN ALPHILDAT.TABMAE S ON H.SECHAB=S.CL1TMA AND S.TIPTMA='SECHAB' WHERE H.IDDHAB<>'0'";
		 if (!seccion.equals("")) {
			 sql += " AND H.SECHAB='"+seccion+"'";
		 }
		 System.out.println(sql);
		// sql += " FETCH FIRST 50 ROWS ONLY ";
		 
			try (	DBAS400 db = new DBAS400();			
					Connection con = db.getCon();			
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery(sql);
				) {	
				while (rs.next()) {
					Struct_hab pac = new Struct_hab();
					pac.setSeccion(rs.getString("SECCION"));
					pac.setCama(rs.getString("CAMA"));	
					pac.setIngreso(rs.getString("INGRESO"));
					pac.setTipoDocumentoPaciente(rs.getString("TIPO_DOC_PACIENTE"));
					pac.setNumDocPaciente(rs.getString("NUM_DOC_PACIENTE"));
					lHab.add(pac);					
				}
				rs.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				response.setErrorCode(500);
				response.setErrorMessage(e.getMessage());
				e.printStackTrace();
			}		
			response.setData(lHab);
			return response;
	}
			
		@Override
			public ResponseDatosHab  ListarDatosHab(String seccion, String cama, float activo) {
			 List<Struct_datosHab> lListHab = new ArrayList();
			 ResponseDatosHab response = new ResponseDatosHab ();
				 
				 String sql = "SELECT H.SECHAB SECCION, H.NUMHAB CAMA, H.INGHAB INGRESO, H.TIDHAB TIPO_DOC_PACIENTE, H.NIDHAB NUM_DOC_PACIENTE, P.NM1PAC PRIMER_NOMBRE, P.NM2PAC SEGUNDO_NOMBRE, P.AP1PAC PRIMER_APELLIDO, P.AP2PAC SEGUNDO_APELLIDO, ih.IP_DISPOSITIVO, ih.MAC_DISPOSITIVO,  ih.ACTIVO FROM ALPHILDAT.FACHAB H INNER JOIN ALPHILDAT.IP_HABITACIONES ih ON H.SECHAB=ih.SECCION AND H.NUMHAB=ih.HABITACION INNER JOIN ALPHILDAT.TABMAE S ON H.SECHAB=S.CL1TMA AND S.TIPTMA='SECHAB' LEFT JOIN ALPHILDAT.RIAPAC P ON H.TIDHAB=P.TIDPAC AND H.NIDHAB=P.NIDPAC ";
				 
				 boolean usarWhere = true;
				 
				 if (!seccion.equals("")) {
					 sql += " WHERE H.SECHAB='"+seccion+"'";
					 usarWhere = false;
				 }
				 if (!cama.equals("")) {
					 sql += (usarWhere ? " WHERE" : " AND") + " H.NUMHAB='"+cama+"'";
					 usarWhere = false;
				 }
				 //if (!activo.equals(0)) {
				 if (activo >= 0) {
					 sql += (usarWhere ? " WHERE" : " AND") + " ih.ACTIVO="+activo+"";
					 usarWhere = false;
				 }
				 System.out.println(sql);
				// sql += " FETCH FIRST 50 ROWS ONLY ";
				 
					try (	DBAS400 db = new DBAS400();			
							Connection con = db.getCon();			
							Statement st = con.createStatement();
							ResultSet rs = st.executeQuery(sql);
						) {	
						while (rs.next()) {
							Struct_datosHab  pac = new Struct_datosHab();
							pac.setSeccion(rs.getString("SECCION"));
							pac.setCama(rs.getString("CAMA"));	
							pac.setIngreso(rs.getInt("INGRESO"));
							pac.setTipoDocumentoPaciente(rs.getString("TIPO_DOC_PACIENTE"));
							pac.setNumDocPaciente(rs.getString("NUM_DOC_PACIENTE"));
							pac.setPrimerNombre(rs.getString("PRIMER_NOMBRE"));
							pac.setSegundoNombre(rs.getString("SEGUNDO_NOMBRE"));
							pac.setPrimerApellido(rs.getString("PRIMER_APELLIDO"));
							pac.setSegundoApellido(rs.getString("SEGUNDO_APELLIDO"));
							pac.setIpDispositivo(rs.getString("IP_DISPOSITIVO"));
							pac.setMacDispositivo(rs.getString("MAC_DISPOSITIVO"));
							pac.setActivo(rs.getInt("ACTIVO"));
							lListHab .add(pac);					
						}
						rs.close();
					} catch (Exception e) {
						response.setErrorCode(500);
						response.setErrorMessage(e.getMessage());
						e.printStackTrace();
					}		
					response.setData(lListHab);
					return response;
			}
		
		
		@Override
		public ResponseGuardar guardarDatosHabitaciones(Struct_guardar requestBody) {
			
			JSONObject obj = new JSONObject(requestBody);
			
			String tipoDispositivo = obj.getString("tipoDispositivo");
			String ipDispositivo = obj.getString("ipDispositivo");
			String macDispositivo = obj.getString("macDispositivo");
			String seccion = obj.getString("seccion");
			String habitacion = obj.getString("habitacion");
			Number activo = obj.getInt("activo");
			String usuario = obj.getString("usuario");
			String programa = obj.getString("programa");

		    ResponseGuardar response = new ResponseGuardar("", 0, "");

		    String selectSql = "SELECT * FROM ALPHILDAT.IP_HABITACIONES WHERE SECCION='"+ seccion +"' AND HABITACION='"+ habitacion +"'";		    
		    String insertSql = "INSERT INTO ALPHILDAT.IP_HABITACIONES (IDIPHAB, TIPO_DISPOSITIVO, IP_DISPOSITIVO, MAC_DISPOSITIVO, SECCION, HABITACION, ACTIVO, USUARIO_CREA, PROGRAMA_CREA) VALUES (ALPHILDAT.GetUUID(), ?, ?, ?, ?, ?, ?, ?, ?)";
		    String updateSql = "UPDATE ALPHILDAT.IP_HABITACIONES SET TIPO_DISPOSITIVO=?, IP_DISPOSITIVO=?, MAC_DISPOSITIVO=?, ACTIVO=?, USUARIO_MODIFICA=?, PROGRAMA_MODIFICA=?, FECHA_HORA_MODIFICA=NOW() WHERE SECCION=? AND HABITACION=?";

		    try (DBAS400 db = new DBAS400();
		            Connection con = db.getCon();
		            PreparedStatement selectStmt = con.prepareStatement(selectSql)) {

		           try (ResultSet resultSet = selectStmt.executeQuery()) {
		               if (!resultSet.next()) {
		                   try (PreparedStatement insertStmt = con.prepareStatement(insertSql)) {
		                       insertStmt.setString(1, tipoDispositivo);
		                       insertStmt.setString(2, ipDispositivo);
		                       insertStmt.setString(3, macDispositivo);
		                       insertStmt.setString(4, seccion);
		                       insertStmt.setString(5, habitacion);
		                       insertStmt.setInt(6, activo.intValue());
		                       insertStmt.setString(7, usuario);
		                       insertStmt.setString(8, programa);

		                       int filasAñadidas = insertStmt.executeUpdate();
		                       if (filasAñadidas > 0) {
		                           System.out.println("Registros añadidos correctamente. IP_HABITACIONES");
		                       } else {
		                           System.out.println("No se agregaron correctamente los registros. IP_HABITACIONES");
		                       }
		                   }
		               } else {
		                   try (PreparedStatement updateStmt = con.prepareStatement(updateSql)) {
		                       updateStmt.setString(1, tipoDispositivo);
		                       updateStmt.setString(2, ipDispositivo);
		                       updateStmt.setString(3, macDispositivo);
		                       updateStmt.setInt(4, activo.intValue());
		                       updateStmt.setString(5, usuario);
		                       updateStmt.setString(6, programa);
		                       updateStmt.setString(7, seccion);
		                       updateStmt.setString(8, habitacion);

		                       int filasActualizadas = updateStmt.executeUpdate();
		                       if (filasActualizadas > 0) {
		                           System.out.println("Registros actualizados correctamente. IP_HABITACIONES");
		                       } else {
		                           System.out.println("No se encontraron registros para actualizar. IP_HABITACIONES");
		                       }
		                   }
		               }
		           }																									

		    } catch (Exception e) {
		        response.setErrorCode(500);
		        response.setErrorMessage(e.getMessage());
		        e.printStackTrace();
		    }

		    return response;
		}
}