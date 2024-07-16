package com.shaio.his.anestesiaperioperatorio;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
//import java.time.LocalDate;
//import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
//import java.util.Map;
import org.springframework.stereotype.Service;
import com.shaio.his.AS400.DBAS400;
//import com.shaio.his.cobro.ResponseCobrarPerioperatorio;
//import com.shaio.his.cobro.StructCobrarPerioperatorio;


@Service
public class XAnestesiaPerioperatorio implements IAnestesiaPerioperatorio{
	
	@Override
	public ResponseAnestesiaPerioperatorio ListarCupsAnestesia() {
	 List<StructAnestesiaperioperatorio> lcups = new ArrayList<StructAnestesiaperioperatorio>();
	 ResponseAnestesiaPerioperatorio response = new ResponseAnestesiaPerioperatorio();
		 
		 String sql = "SELECT TRIM(A.CL1TMA) CODIGO, TRIM(B.DESCUP) DESCRIPCION, TRIM(B.RF5CUP) POSNOPOS  FROM ALPHILDAT.TABMAE A LEFT JOIN ALPHILDAT.RIACUP AS B ON TRIM(A.CL1TMA)=TRIM(B.CODCUP) WHERE A.TIPTMA='CUPANE' AND A.ESTTMA=''";
		  
			try (	DBAS400 db = new DBAS400();			
					Connection conexion = db.getCon();			
					Statement st = conexion.createStatement();
					ResultSet rs = st.executeQuery(sql);
				) {	
				while (rs.next()) {
					StructAnestesiaperioperatorio cups = new StructAnestesiaperioperatorio();
					cups.setCodcup(rs.getString("CODIGO"));
					cups.setDescup(rs.getString("DESCRIPCION"));	
					cups.setPosNopos(rs.getString("POSNOPOS"));
					lcups.add(cups);					
				}
				rs.close();
			} catch (Exception e) {
				response.setErrorCode(500);
				response.setErrorMessage(e.getMessage());
				e.printStackTrace();
			}		
			response.setData(lcups);
			return response;
	}
	
	
	@Override
	 public ResponseAnestesiaValidar ValidarDatosAnestesia(StructAnestesiavalidar cJsonDatos) {	
	 List<StructAnestesiavalidar> lingreso = new ArrayList<StructAnestesiavalidar>();
	 ResponseAnestesiaValidar response = new ResponseAnestesiaValidar();

	 Integer nroingreso = cJsonDatos.getIngreso();
	 String tipoide = cJsonDatos.getTipoidenpaciente();
	 String nroidentificacion = cJsonDatos.getIdentificacionpaciente();
	 String cups = cJsonDatos.getProcedimiento();
	 String especialidadprofesional = cJsonDatos.getEspecialidadmedico();
	 String medicorealiza = cJsonDatos.getRegistromedicorealiza();
	 String viadeingreso = cJsonDatos.getViaingreso();
	 String plandeingreso = cJsonDatos.getPlaningreso();
	 String cobrablenocobrable = cJsonDatos.getCobrable();
	 String textoclinico = cJsonDatos.getDescripcion();
	 String seccionpaciente = cJsonDatos.getSeccionString();
	 String habitacionpaciente = cJsonDatos.getCamaString();

	 System.out.println("VALIDAR ANESTESIA");
	 
	 	String sql = "SELECT NIGING FROM ALPHILDAT.RIAING r WHERE NIGING= '" + nroingreso + "' ";
		try (	DBAS400 db = new DBAS400();			
				Connection conexion = db.getCon();			
				Statement st = conexion.createStatement();
				ResultSet rs = st.executeQuery(sql);
			) {	
			StructAnestesiavalidar datingreso = new StructAnestesiavalidar();
			if (rs.next()) {
				datingreso.setIngreso(rs.getInt("NIGING"));
			}else {
				response.setErrorMessage("No existe ingreso");
			}
			rs.close();
			
			String sql10 = "SELECT TRIM((TRIM(NM1PAC) || ' ' || TRIM(AP1PAC))) AS NOMBREPACIENTE FROM ALPHILDAT.RIAPAC r WHERE r.TIDPAC LIKE '%"+tipoide+"%' AND VARCHAR( r.NIDPAC )LIKE '%"+nroidentificacion+"%'";
			Statement st10 = conexion.createStatement();
			ResultSet rs10 = st10.executeQuery(sql10);
			if (rs10.next()) {
				datingreso.setTipoidenpaciente(tipoide);	
				datingreso.setIdentificacionpaciente(nroidentificacion);	
				datingreso.setNombrePaciente(rs10.getString("NOMBREPACIENTE"));	
			}else {
				response.setErrorMessage("No existe paciente");
			}
			rs10.close();
			
			String sql2 = "SELECT TRIM(DESCUP) DESCRIPCION FROM ALPHILDAT.RIACUP r WHERE CODCUP= '" + cups + "' AND IDDCUP='0'";
			Statement st2 = conexion.createStatement();
			ResultSet rs2 = st2.executeQuery(sql2);
			if (rs2.next()) {
				datingreso.setProcedimiento(rs2.getString("DESCRIPCION"));	
			}else {
				response.setErrorMessage("No existe procedimiento");
			}
			rs2.close();
			
			String sql3 = "SELECT TRIM(DESESP) DESCRIPCION FROM ALPHILDAT.RIAESPE r WHERE CODESP= '" + especialidadprofesional + "' ";
			Statement st3 = conexion.createStatement();
			ResultSet rs3 = st3.executeQuery(sql3);
			if (rs3.next()) {
				datingreso.setEspecialidadmedico(rs3.getString("DESCRIPCION"));	
			}else {
				response.setErrorMessage("No existe especialidad médico");
			}
			rs3.close();
			
			String sql4 = "SELECT TRIM(USUARI) USUARIO FROM ALPHILDAT.RIARGMN r WHERE REGMED= '" + medicorealiza + "' AND ESTRGM=1";
			Statement st4 = conexion.createStatement();
			ResultSet rs4 = st4.executeQuery(sql4);
			if (rs4.next()) {
				datingreso.setRegistromedicorealiza(rs4.getString("USUARIO"));	
			}else {
				response.setErrorMessage("No existe registro médico");
			}
			rs4.close();
			
			String sql5 = "SELECT TRIM(DESVIA) DESCRIPCION FROM ALPHILDAT.RIAVIA r WHERE CODVIA= '" + viadeingreso + "'";
			Statement st5 = conexion.createStatement();
			ResultSet rs5 = st5.executeQuery(sql5);
			if (rs5.next()) {
				datingreso.setViaingreso(rs5.getString("DESCRIPCION"));	
			}else {
				response.setErrorMessage("No existe vía de ingreso");
			}
			rs5.close();
			
			String sql6 = "SELECT TRIM(DSCCON) DESCRIPCION FROM ALPHILDAT.FACPLNC r WHERE PLNCON= '" + plandeingreso + "'";
			Statement st6 = conexion.createStatement();
			ResultSet rs6 = st6.executeQuery(sql6);
			if (rs6.next()) {
				datingreso.setPlaningreso(rs6.getString("DESCRIPCION"));	
			}else {
				response.setErrorMessage("No existe plan paciente");
			}
			rs6.close();
			
			if (!cobrablenocobrable.isEmpty()) {
				if (cobrablenocobrable.equals("S") || cobrablenocobrable.equals("N")) {	
					datingreso.setCobrable(cobrablenocobrable);
				}else {
					response.setErrorMessage("No existe item cobrable");
				}	
			}else {
				response.setErrorMessage("No existe marca cobrable");
			}
			
			if (!textoclinico.isEmpty()) {
				datingreso.setDescripcion(textoclinico);
			}else {
				response.setErrorMessage("No existe descripción texto clínico");
			}
			
			if (!seccionpaciente.isEmpty()) {
				String sql7 = "SELECT TRIM(SECHAB) SECCION FROM ALPHILDAT.FACHAB r WHERE SECHAB= '" + seccionpaciente + "' AND NUMHAB= '" + habitacionpaciente + "' AND INGHAB= '" + nroingreso + "'";
				Statement st7 = conexion.createStatement();
				ResultSet rs7 = st7.executeQuery(sql7);
				if (rs7.next()) {
					datingreso.setHabitacionpaciente(seccionpaciente);	
				}else {
					response.setErrorMessage("No existe seccion para el ingreso");
				}
				rs7.close();
			}
			lingreso.add(datingreso);
		} catch (Exception e) {
			response.setErrorCode(500);
			response.setErrorMessage(e.getMessage());
			e.printStackTrace();
		}		
		response.setDataValidar(lingreso);
		return response;
	}
	
