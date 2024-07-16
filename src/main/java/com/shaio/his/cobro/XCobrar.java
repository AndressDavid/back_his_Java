package com.shaio.his.cobro;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
import org.springframework.stereotype.Service;
import com.shaio.his.AS400.DBAS400;


@Service
public class XCobrar implements ICobro{

	@Override
	public ResponseCobrarPerioperatorio CobrarPerioperatorio(StructCobrarPerioperatorio cJsonDatos) {
	 ResponseCobrarPerioperatorio response = new ResponseCobrarPerioperatorio();

	 Integer nroingreso = cJsonDatos.getIngreso();
	 String numeroingreso = cJsonDatos.getIngresoString();
	 String viaingreso = cJsonDatos.getViaingreso();
	 String planingreso = cJsonDatos.getPlaningreso();
	 String medicorealiza = cJsonDatos.getRegistromedicorealiza();
	 String identificacionpaciente = cJsonDatos.getIdentificacionString();
	 String procedimiento = cJsonDatos.getProcedimiento();
	 String habitacion = cJsonDatos.getHabitacionpaciente();
	 String especialidadrealiza = cJsonDatos.getEspecialidadmedico();
	 String consecutivocita = cJsonDatos.getConsecutivocita();
	 String valorregistrar = "000000000000000"; 
	 String centrodecosto = cJsonDatos.getCentrocosto();
	 String portatil = "";
	 String usuariodecreacion = cJsonDatos.getUsuariocreacion();
	 
	 System.out.println("COBRO");
	 
	 String sql = "SELECT NIGING FROM ALPHILDAT.RIAING r WHERE NIGING= '" + nroingreso + "' ";
	 CallableStatement callableStatement = null;
	 
	 Connection connection = null;
	 
		try (	DBAS400 db = new DBAS400();			
				Connection connection1 = db.getCon();			
				Statement st = connection1.createStatement();
				ResultSet cstmt = st.executeQuery(sql);
			) {	
			if (cstmt.next()) {
				
				System.out.println("INICIA COBRO");

				String sqlprocedure = "{call ALPHILDAT.FACS22PCPP(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
				callableStatement = connection1.prepareCall(sqlprocedure);

	            callableStatement.setString(1, numeroingreso);
	            callableStatement.setString(2, viaingreso);
	            callableStatement.setString(3, planingreso);
	            callableStatement.setString(4, medicorealiza);
	            callableStatement.setString(5, medicorealiza);
	            callableStatement.setString(6, identificacionpaciente);
	            callableStatement.setString(7, procedimiento);
	            callableStatement.setString(8, habitacion);
	            callableStatement.setString(9, especialidadrealiza);
	            callableStatement.setString(10, consecutivocita);
	            callableStatement.setString(11, valorregistrar);
	            callableStatement.setString(12, centrodecosto);
	            callableStatement.setString(13, portatil);
	            callableStatement.setString(14, usuariodecreacion);
	            callableStatement.execute();

	            System.out.println("FINALIZA COBRO");
				
				cstmt.close();
			}else {
				System.out.println("NO EXISTE INGRESO");
			}
		} catch (Exception e) {
			response.setErrorCode(500);
			response.setErrorMessage(e.getMessage());
			e.printStackTrace();
		}finally {
            try {
                if (callableStatement != null) {
                    callableStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
		return response;
	}
	
	
}