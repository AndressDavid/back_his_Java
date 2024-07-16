package com.shaio.his.consecutivos;

import java.io.Console;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;

import com.shaio.his.AS400.DBAS400;
import com.shaio.his.common.StructTipoAnestesia;

public class XConsecutivos {
	
	
	public int obtenerConsecEvolucion(StructFiltroConsecutivoEvolucion filtro) {
		
		
		System.out.println("entro 2");
		
		int consecutivoEvo =0;
		int consecutivo =0;
		
		do {
				
			try (
					DBAS400 db = new DBAS400();
					Connection con = db.getCon();
					CallableStatement cstmt = con.prepareCall("CALL FACGA010CP(?,?,?,?,?,?,?)");
					
				) {
				
				
				cstmt.setInt(1, filtro.getIngreso());
				cstmt.setString(2, filtro.getSeccion());
				cstmt.setString(3, filtro.getCama());
				cstmt.setString(4, filtro.getUsuario());
				cstmt.setString(5, filtro.getPrograma());
				cstmt.setInt(6, filtro.getEstado());
				cstmt.registerOutParameter (7, Types.VARCHAR);
	
				cstmt.executeUpdate();
				
				consecutivoEvo = cstmt.getInt(7);
				consecutivo ++;
				
				cstmt.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		}while(!(consecutivoEvo > 0) && consecutivo  < consecutivoMaximoEvolucion());
		
		System.out.println("salio 2");
		
		return consecutivoEvo;
	}
	
	private int consecutivoMaximoEvolucion() {
		int max = 10;
		
		String sql = "SELECT OP3TMA  FROM TABMAE WHERE CL1TMA='CNSINT'";
		
		try (
				DBAS400 db = new DBAS400();
				Connection con = db.getCon();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql);
			) {
			while (rs.next()) {
				
				max = rs.getInt("OP3TMA");
			}
			
			rs.close();
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		
		return max;
	}
	
}
