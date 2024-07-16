package com.shaio.his.listadoshc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.shaio.his.AS400.DBAS400;


@Service
public class XListadosHC implements IListadoshc{
	
	@Override
	public ResponseTipoDiagnostico ListarTipoDiagnostico() {
	 List<struct_tipodiagnostico> ltipocie = new ArrayList<struct_tipodiagnostico>();
	 ResponseTipoDiagnostico response = new ResponseTipoDiagnostico();
		 
		 String sql = "SELECT SUBSTR(TABCOD, 2, 1) CODIGO, TRIM(TABDSC) DESCRIPCION FROM ALPHILDAT.PRMTAB WHERE TABTIP='TDX' AND TABCOD LIKE 'B%' ORDER BY TABDSC";
		  
			try (	DBAS400 db = new DBAS400();			
					Connection con = db.getCon();			
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery(sql);
				) {	
				while (rs.next()) {
					struct_tipodiagnostico tipocie = new struct_tipodiagnostico();
					tipocie.setCodTipoCie(rs.getString("CODIGO"));
					tipocie.setDesTipoCie(rs.getString("DESCRIPCION"));					
					ltipocie.add(tipocie);					
				}
				rs.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				response.setErrorCode(500);
				response.setErrorMessage(e.getMessage());
				e.printStackTrace();
			}		
			response.setData(ltipocie);
			return response;
	}

	//public ResponseFinalidades ListarFinalidades(String tipofin) {

	@Override
	public ResponseFinalidades ListarFinalidades(String tipofin, String genero) {
	 List<struct_finalidades> lfinalidad = new ArrayList<struct_finalidades>();
	 ResponseFinalidades response = new ResponseFinalidades();


		 String sql = "SELECT TRIM(CL1TMA) CODIGO, TRIM(DE2TMA) DESCRIPCION, TRIM(OP2TMA) TIPO, TRIM(OP6TMA) GENERO, OP3TMA EDADDESDE, "
		 		+ "OP7TMA EDADHASTA FROM ALPHILDAT.TABMAE WHERE TIPTMA='CODFIN' AND OP2TMA LIKE '%" + tipofin +"%' AND OP6TMA LIKE '%" + genero +"%' AND ESTTMA='' ORDER BY DE2TMA";
		  
			try (	DBAS400 db = new DBAS400();			
					Connection con = db.getCon();			
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery(sql);
				) {	
				while (rs.next()) {
					struct_finalidades tipofinalidad = new struct_finalidades();
					tipofinalidad.setCodFinalidad(rs.getString("CODIGO"));
					tipofinalidad.setDesFinalidad(rs.getString("DESCRIPCION"));					
					tipofinalidad.setTipoFinalidad(rs.getString("TIPO"));					
					tipofinalidad.setGenero(rs.getString("GENERO"));
					tipofinalidad.setEdadDesde(rs.getLong("EDADDESDE"));
					tipofinalidad.setEdadHasta(rs.getLong("EDADHASTA"));
					lfinalidad.add(tipofinalidad);					
				}
				rs.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				response.setErrorCode(500);
				response.setErrorMessage(e.getMessage());
				e.printStackTrace();
			}		
			response.setDataFinalidad(lfinalidad);
			return response;
	}
	
	@Override
	public ResponseProfesionalesSalud ListarProfesionalesSalud() {
	 List<struct_profesionales> lmedico = new ArrayList<struct_profesionales>();
	 ResponseProfesionalesSalud response = new ResponseProfesionalesSalud();
		 
		 String sql = "SELECT TRIM(REGMED) REGISTRO, TRIM(USUARI) USUARIO, TRIM(CODRGM) ESPECIALIDAD, "
				 + "IFNULL((SELECT TRIM(DESESP) FROM ALPHILDAT.RIAESPE WHERE CODESP=CODRGM), '') DESCRIPCION_ESPECIALIDAD, "
		 		+ "TPMRGM TIPO, TRIM(TRIM(NOMMED) || ' ' || TRIM(NNOMED)) NOMBRE FROM ALPHILDAT.RIARGMN WHERE (TPMRGM IN(1, 3, 4, 6, 10, 11, 12, 13, 91)) AND ESTRGM=1 ORDER BY NOMMED";
		  
			try (	DBAS400 db = new DBAS400();			
					Connection con = db.getCon();			
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery(sql);
				) {	
				while (rs.next()) {
					struct_profesionales medico = new struct_profesionales();
					medico.setCodRegistro(rs.getString("REGISTRO"));
					medico.setDesRegistro(rs.getString("NOMBRE"));	
					medico.setUsuario(rs.getString("USUARIO"));	
					medico.setEspecialidad(rs.getString("ESPECIALIDAD"));	
					medico.setDescripcionEspecialidad(rs.getString("DESCRIPCION_ESPECIALIDAD"));	
					medico.setTipoProfesional(rs.getLong("TIPO"));
					lmedico.add(medico);					
				}
				rs.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				response.setErrorCode(500);
				response.setErrorMessage(e.getMessage());
				e.printStackTrace();
			}		
			response.setDataMedico(lmedico);
			return response;
	}
	
	@Override
	public ResponseDiagnosticos ListadoDiagnosticos() {
	 List<struct_diagnostico> ldiagnostico = new ArrayList<struct_diagnostico>();
	 ResponseDiagnosticos response = new ResponseDiagnosticos();
		 
		 String sql = "SELECT TRIM(ENFRIP) CODIGO, TRIM(GE2RIP) GENERO, TRIM(EMNRIP) EDADMINIMA, "
		 		+ "TRIM(EMXRIP) EDADMAXIMA, "
		 		+ "TRIM(SUBSTR(TRIM(DE2RIP), 1, 220)) DESCRIPCION FROM ALPHILDAT.RIACIE WHERE ESTRIP='A' ORDER BY DE2RIP";
		  
			try (	DBAS400 db = new DBAS400();			
					Connection con = db.getCon();			
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery(sql);
				) {	
				while (rs.next()) {
					struct_diagnostico diagnostico = new struct_diagnostico();
					diagnostico.setCodCie(rs.getString("CODIGO"));
					diagnostico.setDesCie(rs.getString("DESCRIPCION"));					
					diagnostico.setGeneroCie(rs.getString("GENERO"));	
					diagnostico.setEdadMinimaCie(rs.getString("EDADMINIMA"));
					diagnostico.setEdadMaximaCie(rs.getString("EDADMAXIMA"));
					ldiagnostico.add(diagnostico);					
				}
				rs.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				response.setErrorCode(500);
				response.setErrorMessage(e.getMessage());
				e.printStackTrace();
			}		
			response.setDataDiagnostico(ldiagnostico);
			return response;
	}
	
	
}



