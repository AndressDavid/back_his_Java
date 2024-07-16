package com.shaio.his.common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.shaio.his.AS400.DBAS400;
import com.shaio.his.cups.struct_cups;

@Service
public class XComun implements IComun {

	@Override
	public StructResponseCommon extraerComunesEspecialidades(StructFiltroEspecialidades datosFiltro) {
		
		
		StructResponseCommon respuestaEstructuraEspecilidad = new StructResponseCommon();
		List<StructComunEspecialidades> listaEspecialidades =new ArrayList<StructComunEspecialidades>();
		
		
		String filtroBodesp = (datosFiltro.getBodesp().equals("")) ? "" : " AND bodesp = '"+datosFiltro.getBodesp()+"'";
		String filtroUbiesp = (datosFiltro.getUbiesp().equals("")) ? "" : " AND ubiesp = '" + datosFiltro.getUbiesp() + "'"; 
		String filtroCcsesp = (datosFiltro.getCcsesp().equals("")) ? "" : " AND ccsesp ='" + datosFiltro.getCcsesp() + "'";
		String filtroPgcesp = (datosFiltro.getPgcesp().equals("")) ? "" : " AND pgcesp ='"+ datosFiltro.getPgcesp() + "'";
		
		
		String sql="SELECT CODESP, DESESP FROM ALPHILDAT.RIAESPE WHERE 1=1 " + filtroBodesp + filtroUbiesp + filtroCcsesp + filtroPgcesp + " ORDER BY DESESP ASC" ;
		
		try (
				DBAS400 db = new DBAS400();
				Connection con = db.getCon();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql);
			) {
			while (rs.next()) {
				
				StructComunEspecialidades sEspecialidad = new StructComunEspecialidades();
				
				sEspecialidad.setCodigo( rs.getString("CODESP").trim() );
				sEspecialidad.setDescripcion( rs.getString("DESESP").trim() );
				
				listaEspecialidades.add(sEspecialidad);
				
			}
			
			rs.close();
		} catch (Exception e) {
			System.out.println(e);
			respuestaEstructuraEspecilidad.setErrorCode(500);
			respuestaEstructuraEspecilidad.setErrorMessage(e.toString());
	
			e.printStackTrace();
		}
		
		respuestaEstructuraEspecilidad.setErrorCode(0);
		respuestaEstructuraEspecilidad.setListaComun(listaEspecialidades);
		
