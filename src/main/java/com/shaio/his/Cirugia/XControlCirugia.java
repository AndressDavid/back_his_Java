package com.shaio.his.Cirugia;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shaio.his.ResponseJson;
import com.shaio.his.AS400.DBAS400;
import com.shaio.his.common.StructComunEspecialidades;
import com.shaio.his.consecutivos.StructFiltroConsecutivoEvolucion;
import com.shaio.his.consecutivos.XConsecutivos;


@Service
public class XControlCirugia implements IControlCirugia{
	
    private int fechaActual() {
    	LocalDate locaDate = LocalDate.now();
    	DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyyMMdd");
    	return Integer.parseInt( locaDate.format(formato) );
    }

    private int horaActual() {
   	 LocalDateTime locaDateTime = LocalDateTime.now();

   
   	 String hours  = String.valueOf(locaDateTime.getHour());
   	 String minutes = String.valueOf(locaDateTime.getMinute());
   	 String seconds = String.valueOf(locaDateTime.getSecond());
     String  horaActualString =hours + minutes +seconds;
     
     String completarhora = CadenaConcatenarDerecha(horaActualString.length() , 6, "0", horaActualString);
     return Integer.parseInt(completarhora);
   }

	@Override
	public ResponseListaCirugia extraerListaCirugiasPaciente(StructuraBusqueda datosBusqueda) {
		
		ResponseListaCirugia responseListaCirugia = new ResponseListaCirugia();
		List<StructTablaControlCirugia> lcCirugiaspaciente = new ArrayList<StructTablaControlCirugia>();
		
		
		String filtroIngreso= (datosBusqueda.getIngreso() != 0) ? " AND NINORD = " + datosBusqueda.getIngreso() : "";
		String filtroVia = (datosBusqueda.getVia().equals("")) ? "" : " AND CODVIA = '" + datosBusqueda.getVia() + "'";
		String filtroEspecialidad = (datosBusqueda.getEspecialidad().equals("")) ? "" : " AND CODORD = " + datosBusqueda.getEspecialidad();
		
		String filtroDocumento = (datosBusqueda.getDocumento().equals("") || datosBusqueda.getTipoDocumento().equals("")) ? "" : " AND TIDORD = '" + datosBusqueda.getTipoDocumento() + "' AND NIDORD=" + datosBusqueda.getDocumento();
		
		String filtroFecha = (datosBusqueda.isTodas() || datosBusqueda.getFecha().equals("") ) ? "" : " AND FERORD >= '"+datosBusqueda.getFecha().replace("-", "")+"'";
		
		
		String sql ="SELECT NM1PAC, NM2PAC,AP1PAC, AP2PAC, FERORD,HRLORD, NUMHAB, SECHAB, DESVIA, DESESP, ESTORD, DESCUP, CCIORD, TIDORD, NIDORD,FEIING  * 1000000 as FEIING , HORING, NINORD "
				+ "FROM ALPHILDAT.jordcvcec WHERE CODCUP = '22' " +filtroIngreso +  filtroVia + filtroEspecialidad + filtroDocumento + filtroFecha  ;
		
		
		if(filtroFecha.equals("")) {
			if( (filtroIngreso.equals("") && filtroDocumento.equals("")) && ((!filtroVia.equals("") || !filtroEspecialidad.equals("") )  || (filtroVia.equals("") || filtroEspecialidad.equals("") )) ) {
				
				responseListaCirugia.setErrorCode(0);
				responseListaCirugia.setErrorCode(500);
				responseListaCirugia.setErrorMessage("Ingreso o Documento obligatorio.");
				
				return responseListaCirugia;
			}
		}
		
		
		try (
				DBAS400 db = new DBAS400();
				Connection con = db.getCon();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql);
			) {
			while (rs.next()) {
				
				
				StructTablaControlCirugia sTablaDescripcionQuirugica = new StructTablaControlCirugia();
				
				long  fechaHora = rs.getLong ("FEIING") + rs.getLong("HORING");
				String fechaString = String.valueOf(fechaHora) ;
				
				
				sTablaDescripcionQuirugica.setNombres( rs.getString("NM1PAC").trim() +" "+rs.getString("NM2PAC").trim() );
				sTablaDescripcionQuirugica.setApellidos( rs.getString("AP1PAC").trim()  +" "+rs.getString("AP2PAC").trim()  );
				sTablaDescripcionQuirugica.setFecha( rs.getString("FERORD") );
				sTablaDescripcionQuirugica.setHora(  rs.getInt("HRLORD")  );
				sTablaDescripcionQuirugica.setCama( rs.getString("SECHAB").trim() +" "+rs.getNString("NUMHAB").trim() );
				sTablaDescripcionQuirugica.setVia( rs.getNString("DESVIA").trim()  );
				sTablaDescripcionQuirugica.setEspecialidad( rs.getNString("DESESP").trim()  );
				sTablaDescripcionQuirugica.setEstado( rs.getNString("ESTORD") );
				sTablaDescripcionQuirugica.setTipoAtencion( rs.getNString( "DESCUP" ).trim()  );
				sTablaDescripcionQuirugica.setCita( rs.getInt("CCIORD") );
				sTablaDescripcionQuirugica.setTipoDocumento( rs.getString("TIDORD") );
				sTablaDescripcionQuirugica.setDocumento( rs.getString("NIDORD") );
				sTablaDescripcionQuirugica.setFechaIngreso( fechaString );
				sTablaDescripcionQuirugica.setIngreso( rs.getInt("NINORD") );
				
				lcCirugiaspaciente.add(sTablaDescripcionQuirugica);
				
			}
		} catch (Exception e) {
			
			responseListaCirugia.setErrorCode(500);
			responseListaCirugia.setErrorMessage("Error al insertar" + e.toString());
			
			e.printStackTrace();
		}
		
		
		
		responseListaCirugia.setErrorCode(0);
		responseListaCirugia.setDatos(lcCirugiaspaciente);
		
