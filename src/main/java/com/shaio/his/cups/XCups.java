package com.shaio.his.cups;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.shaio.his.AS400.DBAS400;



@Service
public class XCups implements ICups{

	
	@Override
	public ResponseCups ListarCups(String filter) {
	 List<struct_cups> lcups = new ArrayList();
	 ResponseCups response = new ResponseCups();
		 
		 String sql = "SELECT * FROM ALPHILDAT.RIACUP r WHERE IDDCUP=0 ";
		 if (!filter.equals("")) {
			 sql += " AND DESCUP LIKE '%"+filter+"%' or codcup='"+filter+"'";
		 }
		// sql += " FETCH FIRST 50 ROWS ONLY ";
		 
			try (	DBAS400 db = new DBAS400();			
					Connection con = db.getCon();			
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery(sql);
				) {	
				while (rs.next()) {
					struct_cups pac = new struct_cups();
					pac.setCodcup(rs.getString("CODCUP").trim());
					pac.setDescup(rs.getString("DESCUP").trim());					
					lcups.add(pac);					
				}
				rs.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				response.setErrorCode(500);
				response.setErrorMessage(e.getMessage());
				e.printStackTrace();
			}		
			response.setData(lcups);
			return response;
	}

	@Override
	public ResponseCups ListaCupsQuirurgicos(StructFiltrosCupsQuirurgicos filtros) {
		
		ResponseCups response = new ResponseCups();
		List<struct_cups> lcups = new ArrayList();
		
		String sql = "CALL PROCEDIMIENTOSQUIRURGICOSFILTROS('MODCIRG', '"+filtros.getPrograma()+"','')";
		String sqlRecuperado = "";
		String sql2 = "";
		
		
		try (	DBAS400 db = new DBAS400();			
				Connection con = db.getCon();			
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql);
			) 
		
			{	
			while (rs.next()) {
				sqlRecuperado = sqlRecuperado + " " + rs.getString("QUERY");
			}
			
			if(sqlRecuperado == null || sqlRecuperado.equals("")) {
				response.setErrorCode(500);
				response.setErrorMessage("No se encontro el parametro filtro");
				rs.close();
				return response;
			}
			
			
			rs.close();
			String filtrosWhere = " AND (codcup "+filtroCups(filtros.isExacto(), filtros.getCodigo())+" OR descup "+filtroCups(filtros.isExacto(), filtros.getDescripcion())+" )";
			
			sqlRecuperado = sqlRecuperado.replace("'", "⍟");
			sqlRecuperado = sqlRecuperado.replace("⍟", "''");
			
			sql2 = "CALL PROCEDIMIENTOSQUIRURGICOS('"+sqlRecuperado+"', '"+filtros.getSexo()+"', '"+filtrosWhere+"') ";
			Statement st2 = con.createStatement();
			ResultSet rs2 = st2.executeQuery(sql2);

			
			while (rs2.next()) {
				struct_cups cups = new struct_cups();
				
				cups.setCodcup( rs2.getString("suborg") );
				cups.setDescup( rs2.getString("descod") );
				
				lcups.add(cups);
				
			}
			
			
			rs2.close();
		} catch (Exception e) {
			System.out.println( e );
			response.setErrorCode(500);
			response.setErrorMessage(e.toString());
			e.printStackTrace();
			return response;
		}
		
		
		
		
		response.setErrorCode(0);
		response.setData(lcups);
		
		return response;
	}
	
	
	private String filtroCups(boolean tipo, String dato) {
		
		if(!tipo) {
			return " LIKE ''%"+dato+"%'' ";
		}
		
		return " = ''"+dato+"'' ";
	}



}
