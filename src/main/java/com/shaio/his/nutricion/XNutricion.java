package com.shaio.his.nutricion;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import com.shaio.his.AS400.DBAS400;

@Service
public class XNutricion implements Inutricion {
	
	@Override
	public ResponseNutricion ListarPacientesNutricion(String numeroingreso, long Fecha, String todas, String seccion){
		List<struct_Nutricion> lPacientesNutricion = new ArrayList<struct_Nutricion>();
		ResponseNutricion response = new ResponseNutricion();
		String filtro = "";
				
		if (!numeroingreso.isEmpty()) {
			filtro = filtro + ((filtro!="")?" AND ":"") + "A.NINNTR = " + numeroingreso ; 
		}
		
		if (todas=="NO") {
			filtro = filtro + ((filtro != "")?" AND ":"") + "A.FECNTR = " + Fecha ; 
		}
		
		if (!seccion.isEmpty()) {
			filtro = filtro + ((filtro != "")?" AND ":"") + "D.SECHAB = '" + seccion + "'" ; 
		}
				
		String sql = "SELECT C.TIDPAC, C.NIDPAC, C.NM1PAC, C.NM2PAC, C.AP1PAC, C.AP2PAC, C.FNAPAC, C.SEXPAC, A.NINNTR, A.FECNTR, " 
					+ "A.HORNTR, A.RMENTR, A.SCANTR, A.NCANTR, A.CONNTR, A.ESTNTR, H.DSLTAB, F.NNOMED, F.NOMMED, B.VIAING, E.DESVIA, "
					+ "B.FEIING, B.ENTING, G.CODESP, G.DESESP, D.SECHAB, D.NUMHAB, D.ESTHAB FROM ALPHILDAT.RIANUTR AS A "
					+ "LEFT JOIN ALPHILDAT.RIAING AS B ON A.NINNTR = B.NIGING "
					+ "LEFT JOIN ALPHILDAT.RIAPAC AS C ON B.TIDING = C.TIDPAC AND B.NIDING = C.NIDPAC "
					+ "LEFT JOIN ALPHILDAT.FACHAB AS D ON A.NINNTR = D.INGHAB AND D.ESTHAB = '1' OR D.ESTHAB = '2' "
					+ "LEFT JOIN ALPHILDAT.RIAVIA AS E ON B.VIAING = E.CODVIA "
					+ "LEFT JOIN ALPHILDAT.RIARGMN AS F ON TRIM(A.RMENTR) = TRIM(F.REGMED) "
					+ "LEFT JOIN ALPHILDAT.RIAESPE AS G ON F.TPMRGM = INT(G.CODESP) "
					+ "LEFT JOIN ALPHILDAT.RIATAB AS H ON A.ESTNTR = H.CDSTAB AND H.CDTTAB = 5 AND H.CDRTAB = 1 "
					+ "WHERE " + filtro + " ORDER BY C.TIDPAC, C.NIDPAC, A.NINNTR, A.CONNTR";
		
			try (	DBAS400 db = new DBAS400();			
					Connection con = db.getCon();			
					Statement st = con.createStatement();
					ResultSet resul = st.executeQuery(sql);
				) {	
				while (resul.next()) {
					struct_Nutricion lista = new struct_Nutricion();
					lista.setTippac(resul.getString("TIDPAC"));
					lista.setNidpac(resul.getString("NIDPAC"));
					lista.setNombrep(resul.getString("NM1PAC"));
					lista.setNombres(resul.getString("NM2PAC"));
					lista.setApellidop(resul.getString("AP1PAC"));
					lista.setApellidos(resul.getString("AP2PAC"));
					lista.setFechanacimiento(resul.getInt("FNAPAC"));
					lista.setSexo(resul.getString("SEXPAC"));
					lista.setIngreso(resul.getInt("NINNTR"));
					lista.setFechanutricion(resul.getInt("FECNTR"));
					lista.setHoranutricion(resul.getInt("HORNTR"));
					lista.setRegnutricion(resul.getString("RMENTR"));
					lista.setSecnutricion(resul.getString("SCANTR"));
					lista.setCamanutricion(resul.getString("NCANTR"));
					lista.setConnutricion(resul.getInt("CONNTR"));
					lista.setEstnutricion(resul.getInt("ESTNTR"));					
					lista.setEstdescripcion(resul.getString("DSLTAB"));					
					lista.setNombresusu(resul.getString("NNOMED"));
					lista.setApellidousu(resul.getString("NOMMED"));
					lista.setCodigovia(resul.getString("VIAING"));
					lista.setDescripcionvia(resul.getString("DESVIA"));
					lista.setFechaingreso(resul.getInt("FEIING"));
					lista.setEntidadingreso(resul.getInt("ENTING"));
					lista.setCodigoespecialidad(resul.getString("CODESP"));
					lista.setDescripespecialidad(resul.getString("DESESP"));
					lista.setSechabita(resul.getString("SECHAB"));
					lista.setCamahabita(resul.getString("NUMHAB"));
					lista.setEsthabita(resul.getString("ESTHAB"));					
					lPacientesNutricion.add(lista);					
				}
			} catch (Exception e) {
				response.setErrorCode(500);
				response.setErrorMessage(e.getMessage());
				e.printStackTrace();
			}	
			response.setData(lPacientesNutricion);
			return response;
		
	}

}
