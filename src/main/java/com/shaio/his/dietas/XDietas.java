package com.shaio.his.dietas;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.shaio.his.AS400.DBAS400;



@Service
public class XDietas implements IDietas{

	
	@Override
	public ResponseDietas ListarDietas() {
	 List<struct_dietas> ldietas = new ArrayList();
	 ResponseDietas response = new ResponseDietas();
		 
		 String sql = "SELECT TRIM(DE1TMA)AS CODIGO, TRIM(DE2TMA) AS DESCRIPCION FROM ALPHILDAT.TABMAE WHERE TIPTMA='DIESHA' AND ESTTMA='' ORDER BY DE2TMA";
		 System.out.println("Aqui entramos");
		 
		 try (	DBAS400 db = new DBAS400();			
					Connection con = db.getCon();			
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery(sql);
				) {	
				while (rs.next()) {
					struct_dietas pac = new struct_dietas();
					pac.setCodigo(rs.getString("codigo"));
					pac.setDescripcion(rs.getString("descripcion"));					
					ldietas.add(pac);
				}
				rs.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				response.setErrorCode(500);
				response.setErrorMessage(e.getMessage());
				e.printStackTrace();
			}		
			response.setData(ldietas);
			return response;
	}
}
	
	

