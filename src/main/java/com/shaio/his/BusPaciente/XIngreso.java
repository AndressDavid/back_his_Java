package com.shaio.his.BusPaciente;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.shaio.his.AS400.DBAS400;



@Service
public class XIngreso implements IIngreso {
	
	@Override
	public ResponseIngreso buscarIngreso(long idingreso) {
		List<struct_ingreso> lingreso = new ArrayList<struct_ingreso>();
		ResponseIngreso response = new ResponseIngreso();
	 
		String sql = "SELECT TIDING, NIDING, NIGING, ESTING, TRIM(PLAING) PLAN FROM ALPHILDAT.RIAING r WHERE NIGING= '" + idingreso + "' ";

		 struct_ingreso ringreso = new struct_ingreso();
		 
			try (	DBAS400 db = new DBAS400();			
					Connection con = db.getCon();			
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery(sql);
				) {	
				while (rs.next()) {
					ringreso.setNumeroIngreso(rs.getLong("NIGING"));
					ringreso.setTipoIdentificacion(rs.getString("TIDING"));
					ringreso.setNumeroIdentificacion(rs.getLong("NIDING"));
					ringreso.setEstadoIngreso(rs.getString("ESTING"));
					ringreso.setPlanIngreso(rs.getString("PLAN"));		
					lingreso.add(ringreso);					
				}
				rs.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				response.setErrorCode(500);
				response.setErrorMessage(e.getMessage());
				e.printStackTrace();
			}		
			response.setData(ringreso);
			return response;
	}
	
	
}
