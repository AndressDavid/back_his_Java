package com.shaio.his.doctores;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.shaio.his.AS400.DBAS400;



@Service
public class XDoctores implements IDoctores{

	
	@Override
	public ResponseDoctores ListarDoctores() {
	 List<struct_doctores> ldoctores = new ArrayList();
	 ResponseDoctores response = new ResponseDoctores();
		 
		 String sql = "SELECT TRIM(NOMMED)||' '||NNOMED AS NOMMED, REGMED AS REGMED, CODRGM AS ESPMED, ESTRGM AS ESTMED, NIDRGM AS NIDMED, USUARI AS USUMED, CHAR(TPMRGM) AS TPMMED FROM ALPHILDAT.RIARGMN WHERE TPMRGM IN (1,3,4,6,10,11,12,13,91) AND ESTRGM=1";
//		 System.out.println("Aqui entramos");
		 
		 try (	DBAS400 db = new DBAS400();			
					Connection con = db.getCon();			
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery(sql);
				) {	
				while (rs.next()) {
					struct_doctores pac = new struct_doctores();
					pac.setUsuario(rs.getString("USUMED"));
					pac.setNombre(rs.getString("NOMMED"));					
					ldoctores.add(pac);					
				}
				rs.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				response.setErrorCode(500);
				response.setErrorMessage(e.getMessage());
				e.printStackTrace();
			}		
			response.setData(ldoctores);
			return response;
	}
}
	
	