		return respuestaEstructuraEspecilidad;
	}

	@Override
	public StructResponseCommon extraerSalas(String tipo) {
		
		
		List<StructSalas> Listsalas =new ArrayList<StructSalas>();
		StructResponseCommon respuestaEstructuraSalas= new StructResponseCommon();
		
		String sql = "SELECT TRIM(SECHAB)||TRIM(NUMHAB) AS NUMHAB FROM ALPHILDAT.FACHABL0 WHERE IDDHAB='0' AND (SECHAB LIKE '"+tipo+"%') ORDER BY SECHAB, NUMHAB";
		
		try (
				DBAS400 db = new DBAS400();
				Connection con = db.getCon();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql);
			) {
			while (rs.next()) {
				
				StructSalas salas = new StructSalas(); 	
				
				salas.setNumbHabitacion(rs.getString("NUMHAB"));
				Listsalas.add(salas);
			

			}
			rs.close();
		} catch (Exception e) {
			System.out.println(e);
			respuestaEstructuraSalas.setErrorCode(500);
			respuestaEstructuraSalas.setErrorMessage(e.toString());
			
			e.printStackTrace();
		}
		
		respuestaEstructuraSalas.setErrorCode(0);
		respuestaEstructuraSalas.setListaComun(Listsalas);
		
		return respuestaEstructuraSalas;
	}

	@Override
	public StructResponseCommon extraerOtrasSalas() {
		
		List<StructOtrasSalas> ListOtrasSalas =new ArrayList<StructOtrasSalas>();
		StructResponseCommon respuestaEstructuraOtrasSalas = new StructResponseCommon();
		
		String sql = "SELECT CL2TMA as Codigo, TRIM(DE2TMA) as Descripcion, OP2TMA as OtraAbreviado "
				+ " FROM ALPHILDAT.tabmae WHERE CL1TMA='OTRASAL' AND ESTTMA='' AND TIPTMA='DESCRQX' ORDER BY Descripcion";
		
		try (
				DBAS400 db = new DBAS400();
				Connection con = db.getCon();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql);
			) {
			while (rs.next()) {
				
				StructOtrasSalas otraSala = new StructOtrasSalas(); 	
				
				otraSala.setCodigo( rs.getString("Codigo").trim() );
				otraSala.setDescripcion( rs.getString("Descripcion").trim()  );
				otraSala.setAbreviado( rs.getString("OtraAbreviado").trim()  );
				
				ListOtrasSalas.add(otraSala);
				
			}
			rs.close();
		} catch (Exception e) {
			System.out.println(e);
			respuestaEstructuraOtrasSalas.setErrorCode(500);
			respuestaEstructuraOtrasSalas.setErrorMessage(e.toString());
			
			e.printStackTrace();
		}
		
		respuestaEstructuraOtrasSalas.setErrorCode(0);
		respuestaEstructuraOtrasSalas.setListaComun(ListOtrasSalas);
		
		return respuestaEstructuraOtrasSalas;
	}

	@Override
	public StructResponseCommon extaerTipoCirugia() {

		StructResponseCommon respuestaTipoCirugia = new StructResponseCommon();
		List<StructTipoCirugia> ListTipoCirugia =new ArrayList<StructTipoCirugia>();
		
		String sql = "SELECT DSLTAB AS DESTCI,SUBSTR(CDSTAB,1,2) AS CODTCI FROM ALPHILDAT.RIATAB"
				+ " WHERE CDTTAB=16 AND CDRTAB=1 AND CDSTAB>0";
		
		try (
				DBAS400 db = new DBAS400();
				Connection con = db.getCon();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql);
			) {
			while (rs.next()) {
				
				StructTipoCirugia tipoCirugia = new StructTipoCirugia(); 	
				
				tipoCirugia.setCodigo( rs.getString("CODTCI").trim() );
				tipoCirugia.setDescripcion( rs.getString("DESTCI").trim()  );
				
				ListTipoCirugia.add(tipoCirugia);

			}
			rs.close();
		} catch (Exception e) {
			System.out.println(e);
			respuestaTipoCirugia.setErrorCode(500);
			respuestaTipoCirugia.setErrorMessage(e.toString());
			
			e.printStackTrace();
		}
		
		respuestaTipoCirugia.setErrorCode(0);
		respuestaTipoCirugia.setListaComun(ListTipoCirugia);
		
		return respuestaTipoCirugia;
		
	}

	@Override
	public StructResponseCommon extaerListProfesional(StructFiltrosTipoProfesional filtros) {
		
		
		StructResponseCommon respuestaProfesionales = new StructResponseCommon();
		List<StructProfesional> listProfesional =new ArrayList<StructProfesional>();
		
		
		String filtroUsuario = (filtros.getUsuario().equals("")) ? "" : " AND "+filtros.getUsuario();
		
		String sql ="SELECT	TRIM(NOMMED)||' '||NNOMED AS NOMMED,REGMED AS REGMED,CODRGM AS ESPMED,ESTRGM AS ESTMED,NIDRGM AS NIDMED,USUARI AS USUMED,CHAR(TPMRGM) AS TPMMED, ESTRGM"
				+ " FROM ALPHILDAT.RIARGMN3 WHERE TPMRGM IN( "+filtros.getTipos()+" ) AND ESTRGM in ( "+filtros.getEstado()+" )"+ filtroUsuario;
		try (
				DBAS400 db = new DBAS400();
				Connection con = db.getCon();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql);
			) {
			while (rs.next()) {
				
				StructProfesional profesional = new StructProfesional();
				
				profesional.setNombre(  rs.getString("NOMMED").trim() );
				profesional.setRegistro( rs.getString("REGMED").trim()  );
				profesional.setEstado( rs.getString("ESTRGM") );
				profesional.setUser( rs.getString("USUMED").trim()  );
				profesional.setEspecialidad( rs.getString("ESPMED").trim() );
				
				listProfesional.add(profesional);
			}
			
			rs.close();
		} catch (Exception e) {
			System.out.println(e);
			respuestaProfesionales.setErrorCode(500);
			respuestaProfesionales.setErrorMessage(e.toString());
			
			e.printStackTrace();
		}
		
		respuestaProfesionales.setErrorCode(0);
		respuestaProfesionales.setListaComun(listProfesional);
		
		return respuestaProfesionales;
	}

	@Override
	public StructResponseCommon extaerTipoAnestesia() {
		
		
		StructResponseCommon respuestaTipoAnestesia = new StructResponseCommon();
		List<StructTipoAnestesia> listTipoanestesia =new ArrayList<StructTipoAnestesia>();
		
		String sql="SELECT UPPER(DE1TMA) AS DESANE, SUBSTR(TRIM(CL1TMA),1,2) AS CODANE FROM ALPHILDAT.TABMAEL01 WHERE TIPTMA='CODTAN' AND ESTTMA<>'1' ";
		
		try (
				DBAS400 db = new DBAS400();
				Connection con = db.getCon();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql);
			) {
			while (rs.next()) {
				
				StructTipoAnestesia tipo = new StructTipoAnestesia();
				
				tipo.setAnestesia( rs.getString("DESANE").trim() );
				tipo.setCodigo( rs.getString("CODANE").trim()  );
				
				listTipoanestesia.add(tipo);
			}
			
			rs.close();
		} catch (Exception e) {
			System.out.println(e);
			respuestaTipoAnestesia.setErrorCode(500);
			respuestaTipoAnestesia.setErrorMessage(e.toString());
			
			e.printStackTrace();
		}
		
		respuestaTipoAnestesia.setErrorCode(0);
		respuestaTipoAnestesia.setListaComun(listTipoanestesia);
		
		return respuestaTipoAnestesia;
	}

	@Override
	public StructResponseCommon extaerDiagnostico(StructFiltroDiagnostico body) {
		
		StructResponseCommon respuestaDiagnosticos = new StructResponseCommon();
		List<StructDiagnostico> listDiagnostico =new ArrayList<StructDiagnostico>();
		
		
		
		if(!body.getTipoFiltro().equals("LIKE") && !body.getTipoFiltro().equals("IGUAL")) {
			respuestaDiagnosticos.setErrorCode(500);
			respuestaDiagnosticos.setErrorMessage("El campo tipo filtro debe ser LIKE o IGUAL");
			return respuestaDiagnosticos;
		}
		
		String sql="CALL DIAGNOSTICOSRIPS('"+body.getTipmae()+"', '"+body.getCl1tma()+"')";
		String whereRips ="";
		
		try (
				DBAS400 db = new DBAS400();
				Connection con = db.getCon();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql);
			) {
			while (rs.next()) {
				whereRips = rs.getString("DE2TMA").trim();
			}
			
			String rips = (whereRips.equals("")) ? "" : " "+whereRips;
			rips = rips.replace("'", "¤");
			rips = rips.replace("¤", "''");
			
			
			String where = "(ENFRIP "+filtro(body.getTipoFiltro(), body.getCodigo())+" "
					+ "OR DESRIP "+filtro(body.getTipoFiltro(), body.getDescripcion())+") "
					+ " AND  SEXRIP IN ("+ body.getSexRips()+", 0 )" +rips;

			
			String json = "{\"SELECT\": \"ENFRIP,DESRIP\",  \"WHERE\": \""+where+"\",\"ORDER\": \"ENFRIP ASC\" }";
			
			String sql2 = "CALL DIAGNOSTICOS('"+json+"')";

			Statement st2 = con.createStatement();
			ResultSet rs2 = st2.executeQuery(sql2);
			
			while (rs2.next()) {
				
				StructDiagnostico diagnostico = new StructDiagnostico();
				
				diagnostico.setCodigo(rs2.getString("ENFRIP").trim());
				diagnostico.setDescripcion(rs2.getString("DESRIP").trim());
				

				listDiagnostico.add(diagnostico);
			}
			
			rs.close();
			rs2.close();
			
		} catch (Exception e) {
			System.out.println(e);
			respuestaDiagnosticos.setErrorCode(500);
			respuestaDiagnosticos.setErrorMessage(e.toString());
			e.printStackTrace();
		}
		
		respuestaDiagnosticos.setErrorCode(0);
		respuestaDiagnosticos.setListaComun(listDiagnostico);
		
		return respuestaDiagnosticos;
	}
	
	private String filtro(String tipo, String dato) {
		
		if(tipo.equals("LIKE")) {
			return " like ''%"+dato+"%''";
		}
		
		return " = ''"+dato+"''";
	}

	@Override
	public StructResponseCommon extraerEspecialidadesMedicos(StructFiltroEspecialidadMedicos body) {
		
		
		StructResponseCommon respuestaEspecialidadesMedicos= new StructResponseCommon();
		List<StructEspecialidadesMedicos> listEspecialidadesMedicos =new ArrayList<StructEspecialidadesMedicos>();
		
		String sql = "CALL ESPECIALIDADESMEDICOS('"+body.getOpcion1()+"', '"+body.getOpcion2()+"')";
		String sql2 = "";
		
		try (
				DBAS400 db = new DBAS400();
				Connection con = db.getCon();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql);
			) {
			
			while (rs.next()) {	
				sql2 = sql2 + " " +rs.getString("DE2TMA").trim();
			}
			
			
			Statement st2 = con.createStatement();
			ResultSet rs2 = st2.executeQuery(sql2);
			
			
			while (rs2.next()) {	
				StructEspecialidadesMedicos especialidades = new StructEspecialidadesMedicos();
				
				especialidades.setCodigo(  rs2.getString("CODIGO_ESPEC").trim() );
				especialidades.setDescripcion( rs2.getString("DESCRIP_ESPEC").trim() );
				
				
				listEspecialidadesMedicos.add(especialidades);
			}
			
			
			rs2.close();
			rs.close();
		} catch (Exception e) {
			System.out.println(e);
			respuestaEspecialidadesMedicos.setErrorCode(500);
			respuestaEspecialidadesMedicos.setErrorMessage(e.toString());
			e.printStackTrace();
		}
		
		respuestaEspecialidadesMedicos.setErrorCode(0);
		respuestaEspecialidadesMedicos.setListaComun(listEspecialidadesMedicos);
		
		
		return respuestaEspecialidadesMedicos;
	}

	@Override
	public StructResponseCommon extaerMedicosXespecialidades(StructFiltroMedicoXespecialidad body) {

		StructResponseCommon respuestaMedicosEspecialidad= new StructResponseCommon();
		List<StructProfesional> listProfesional =new ArrayList<StructProfesional>();
		
		String sql = "CALL ESPECIALIDADESMEDICOS('"+body.getOpcion1()+"', '"+body.getOpcion2()+"')";
		String preConsulta = "";
		
		try (
				DBAS400 db = new DBAS400();
				Connection con = db.getCon();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql);
			) {
			
			while (rs.next()) {	
				preConsulta = preConsulta+ " " + rs.getString("DE2TMA").trim() ;
			}
			
			String separador = Pattern.quote("ORDER BY");
			String[] consultaPartida = preConsulta.split(separador);
			
		
			
			if( preConsulta.equals(" ") || preConsulta.equals("")) {
				respuestaMedicosEspecialidad.setErrorCode(0);
				return respuestaMedicosEspecialidad;
			}
			
			String sql2 = consultaPartida[0] +" AND B.ESPTUS = " + body.getEspecialidad() +" ORDER BY "+ consultaPartida[1];
			
			
			System.out.println("dato: "+sql2);
			
			Statement st2 = con.createStatement();
			ResultSet rs2 = st2.executeQuery(sql2);
			
			while (rs2.next()) {	
				
				StructProfesional profesional = new StructProfesional();
				
				
				profesional.setNombre(  rs2.getString("NOMBRE_MEDICO").trim() );
				profesional.setRegistro( rs2.getString("REGISTRO").trim()  );
				profesional.setEstado( "NA" );
				profesional.setUser( rs2.getString("USUARIO").trim()  );
				profesional.setEspecialidad( rs2.getString("COD_ESPEC").trim() );
				
				listProfesional.add(profesional);
				
			}
			
			rs2.close();
			rs.close();
		} catch (Exception e) {
			System.out.println(e);
			respuestaMedicosEspecialidad.setErrorCode(500);
			respuestaMedicosEspecialidad.setErrorMessage(e.toString());
			e.printStackTrace();
		}
		
		
		respuestaMedicosEspecialidad.setErrorCode(0);
		respuestaMedicosEspecialidad.setListaComun(listProfesional);
		
		return respuestaMedicosEspecialidad;
	}

	@Override
	public StructResponseCommon extraerCupsPatologias() {
		
		StructResponseCommon respuestaCupsPatologia= new StructResponseCommon();
		List<StructListaCupsPatologia> listaComun = new ArrayList<StructListaCupsPatologia>();	
		
		String sql = "CALL CUPSPATOLOGIAMAE()";
		
		try (
				DBAS400 db = new DBAS400();
				Connection con = db.getCon();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql);
			) {
			while (rs.next()) {
				List<struct_cups> listaCupspatologia = new ArrayList<struct_cups>();
				StructListaCupsPatologia patologia = new StructListaCupsPatologia();
				
				String inWhere ="";
				inWhere = rs.getString("DE2TMA").trim().replace(",", "'',''");
				inWhere = "''"+ inWhere +"''";
				
				String sql2 = "CALL CUPSPATOLOGIA('"+inWhere+"')";
				
				
				Statement st2 = con.createStatement();
				ResultSet rs2 = st2.executeQuery(sql2);
				
				while (rs2.next()) {
					struct_cups cups = new struct_cups();
					
					cups.setCodcup( rs2.getString("CODCUP") );
					cups.setDescup( rs2.getString("DESCUP") );
					
					listaCupspatologia.add(cups);
				}
				
				patologia.setListaComun(listaCupspatologia);
				patologia.setTipoPatologia( rs.getString("DE1TMA").trim() );
				
				listaComun.add(patologia);
								
				rs2.close();
				st2.close();
			}
			
			
			
			rs.close();
			st.close();
			
			
		} catch (Exception e) {
			respuestaCupsPatologia.setErrorCode(500);
			respuestaCupsPatologia.setErrorMessage(e.toString());
			System.out.println(e);
			e.printStackTrace();
		}
		
		respuestaCupsPatologia.setErrorCode(0);
		respuestaCupsPatologia.setListaComun(listaComun);
		
		return respuestaCupsPatologia;
	}

	@Override
	public StructResponseCommon extraerlistaMedicamentos(StructFiltroMedicamentos body) {
		
		
		StructResponseCommon respuestaMedicamentos= new StructResponseCommon();
		List<StructMedicamentos> listMedicamentos =new ArrayList<StructMedicamentos>();
		
		
		String sql = "CALL MEDICAMENTOS('"+filtro(body.getTipoFiltro(), body.getDescripcion())+"', ' "+filtro(body.getTipoFiltro(), body.getCodigo())+" ')";

		
		try (
				DBAS400 db = new DBAS400();
				Connection con = db.getCon();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql);
			) {
			while (rs.next()) {
				
				StructMedicamentos medicamento = new StructMedicamentos();
				
				medicamento.setCodigo( rs.getString("CodMed").trim() );
				medicamento.setDescripcion( rs.getString("DesMed").trim()  );
				medicamento.setIsPos( rs.getString("PosMed").trim()  );
				
				
				listMedicamentos.add(medicamento);
			}
			rs.close();
		} catch (Exception e) {
			System.out.println(e);
			respuestaMedicamentos.setErrorCode(500);
			respuestaMedicamentos.setErrorMessage(e.toString());
			e.printStackTrace();
		}
		
		
		respuestaMedicamentos.setErrorCode(0);
		respuestaMedicamentos.setListaComun(listMedicamentos);
		
		return respuestaMedicamentos;
	}

}