		return responseListaCirugia;
	}

	@Override
	public ResponseListaCirugia extaerConsumoSalaCirugia(StructFiltroConsumoSala body) {
		
		
		ResponseListaCirugia responseListaCirugia = new ResponseListaCirugia();
		List<String> jsonConsumo = new ArrayList<String>();
		
	
		String sql ="SELECT F_OBTENER_CONSUMOS_SALAS('"+ConvertToJson(body)+"') FROM SYSIBM.SYSDUMMY1";
		
		try (
				DBAS400 db = new DBAS400();
				Connection con = db.getCon();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql);
			) {
			while (rs.next()) {
				
				jsonConsumo.add(rs.getString("00001") );
				
			}
		} catch (Exception e) {
			responseListaCirugia.setErrorCode(500);
			responseListaCirugia.setErrorMessage(e.toString());
			e.printStackTrace();
		}
		
		responseListaCirugia.setErrorCode(0);
		responseListaCirugia.setDatos(jsonConsumo);
		
		return responseListaCirugia;
	}
	
	private String ConvertToJson(Object body) {
		ObjectMapper mapper = new ObjectMapper();
		
		try {
		    return mapper.writeValueAsString(body);
		}
		catch (JsonProcessingException  e) {
		    // catch various errors
		    e.printStackTrace();
		    return "";
		}
	}

	
	@Override
	public ResponseJsonGuardadoCirugia guardarControlQuirurgico(StructGuardarControlQuirurgico body) {
		
		//RIA133WEB
		
		
		ResponseJsonGuardadoCirugia response = new ResponseJsonGuardadoCirugia();
		
		
		XConsecutivos consecutivo = new XConsecutivos();
		StructFiltroConsecutivoEvolucion filtro = new StructFiltroConsecutivoEvolucion();
		
	
		filtro.setIngreso( Integer.parseInt(body.getIngreso()) );
		filtro.setCama( body.getInformacionPaciente().getcHabita() );
		filtro.setEstado( Integer.parseInt(body.getInformacionPaciente().getcEstado()) );
		filtro.setPrograma(body.getPrograma());
		filtro.setUsuario(body.getUsuario());
		filtro.setSeccion( body.getInformacionPaciente().getcSeccion() );
		
		
		
		int consecutivoEvolucion = consecutivo.obtenerConsecEvolucion(filtro);
		int consecutivoCita = 0;
		int [] respuetaConsecutivoPaciente = null;
		
		
		String dxComplicacion =  body.getActoQuirurgico().getDxComplicacion().split("-")[0];
		
		
		boolean medicoTratante = (body.getInformacionPaciente().getcEspecialidadMedicoTratante().equals("310") || body.getInformacionPaciente().getcEspecialidadMedicoTratante().equals("160")) ? true : false;
		boolean medicoSaliente = (body.getEspecialidadUsuario().equals("310") || body.getEspecialidadUsuario().equals("160")) ? true : false;
		
		if(body.getActoQuirurgico().getOtraSala() == "02" && (medicoTratante || medicoSaliente ) ) {
			consecutivoCita = validarConsecutivoCita( Integer.parseInt(body.getIngreso()) );
		}
		

		if(consecutivoCita < 1) {
			respuetaConsecutivoPaciente = consecutivoXPaciente(body.getInformacionPaciente().getcTipId(), body.getInformacionPaciente().getnNumId());
			consecutivoCita=  respuetaConsecutivoPaciente[0];
		}
		
		
		boolean riapacConsecutivo = actualizarRiapacConsecutivo(respuetaConsecutivoPaciente,  consecutivoCita,  body,  consecutivoEvolucion);
		
		if(!riapacConsecutivo) {
			response.setErrorCode(500);
			response.setErrorMessage("Error al generar consecutivo RIAPAC");
			return response;
		}
		
		
		try(
			DBAS400 db = new DBAS400();
			Connection con = db.getCon();
		)  {
			
			con.setAutoCommit(false);
			int [] consecutivoConsulta = generarConsecutivoConsulta(body);
			
			boolean evoluciones =  guardarEvoluciones(consecutivoConsulta, consecutivoCita,  body,  consecutivoEvolucion, con );
			
			if(!evoluciones) {
				System.out.println("RIA133: Error al guardar la evolucion");
				response.setErrorCode(500);
				response.setErrorMessage("Error Error al guardar la evolucion");
				con.rollback();
				con.close();
				return response;
			}
			
			
			boolean anaEpi = guardarAnaEpi(body,consecutivoEvolucion, con );
			
			if(!anaEpi) {
				System.out.println("RIA133: Error al guardar la epicrisis");
				response.setErrorCode(500);
				response.setErrorMessage("Error Error al guardar la epicrisis");
				con.rollback();
				con.close();
				return response;
			}
			
			boolean riaHis =  guardarRiaHis(body,consecutivoEvolucion,consecutivoCita, dxComplicacion, con, consecutivoConsulta );
			
			if(!riaHis) {
				System.out.println("RIA133: Error al guardar la historia clinica");
				response.setErrorCode(500);
				response.setErrorMessage("Error al guardar la historia clinica");
				con.rollback();
				con.close();
				return response;
			}
			
			boolean factura =  procedimientoFacturas( body,consecutivoConsulta,  con );
			
			if(!factura) {
				System.out.println("RIA133: Error al guardar la factura");
				response.setErrorCode(500);
				response.setErrorMessage("Error al guardar la factura");
				con.rollback();
				con.close();
				return response;
			}
			
			boolean riaord = actualizarRiaord( body,  consecutivoConsulta, consecutivoCita, con);
			
			if(!riaord) {
				System.out.println("RIA133: Error al guardar la orden");
				response.setErrorCode(500);
				response.setErrorMessage("Error al guardar la orden");
				con.rollback();
				con.close();
				return response;
			}
			
			boolean guardarRips =  guardarRips( body,  con,  consecutivoConsulta );
			
			if(!guardarRips) {
				System.out.println("RIA133: Error al generar RIPS");
				response.setErrorCode(500);
				response.setErrorMessage("Error al generar RIPS");
				con.rollback();
				con.close();
				return response;
			}
			
			boolean actualizarFaccirh =  actualizarFaccirh( body,  consecutivoConsulta,  con );
			
			if(!actualizarFaccirh) {
				System.out.println("RIA133: Error al guardar FACRIS");
				response.setErrorCode(500);
				response.setErrorMessage("Error al guardar FACRIS");
				con.rollback();
				con.close();
				return response;
			}
			
			boolean medicamentos = guardarMedicamentos( body,  consecutivoConsulta,   consecutivoCita, con);
			
			if(!medicamentos) {
				System.out.println("RIA133: Error al guardar los medicamentos");
				response.setErrorCode(500);
				response.setErrorMessage("Error al guardar los medicamentos");
				con.rollback();
				con.close();
				return response;
			}
			
			con.commit();
			
			
			
			boolean liquidacion = crearLiquidacion( body,consecutivoConsulta, consecutivoCita ) ;
			
			if(!liquidacion) {
				response.setErrorCode(500);
				response.setErrorMessage("Error al generar la liquidación");
				return response;
			}
			
			boolean centroCostos = actualizarRecursos(body,  con, consecutivoConsulta,  consecutivoCita);
			
			if(!centroCostos) {
				System.out.println("RIA133: Error al actualizar centro de costos");
				response.setErrorCode(500);
				response.setErrorMessage("Error al actualizar centro de costos");
				return response;
			}
			
			boolean cierresAmbulatorios =  cierreAmbulatorio( body,  con, consecutivoConsulta,consecutivoCita);
			
			if(!cierresAmbulatorios) {
				System.out.println("RIA133: Error al actualizar el cierre ambulatorio");
				response.setErrorCode(500);
				response.setErrorMessage("Error al actualizar el cierre ambulatorio");
				return response;
			}
			
			con.commit();
			con.close();
			
			response.setConsecCita(String.valueOf(consecutivoConsulta[0]));
			response.setConsecCons(String.valueOf(consecutivoCita));
			response.setFechaHora(obtenerfechaHoraRiaOrd( body, consecutivoConsulta[0],  consecutivoCita ));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		response.setErrorCode(0);
		response.setErrorMessage("Se guardó la descripción quirúrgica con éxito.");
		
		return response;
	}
	
	private String obtenerfechaHoraRiaOrd(StructGuardarControlQuirurgico body,int consecutivoConsulta, int consecutivoCita ) {
		String fecha="";
		
		String sql="SELECT "
				+ " SUBSTRING(FERORD, 1,4)||'-'||SUBSTRING(FERORD, 5 ,2)||'-'||SUBSTRING(FERORD, 7 ,2)FECHA, "
				+ " TIME('00:00:00' ) + CAST(HRLORD AS DEC(6,0)) HORA"
				+ " FROM RIAORD WHERE NINORD =? AND CCOORD  = ? AND CCIORD =?";
		
		try(
			DBAS400 db = new DBAS400();
			Connection con = db.getCon();
			PreparedStatement  pt = con.prepareStatement(sql);
		) {
			
			pt.setString(1, body.getIngreso());
			pt.setInt(2, consecutivoConsulta);
			pt.setInt(3, consecutivoCita);
			
			
			ResultSet rs = pt.executeQuery();
			
			while( rs.next() ) {
				fecha = rs.getString("FECHA") +" "+rs.getString("HORA") ;
			}
			
		} catch (Exception e) {
			System.out.println("RIA133: "+e.toString());
			e.printStackTrace();
			return "ERR";
		}
		System.out.println("fecha {{"+fecha+"}}");
		return fecha;
	}
	
	private boolean guardarMedicamentos(StructGuardarControlQuirurgico body, int [] respuetaConsecutivoPaciente,  int consecutivoCita, Connection con) {
		
		String sqlInserMedicamentos ="INSERT INTO riahis (nroing,concon,indice,subind,codigo,subhis,suborg,consec,conhis,descri,nitent,tidhis,nidhis,usrhis,pgmhis,fechis,horhis) "
				+ "VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";
		
		int consecutivoLinea = 301;
		for( StructMedicamentos medicamento : body.getMedicamentos() ) {

			
			try (
				PreparedStatement psInserMedicamentos = con.prepareStatement(sqlInserMedicamentos);
			) {
				
				String descripcion ="Cantidad: "+ medicamento.getCantidad();
				
				psInserMedicamentos.setString( 1, body.getIngreso() );
				psInserMedicamentos.setInt(2, consecutivoCita);
				psInserMedicamentos.setInt(3, 45);
				psInserMedicamentos.setInt(4,1);
				psInserMedicamentos.setInt(5, 2);	
				psInserMedicamentos.setInt(6, 0);
				psInserMedicamentos.setString(7, medicamento.getCodigo());
				psInserMedicamentos.setInt(8, consecutivoLinea);
				psInserMedicamentos.setInt(9, 0);
				psInserMedicamentos.setString(10, descripcion);
				psInserMedicamentos.setInt(11, body.getInformacionPaciente().getnEntidad());
				psInserMedicamentos.setString(12, body.getInformacionPaciente().getcTipId());
				psInserMedicamentos.setString(13, body.getInformacionPaciente().getnNumId());
				psInserMedicamentos.setString(14, body.getUsuario());
				psInserMedicamentos.setString(15, body.getPrograma());
				psInserMedicamentos.setInt(16, fechaActual());
				psInserMedicamentos.setInt(17, horaActual());
				
				
				int rsInserMedicamentos = psInserMedicamentos.executeUpdate();
				
				if(rsInserMedicamentos < 1) {
					System.out.println("RIA133: No se guardo ningun medicamento");
					return false;
				}
				
			} catch (Exception e) {
				System.out.println("RIA133: "+e.toString());
				e.printStackTrace();
				return false;
			}
		}
		
		return true;
	}
	
	private boolean actualizarRiaord(StructGuardarControlQuirurgico body, int [] respuetaConsecutivoPaciente,  int consecutivoCita, Connection con) {
		
		String tipoDocumento = body.getInformacionPaciente().getcTipId();
		String documento = body.getInformacionPaciente().getnNumId();
		int ingreso = Integer.parseInt(body.getIngreso());
		int consecutivoConsulta = respuetaConsecutivoPaciente[0];
		int consecutivocita = consecutivoCita;
		String codigo = "22";
		int estado =3;
		String especialidadCirujano = "";
		String registroCirujano="";
		String regMedicoRealiza = obtenerRegistroMedico(body.getUsuario());
		
		for(StructProcedimientos procedimiento : body.getProcedimientos()) {
			especialidadCirujano = procedimiento.getEspecialdad_cirujano();
			registroCirujano = procedimiento.getRegistro_cirujano();
			break;
		}
		
		if(regMedicoRealiza.equals("")) {
			System.out.println("RIA133: No obtuvo registreo medico");
			return false;
		}
		
		String sqlRiaIng = "SELECT enting, TRIM(viaing) viaing, TRIM(plaing) plaing, FEIING, HORING, FEEING, HREING, TRIM(ESTING) ESTING FROM RIAING WHERE NIGING=? ORDER BY enting DESC FETCH FIRST 1 ROWS ONLY";
		
		String via="";
		String plan ="";
		int enting=0;
		int fechaIngreso=0;
		int horaIngreso=0;
		int fechaEgreso=0;
		int horaEgreso=0;
		int estadoIngreso=0;	
		
		
		try (
				DBAS400 db = new DBAS400();
				Connection conRia = db.getCon();
				PreparedStatement psRiaIng = conRia.prepareStatement(sqlRiaIng);
			) {
			
			psRiaIng.setInt(1, ingreso );
			
			ResultSet rsRiaIng = psRiaIng.executeQuery();
			
			while( rsRiaIng.next() ) {
				via= rsRiaIng.getString("viaing");
				plan = rsRiaIng.getString("plaing");
				enting= rsRiaIng.getInt("enting");
				fechaIngreso= rsRiaIng.getInt("FEIING");
				horaIngreso= rsRiaIng.getInt("HORING");
				fechaEgreso= rsRiaIng.getInt("FEEING");
				horaEgreso= rsRiaIng.getInt("HREING");
				estadoIngreso= rsRiaIng.getInt("ESTING");
			}
			
			
		} catch (Exception e) {
			System.out.println("RIA133: "+e.toString());
			e.printStackTrace();
			return false;
		}
		
		if(consecutivocita > 1) {
			
			int count =0;
			String sqlRiaord = "SELECT * FROM RIAORDLI WHERE NINORD=? AND CCIORD=?";
			try (
					DBAS400 db = new DBAS400();
					Connection conRia = db.getCon();
					PreparedStatement psRiaord = conRia.prepareStatement(sqlRiaord);
				) {
				
				psRiaord.setInt(1, ingreso );
				psRiaord.setInt(2, consecutivocita );
				
				ResultSet rsRiaord = psRiaord.executeQuery();
				
				
				while( rsRiaord.next() ) {
					count++;
				}
				
			} catch (Exception e) {
				System.out.println("RIA133: "+e.toString());
				e.printStackTrace();
				return false;
			}
			
			
			if(count < 1) {
				String sqlInsertRiaIng="INSERT INTO RIAORD (tidord,nidord,ninord,ccoord,cciord,codord,coaord,fcoord,frlord,hocord,rmrord,ferord,hrlord,estord,entord,viaord,plaord,usrord,pgmord,fecord,horord) "
						+ "VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";
				try (
					PreparedStatement psInsertRiaIng = con.prepareStatement(sqlInsertRiaIng);
				) {
					
					psInsertRiaIng.setString(1, tipoDocumento );
					psInsertRiaIng.setString(2, documento );
					psInsertRiaIng.setInt(3, ingreso );
					psInsertRiaIng.setInt(4, consecutivoConsulta );
					psInsertRiaIng.setInt(5, consecutivocita );
					psInsertRiaIng.setString(6, especialidadCirujano );
					psInsertRiaIng.setString(7, codigo );
					psInsertRiaIng.setInt(8, fechaActual() );
					psInsertRiaIng.setInt(9, fechaActual() );
					psInsertRiaIng.setInt(10, horaActual() );
					psInsertRiaIng.setString(11, regMedicoRealiza );
					psInsertRiaIng.setInt(12, fechaActual() );
					psInsertRiaIng.setInt(13, horaActual() );
					psInsertRiaIng.setInt(14, estado );
					psInsertRiaIng.setInt(15, enting );
					psInsertRiaIng.setString(16, via );
					psInsertRiaIng.setString(17, plan );
					psInsertRiaIng.setString(18, body.getUsuario());
					psInsertRiaIng.setString(19, body.getPrograma());
					psInsertRiaIng.setInt(20, fechaActual());
					psInsertRiaIng.setInt(21, horaActual());
					
					int rsInsertRiaIng = psInsertRiaIng.executeUpdate();
						
					if(rsInsertRiaIng < 1) {
						System.out.println("RIA133: no inserto en la RIAORD ");
						return false;
					}
					
					
				} catch (Exception e) {
					System.out.println("RIA133: "+e.toString());
					e.printStackTrace();
					return false;
				}
			}else {
				String sqlUpdateRiaord ="UPDATE RIAORD SET "
						+ "CODORD=?,"
						+ "COAORD=?,"
						+ "RMRORD=?,"
						+ "FERORD=?,"
						+ "HRLORD=?,"
						+ "ESTORD=?,"
						+ "UMOORD=?,"
						+ "PMOORD=?,"
						+ "FMOORD=?,"
						+ "HMOORD=?"
						+ "WHERE NINORD=? AND CCIORD=?";
				
				try (
					PreparedStatement pslUpdateRiaord = con.prepareStatement(sqlUpdateRiaord);
				) {
					
					pslUpdateRiaord.setInt(1, consecutivoConsulta );
					pslUpdateRiaord.setString(2, "22");
					pslUpdateRiaord.setString(3, regMedicoRealiza);
					pslUpdateRiaord.setInt(4, fechaActual() );
					pslUpdateRiaord.setInt(5, horaActual() );
					pslUpdateRiaord.setInt(6, estado );
					pslUpdateRiaord.setString(7, body.getUsuario());
					pslUpdateRiaord.setString(8, body.getPrograma());
					pslUpdateRiaord.setInt(9, fechaActual());
					pslUpdateRiaord.setInt(10, horaActual());
					pslUpdateRiaord.setInt(11, ingreso);
					pslUpdateRiaord.setInt(12, consecutivocita);
					
					int rslUpdateRiaord = pslUpdateRiaord.executeUpdate();
					
					if(rslUpdateRiaord > 1) {
						System.out.println("RIA133: RIAORD trato de actualizar "+rslUpdateRiaord+" registros" );
						return false;
					}
					
					if(rslUpdateRiaord < 1) {
						System.out.println("RIA133: no actualizo RIAORD");
						return false;
					}
					
					
				} catch (Exception e) {
					System.out.println("RIA133: "+e.toString());
					e.printStackTrace();
					return false;
				}
			}
			
		}else {
			String sqlInsertRiaIng="INSERT INTO RIAORD(tidord,nidord,ninord,ccoord,cciord,codord,coaord,fcoord,frlord,hocord,rmrord,ferord,hrlord,estord,entord,viaord,plaord,usrord,pgmord,fecord,horord) "
					+ "VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";
			try (
				PreparedStatement psInsertRiaIng = con.prepareStatement(sqlInsertRiaIng);
			) {
				
				psInsertRiaIng.setString(1, tipoDocumento );
				psInsertRiaIng.setString(2, documento );
				psInsertRiaIng.setInt(3, ingreso );
				psInsertRiaIng.setInt(4, consecutivoConsulta );
				psInsertRiaIng.setInt(5, consecutivocita );
				psInsertRiaIng.setString(6, especialidadCirujano );
				psInsertRiaIng.setString(7, codigo );
				psInsertRiaIng.setInt(8, fechaActual() );
				psInsertRiaIng.setInt(9, fechaActual() );
				psInsertRiaIng.setInt(10, horaActual() );
				psInsertRiaIng.setString(11, regMedicoRealiza );
				psInsertRiaIng.setInt(12, fechaActual() );
				psInsertRiaIng.setInt(13, horaActual() );
				psInsertRiaIng.setInt(14, estado );
				psInsertRiaIng.setInt(15, enting );
				psInsertRiaIng.setString(16, via );
				psInsertRiaIng.setString(17, plan );
				psInsertRiaIng.setString(18, body.getUsuario());
				psInsertRiaIng.setString(19, body.getPrograma());
				psInsertRiaIng.setInt(20, fechaActual());
				psInsertRiaIng.setInt(21, horaActual());
				
				int rsInsertRiaIng = psInsertRiaIng.executeUpdate();
					
				if(rsInsertRiaIng < 1) {
					System.out.println("RIA133: no inserto en la RIAORD");
					return false;
				}
				
			} catch (Exception e) {
				System.out.println("RIA133: "+e.toString());
				e.printStackTrace();
				return false;
			}
		}
		
		String sqlSelectRiaIng ="SELECT * FROM RIAINGTL01 WHERE NIGINT=?";
		int countRia = 0;
		try (
				DBAS400 db = new DBAS400();
				Connection conRia = db.getCon();
				PreparedStatement psRiaIngSelect = conRia.prepareStatement(sqlSelectRiaIng);
			) {
			
			psRiaIngSelect.setInt(1, ingreso );
			
			ResultSet rsRiaIng = psRiaIngSelect.executeQuery();
			
			while( rsRiaIng.next() ) {
				countRia++;
			}
			
		} catch (Exception e) {
			System.out.println("RIA133: "+e.toString());
			e.printStackTrace();
			return false;
		}
		
		if(countRia > 0) {
			
			String sqlUpdateRiaIng="UPDATE RIAINGT SET "
					+ "DPTINT=?,"
					+ "MEDINT=?,"
					+ "UMOINT=?,"
					+ "PMOINT=?,"
					+ "FMOINT=?,"
					+ "HMOINT=?"
					+ "WHERE NIGINT=?";
			
			try (
					
				PreparedStatement psUpdateRiaIng = con.prepareStatement(sqlUpdateRiaIng);
			) {
				
				psUpdateRiaIng.setString(1, especialidadCirujano  );
				psUpdateRiaIng.setString(2, registroCirujano  );
				psUpdateRiaIng.setString(3, body.getUsuario());
				psUpdateRiaIng.setString(4, body.getPrograma());
				psUpdateRiaIng.setInt(5, fechaActual());
				psUpdateRiaIng.setInt(6, horaActual());
				psUpdateRiaIng.setInt(7, ingreso);
				
				int rsUpdateRiaIng = psUpdateRiaIng.executeUpdate();
				
				if(rsUpdateRiaIng > 1) {
					System.out.println("RIA133: RIAINGT trato de actualizar "+rsUpdateRiaIng+" registros" );
					return false;
				}
				
				if(rsUpdateRiaIng < 1) {
					System.out.println("RIA133: no actualizo RIAING");
					return false;
				}
				
				
			} catch (Exception e) {
				System.out.println("RIA133: "+e.toString());
				e.printStackTrace();
				return false;
			}
			
		}else {
			
			String sqlInsertRiaIng = "INSERT INTO RIAINGT (nidint,nigint,dptint,medint,usrint,pgmint,fecint,horint) "
					+ "VALUES ( ?,?,?,?,?,?,?,? )";
			
			try (	
				PreparedStatement psInsertRiaIng = con.prepareStatement(sqlInsertRiaIng);
			) {
				
				psInsertRiaIng.setString(1, documento );
				psInsertRiaIng.setInt(2, ingreso );
				psInsertRiaIng.setString(3, especialidadCirujano  );
				psInsertRiaIng.setString(4, registroCirujano  );
				psInsertRiaIng.setString(5, body.getUsuario());
				psInsertRiaIng.setString(6, body.getPrograma());
				psInsertRiaIng.setInt(7, fechaActual());
				psInsertRiaIng.setInt(8, horaActual());
				
				int rsInsertRiaIng = psInsertRiaIng.executeUpdate();
				
				if( rsInsertRiaIng < 1 ) {
					System.out.println("RIA133: no inserto RIAINGL");
					return false;
				}
				
				
			} catch (Exception e) {
				System.out.println("RIA133: "+e.toString());
				e.printStackTrace();
				return false;
			}
		}
		
		String  sqlInsertRiaingtd ="INSERT INTO riaingtd (ingdme,secdme,camdme,meddme,ftrdme,htrdme,cnsdme,clidme,usrdme,pgmdme,fecdme,hordme) "
				+ "VALUES ( ?,?,?,?,?,?,?,?,?,?,?,? )";
		
		try (
			PreparedStatement psInsertRiaingtd = con.prepareStatement(sqlInsertRiaingtd);
		) {
			
			String tempCama = ( body.getActoQuirurgico().getSalaCirugia().equals("") ) ? body.getActoQuirurgico().getOtraSala() : body.getActoQuirurgico().getSalaCirugia() ;
			
			String cama= tempCama.substring( 2, tempCama.length() );
			String seccion= tempCama.substring( 0, 2 );
			
			int registroMedico = Integer.parseInt(obtenerRegistroMedico( body.getUsuario()));
			
			psInsertRiaingtd.setInt(1, ingreso);
			psInsertRiaingtd.setString(2, seccion);
			psInsertRiaingtd.setString(3, cama);
			psInsertRiaingtd.setInt(4, registroMedico  );
			psInsertRiaingtd.setInt(5, fechaActual());
			psInsertRiaingtd.setInt(6, horaActual());
			psInsertRiaingtd.setInt(7, 1);
			psInsertRiaingtd.setInt(8, 1);
			psInsertRiaingtd.setString(9, body.getUsuario());
			psInsertRiaingtd.setString(10, body.getPrograma());
			psInsertRiaingtd.setInt(11, fechaActual());
			psInsertRiaingtd.setInt(12, horaActual());
			
			
			
			System.out.println("RIA133 ===>: INSERT INTO riaingtd (ingdme,secdme,camdme,meddme,ftrdme,htrdme,cnsdme,clidme,usrdme,pgmdme,fecdme,hordme) VALUES ( "+ingreso+",'"+seccion+"','"+cama+"'"
					+ ","+registroMedico+","+fechaActual()+","+horaActual()+",1,1,'"+body.getUsuario()+"','"+body.getPrograma()+"',"+fechaActual()+","+horaActual()+" )");
			
			int rsInsertRiaingtd = psInsertRiaingtd.executeUpdate();
			
		
			
			if(rsInsertRiaingtd < 1) {
				System.out.println("RIA133: no inserto RIAINGLD");
				return false;
			}
			
			
		} catch (Exception e) {
			System.out.println("RIA133: "+e.toString());
			e.printStackTrace();
			return false;
		}
		
		String fechaIng = String.valueOf(fechaIngreso)+"-"+String.valueOf(horaIngreso);
		String fechaEgre = String.valueOf(fechaEgreso)+"-"+String.valueOf(horaEgreso);
		
		boolean guardarHistorico = guardaIngresoHistorico( body,  con, respuetaConsecutivoPaciente, consecutivoCita,plan,fechaIng, fechaEgre, estadoIngreso );
		
		if(!guardarHistorico) {
			System.out.println("RIA133: Error al insertar el historico del ingreso");
			return false;
		}
		
		return true;
	}
	
	
	private boolean cierreAmbulatorio(StructGuardarControlQuirurgico body, Connection con, int [] respuetaConsecutivoPaciente, int consecutivoCita)  {
		
		String sqlTabMae = "SELECT TRIM(DE2TMA) DE2TMA FROM TABMAE  WHERE TIPTMA =? AND CL1TMA=? AND ESTTMA=? ";
		
		String datoTabMae="";
		try (
			DBAS400 db = new DBAS400();
			Connection conMae = db.getCon();
			PreparedStatement psMae = conMae.prepareStatement(sqlTabMae);
		) {
			
			psMae.setString(1, "DATING" );
			psMae.setString(2, "VIAAMB" );
			psMae.setString(3, "" );
			
			ResultSet rsMae = psMae.executeQuery();
			
			while( rsMae.next() ) {
				datoTabMae = rsMae.getString("DE2TMA");
			}
			
		} catch (Exception e) {
			System.out.println("RIA133: "+e.toString());
			e.printStackTrace();
			return false;
		}
		
		if( body.getActoQuirurgico().getVia() !=0  && !datoTabMae.equals("") ) {
			
			String sqlUpdateRiaIng ="UPDATE RIAING SET "
					+ "FEEING=?,"
					+ "HREING=?,"
					+ "UMOING=?,"
					+ "PMOING=?,"
					+ "FMOING=?,"
					+ "HMOING=?"
					+ "WHERE NIGING=?";
			try (
				PreparedStatement psUpdateRiaIng = con.prepareStatement(sqlUpdateRiaIng);
			) {
				
				psUpdateRiaIng.setInt(1, fechaActual());
				psUpdateRiaIng.setInt(2, horaActual());
				psUpdateRiaIng.setString(3, body.getUsuario());
				psUpdateRiaIng.setString(4, body.getPrograma());
				psUpdateRiaIng.setInt(5, fechaActual());
				psUpdateRiaIng.setInt(6, horaActual());
				psUpdateRiaIng.setInt(7, Integer.parseInt( body.getIngreso()  ));
				
				int rsUpdateRiaIng = psUpdateRiaIng.executeUpdate();
				
				if( rsUpdateRiaIng> 1 ) {
					System.out.println("RIA133: RIAING trato de actualizar "+rsUpdateRiaIng+" registros");
					return false;
				}
				
				
				
			} catch (Exception e) {
				System.out.println("RIA133: "+e.toString());
				e.printStackTrace();
				return false;
			}
			
		}
		
		return true;
	}
	
	private boolean guardaIngresoHistorico(StructGuardarControlQuirurgico body, Connection con, int [] respuetaConsecutivoPaciente, int consecutivoCita, String plan, String fechaIngreso, String fechaEgreso, int estado) {
		
		int fechaIngresos = (fechaIngreso.split("-").equals("-")) ? 0 : Integer.parseInt(fechaIngreso.split("-")[0]); 
		int horaIngresos = (fechaIngreso.split("-").equals("-")) ? 0 : Integer.parseInt(fechaIngreso.split("-")[1]); 
		
		int fechaEgresos = (fechaEgreso.split("-").equals("-")) ? 0 : Integer.parseInt(fechaEgreso.split("-")[0]);
		int horaEgresos = (fechaEgreso.split("-").equals("-")) ? 0 : Integer.parseInt(fechaEgreso.split("-")[1]);
		
		String sqlInsertRiaIngd ="INSERT INTO RIAINGD(TIDIND,NIDIND,NIGIND,NHCIND,PLAIND,VIAIND,FEIIND,HININD,FEEIND,HREIND,ESTIND,USRIND,PGMIND,FECIND,HORIND) "
				+ "VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";
		
		try (
			
			PreparedStatement psRiaIngd = con.prepareStatement(sqlInsertRiaIngd);
		) {
			
			psRiaIngd.setString(1, body.getInformacionPaciente().getcTipId());
			psRiaIngd.setString(2, body.getInformacionPaciente().getnNumId());
			psRiaIngd.setInt(3, Integer.parseInt(body.getIngreso()));
			psRiaIngd.setString(4, body.getInformacionPaciente().getnHistoria());
			psRiaIngd.setString(5, plan );
			psRiaIngd.setInt(6, body.getActoQuirurgico().getVia() );
			psRiaIngd.setInt(7, fechaIngresos);
			psRiaIngd.setInt(8, horaIngresos);
			psRiaIngd.setInt(9, fechaEgresos);
			psRiaIngd.setInt(10, horaEgresos);
			psRiaIngd.setInt(11, estado);
			psRiaIngd.setString(12, body.getUsuario());
			psRiaIngd.setString(13, body.getPrograma());
			psRiaIngd.setInt(14, fechaActual());
			psRiaIngd.setInt(15, horaActual());
			
			int rsRiaIngd = psRiaIngd.executeUpdate();
			
			if(rsRiaIngd < 1) {
				System.out.println("RIA133: Error al insertar el historico del ingreso");
				return false;
			}
			
			
		} catch (Exception e) {
			System.out.println("RIA133: "+e.toString());
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	
	private boolean actualizarRecursos(StructGuardarControlQuirurgico body, Connection con, int [] respuetaConsecutivoPaciente, int consecutivoCita) {
		
		
		String sqlTabMae="SELECT TRIM(DE2TMA) DE2TMA FROM TABMAE  WHERE TIPTMA =? AND CL1TMA=? AND ESTTMA=? ";
		
		for(StructProcedimientos procedimiento: body.getProcedimientos()) {

			try (
				DBAS400 db = new DBAS400();
				Connection conRecursos = db.getCon();
				
			) {
				
				String listaCentroCosto ="";
				
				try(
					PreparedStatement psTabMae = conRecursos.prepareStatement(sqlTabMae);
				){		
					
					psTabMae.setString(1, "DESCRQX");
					psTabMae.setString(2, "1");
					psTabMae.setString(3, "");
					
					ResultSet rsTabMae = psTabMae.executeQuery();
					
					int count =0;
					while( rsTabMae.next() ) {
						count ++;
						listaCentroCosto =  rsTabMae.getString("DE2TMA");
					}
					
					if(count < 1) {
						listaCentroCosto = "'2001'";
					}
					
				} catch (Exception e) {
					System.out.println("RIA133: "+e.toString());
					e.printStackTrace();
					return false;
				}
				
				
				String sqlRecursos ="SELECT TRIM(CNSEST) CNSEST FROM RIAESTM WHERE INGEST=? "
						+ "AND CNCEST=? "
						+ "AND CUPEST=? AND TINEST=? AND (CCTEST NOT IN("+listaCentroCosto+"))";
				
				
				int coutnCentros=0;
				String centrocosto ="";
				try(
					PreparedStatement psRecursos = conRecursos.prepareStatement(sqlRecursos);
				){
					
					psRecursos.setString(1, body.getIngreso());
					psRecursos.setInt(2, respuetaConsecutivoPaciente[0] );
					psRecursos.setString(3, procedimiento.getCodigoProcedimiento());
					psRecursos.setString(4, "400");
					
					ResultSet rsRecursos = psRecursos.executeQuery();
					
					while( rsRecursos.next() ) {
						coutnCentros++;
						centrocosto = rsRecursos.getString("CNSEST");
					}
					
					
				} catch (Exception e) {
					System.out.println("RIA133: "+e.toString());
					e.printStackTrace();
					return false;
				}
				
				
				String sqlRecursosUpdate = "UPDATE RIAESTM SET "
						+ "DPTEST=?,"
						+ "USREST=?,"
						+ "UMOEST=?,"
						+ "PMOEST=?,"
						+ "FMOEST=?,"
						+ "HMOEST=?,"
						+ "CNOEST=?"
						+ "WHERE INGEST=? AND CNSEST=? AND CNCEST=?";
			
				if(coutnCentros  > 0) {
					try (
						PreparedStatement psRecursosUpdate = con.prepareStatement(sqlRecursosUpdate);
					) {
						
						psRecursosUpdate.setString(1, procedimiento.getEspecialdad_cirujano());
						psRecursosUpdate.setString(2, body.getUsuario());
						psRecursosUpdate.setString(3, body.getUsuario());
						psRecursosUpdate.setString(4, body.getPrograma());
						psRecursosUpdate.setInt(5, fechaActual());
						psRecursosUpdate.setInt(6, horaActual());
						psRecursosUpdate.setInt(7, consecutivoCita);
						psRecursosUpdate.setInt(8, Integer.parseInt( body.getIngreso() ));
						psRecursosUpdate.setString(9,centrocosto );
						psRecursosUpdate.setInt(10, respuetaConsecutivoPaciente[0]);
						
						int rsRecursosUpdate = psRecursosUpdate.executeUpdate();
						
						if(rsRecursosUpdate > 1) {
							System.out.println("RIA133: RIAESTM trato de actualizar "+rsRecursosUpdate+" registros");
							return false;
						}
						
						
					} catch (Exception e) {
						System.out.println("RIA133: "+e.toString());
						e.printStackTrace();
						return false;
					}
				}
			
				
			} catch (Exception e) {
				System.out.println("RIA133: "+e.toString());
				e.printStackTrace();
				return false;
			}
		
		}
		
		return true;
	}
	

	private boolean guardarRips(StructGuardarControlQuirurgico body, Connection con, int [] respuetaConsecutivoPaciente ) {
		
		List<StructProcedimientos> listaProcedimientos =new ArrayList<StructProcedimientos>();
		
		String sql ="SELECT riacon FROM facplnc WHERE plncon=''";
		int count=0;
		String consecutivo ="";
		String factura="";
		
		
		try (
				DBAS400 db = new DBAS400();
				Connection conFacplnc = db.getCon();
				Statement st = conFacplnc.createStatement();
				ResultSet rs = st.executeQuery(sql);
			) {
			
		
			while (rs.next()) {
				consecutivo = rs.getString("riacon");
				count ++;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(count < 1 ) {
			consecutivo="";
		}
		
		int countProcedimientos =1;
		for(StructProcedimientos procedimientoNfil : body.getProcedimientos()) {
			
			if(countProcedimientos > 2) {
				break;
			}
			listaProcedimientos.add(procedimientoNfil);
		}
		
		
		for(StructProcedimientos procedimiento : body.getProcedimientos()) {
			String codigoProcedimiento =  procedimiento.getCodigoProcedimiento() ;
			String diagnosticoPrincipal = procedimiento.getPrequirurgico().split("-")[0] ;
			String diagnosticoRelacional = procedimiento.getRelacionado().split("-")[0] ;
			String diagnosticoComplicion = body.getActoQuirurgico().getDxComplicacion().split("-")[0] ;
			String actoQuirurgico ="0";
			
		
			int lengtDescrip = procedimiento.getProcedimiento().replace(codigoProcedimiento+"-", "").length();
			String descripcionProcedimiento="";
			
			if(lengtDescrip >20) {
				 descripcionProcedimiento = procedimiento.getProcedimiento().replace(codigoProcedimiento+"-", "").substring(0,20);
			}else {
				 descripcionProcedimiento = procedimiento.getProcedimiento().replace(codigoProcedimiento+"-", "").substring(0,lengtDescrip);
			}
			
			
			
			if(procedimiento.getBilateralidad().equals("N")) {
				actoQuirurgico ="1";
			}else {
				actoQuirurgico ="4";
				
				if( listaProcedimientos.size() > 0 ) {
					for(StructProcedimientos nFil : listaProcedimientos) {
						
						if( nFil.getVia() == procedimiento.getVia() ) {
							if( nFil.getEspecialdad_cirujano().equals( procedimiento.getEspecialdad_cirujano() ) ) {
								actoQuirurgico ="5";
							}
						}else {
							actoQuirurgico ="2";
							if(  nFil.getEspecialdad_cirujano().equals( procedimiento.getEspecialdad_cirujano() ) ) {
								actoQuirurgico ="3";
								break;
							}
						}
						
					}
					
				}
				
				String sqlInsertRipaps = "INSERT INTO ripaps"
						+ "(FACAPS,INGAPS,CSCAPS,PRSAPS,TIDAPS,NIDAPS,FEPAPS,AUTAPS,CPRAPS,DPRAPS,AMBAPS,FPRAPS,PERAPS,DG1APS,DG2APS,DGCAPS,FAQAPS,VLRAPS,USRAPS,PGMAPS,FECAPS,HORAPS)"
						+ "VALUES( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				
				try (
					PreparedStatement psInsertRipaps = con.prepareStatement(sqlInsertRipaps);
				) {
					
					psInsertRipaps.setString(1, factura);
					psInsertRipaps.setInt(2, Integer.parseInt( body.getIngreso() ));
					psInsertRipaps.setInt(3, respuetaConsecutivoPaciente[0]);
					psInsertRipaps.setString(4, "");
					psInsertRipaps.setString(5, body.getInformacionPaciente().getcTipId() );
					psInsertRipaps.setString(6, body.getInformacionPaciente().getnNumId());
					psInsertRipaps.setInt(7, fechaActual());
					psInsertRipaps.setString(8, "");
					psInsertRipaps.setString(9, codigoProcedimiento);
					psInsertRipaps.setString(10, descripcionProcedimiento);
					psInsertRipaps.setString(11,"");
					psInsertRipaps.setString(12, body.getActoQuirurgico().getFinalidad() );
					psInsertRipaps.setString(13, "1" );
					psInsertRipaps.setString(14, diagnosticoPrincipal );
					psInsertRipaps.setString(15, diagnosticoRelacional);
					psInsertRipaps.setString(16, diagnosticoComplicion);
					psInsertRipaps.setString(17, actoQuirurgico);
					psInsertRipaps.setInt(18, 0);
					psInsertRipaps.setString(19, body.getUsuario());
					psInsertRipaps.setString(20, body.getPrograma());
					psInsertRipaps.setInt(21, fechaActual());
					psInsertRipaps.setInt(22, horaActual());
					
					int rsInsertRipaps = psInsertRipaps.executeUpdate();
					
					if(rsInsertRipaps < 1) {
						System.out.println("RIA133: Error al insertar los rips");
						return false;
					}
					
					
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("RIA133:"+ e);
					return false;
				}
				
			}
			/// RIPS
			
		}
		
		return true;
	}
	
	private List<String> partirCadena(String cadena, int size) {
		
		List<String> datos = new ArrayList<String>();
		int inicio=0;
		int fin = size;
		int sizeCadena = (int) Math.ceil((float)cadena.length() / size);
		
		for(int i =0; i < sizeCadena; i++) {
			
			int faltante =   cadena.length() - inicio ;
			
			if(faltante < size) {
				datos.add(cadena.substring(inicio,cadena.length()));
			}else {
				datos.add(cadena.substring(inicio, fin));
				inicio = fin;
				fin = fin +size;
			}
		}
		
		return datos;
	}
	
	private String TTOC() {
		
	    LocalDateTime locaDateTime = LocalDateTime.now();
	    DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss a");
	    String fechaFormateada = locaDateTime.format(formato);

		
		return fechaFormateada;
	}
	
	
	private int validarConsecutivoCita(int ingreso) {
		
		int consecutivo =0; 
		
		String sql = "SELECT ccicit FROM riacit WHERE nincit = "+ingreso
				+ " AND codcit = '310'"
				+ "AND evocit >= 1"
				+ "AND ((COACIT NOT LIKE '8902%') AND (COACIT NOT LIKE '8903%'))"
				+ "AND viacit = '02' AND estcit = 8";
		
		try (
				DBAS400 db = new DBAS400();
				Connection con = db.getCon();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql);
			) {
			
		
			while (rs.next()) {
				consecutivo = rs.getInt("ccicit");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return consecutivo;
	}
	
	private boolean actualizarRiapacConsecutivo(int [] respuetaConsecutivoPaciente, int consecutivoCita, StructGuardarControlQuirurgico body, int consecutivoEvolucion) {
		String sqlConsecutivoXPaciente ="UPDATE RIAPAC SET ccipac=? WHERE tidpac= ? AND nidpac=?";
		try(
			DBAS400 db = new DBAS400();
			Connection conRiapac = db.getCon();
			PreparedStatement psUpdateConsecutivoXPaciente = conRiapac.prepareStatement(sqlConsecutivoXPaciente);
		){
				conRiapac.setAutoCommit(false);
				
				if(respuetaConsecutivoPaciente[1] > 0) {

					psUpdateConsecutivoXPaciente.setInt(1, consecutivoCita+1);
					psUpdateConsecutivoXPaciente.setString(2, body.getInformacionPaciente().getcTipId());
					psUpdateConsecutivoXPaciente.setString(3, body.getInformacionPaciente().getnNumId());
					
					int updateConsecutivoXPaciente  = psUpdateConsecutivoXPaciente.executeUpdate();
					
					if( updateConsecutivoXPaciente > 1  || updateConsecutivoXPaciente < 1) {
						psUpdateConsecutivoXPaciente.close();
						conRiapac.rollback();
						return false;
					}
					conRiapac.commit();
					psUpdateConsecutivoXPaciente.close();
					conRiapac.close();
				}
			} catch (Exception e) {
				
				e.printStackTrace();
				return false;
			}
		
		return true;
			
	}
	
	private boolean guardarEvoluciones( int [] respuetaConsecutivoPaciente, int consecutivoCita, StructGuardarControlQuirurgico body, int consecutivoEvolucion, Connection con ) {
		
	
			String sqlEvolucion = "INSERT INTO evoluc (ninevl,conevl,ccievl,usrevl,pgmevl,fecevl,horevl,cnlevl,desevl) VALUES";
			
			int consecutivoRegistroEvo = 5001;
			String principio = "("+body.getIngreso()+","+consecutivoEvolucion+","+consecutivoCita+",'"+body.getUsuario()+"','"+body.getPrograma()+"'," + fechaActual()+","+horaActual()+ ",";
			
			String descripcionEv = principio
					+consecutivoRegistroEvo+",' - DESCRIPCION QUIRURGICA "+ TTOC()+"  Hab.:"+body.getInformacionPaciente().getcSeccion()+"-"+body.getInformacionPaciente().getcHabita()+"')";
			
			
			for(StructProcedimientos procedimiento : body.getProcedimientos()) {
				
				consecutivoRegistroEvo = consecutivoRegistroEvo+ 1;
				
				String [] arrayProcedimiento = procedimiento.getProcedimiento().split("-");
				String nombreProcedimiento = procedimiento.getProcedimiento().replace(arrayProcedimiento[0]+"-", "");
				
				descripcionEv = descripcionEv +","+principio+consecutivoRegistroEvo+",'"+procedimiento.getCodigoProcedimiento()+' '+nombreProcedimiento+' '+procedimiento.getCirujano()+"')";
				
			}
			
			consecutivoRegistroEvo = consecutivoRegistroEvo+1;
			
			descripcionEv = descripcionEv +  ","+principio+ consecutivoRegistroEvo +",'"+"HALLAZGOS"+"')" ;

			try(
				Statement stmtEvoluciones = con.createStatement();
			){
				
				int evolucionesPt1 = stmtEvoluciones.executeUpdate(sqlEvolucion +" "+ descripcionEv );

				if(evolucionesPt1 < 0){
					System.out.println("RIA133: No inserto la Evolución");
					return false;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			
			List<String> datosHallazgos = partirCadena(body.getActoQuirurgico().getHallazgos(), 220);
			
			String HallazgosEvolucion = "";
			
			for(String dato : datosHallazgos) {
				consecutivoRegistroEvo = consecutivoRegistroEvo +1;
				
				HallazgosEvolucion = HallazgosEvolucion+ principio+consecutivoRegistroEvo+",'"+dato+"')," ;
				
			}
			
			String sqlHallazgos =  sqlEvolucion + ""+HallazgosEvolucion.substring(0, HallazgosEvolucion.length()-1);
			
			try(
				Statement stmtEvolucionesHallazgos = con.createStatement();
			){
					
				int evolucionesPt1 = stmtEvolucionesHallazgos.executeUpdate(sqlHallazgos);

				if(evolucionesPt1 < 1) {
					System.out.println("RIA133: no inserto");
					return false;
				}
					
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			
			consecutivoRegistroEvo = consecutivoRegistroEvo +1;
			
			String DescripcionEvolucion = principio+consecutivoRegistroEvo+",'DESCRIPCION')," ;
			
			List<String> datosDescripcion= partirCadena(body.getDescripcionQuirurgica().getDescripcion(), 220);
			
			for(String dato : datosDescripcion) {
				consecutivoRegistroEvo = consecutivoRegistroEvo +1;
				
				DescripcionEvolucion = DescripcionEvolucion+ principio+consecutivoRegistroEvo+",'"+dato+"')," ;
				
			}
			
			String sqlEvolucionHallazgos = sqlEvolucion + DescripcionEvolucion.substring(0, DescripcionEvolucion.length()-1);
			
			try(
				Statement stmtEvolucionesDescripcion = con.createStatement();
			){
						
				int evolucionesDescripcion = stmtEvolucionesDescripcion.executeUpdate(sqlEvolucionHallazgos);

				if(evolucionesDescripcion < 0) {
					System.out.println("RIA133: no inserto");
					con.rollback();
					return false;
				}
						
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			
			consecutivoRegistroEvo = consecutivoRegistroEvo +1;
			String envioPatologico = principio+consecutivoRegistroEvo+",'ENVIO A PATOLOGIA')," ;
			
			List<String> datosPatologicos= partirCadena(body.getDescripcionQuirurgica().getPatologia(), 220);
			
			for(String dato : datosPatologicos) {
				consecutivoRegistroEvo = consecutivoRegistroEvo +1;
				
				envioPatologico = envioPatologico+ principio+consecutivoRegistroEvo+",'"+dato+"')," ;
				
			}
			consecutivoRegistroEvo ++;
			String envioEspecialidad = principio+consecutivoRegistroEvo+",'"+body.getEspecialidadUsuario()+" - "+body.getInformacionPaciente().getcSeccion()+"-"+body.getInformacionPaciente().getcHabita()+"')," ;
			
			envioPatologico= envioPatologico+ envioEspecialidad;

			String sqlEnvioPatologico = sqlEvolucion + envioPatologico.substring(0, envioPatologico.length()-1) ;
			
			try(
				Statement stmtEvolucionesPatologia = con.createStatement();
			){
							
				int evolucionesPatologia = stmtEvolucionesPatologia.executeUpdate(sqlEnvioPatologico);

				if(evolucionesPatologia > 0) {
					System.out.println("inserto");
				}else {
					System.out.println("no inserto");
					return false;
				}
							
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		return true;
	}
	
	
	private boolean guardarAnaEpi(StructGuardarControlQuirurgico body, int consecutivoEvolucion, Connection con ) {
		
		String datos = " - HALLAZGOS:"+ body.getActoQuirurgico().getHallazgos();
		datos  = datos+" - DESCRIPCION:"+body.getDescripcionQuirurgica().getDescripcion();
		datos = datos+" - ENVIO A PATOLOGIA:"+body.getDescripcionQuirurgica().getPatologia();
		
		int consecutivolinea =0;
		
		String sql = "INSERT INTO anaepi (INGAEP, TIPAEP, CEVAEP, INDAEP, USRAEP, PGMAEP, FECAEP, HORAEP, CNLAEP, DESAEP) VALUES";
		String inicio =  body.getIngreso()+",'DQ',"+consecutivoEvolucion+"0,0,'"+body.getUsuario()+"','"+body.getPrograma()+"',"+fechaActual()+","+horaActual();
		
		
		List<String> datosPartidos = partirCadena(datos, 220);
		
		
		for ( String dato : datosPartidos) {
			consecutivolinea ++;
			try(
				Statement stmtAnaEpi = con.createStatement();
			){
				String sqlAnaEpi = sql + "(" + inicio + ","+consecutivolinea+",'"+dato+"')";
				int AnaEpi = stmtAnaEpi.executeUpdate(sqlAnaEpi);
				
				if(AnaEpi > 0) {
					System.out.println("inserto");
				}else {
					System.out.println("no inserto");
					con.rollback();
				}
								
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			
		}
		
		return true;
	}
	
	private boolean guardarRiaHis(StructGuardarControlQuirurgico body, int consecutivoEvolucion, int consecutivoCita, String dxComplicacion, Connection con, int[] respuetaConsecutivoPaciente ) {
		
		String sql ="INSERT INTO riahis (nroing,concon,indice,subind,codigo,subhis,suborg,conhis,tidhis,nidhis,usrhis,pgmhis,fechis,horhis,consec,descri, fille1,fille2,fille3) VALUES";
		String inicio = "("+body.getIngreso()+","+consecutivoCita+",70,0,0,0,22,"+consecutivoEvolucion+",'"+body.getInformacionPaciente().getcTipId()+"','"+body.getInformacionPaciente().getnNumId()
				+"','"+body.getUsuario()+"','"+body.getPrograma()+"',"+fechaActual()+","+horaActual();
		
		int consecutivoRiaHis =1;
		String sala = body.getActoQuirurgico().getSalaCirugia()+body.getActoQuirurgico().getOtraSala();
		String valuesRiaHis = inicio+","+consecutivoRiaHis+",'Sala de Cirugia . . : "+sala+"',0,0,''),";
		
		consecutivoRiaHis= 2;
		valuesRiaHis = valuesRiaHis + inicio +","+consecutivoRiaHis+",'Tipo Cirugia  . . . : "+ body.getActoQuirurgico().getTipoCirugia()+" "+body.getActoQuirurgico().getClasificacionCirugia()+"',0,0,'')," ;
		
		consecutivoRiaHis= 5;
		valuesRiaHis = valuesRiaHis+ inicio +","+consecutivoRiaHis+",'Anestesiologo . . . : "+body.getActoQuirurgico().getAnestesiologo()+"',0,0,''),";
		
		
		consecutivoRiaHis= 9;
		valuesRiaHis = valuesRiaHis+ inicio +","+consecutivoRiaHis+",'Perfusionista . . . : "+body.getActoQuirurgico().getPerfusionista()+"',0,0,''),";
		
		consecutivoRiaHis= 10;
		valuesRiaHis = valuesRiaHis+ inicio +","+consecutivoRiaHis+",'Tipo de Anestesia . : "+body.getActoQuirurgico().getTipoAnestesia()+"',0,0,''),";
		
		
		consecutivoRiaHis= 20;
		int poli = (body.getActoQuirurgico().isPolitraumatizado()) ? 1 : 0;
		
		String descripcionQ = "Finalid : "+body.getActoQuirurgico().getFinalidad() +
		" Pac. Pol: " + poli+
		" Vias    : " +  body.getActoQuirurgico().getVia()+ 
		" Dg Comp : " + dxComplicacion;
		
		valuesRiaHis = valuesRiaHis+ inicio +","+consecutivoRiaHis+",'"+descripcionQ+"',0,0,''),";
		
		consecutivoRiaHis= 30;
		
		String descripcionHr = "Hor Ini :    "+
		body.getActoQuirurgico().getHoroInicio()+" Hor Sal :    "+
		body.getActoQuirurgico().getHoraFin()+" Riesgo  :    "+body.getActoQuirurgico().getRiesgo()+" Asa    :  "+body.getActoQuirurgico().getAsa();

		valuesRiaHis = valuesRiaHis+ inicio +","+consecutivoRiaHis+",'"+descripcionHr+"',0,0,''),";
		
		consecutivoRiaHis= 300;
		List<String> Hallazgos = partirCadena(body.getActoQuirurgico().getHallazgos(), 70);
		
		for( String hallazgo : Hallazgos ) {
			consecutivoRiaHis++;
			valuesRiaHis = valuesRiaHis+ inicio +","+consecutivoRiaHis+",'"+hallazgo+"',0,0,''),";
		}
		
		
		
		consecutivoRiaHis =400;
		
		String DescripcionProcedimientos = "";
		for(StructProcedimientos procedimiento : body.getProcedimientos()) {
	
			if(consecutivoRiaHis >= 500) {
				System.out.println("RIA133: cantidad de procedimientosm igual a 500");
				return false;
			}
			
			String codigoProcedimiento = procedimiento.getCodigoProcedimiento();
			String diagnosticoSale = procedimiento.getPostquirurgico().split("-")[0];
			String codigoRelacional = procedimiento.getRelacionado().split("-")[0];
			String codigoEntra = procedimiento.getPrequirurgico().split("-")[0];
			String cirujano = procedimiento.getRegistro_cirujano();
			String ayudante = procedimiento.getRegistro_ayudante1();
			
			DescripcionProcedimientos ="Procedimiento . . . :   "+CadenaConcatenarIzquierda(codigoProcedimiento.length(), 9,"",codigoProcedimiento )+
					" "+CadenaConcatenarIzquierda(diagnosticoSale.length(), 5," ",diagnosticoSale )+
					" "+CadenaConcatenarIzquierda(codigoRelacional.length(), 5," ",codigoRelacional )+
					" "+CadenaConcatenarIzquierda(cirujano.length(), 13," ",cirujano )+
					" "+CadenaConcatenarIzquierda(ayudante.length(), 13," ",ayudante );
			
			consecutivoRiaHis++;
			
			int bilateral = (procedimiento.getBilateralidad().equals("S")) ? 1 : 0;
			int cobroCirujano = (procedimiento.getC1().equals("S")) ? 1 : 0;
			int cobroAyudante = (procedimiento.getC2().equals("S")) ? 1 : 0;
			
			String fille3 = CadenaConcatenarIzquierda(1,1," ",String.valueOf(procedimiento.getVia()))+
					" "+ CadenaConcatenarIzquierda(1,1," ",String.valueOf(bilateral))+
					" "+ CadenaConcatenarIzquierda(1,3," ",String.valueOf(0))+
					" "+CadenaConcatenarIzquierda(1,1," ",String.valueOf(cobroCirujano))+
					" "+CadenaConcatenarIzquierda(1,1," ",String.valueOf(cobroAyudante))+
					" "+CadenaConcatenarIzquierda(codigoEntra.length(),5," ",String.valueOf(codigoEntra));
			
			valuesRiaHis = valuesRiaHis+ inicio +","+consecutivoRiaHis+",'"+DescripcionProcedimientos+"',"+respuetaConsecutivoPaciente[0]+",0,'"+fille3+"'),";
		}

		
		consecutivoRiaHis =500;
		
		List<String> descripcionQuirurgica = partirCadena(body.getDescripcionQuirurgica().getDescripcion(), 70);
		
		for( String descripcion : descripcionQuirurgica ) {
			consecutivoRiaHis++;
			valuesRiaHis = valuesRiaHis+ inicio +","+consecutivoRiaHis+",'"+descripcion+"',0,0,''),";
		}
		
		if(body.getPerfucion().isEnvio()) {
			
			
			
			String descripcion = "";
			
			consecutivoRiaHis =100551;
			int preoperatorio = ( body.getPerfucion().isPreoperatorio() ) ? 1 : 0;
			descripcion ="A. Vent. Preoperat. : " +preoperatorio;
			valuesRiaHis = valuesRiaHis+ inicio +","+consecutivoRiaHis+",'"+descripcion+"',0,0,''),";
			
			consecutivoRiaHis =100552;
			int interoperatorio = ( body.getPerfucion().isInteroperatorio() ) ? 1 : 0;
			descripcion ="A. Vent. Intraoper. : " +interoperatorio;
			valuesRiaHis = valuesRiaHis+ inicio +","+consecutivoRiaHis+",'"+descripcion+"',0,0,''),";
			
			consecutivoRiaHis =100553;
			int posoperatorio = ( body.getPerfucion().isPosoperatorio() ) ? 1 : 0;
			descripcion ="A. Vent. Posoperat. : " +posoperatorio;
			valuesRiaHis = valuesRiaHis+ inicio +","+consecutivoRiaHis+",'"+descripcion+"',0,0,''),";
			
			consecutivoRiaHis =100554;
			descripcion ="Temperatura . . . . : " +body.getPerfucion().getTemperaturaRectal();
			valuesRiaHis = valuesRiaHis+ inicio +","+consecutivoRiaHis+",'"+descripcion+"',0,0,''),";
			
			
			consecutivoRiaHis =100555;
			int cardioplejia = ( body.getPerfucion().isCardioplejia() ) ? 1 : 0;
			descripcion ="Cardioplejia. . . . : " +cardioplejia;
			valuesRiaHis = valuesRiaHis+ inicio +","+consecutivoRiaHis+",'"+descripcion+"',0,0,''),";
			
			consecutivoRiaHis =100556;
			descripcion ="Cardiop.Anterograda : " +body.getPerfucion().getAnterogrado();
			valuesRiaHis = valuesRiaHis+ inicio +","+consecutivoRiaHis+",'"+descripcion+"',0,0,''),";
		
			consecutivoRiaHis =100557;
			descripcion ="Cardiop.Retrograda. : " +body.getPerfucion().getRetrograda();
			valuesRiaHis = valuesRiaHis+ inicio +","+consecutivoRiaHis+",'"+descripcion+"',0,0,''),";
			
			consecutivoRiaHis =100558;
			descripcion ="Cardiop.Simultanea. : " +body.getPerfucion().getSimultanea();
			valuesRiaHis = valuesRiaHis+ inicio +","+consecutivoRiaHis+",'"+descripcion+"',0,0,''),";
			
			consecutivoRiaHis =100559;
			descripcion ="Tiempo Pinza Aortica: " +body.getPerfucion().getPinzaAortica();
			valuesRiaHis = valuesRiaHis+ inicio +","+consecutivoRiaHis+",'"+descripcion+"',0,0,''),";
			
			consecutivoRiaHis =100560;
			descripcion ="Tiempo Perfusion. . : " +body.getPerfucion().getPerfusion();
			valuesRiaHis = valuesRiaHis+ inicio +","+consecutivoRiaHis+",'"+descripcion+"',0,0,''),";
			
			consecutivoRiaHis =100561;
			descripcion ="Tiempo paro Total . : " +body.getPerfucion().getParoTotal();
			valuesRiaHis = valuesRiaHis+ inicio +","+consecutivoRiaHis+",'"+descripcion+"',0,0,''),";
			
		}
		
		consecutivoRiaHis =111000;
		
		String descripcionPatologia ="";
		
		descripcionPatologia ="Patologia . . . . : " +body.getPerfucion().getParoTotal();
		valuesRiaHis = valuesRiaHis+ inicio +","+consecutivoRiaHis+",'"+descripcionPatologia+"',0,0,''),";
		
		List<String> listPatologia = partirCadena(body.getDescripcionQuirurgica().getPatologia(), 70);
		
		consecutivoRiaHis =111001;
		
		for( String patologia : listPatologia ) {
			consecutivoRiaHis++;
			valuesRiaHis = valuesRiaHis+ inicio +","+consecutivoRiaHis+",'"+patologia+"',0,0,''),";
		}
		
		if(body.getDescripcionQuirurgica().getComplicaciones().equals("S") && !body.getDescripcionQuirurgica().getDescripcionComplicacion().equals("")) {
			consecutivoRiaHis =120000;
			
			List<String> listComplicacion = partirCadena(body.getDescripcionQuirurgica().getDescripcionComplicacion(), 70);
			
			for( String complicacion : listComplicacion ) {
				consecutivoRiaHis++;
				valuesRiaHis = valuesRiaHis+ inicio +","+consecutivoRiaHis+",'"+complicacion+"',0,0,''),";
			}
		}
		
		if( !body.getDescripcionQuirurgica().getEstadoSalida().equals("") ) {
			consecutivoRiaHis =131000;
			
			String fechaHoraInicio = String.valueOf(body.getActoQuirurgico().getFechaInicio());
			String fechaHoraSalida = String.valueOf(body.getActoQuirurgico().getFechaFin());
			
			int salida = ( body.getDescripcionQuirurgica().getEstadoSalida().equals("V")) ? 1 : 0;
			
			String DescripcionSalida=salida+"~"+fechaHoraInicio+"~"+fechaHoraSalida;
			valuesRiaHis = valuesRiaHis+ inicio +","+consecutivoRiaHis+",'"+DescripcionSalida+"',0,0,''),";
		}
		
		if( !body.getActoQuirurgico().getInstrumentador().equals("") ) {
			consecutivoRiaHis =132000;
			valuesRiaHis = valuesRiaHis+ inicio +","+consecutivoRiaHis+",'"+body.getActoQuirurgico().getInstrumentador()+"',0,0,''),";
		}
		
		
		valuesRiaHis = valuesRiaHis.substring( 0, valuesRiaHis.length()-1 );
		
		try(
			Statement stmtRiaHis = con.createStatement();
		){
			
			int riaHis = stmtRiaHis.executeUpdate(sql +valuesRiaHis );
				
			if(riaHis < 1) {
				System.out.println("no inserto");
				con.rollback();
			}
								
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		
		return true;
	}
	
	private boolean actualizarFaccirh(StructGuardarControlQuirurgico body, int[] consecutivosCirugias, Connection con ){
		
		String sqlActualizarFaccirh ="UPDATE FACCIRH SET ESTCRH=? WHERE INGCRH=? AND CNSCRH=?";
		
		try(
				PreparedStatement psActualizarFaccirh= con.prepareStatement(sqlActualizarFaccirh);
			){
				
			psActualizarFaccirh.setString(1, "4");
			psActualizarFaccirh.setInt(2, Integer.parseInt(body.getIngreso()));
			psActualizarFaccirh.setInt(3, consecutivosCirugias[0]);
			
			int rsActualizarFaccirh = psActualizarFaccirh.executeUpdate();
			
			if(rsActualizarFaccirh > 1) {
				System.out.println("RIA133: FACCIRH trato de actualizar: "+rsActualizarFaccirh+" registros");
				return false;
			}
									
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		
		return true;
	}
	
	private boolean procedimientoFacturas(StructGuardarControlQuirurgico body, int[] consecutivosCirugias, Connection con ) {
		
		
		boolean indicadoresSalas;
		try {
			
			
			indicadoresSalas = crearIndicadoresSala( body,  consecutivosCirugias,  con );	
			if(!indicadoresSalas) {
				return false;
			}
			
			boolean facturaIrpRiq = crearFacturaIrpRiq(body, consecutivosCirugias, con);
			if(!facturaIrpRiq) {
				return false;
			}
			


			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	private boolean crearLiquidacion(StructGuardarControlQuirurgico body, int [] consecutivosCirugia, int consecutivocita ) {
		// AQUI

		for(StructProcedimientos procedimiento : body.getProcedimientos()) {
			
			String ingreso = CadenaConcatenarIzquierda(body.getIngreso().length(), 8 , "0", body.getIngreso());
			
			int longitudConsecutivo = String.valueOf(consecutivosCirugia[0]).length();
			String consecutivoCita  = CadenaConcatenarIzquierda(longitudConsecutivo, 6 , "0", String.valueOf(consecutivosCirugia[0]));
					
			String planEnt ="";
			
			String consecutivoFactura = consecutivoFacturas();
			
			try
			{
				
				String pgmds ="";
				String prcva ="";
				int longitudPgmds=0;
			
				if(!consecutivoFactura.equals("")) {
					
					pgmds = consecutivoFactura.substring(1, 10);
					prcva = String.valueOf(Integer.parseInt(pgmds) +1);
					longitudPgmds = prcva.length();
					pgmds= CadenaConcatenarIzquierda(longitudPgmds, 10, "0", prcva )+" VALES";
					
					
					boolean actualizarTablaPrmpgm= actualizarPrmpgm( consecutivoFactura, pgmds);
					
					if(!actualizarTablaPrmpgm) {
						return false;
					}
					
					liquidarProcedure( ingreso, consecutivoCita,  planEnt,  prcva );
					
				
				}else {
					return false;
				}
				
				
											
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			
		}

		return true;
	}
	
	private boolean liquidarProcedure(String ingreso,String consecutivoCita, String planEnt, String prcva ) {
		
		
		String sqlProcedureCobro = "CALL FACQ38P1P(?, ?, ?, ?)";
		try (
			DBAS400 db = new DBAS400();
			Connection conProcedure = db.getCon();
			CallableStatement cstmt = conProcedure.prepareCall(sqlProcedureCobro);
		) {
			
			cstmt.setString(1, ingreso);
			cstmt.setString(2, consecutivoCita);
			cstmt.setString(3, planEnt);
			cstmt.setString(4, prcva);
		
			cstmt.execute();
			
			
		} catch (Exception e) {
			System.out.println("RIA133: "+e.toString());
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	private boolean actualizarPrmpgm(String consecutivoFactura, String pgmds) {
		

		String sqlUpdatePrmpgm ="UPDATE prmpgm SET pgmdsc=? WHERE pgmtip=? AND pgmcod=?";

		try(
			DBAS400 db = new DBAS400();
			Connection conPrmpgm = db.getCon();
			PreparedStatement psUpdatePrmpgm = conPrmpgm.prepareStatement(sqlUpdatePrmpgm);
		){
	
			psUpdatePrmpgm.setString(1,pgmds);
			psUpdatePrmpgm.setString(2,"50");
			psUpdatePrmpgm.setString(3,"VALE");
			
			int rsUpdatePrmpgm = psUpdatePrmpgm.executeUpdate();
			
			if(rsUpdatePrmpgm > 1 || rsUpdatePrmpgm < 1) {
				psUpdatePrmpgm.close();
				return false;
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
	
	private String consecutivoFacturas() {
		
		String sqlPrmpgm ="SELECT TRIM(pgmdsc) as pgmdsc FROM prmpgm WHERE pgmtip='50' AND pgmcod='VALE' ORDER BY pgmtip FETCH FIRST 1 ROWS ONLY";
		String pgmdsc="";
		int count =0;
		try(
			DBAS400 db = new DBAS400();
			Connection con = db.getCon();
			PreparedStatement psPrmpgm = con.prepareStatement(sqlPrmpgm);	
			ResultSet rsPrmpgm = psPrmpgm.executeQuery();
		){
			
			while(rsPrmpgm.next()) {
				pgmdsc = rsPrmpgm.getString("pgmdsc");
				count++;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		
		if(count < 1) {
			return "";
		}
		
		return pgmdsc;
		
	}
	
	
	private boolean crearFacturaIrpRiq(StructGuardarControlQuirurgico body, int [] consecutivoConsulta, Connection con ) {
		
		String anesteciologo = recuperarDatosMedicos(body.getActoQuirurgico().getAnestesiologo(), 6).getDocumento();
		int cobroAnesteciologo = 0;
		
		String perfusionistaNit = recuperarDatosMedicos(body.getActoQuirurgico().getPerfusionista(), 5).getDocumento();
		int cobroPerfusionista = 100;
		
		String perfusionista = (perfusionistaNit.equals("")) ? "0": perfusionistaNit;
		
		
		for(StructProcedimientos procedimiento : body.getProcedimientos()) {
			
			int idProcedimiento = 1;
			
			String activoAyudante2 = ( procedimiento.getAyudante2().equals("") ) ? "N": "S";
			String activoPerfucionista = ( body.getActoQuirurgico().getPerfusionista().equals("") ) ? "N": "S"; 
			
			int cobroCirujano = (procedimiento.getC1().equals("S")) ? 100 : 0;
			int cobroAyudante1 = (procedimiento.getC2().equals("S")) ? 100 : 0;
			int cobroAyudante2= (procedimiento.getC2().equals("S")) ? 100 : 0;
			
			
			StructMedicosControlQuirurgico cirujano = recuperarDatosMedicos(procedimiento.getCirujano(), 6);
			int nitCirujano = (!cirujano.getDocumento().equals("") ) ? Integer.parseInt(cirujano.getDocumento()): 0; 
			
			StructMedicosControlQuirurgico ayudante1 = recuperarDatosMedicos(procedimiento.getAyudante1(), 6);
			int nitayudante1 = (!ayudante1.getDocumento().equals("") ) ? Integer.parseInt(ayudante1.getDocumento()): 0; 
			
			StructMedicosControlQuirurgico ayudante2 = recuperarDatosMedicos(procedimiento.getAyudante2(), 6);
			int niayudante2 =  (!ayudante2.getDocumento().equals("") ) ? Integer.parseInt(ayudante2.getDocumento()): 0; 
			
			
			String sqlFaccuirp = "SELECT * FROM faccirp WHERE ingcrp=? AND cnscrp=? AND cupcrp=?";
			try(
				PreparedStatement psFaccuirp = con.prepareStatement(sqlFaccuirp);
			){
				psFaccuirp.setInt( 1,  Integer.parseInt( body.getIngreso() ) );
				psFaccuirp.setInt( 2,  consecutivoConsulta[0]);
				psFaccuirp.setString(3, procedimiento.getCodigoProcedimiento());
				
				ResultSet rsFaccuirp = psFaccuirp.executeQuery();
				
				int count =0;
				while( rsFaccuirp.next() ) {
					count++;
				}
				
				if(count > 0) {
					
					String sqlUpdateFaccirp = "UPDATE FACCIRP SET"
							+ " viacrp=?,"
							+ " eqpcrp=?,"
							+ " faqcrp=?,"
							+ " ay1crp=?,"
							+ " ay2crp=?,"
							+ " prfcrp=?,"
							+ " dg1crp=?,"
							+ " dg2crp=?,"
							+ " vprfrp=?,"
							+ " umocrp=?,"
							+ " pmocrp=?,"
							+ " fmocrp=?,"
							+ " hmocrp=?"
							+ " WHERE ingcrp=?"
							+ " AND cnscrp=?"
							+ " AND cupcrp=?";
					
					PreparedStatement psUpdateFaccirp = con.prepareStatement(sqlUpdateFaccirp);
					
					psUpdateFaccirp.setInt(1, procedimiento.getVia());
					psUpdateFaccirp.setInt(2,idProcedimiento );
					psUpdateFaccirp.setString(3, "");
					psUpdateFaccirp.setString(4, procedimiento.getBilateralidad());
					psUpdateFaccirp.setString(5, activoAyudante2); 
					psUpdateFaccirp.setString(6, activoPerfucionista);
					psUpdateFaccirp.setString(7, procedimiento.getPrequirurgico().split("-")[0]  );
					psUpdateFaccirp.setString(8, procedimiento.getPostquirurgico().split("-")[0]  );
					psUpdateFaccirp.setInt(9, 0);
					psUpdateFaccirp.setString(10, body.getUsuario());
					psUpdateFaccirp.setString(11, body.getPrograma());
					psUpdateFaccirp.setInt(12, fechaActual());
					psUpdateFaccirp.setInt(13, horaActual());
					psUpdateFaccirp.setInt(14, Integer.parseInt(body.getIngreso()));
					psUpdateFaccirp.setInt(15, consecutivoConsulta[0]);
					psUpdateFaccirp.setString(16, procedimiento.getCodigoProcedimiento());
					
					int rsUpdateFaccirp = psUpdateFaccirp.executeUpdate();
					
					if(rsUpdateFaccirp > 1 || rsUpdateFaccirp < 1) {
						
						System.out.println("RIA133: PROCESO rsUpdateFaccirp INTENTO ACTUALIZAR " + rsUpdateFaccirp);
						
						return false;
					}
					
				}else {
					String sqlInsertFaccirp = "INSERT INTO faccirp "
							+ "(ingcrp,cnscrp,cupcrp,cnpcrp,viacrp,eqpcrp,faqcrp,ay1crp,ay2crp,prfcrp,dg1crp,dg2crp,vprfrp,usrcrp,pgmcrp,feccrp,horcrp) "
							+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					
					PreparedStatement psInsertFaccirp = con.prepareStatement(sqlInsertFaccirp);
					
					psInsertFaccirp.setInt(1,  Integer.parseInt( body.getIngreso() ) );
					psInsertFaccirp.setInt( 2,  consecutivoConsulta[0]);
					psInsertFaccirp.setString(3, procedimiento.getCodigoProcedimiento());
					psInsertFaccirp.setInt(4, idProcedimiento);
					psInsertFaccirp.setInt(5, procedimiento.getVia());
					psInsertFaccirp.setInt(6, idProcedimiento);
					psInsertFaccirp.setString(7, "");
					psInsertFaccirp.setString(8, procedimiento.getBilateralidad());
					psInsertFaccirp.setString(9, activoAyudante2); 
					psInsertFaccirp.setString(10, activoPerfucionista);
					psInsertFaccirp.setString(11, procedimiento.getPrequirurgico().split("-")[0]  );
					psInsertFaccirp.setString(12, procedimiento.getPostquirurgico().split("-")[0]  );
					psInsertFaccirp.setInt(13, 0);
					psInsertFaccirp.setString(14, body.getUsuario());
					psInsertFaccirp.setString(15, body.getPrograma());
					psInsertFaccirp.setInt(16, fechaActual());
					psInsertFaccirp.setInt(17, horaActual());
					
				
					int rsInsertFaccirp = psInsertFaccirp.executeUpdate();
					
					if(rsInsertFaccirp < 1 ) {
						con.rollback();
						return false;
					}
					
				}
				
				
				String sqlCircupp = "SELECT * FROM CIRCUPP WHERE INGCRP=? AND CNSCRP=? AND CUPCRP=? AND CNPCRP=?";
				
				PreparedStatement psCircupp = con.prepareStatement(sqlCircupp);
				
				psCircupp.setInt(1, Integer.parseInt( body.getIngreso() ) );
				psCircupp.setInt(2, consecutivoConsulta[0] );
				psCircupp.setString(3, procedimiento.getCodigoProcedimiento() );
				psCircupp.setInt(4, idProcedimiento);
				
				ResultSet rsCircupp = psCircupp.executeQuery();
				
				int countCircupp = 0;
				
				while( rsCircupp.next() ) {
					countCircupp++;
				}
				
				if(countCircupp <1) {
					String sqlInsertCircupp = "INSERT INTO CIRCUPP (INGCRP,CNSCRP,CUPCRP,CNPCRP,VIACRP,EQPCRP,FAQCRP,AY1CRP,AY2CRP,PRFCRP,DG1CRP,DG2CRP,VPRFRP,USRCRP,PGMCRP,FECCRP,HORCRP)"
							+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					
					PreparedStatement psInsertCircupp = con.prepareStatement(sqlInsertCircupp);
					
					psInsertCircupp.setInt(1,  Integer.parseInt( body.getIngreso() ) );
					psInsertCircupp.setInt( 2,  consecutivoConsulta[0]);
					psInsertCircupp.setString(3, procedimiento.getCodigoProcedimiento());
					psInsertCircupp.setInt(4, idProcedimiento);
					psInsertCircupp.setInt(5, procedimiento.getVia());
					psInsertCircupp.setInt(6, idProcedimiento);
					psInsertCircupp.setString(7, "");
					psInsertCircupp.setString(8, procedimiento.getBilateralidad());
					psInsertCircupp.setString(9, activoAyudante2); 
					psInsertCircupp.setString(10, activoPerfucionista);
					psInsertCircupp.setString(11, procedimiento.getPrequirurgico().split("-")[0]  );
					psInsertCircupp.setString(12, procedimiento.getPostquirurgico().split("-")[0]  );
					psInsertCircupp.setInt(13, 0);
					psInsertCircupp.setString(14, body.getUsuario());
					psInsertCircupp.setString(15, body.getPrograma());
					psInsertCircupp.setInt(16, fechaActual());
					psInsertCircupp.setInt(17, horaActual());
					
					int rsInsertCircupp = psInsertCircupp.executeUpdate();
					
					if(rsInsertCircupp < 1) {
						return false;
					}
					
				}
				
				String sqlFaccirq="SELECT * FROM faccirq WHERE ingcrq=? AND cnscrq=? AND eqpcrq=?";
				
				PreparedStatement psFaccirq = con.prepareStatement(sqlFaccirq);
				
				psFaccirq.setInt(1,  Integer.parseInt(body.getIngreso()) );
				psFaccirq.setInt(2,  consecutivoConsulta[0]  );
				psFaccirq.setInt(3,  idProcedimiento  );
				
				ResultSet rsFaccirq = psFaccirq.executeQuery();
				
				int countFaccirq =0;
				
				while(rsFaccirq.next()) {
					countFaccirq ++;
				}
				
				if(countFaccirq > 0) {
					String sqlUpdateFaccirq = "UPDATE faccirq SET"
							+ " espcrq=?,"
							+ " pescrq=?,"
							+ " anecrq=?,"
							+ " pancrq=?,"
							+ " tancrq=?,"
							+ " ay1crq=?,"
							+ " pa1crq=?,"
							+ " ay2crq=?,"
							+ " pa2crq=?,"
							+ " pprcrq=?,"
							+ " ds1crq=?,"
							+ " DS3CRQ=?,"
							+ " umocrq=?,"
							+ " pmocrq=?,"
							+ " fmocrq=?,"
							+ " hmocrq=?"
							+ " WHERE ingcrq=? AND cnscrq=? AND eqpcrq=?";
					
					
					PreparedStatement psUpdateFaccirq = con.prepareStatement(sqlUpdateFaccirq);
					
					psUpdateFaccirq.setInt(1,nitCirujano);
					psUpdateFaccirq.setInt(2, cobroCirujano);
					psUpdateFaccirq.setString(3,anesteciologo);
					psUpdateFaccirq.setInt(4, cobroAnesteciologo);
					psUpdateFaccirq.setInt(5,body.getActoQuirurgico().getTipoAnestesia());
					psUpdateFaccirq.setInt(6,nitayudante1  );
					psUpdateFaccirq.setInt(7,cobroAyudante1);
					psUpdateFaccirq.setInt(8, niayudante2 );
					psUpdateFaccirq.setInt(9,cobroAyudante2);
					psUpdateFaccirq.setInt(10,cobroPerfusionista);
					psUpdateFaccirq.setString(11, procedimiento.getCodigoProcedimiento() );
					psUpdateFaccirq.setString(12, procedimiento.getEspecialdad_cirujano());
					psUpdateFaccirq.setString(13, body.getUsuario());
					psUpdateFaccirq.setString(14, body.getPrograma());
					psUpdateFaccirq.setInt(15, fechaActual());
					psUpdateFaccirq.setInt(16, horaActual());
					psUpdateFaccirq.setInt(17, Integer.parseInt(body.getIngreso()));
					psUpdateFaccirq.setInt(18, consecutivoConsulta[0]);
					psUpdateFaccirq.setInt(19, idProcedimiento);
					
					int rsUpdateFaccirq = psUpdateFaccirq.executeUpdate();
					
					if(rsUpdateFaccirq > 1 || rsUpdateFaccirq < 1) {
						System.out.println(" RIA133: PROCESO rsUpdateFaccirq INTENTO ACTUALIZAR " + rsUpdateFaccirq);
						con.rollback();
						return false;
					}
					
				}else {
					String sqlUnsertFaccirq = "INSERT INTO faccirq (ingcrq,cnscrq,eqpcrq,espcrq,pescrq,anecrq,pancrq,tancrq,ay1crq,pa1crq,ay2crq,pa2crq,prfcrq,pprcrq,ds1crq,DS3CRQ,usrcrq,pgmcrq,feccrq,horcrq)"
							+ "VALUES( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";
					
					PreparedStatement psInsertFaccirq = con.prepareStatement(sqlUnsertFaccirq);
					
					psInsertFaccirq.setInt(1, Integer.parseInt( body.getIngreso() ) );
					psInsertFaccirq.setInt(2, consecutivoConsulta[0]);
					psInsertFaccirq.setInt(3, idProcedimiento);
					psInsertFaccirq.setInt(4,nitCirujano);
					psInsertFaccirq.setInt(5, cobroCirujano);
					psInsertFaccirq.setString(6,anesteciologo);
					psInsertFaccirq.setInt(7, cobroAnesteciologo);
					psInsertFaccirq.setInt(8,body.getActoQuirurgico().getTipoAnestesia());
					psInsertFaccirq.setInt(9, nitayudante1);
					psInsertFaccirq.setInt(10,cobroAyudante1);
					psInsertFaccirq.setInt(11, niayudante2);
					psInsertFaccirq.setInt(12,cobroAyudante2);
					psInsertFaccirq.setInt(13, Integer.parseInt(perfusionista) );
					psInsertFaccirq.setInt(14,cobroPerfusionista);
					psInsertFaccirq.setString(15, procedimiento.getCodigoProcedimiento() );
					psInsertFaccirq.setString(16, procedimiento.getEspecialdad_cirujano());
					psInsertFaccirq.setString(17, body.getUsuario());
					psInsertFaccirq.setString(18, body.getPrograma());
					psInsertFaccirq.setInt(19, fechaActual());
					psInsertFaccirq.setInt(20, horaActual());
					
					int rsUnsertFaccirq = psInsertFaccirq.executeUpdate();
					
					if(rsUnsertFaccirq < 1) {
						con.rollback();
						return false;
					}
				}
				
				String sqlCirMedq = "SELECT * FROM CIRMEDQ WHERE"
						+ " INGCRQ=? AND CNSCRQ=? AND EQPCRQ=?";
				
				PreparedStatement psCirMedq = con.prepareStatement(sqlCirMedq);
				
				psCirMedq.setInt(1, Integer.parseInt(body.getIngreso()) );
				psCirMedq.setInt(2, consecutivoConsulta[0]);
				psCirMedq.setInt(3, idProcedimiento);
				
				ResultSet rsCirMedq = psCirMedq.executeQuery();
				int countCirMedq =0;
				
				while( rsCirMedq.next() ) {
					countCirMedq++; 
				}
				
				if(countCirMedq < 1) {
					String sqlInsertCirMedq="INSERT INTO CIRMEDQ (ingcrq,cnscrq,eqpcrq,espcrq,pescrq,anecrq,pancrq,tancrq,ay1crq,pa1crq,ay2crq,pa2crq,prfcrq,pprcrq,ds1crq,DS2CRQ,DS3CRQ,usrcrq,pgmcrq,feccrq,horcrq)"
							+ "VALUES( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";
					
					PreparedStatement psInsertCirMedq = con.prepareStatement(sqlInsertCirMedq);
					
					psInsertCirMedq.setInt(1, Integer.parseInt( body.getIngreso() ) );
					psInsertCirMedq.setInt(2, consecutivoConsulta[0]);
					psInsertCirMedq.setInt(3, idProcedimiento);
					psInsertCirMedq.setInt(4,nitCirujano);
					psInsertCirMedq.setInt(5, cobroCirujano);
					psInsertCirMedq.setString(6,anesteciologo);
					psInsertCirMedq.setInt(7, cobroAnesteciologo);
					psInsertCirMedq.setInt(8,body.getActoQuirurgico().getTipoAnestesia());
					psInsertCirMedq.setInt(9, nitayudante1);
					psInsertCirMedq.setInt(10,cobroAyudante1);
					psInsertCirMedq.setInt(11, niayudante2);
					psInsertCirMedq.setInt(12,cobroAyudante2);
					psInsertCirMedq.setInt(13, Integer.parseInt(perfusionista) );
					psInsertCirMedq.setInt(14,cobroPerfusionista);
					psInsertCirMedq.setString(15, procedimiento.getCodigoProcedimiento() );
					psInsertCirMedq.setString(16, procedimiento.getPrequirurgico().split("-")[0]  );
					psInsertCirMedq.setString(17, procedimiento.getEspecialdad_ayudante1() );
					psInsertCirMedq.setString(18, body.getUsuario());
					psInsertCirMedq.setString(19, body.getPrograma());
					psInsertCirMedq.setInt(20, fechaActual());
					psInsertCirMedq.setInt(21, horaActual());
					
					int rsInsertCirMedq = psInsertCirMedq.executeUpdate();
					
					if(rsInsertCirMedq < 1) {
						con.rollback();
						return false;
					}
					
				}
				
				
				String sqlInsertRiadet ="INSERT INTO riadet (tiddet,niddet,ingdet,ccidet,cupdet,ferdet,hrrdet,estdet,mardet,usrdet,pgmdet,fecdet,hordet) "
						+ "VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,? )";
				
				PreparedStatement psInsertRiadet = con.prepareStatement(sqlInsertRiadet);
				
				psInsertRiadet.setString(1, body.getInformacionPaciente().getcTipId());
				psInsertRiadet.setString(2, body.getInformacionPaciente().getnNumId());
				psInsertRiadet.setString(3, body.getIngreso());
				psInsertRiadet.setInt(4, consecutivoConsulta[0]);
				psInsertRiadet.setString(5, procedimiento.getCodigoProcedimiento());
				psInsertRiadet.setInt(6, fechaActual());
				psInsertRiadet.setInt(7, horaActual());
				psInsertRiadet.setInt(8, 8);
				psInsertRiadet.setInt(9, 1);
				psInsertRiadet.setString(10, body.getUsuario());
				psInsertRiadet.setString(11, body.getPrograma());
				psInsertRiadet.setInt(12, fechaActual());
				psInsertRiadet.setInt(13, horaActual());
				
				int rsInsertRiadet = psInsertRiadet.executeUpdate();
				
				if(rsInsertRiadet < 1) {
					con.rollback();
					return false;
				}
				
				idProcedimiento ++;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		
		return true;
	}
	
	private String obtenerRegistroMedico(String usuario) {
		
		String registro ="";
		
		String sql="SELECT REGMED FROM RIARGMN WHERE USUARI=?";
	
		try(
				DBAS400 db = new DBAS400();
				Connection con = db.getCon();
				PreparedStatement psMedicos = con.prepareStatement(sql);
		){
				
			
			psMedicos.setString(1, usuario);
			
			ResultSet rsMedicos = psMedicos.executeQuery();
			
			while(rsMedicos.next()) {
				registro = rsMedicos.getString("REGMED");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("RIA133: No obtuvo registro medico");
			return "";
		}
		
		
		return registro;
	}
	
	private StructMedicosControlQuirurgico recuperarDatosMedicos(String registroMedico, int especialidad) {
		
		StructMedicosControlQuirurgico medico = new StructMedicosControlQuirurgico();
		
		String sql="SELECT "
					+ "TRIM(REGMED) REGMED, TRIM(USUARI) USUARI, "
					+ "TRIM(NOMMED) ||' '|| TRIM(NNOMED) NOMBRE, "
					+ "TIDRGM TIPODOC,"
					+ "NIDRGM DOCUMENTO,"
					+ "CODRGM,"
					+ "TPMRGM,"
					+ "CENCO "
					+ "FROM RIARGMN WHERE TPMRGM=? AND REGMED =?";
		
		try(
				DBAS400 db = new DBAS400();
				Connection con = db.getCon();
				PreparedStatement psMedicos = con.prepareStatement(sql);
		){
				
			psMedicos.setInt(1, especialidad);
			psMedicos.setString(2, registroMedico);
			
			
			ResultSet rsMedicos = psMedicos.executeQuery();
			
			while(rsMedicos.next()) {
				medico.setRegistroMedico( rsMedicos.getString("REGMED") );
				medico.setUsuario(  rsMedicos.getString("USUARI") );
				medico.setNombre(  rsMedicos.getString("NOMBRE")  );
				medico.setTipoDocumento( rsMedicos.getString("TIPODOC")  );
				medico.setDocumento(  rsMedicos.getString("DOCUMENTO") );
				medico.setCodrgm(  rsMedicos.getString("CODRGM") );
				medico.setTpmrgm(  rsMedicos.getInt("TPMRGM") );
				medico.setCenco( rsMedicos.getInt("CENCO") );
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return medico;
		}
			
		return medico;
	}
	
	
	private boolean crearIndicadoresSala(StructGuardarControlQuirurgico body,  int[] consecutivosCirugias, Connection con ) throws SQLException {
		
		String salaReal = (body.getActoQuirurgico().getOtraSala().equals("")) ? body.getActoQuirurgico().getSalaCirugia() : body.getActoQuirurgico().getOtraSala();
		String salaAbreviada = (body.getActoQuirurgico().getOtraSala().equals("")) ? body.getActoQuirurgico().getSalaCirugia().substring(0,2) :  body.getActoQuirurgico().getLabelOtraSala().substring(0,3) ;
		String procedimientoPrincipal ="";
		String diagnosticoSale ="";
		String rsSuborg ="";
		
		
		int politraumatizado = (body.getActoQuirurgico().isPolitraumatizado()) ? 1 : 0;
		
		for(StructProcedimientos procedimiento : body.getProcedimientos()) {
			
			 procedimientoPrincipal = procedimiento.getCodigoProcedimiento();
			 diagnosticoSale = procedimiento.getPostquirurgico().split("-")[0];
			
			 break;
		}
		
		
		String sql ="SELECT TRIM(suborg) as suborg FROM riahis WHERE nroing=? AND indice=? ORDER BY suborg DESC FETCH FIRST 1 ROWS ONLY";
		
		try(
			PreparedStatement  ptEntradaDiagnostico = con.prepareStatement(sql);
		){
				
			ptEntradaDiagnostico.setString(1, body.getIngreso());
			ptEntradaDiagnostico.setInt(2, 25);
			
			ResultSet rsEntradaDiagnostico = ptEntradaDiagnostico.executeQuery();
			
			while(rsEntradaDiagnostico.next()) {
				rsSuborg = rsEntradaDiagnostico.getString("suborg");
			}
			
		} catch (Exception e) {
			con.rollback();
			e.printStackTrace();
			return false;
		}
		
		
		if(consecutivosCirugias[1] ==1) {
			
			String sqlUpdateFaccRh="UPDATE FACCIRH SET"
					+ "FHRCRH=?,"
					+ "HRRCRH=?,"
					+ "SLRCRH=?,"
					+ "TPRCRH=?,"
					+ "TANCRH=?,"
					+ "FORCRH=?,"
					+ "CTRCRH=?,"
					+ "CUPCRH=?,"
					+ "DG1CRH=?,"
					+ "DG2CRH=?,"
					+ "UMOCRH=?,"
					+ "PMOCRH=?,"
					+ "FMOCRH=?,"
					+ "HMOCRH=?"
					+ "WHERE INGCRH=? AND CNSCRH=?";
			
			try(
				PreparedStatement  ptUpdateFaccRh = con.prepareStatement(sqlUpdateFaccRh);
			){
				
				ptUpdateFaccRh.setInt(1, fechaActual());
				ptUpdateFaccRh.setInt(2, horaActual());
				ptUpdateFaccRh.setString(3, salaAbreviada);
				ptUpdateFaccRh.setInt(4, body.getActoQuirurgico().getTipoCirugia() );
				ptUpdateFaccRh.setInt(5, body.getActoQuirurgico().getTipoAnestesia() );
				ptUpdateFaccRh.setInt(6, politraumatizado);
				ptUpdateFaccRh.setString(7, "0007");
				ptUpdateFaccRh.setString(8, procedimientoPrincipal);
				ptUpdateFaccRh.setString(9, rsSuborg);
				ptUpdateFaccRh.setString(10, diagnosticoSale);
				ptUpdateFaccRh.setString(11, body.getUsuario());
				ptUpdateFaccRh.setString(12, body.getPrograma());
				ptUpdateFaccRh.setInt(13, fechaActual());
				ptUpdateFaccRh.setInt(14, horaActual());
				ptUpdateFaccRh.setInt(15, Integer.parseInt(body.getIngreso()));
				ptUpdateFaccRh.setInt(16, consecutivosCirugias[0]);
				
				int rsUpdateFaccRh = ptUpdateFaccRh.executeUpdate();
				
				if(rsUpdateFaccRh > 1) {
					System.out.println("RIA133: PROCESO rsUpdateFaccRh INTENTO ACTUALIZAR " + rsUpdateFaccRh);
					con.rollback();
					return false;
				}
				if(rsUpdateFaccRh < 1) {
					con.rollback();
					return false;
				}
				
				
			} catch (Exception e) {
				con.rollback();
				e.printStackTrace();
				return false;
			}
		}else {
			
			String sqlInsertFaccRh="INSERT INTO FACCIRH (ingcrh,cnscrh,fhrcrh,hrrcrh,slrcrh,tprcrh,tancrh,forcrh,ctrcrh,cupcrh,dg1crh,dg2crh,usrcrh,pgmcrh,feccrh,horcrh) "
					+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			try(
				PreparedStatement  ptEntradaDiagnostico = con.prepareStatement(sqlInsertFaccRh);
			){
				
				ptEntradaDiagnostico.setInt(1, Integer.parseInt(body.getIngreso()));
				ptEntradaDiagnostico.setInt(2,consecutivosCirugias[0]);
				ptEntradaDiagnostico.setInt(3, fechaActual());
				ptEntradaDiagnostico.setInt(4, horaActual());
				ptEntradaDiagnostico.setString(5, salaReal);
				ptEntradaDiagnostico.setInt(6, body.getActoQuirurgico().getTipoCirugia() );
				ptEntradaDiagnostico.setInt(7, body.getActoQuirurgico().getTipoAnestesia());
				ptEntradaDiagnostico.setInt(8, politraumatizado);
				ptEntradaDiagnostico.setString(9, "0007");
				ptEntradaDiagnostico.setString(10, procedimientoPrincipal);
				ptEntradaDiagnostico.setString(11, rsSuborg);
				ptEntradaDiagnostico.setString(12, diagnosticoSale);
				ptEntradaDiagnostico.setString(13, body.getUsuario());
				ptEntradaDiagnostico.setString(14, body.getPrograma());
				ptEntradaDiagnostico.setInt(15, fechaActual());
				ptEntradaDiagnostico.setInt(16, horaActual());
				
				int rsInsertFaccRh = ptEntradaDiagnostico.executeUpdate();
				
				if(rsInsertFaccRh < 1) {
					con.rollback();
					return false;
				}
				
				String sqlCirCabH = "SELECT * FROM CIRCABH WHERE INGCRH=? AND CNSCRH=?";
				
				PreparedStatement  ptCirCabH = con.prepareStatement(sqlCirCabH);
				
				ptCirCabH.setInt(1,  Integer.parseInt( body.getIngreso() ) );
				ptCirCabH.setInt(2,consecutivosCirugias[0]);
				
				ResultSet rsCirCabH  = ptCirCabH.executeQuery();
				
				int count =0;
				while(rsCirCabH.next()) {
					count++;
				}
				
				if(count < 1) {
					String sqlInsertCirCabh = "INSERT INTO CIRCABH (INGCRH,CNSCRH,FHRCRH,HRRCRH,SLRCRH,TPRCRH,TANCRH,FORCRH,CTRCRH,CUPCRH,DG1CRH,DG2CRH,USRCRH,PGMCRH,FECCRH,HORCRH)"
							 + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					
					PreparedStatement  ptCirCabHInsert = con.prepareStatement(sqlInsertCirCabh);
					
					ptCirCabHInsert.setInt(1, Integer.parseInt(body.getIngreso()));
					ptCirCabHInsert.setInt(2,consecutivosCirugias[0]);
					ptCirCabHInsert.setInt(3, fechaActual());
					ptCirCabHInsert.setInt(4, horaActual());
					ptCirCabHInsert.setString(5, salaReal);
					ptCirCabHInsert.setInt(6, body.getActoQuirurgico().getTipoCirugia() );
					ptCirCabHInsert.setInt(7, body.getActoQuirurgico().getTipoAnestesia());
					ptCirCabHInsert.setInt(8, politraumatizado);
					ptCirCabHInsert.setString(9, "0007");
					ptCirCabHInsert.setString(10, procedimientoPrincipal);
					ptCirCabHInsert.setString(11, rsSuborg);
					ptCirCabHInsert.setString(12, diagnosticoSale);
					ptCirCabHInsert.setString(13, body.getUsuario());
					ptCirCabHInsert.setString(14, body.getPrograma());
					ptCirCabHInsert.setInt(15, fechaActual());
					ptCirCabHInsert.setInt(16, horaActual());
					
					int rsCirCabHInsert  = ptCirCabHInsert.executeUpdate();
					if(rsCirCabHInsert < 1) {
						con.rollback();
						return false;
					}
				}
				
				
			} catch (Exception e) {
				con.rollback();
				e.printStackTrace();
				return false;
			}
		}
		
		
		
		return true;
	}
	
	
	private int[] generarConsecutivoConsulta(StructGuardarControlQuirurgico body) {
		
		int [] consecutivos = {0,0};
		
		String salaAbreviada = (body.getActoQuirurgico().getOtraSala().equals("")) ? body.getActoQuirurgico().getSalaCirugia().substring(0,2) :  body.getActoQuirurgico().getLabelOtraSala().substring(0,3) ;
		

		String sql ="SELECT cnscrh FROM faccirhl01 WHERE ingcrh=? AND estcrh=? AND salcir=? ORDER BY cnscrh DESC FETCH FIRST 1 ROWS ONLY";
		String sqlConsecutivoCirugia= "SELECT cnscrh FROM faccirh WHERE ingcrh=? ORDER BY cnscrh DESC FETCH FIRST 1 ROWS ONLY";
		
		
		try(
				DBAS400 db = new DBAS400();
				Connection con = db.getCon();
				PreparedStatement  ptConsecutivo = con.prepareStatement(sql);
				PreparedStatement  ptCirugia= con.prepareStatement(sqlConsecutivoCirugia);
		) {
			
				ptConsecutivo.setString(1, body.getIngreso());
				ptConsecutivo.setString(2, "0");
				ptConsecutivo.setString(3, salaAbreviada);
				
				ResultSet rs = ptConsecutivo.executeQuery();
				
				int count1=0;
				while (rs.next()) {
					count1++;
					consecutivos[0] = rs.getInt("cnscrh");
					
				}
				
				ptConsecutivo.close();
				
				if(count1  > 0) {
					String sqlRiaHis="SELECT * FROM riaord WHERE ninord=? and TRIM(coaord)=? and ccoord=? and estord=? FETCH FIRST 1 ROWS ONLY";
					PreparedStatement  ptConsecutivoRiaHis = con.prepareStatement(sqlRiaHis);
					
					
					ptConsecutivoRiaHis.setString(1, body.getIngreso());
					ptConsecutivoRiaHis.setString(2, "22");
					ptConsecutivoRiaHis.setInt(3, consecutivos[0] );
					ptConsecutivoRiaHis.setInt(4, 3);
					
					ResultSet resulultConsecutivoRiaHis = ptConsecutivoRiaHis.executeQuery();
					
					int count2=0;
					while(resulultConsecutivoRiaHis.next()) {
						count2++;
					}
					
					if(count2 < 1) {
						consecutivos[1] =1;
					}else {
						
						String sqlFaccirh = "SELECT cnscrh FROM faccirh WHERE ingcrh=? ORDER BY cnscrh DESC FETCH FIRST 1 ROWS ONLY";
						
						PreparedStatement  ptFaccirh= con.prepareStatement(sqlFaccirh);
						
						ptFaccirh.setString(1, body.getIngreso());
						
						ResultSet resulultConsecutivoFaccirh = ptConsecutivoRiaHis.executeQuery();
						
						int count =0;
						int conse =0;
						while (resulultConsecutivoFaccirh.next()) {
							count++;
							conse = resulultConsecutivoFaccirh.getInt("cnscrh");
						}
						
						if(count > 0) {
							consecutivos[0]  = conse +1;
						}else {
							consecutivos[0] =1;
						}
						ptFaccirh.close();
					}
				}else {

					
					ptCirugia.setString(1, body.getIngreso());
					
					ResultSet resulultConsecutivoCiru = ptCirugia.executeQuery();
					
					int count =0;
					int conse =0;
					while (resulultConsecutivoCiru.next()) {
						count++;
						conse = resulultConsecutivoCiru.getInt("cnscrh");
					}
					
					
					if(count > 0) {
						consecutivos[0]  = conse+1;
					}else {
						consecutivos[0]  =1;
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				return consecutivos;
			}
		

		
		return consecutivos;
	}
	
	
	
	private int[] consecutivoXPaciente(String tipoDocumento, String documento) {
	
		
		String sql = "SELECT ccipac FROM riapac WHERE tidpac=? AND nidpac=?";
		int consecutivo = 0;
		int [] respuesta = {0,0};
		int count =0;
		
		try(
			DBAS400 db = new DBAS400();
			Connection con = db.getCon();
			PreparedStatement  pt = con.prepareStatement(sql);
		) {

			pt.setString(1, tipoDocumento);
			pt.setString(2, documento);
			
			ResultSet rs = pt.executeQuery();

			while(rs.next()) {
				count ++;
				consecutivo = rs.getInt("ccipac");
			}
			
			if(count < 1) {
				consecutivo=1;
				count =1;
			}
			
			
			rs.close();
			pt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		respuesta[0] = consecutivo;
		respuesta[1] = count;
		
		return respuesta;
	}
	
	
	public String CadenaConcatenarDerecha(int longitud, int maximo, String caracter, String dato) {
		
		int LongitudCadena = maximo- longitud ;
		String cadenaBlanca =caracter;
		
		cadenaBlanca = cadenaBlanca.repeat(LongitudCadena);
		
		return dato+cadenaBlanca;
	}
	
	
	public String CadenaConcatenarIzquierda(int longitud, int maximo, String caracter, String dato) {
		
		int LongitudCadena = maximo- longitud ;
		String cadenaBlanca =caracter;
		
		cadenaBlanca = cadenaBlanca.repeat(LongitudCadena);
		
		return cadenaBlanca+dato;
	}

	public StructValidarProcedimientos validarProcedimientos(StructFiltroValidarProcedimiento procedimiento) {
		

		StructValidarProcedimientos validarProcedimiento = new StructValidarProcedimientos();
		
		String sql = "SELECT"
				+ " CASE WHEN A.RF5CUP='NOPB' THEN 'N' ELSE 'P' END AS NOPOS,"
				+ "  TRIM(A.ESPCUP) ESPCUP "
				+ "FROM "
				+ "RIACUPL10 AS A"
				+ " LEFT JOIN RIACUPA AS B ON A.CODCUP=B.CODCUA"
				+ " WHERE A.RIPCUP<>? AND A.CODCUP = ?"
				+ " ORDER BY"
				+ " A.DESCUP";
		
		try(
			DBAS400 db = new DBAS400();
			Connection con = db.getCon();
			PreparedStatement  pt = con.prepareStatement(sql);
		) {
			
			pt.setString(1,"P");
			pt.setString(2,procedimiento.getProcedimiento());
			
			ResultSet rs = pt.executeQuery();
			
			while( rs.next()  ) {
				validarProcedimiento.setPos( rs.getString("NOPOS") );
				validarProcedimiento.setEspecialidad( rs.getString("ESPCUP") );
			}
			
		} catch (Exception e) {
			validarProcedimiento.setErrorCode(500);
			validarProcedimiento.setErrorMessage(e.toString());
			System.out.println("RIA133: "+e.toString());
			e.printStackTrace();
			return validarProcedimiento;
		}
		
		
		return validarProcedimiento;
	}
	
	
	public StructResponseMiPres datosMipres(StructFiltroMiPres filtros) {
		
		StructResponseMiPres miPres = new StructResponseMiPres();
		
		
		String sql ="CALL MIPRES()";
		
		try(
			DBAS400 db = new DBAS400();
			Connection con = db.getCon();
			PreparedStatement  pt = con.prepareStatement(sql);
		) {
		
			ResultSet rs = pt.executeQuery();
			
			while( rs.next() ) {
				
				miPres.setTexto( rs.getString("TEXTO") );
				miPres.setSubtitulo( rs.getString("SUBTITULOS") );
				miPres.setAlerta( rs.getString("ALERTA") );
				miPres.setConfirmacion1( rs.getString("CONFIRMACION1") );
				miPres.setConfirmacion2( rs.getString("CONFIRMACION2") );
				miPres.setRuta( rs.getString("RUTA") );
				
			}
			
						
		} catch (Exception e) {
			System.out.println("RIA133: "+e.toString());
			e.printStackTrace();
			miPres.setErrorCode(0);
			miPres.setErrorMessage("Error al obtener los datos de MiPres");
			return miPres;
		}
		
		String isMipres = obtenerPlanMipres(filtros);
		
		if( isMipres.equals("ERR") ) {
			miPres.setErrorCode(0);
			miPres.setErrorMessage("Error al obtener los datos de MiPres");
			return miPres;
		}
		
		miPres.setIsMipre(isMipres);
		miPres.setErrorCode(0);
		
		return miPres;
	}
	
	private String obtenerPlanMipres(StructFiltroMiPres filtros) {
		
		String sql ="SELECT DE2TMA FROM TABMAE WHERE TIPTMA=? AND CL1TMA=? AND CL2TMA=? AND ESTTMA=?";
		String respuesta ="N";
		
		int codigo = 0;
		
		try(
			DBAS400 db = new DBAS400();
			Connection con = db.getCon();
			PreparedStatement  pt = con.prepareStatement(sql);
		) {
					
			pt.setString(1,"WSNOPOS");
			pt.setString(2,"PLANOPOS");
			pt.setString(3,"1");
			pt.setString(4,"");
					
			ResultSet rs = pt.executeQuery();
			
			while(rs.next()) {
				codigo = Integer.parseInt(rs.getString("DE2TMA").trim());
			}
					
		} catch (Exception e) {
			System.out.println("RIA133: "+e.toString());
			e.printStackTrace();
			return "ERR";
		}
		
		String tipoComplementario ="";
		String tipoEntidad="";
		
		if(codigo > 0) {
			
			String sqlComplementario ="SELECT CL3TMA FROM TABMAE WHERE TIPTMA=?  AND CL1TMA=? AND CL2TMA=? AND ESTTMA=?";
			
			try(
				DBAS400 db = new DBAS400();
				Connection con = db.getCon();
				PreparedStatement  ptComplementario = con.prepareStatement(sqlComplementario);
			) {
				
				ptComplementario.setString(1, "WSNOPOS");
				ptComplementario.setString(2, "COMPLE");
				ptComplementario.setString(3, filtros.getPlan());
				ptComplementario.setString(4, "");
							
				ResultSet rsComplementario = ptComplementario.executeQuery();
					
				while(rsComplementario.next()) {
					tipoComplementario = rsComplementario.getString("CL3TMA").trim();
				}
							
			} catch (Exception e) {
				System.out.println("RIA133: "+e.toString());
				e.printStackTrace();
				return "ERR";
			}
			
			System.out.println("{"+tipoComplementario+"}");
			
			tipoEntidad =( !tipoComplementario.equals("") ) ? tipoComplementario : filtros.getTipoPlan();
			
			String sqlRegimenPlan="SELECT TRIM(CL3TMA) CL3TMA  FROM TABMAE WHERE TIPTMA=? AND CL1TMA=? AND CL2TMA=? AND ESTTMA=?";
			
			try(
				DBAS400 db = new DBAS400();
				Connection con = db.getCon();
				PreparedStatement  ptRegimenPlan = con.prepareStatement(sqlRegimenPlan);
			) {
				
				ptRegimenPlan.setString(1, "WSNOPOS");
				ptRegimenPlan.setString(2, "TIPENT");
				ptRegimenPlan.setString(3, tipoEntidad);
				ptRegimenPlan.setString(4, "");
				
				ResultSet rsRegimenPlan = ptRegimenPlan.executeQuery();
				
				while( rsRegimenPlan.next() ) {
					respuesta = rsRegimenPlan.getString("CL3TMA");
				}
				
			} catch (Exception e) {
				System.out.println("RIA133: "+e.toString());
				e.printStackTrace();
				return "ERR";
			}
			
		}else {
			return respuesta;
		}
		
		
		return respuesta;
	}
	

}