	@Override
	public ResponseAnestesiaGuardar GuardarDatosAnestesia(StructAnestesiaguardar cJsonDatos) {
	 List<StructAnestesiaguardar> lingreso = new ArrayList<StructAnestesiaguardar>();
	 ResponseAnestesiaGuardar response = new ResponseAnestesiaGuardar();

	 Integer nroingreso = cJsonDatos.getIngreso();
	 String tipoide = cJsonDatos.getTipoidenpaciente();
	 Integer nroidentificacion = cJsonDatos.getIdentificacionpaciente();
	 Integer consecutivocita = cJsonDatos.getConsecutivocita();
	 String especialidadprofesional = cJsonDatos.getEspecialidadmedico();
	 String cups = cJsonDatos.getProcedimiento();
	 String medicorealiza = cJsonDatos.getRegistromedicorealiza();
	 Integer nitplan = cJsonDatos.getNitingreso();
	 String viadeingreso = cJsonDatos.getViaingreso();
	 String plandeingreso = cJsonDatos.getPlaningreso();
	 String seccionpaciente = cJsonDatos.getSeccionString();
	 String habitacionpaciente = cJsonDatos.getCamaString();
	 String posonopos = cJsonDatos.getPosnopos();
	 String cobrablenocobrable = cJsonDatos.getCobrable();
	 String usuariodecreacion = cJsonDatos.getUsuariocreacion();
	 String programadecreacion = cJsonDatos.getProgramacreacion();
	 String textoclinico = cJsonDatos.getDescripcion();
	 Integer fechaCreacion = cJsonDatos.getFechaActual();
	 Integer HoraCreacion = cJsonDatos.getHoraActual();
	 String jsonInput="";
	 
	 String sql = "SELECT TIDING, NIDING, NIGING, ESTING, TRIM(PLAING) PLAN FROM ALPHILDAT.RIAING r WHERE NIGING= '" + nroingreso + "' ";
	 StructAnestesiaguardar ringreso = new StructAnestesiaguardar();
	 	 
		try (	DBAS400 db = new DBAS400();			
				Connection con = db.getCon();			
				Statement st = con.createStatement();
				ResultSet cstmt = st.executeQuery(sql);
			) {	
			if (cstmt.next()) {
				
				System.out.println("Inicia guardar");
				 
				jsonInput = "{\"tipoidenpaciente\":\"" + tipoide + "\", \"identificacionpaciente\":" + nroidentificacion + ", \"ingreso\":" + nroingreso + ", , \"consecutivocita\":" + consecutivocita + 
							", \"especialidadmedico\":\"" + especialidadprofesional + "\", \"procedimiento\":\"" + cups + "\", \"registromedicorealiza\":\"" + medicorealiza + "\", \"nitingreso\":" + 
							nitplan + ", \"viaingreso\":\"" + viadeingreso + "\", \"planingreso\":\"" + plandeingreso + "\", \"seccionpaciente\":\"" + seccionpaciente + "\", \"camapaciente\":\"" + 
							habitacionpaciente + "\", \"posnopos\":\"" + posonopos  + "\", \"cobrable\":\"" + cobrablenocobrable + "\", \"usuariocreacion\":\"" + usuariodecreacion + "\", \"programacreacion\":\"" + 
							programadecreacion + "\", \"fechacreacion\":" + fechaCreacion.toString() + ", \"horacreacion\":" + HoraCreacion.toString() + ", \"descripcion\":\"" + textoclinico + "\"}";
				
				CallableStatement cstmt1;
				cstmt1 = con.prepareCall("call ALPHILDAT.f_guardar_anestesia_perioperatorio(?,?)");                
				cstmt1.setString (1, jsonInput);
				cstmt1.registerOutParameter (2, Types.CHAR);  
				cstmt1.execute();            
				String variableRetorno = cstmt1.getString(2);
				 
				System.out.println("Datos guardados "+ variableRetorno);
				
				ringreso.setValidar(variableRetorno);		
				lingreso.add(ringreso);					
				cstmt1.close();
				
			}else {
				System.out.println("NO GUARDADO ");
			}
		} catch (Exception e) {
			response.setErrorCode(500);
			response.setErrorMessage(e.getMessage());
			e.printStackTrace();
		}
		response.setDataGuardar(lingreso);
		return response;
	}

}
