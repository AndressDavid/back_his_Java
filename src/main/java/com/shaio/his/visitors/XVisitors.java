package com.shaio.his.visitors;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import com.shaio.his.AS400.DBAS400;

@Service
public class XVisitors implements IVisitors {

	@Override
	public ResponseVisitors searchVisitorsByPatient(String typeDocumentPatient, String documentPatient) {
		ResponseVisitors result = new ResponseVisitors();
		List<StructVisitors> lVisitors = new ArrayList<StructVisitors>();

		String sql = "SELECT * FROM  ALPHILDAT.REGISTROVISITAS JOIN ALPHILDAT.RIATI ON TIPDOC = TIPODOCUMENTOVISITANTE  WHERE TIPODOCUMENTOPACIENTE = ? AND NUMERODOCUMENTOPACIENTE = ? AND ESTADOVISITANTE = '1'";
		try (DBAS400 db = new DBAS400();
				Connection con = db.getCon();
				PreparedStatement ps = con.prepareStatement(sql);) {

			ps.setString(1, typeDocumentPatient);
			ps.setString(2, documentPatient);
			try (ResultSet rs = ps.executeQuery();) {

				while (rs.next()) {
					StructVisitors visitor = new StructVisitors();
					StructTypeDocument typeDocument = new StructTypeDocument();

					typeDocument.setCode(rs.getString("TIPDOC").trim());
					typeDocument.setCodeTypeDoc(rs.getString("DOCUME").trim());
					typeDocument.setDescription(rs.getString("DESDOC").trim());
					visitor.setTypeDocumentVisitor(typeDocument);
					visitor.setDocumentVisitor(rs.getString("NUMERODOCUMENTOVISITANTE").trim());
					visitor.setNameVisitor(rs.getString("NOMBREVISITANTE").trim());
					visitor.setLastNameVisitor(rs.getString("APELLIDOSVISITANTE").trim());
					visitor.setVia(rs.getString("CODIGOVIA").trim());
					visitor.setTypeVisitor(rs.getString("TIPOVISITANTE"));
					visitor.setDateAdmissionVisitor(rs.getString("FECHAINGRESOVISITANTE"));
					visitor.setEgressDateVisitor(rs.getString("FECHAEGRESOVISITANTE"));
					visitor.setState(rs.getString("ESTADOVISITANTE").trim());
					visitor.setTypeDocumentPatient(rs.getString("TIPODOCUMENTOPACIENTE").trim());
					visitor.setDocumentPatient(rs.getString("NUMERODOCUMENTOPACIENTE").trim());
					visitor.setUser(rs.getString("USUARIO").trim());
					visitor.setProgram(rs.getString("PROGRMA").trim());
					visitor.setDateCreate(rs.getString("FECHACREACION"));
					visitor.setUserModify(rs.getString("USUARIOMODIFICA"));
					visitor.setModifyProgram(rs.getString("PROGRAMAMODIFICA"));
					visitor.setDateModify(rs.getString("FECHAMODIFICACION"));
					lVisitors.add(visitor);
				}
				result.setData(lVisitors);
				rs.close();
			} catch (Exception e) {
				result.setErrorCode(99);
				result.setErrorMessage(e.toString());
				e.printStackTrace();
			}

		} catch (Exception e) {
			result.setErrorCode(99);
			result.setErrorMessage(e.toString());
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public ResponseSummary getSummaryByDate() {
		ResponseSummary result = new ResponseSummary();

		String sqlActivos = "SELECT COUNT(*) AS CANTIDAD FROM ALPHILDAT.REGISTROVISITAS WHERE ESTADOVISITANTE = 1";

		String sqlEgresados = "SELECT COUNT(*) AS CANTIDAD FROM ALPHILDAT.REGISTROVISITAS WHERE ESTADOVISITANTE = 2";

		String sqlData = "SELECT D.*, V.*, R.DESVIA FROM ALPHILDAT.REGISTROVISITAS V JOIN ALPHILDAT.RIAVIA R ON R.CODVIA = V.CODIGOVIA  JOIN ALPHILDAT.RIATI D ON D.TIPDOC = V.TIPODOCUMENTOVISITANTE WHERE ESTADOVISITANTE = 1";

		try (DBAS400 db = new DBAS400(); Connection con = db.getCon(); Statement st = con.createStatement()) {

			ResultSet rsActivos = st.executeQuery(sqlActivos);
			if (rsActivos.next()) {
				result.getData().setActive(rsActivos.getString("CANTIDAD"));
			}
			ResultSet rsEgresados = st.executeQuery(sqlEgresados);
			if (rsEgresados.next()) {
				result.getData().setInactive(rsEgresados.getString("CANTIDAD"));
			}
			ResultSet rsData = st.executeQuery(sqlData);

			Map<String, StructSummaryData> summaryDataMap = new HashMap<>();
			List<StructSummaryData> data = new ArrayList<>();

			while (rsData.next()) {
				String codigoVia = rsData.getString("CODIGOVIA");
				StructSummaryData summaryData = summaryDataMap.get(codigoVia);
				if (summaryData == null) {
					summaryData = new StructSummaryData();
					summaryData.setNameSection(rsData.getString("DESVIA").trim());
					summaryDataMap.put(codigoVia, summaryData);
					data.add(summaryData);
				}

				StructTypeDocument typeDocument = new StructTypeDocument();

				typeDocument.setCode(rsData.getString("TIPDOC").trim());
				typeDocument.setCodeTypeDoc(rsData.getString("DOCUME").trim());
				typeDocument.setDescription(rsData.getString("DESDOC").trim());

				StructVisitors visitor = new StructVisitors();
				visitor.setTypeDocumentVisitor(typeDocument);
				visitor.setDocumentVisitor(rsData.getString("NUMERODOCUMENTOVISITANTE"));
				visitor.setVia(rsData.getString("CODIGOVIA"));
				visitor.setNameVisitor(rsData.getString("NOMBREVISITANTE"));
				visitor.setLastNameVisitor(rsData.getString("APELLIDOSVISITANTE"));
				visitor.setTypeVisitor(rsData.getString("TIPOVISITANTE"));
				visitor.setDateAdmissionVisitor(rsData.getString("FECHAINGRESOVISITANTE"));
				visitor.setTypeDocumentPatient(rsData.getString("TIPODOCUMENTOPACIENTE"));
				visitor.setDocumentPatient(rsData.getString("NUMERODOCUMENTOPACIENTE"));

				List<StructVisitors> visitors = summaryData.getVisitors();
				if (visitors == null) {
					visitors = new ArrayList<>();
					summaryData.setVisitors(visitors);
				}
				visitors.add(visitor);

				summaryData.setCountBySection(visitors.size());
			}

			result.getData().setSummary(data);

		} catch (Exception e) {
			result.setErrorCode(99);
			result.setErrorMessage(e.toString());
			e.printStackTrace();
		}

		result.setErrorCode(0);
		return result;
	}

	@Override
	public ResponseVisitors managmentVisitorByPatient(String documentTypePatient, String documentPatient,
			StructAdmissionUser visitorData, String user, String program) {
		ResponseVisitors result = new ResponseVisitors();

		if (documentTypePatient.equals(visitorData.getTypeDocument())
				&& documentPatient.equals(visitorData.getDocument())) {
			result.setErrorCode(90);
			result.setErrorMessage("El visitante o acompañante no puede ser el mismo que el paciente!");
			return result;
		}

		String sql = "SELECT * FROM ALPHILDAT.REGISTROVISITAS WHERE TIPODOCUMENTOVISITANTE = ? AND NUMERODOCUMENTOVISITANTE = ?";

		try (DBAS400 db = new DBAS400();
				Connection con = db.getCon();
				PreparedStatement ps = con.prepareStatement(sql);) {
			ps.setString(1, visitorData.getTypeDocument());
			ps.setString(2, visitorData.getDocument());

			try (ResultSet rs = ps.executeQuery();) {

				if (rs.next()) {

					if (visitorData.getAction() == 1 && rs.getString("ESTADOVISITANTE").equals("1")) {
						result.setErrorCode(90);
						result.setErrorMessage("El usuario ya se encuentra activo en el sistema!");
						return result;
					}
					if (visitorData.getAction() == 2 && rs.getString("ESTADOVISITANTE").equals("1")) {
						if (!updateVisitByPatient(visitorData, user, program)) {
							result.setErrorCode(90);
							result.setErrorMessage("Error en la actualización del visitante o acompañante!");
							return result;
						}
					}
					if ((!visitorData.getVia().equals(rs.getString("CODIGOVIA"))
							|| rs.getString("ESTADOVISITANTE").equals("2")) && visitorData.getAction() == 1) {
						if (!updateVisitByPatient(visitorData, user, program)) {
							result.setErrorCode(90);
							result.setErrorMessage("Error en la actualización del visitante o acompañante!");
							return result;
						}
					}

				} else {
					if (!insertVisitByPatient(documentTypePatient, documentPatient, visitorData, user, program)) {
						result.setErrorCode(90);
						result.setErrorMessage("Error en la creación del visitante o acompañante!");
						return result;
					}
				}
				rs.close();
			} catch (Exception e) {
				result.setErrorCode(99);
				result.setErrorMessage(e.toString());
				e.printStackTrace();
				return result;
			}

		} catch (

		Exception e) {
			result.setErrorCode(99);
			result.setErrorMessage(e.toString());
			e.printStackTrace();
			return result;
		}
		result.setErrorCode(0);
		return result;
	}

	private boolean insertVisitByPatient(String documentTypePatient, String documentPatient,
			StructAdmissionUser visitorData, String userCreate, String program) {
		String sql = "INSERT INTO ALPHILDAT.REGISTROVISITAS (TIPODOCUMENTOVISITANTE,NUMERODOCUMENTOVISITANTE,CODIGOVIA,NOMBREVISITANTE,APELLIDOSVISITANTE,TIPOVISITANTE,ESTADOVISITANTE,TIPODOCUMENTOPACIENTE,NUMERODOCUMENTOPACIENTE,USUARIO,PROGRMA) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
		try (DBAS400 db = new DBAS400();
				Connection con = db.getCon();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, visitorData.getTypeDocument());
			ps.setString(2, visitorData.getDocument());
			ps.setString(3, visitorData.getVia());
			ps.setString(4, visitorData.getName());
			ps.setString(5, visitorData.getLastName());
			ps.setString(6, visitorData.getTypeVisit());
			ps.setString(7, "1");
			ps.setString(8, documentTypePatient);
			ps.setString(9, documentPatient);
			ps.setString(10, userCreate);
			ps.setString(11, program);

			int rowsInserted = ps.executeUpdate();

			ps.close();
			return rowsInserted > 0;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean updateVisitByPatient(StructAdmissionUser visitorData, String userModify, String program) {
		String sql = "UPDATE ALPHILDAT.REGISTROVISITAS SET CODIGOVIA = ?, USUARIOMODIFICA = ?, PROGRAMAMODIFICA = ?, FECHAMODIFICACION = ?, ESTADOVISITANTE = ?, FECHAEGRESOVISITANTE = ?  WHERE TIPODOCUMENTOVISITANTE = ? AND NUMERODOCUMENTOVISITANTE = ?";
		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String formattedTimestamp = dateFormat.format(currentTimestamp);

		String estvis = (visitorData.getAction() == 1) ? "1" : "2";
		String dateEgress = (visitorData.getAction() == 2) ? formattedTimestamp : null;

		try (DBAS400 db = new DBAS400();
				Connection con = db.getCon();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, visitorData.getVia());
			ps.setString(2, userModify);
			ps.setString(3, program);
			ps.setString(4, formattedTimestamp);
			ps.setString(5, estvis);
			ps.setString(6, dateEgress);
			ps.setString(7, visitorData.getTypeDocument());
			ps.setString(8, visitorData.getDocument());

			int rowsUpdated = ps.executeUpdate();

			ps.close();
			return rowsUpdated > 0;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
