package com.shaio.his.consentimientoInformado;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoWriteConcernException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.shaio.his.mongo.DBmongo;

@Service
public class XConsentimientoInformado implements IConsentimientoInformado {

	@Override
	public ResponseListPlantillas ListarPlantillas() {
		
		ResponseListPlantillas response = new ResponseListPlantillas();
		
		try(
			DBmongo mongo = new DBmongo();
		){
			
			MongoCollection<Document> collection = mongo.getShaioDB().getCollection("plantilla_consentimiento");		
		    BasicDBObject query = new BasicDBObject("active", "true");
		    FindIterable<Document> documents = collection.find(query);
			try (
					MongoCursor<Document> cursor = documents.iterator();
			    ) {					
				while (cursor.hasNext()) {		     
					StructPLantillaConsentimiento cons = new StructPLantillaConsentimiento().fromJson(cursor.next().toJson());				
					response.lplantillas.add(cons);
				}
				cursor.close();
			}
			catch( com.mongodb.MongoWriteException | MongoWriteConcernException  e) {
				System.out.println(e.getMessage());
				mongo.close();
			}
			mongo.close();
		}catch( com.mongodb.MongoWriteException | MongoWriteConcernException  e) {
			System.out.println(e.getMessage());
		}
		
		
		return response;
	}
	
	@Override
	public void setLplantillas(String body) {
		try(
			DBmongo mongo = new DBmongo()
		) {
			JSONObject json_transform = new JSONObject(body);
			
			MongoCollection<Document> collection = mongo.getShaioDB().getCollection("plantilla_consentimiento");		
		    BasicDBObject query = new BasicDBObject("id_document", json_transform.get("id_document") );
		    FindIterable<Document> documents = collection.find(query);
			
			
			Document newValues = new Document();
			newValues.append("type", json_transform.getString("type"));
			newValues.append("subType", json_transform.getString("subType"));
			newValues.append("title", json_transform.getString("title"));
			newValues.append("id_document", json_transform.getString("id_document"));
			newValues.append("code", json_transform.getString("code"));
			newValues.append("version", json_transform.getString("version"));
			newValues.append("active", json_transform.optString("active", "true"));
			newValues.append("formulario", json_transform.getString("formulario"));
			newValues.append("text", json_transform.getString("text"));
			if(documents.iterator().hasNext()) {
				Document foundDocument = documents.iterator().next();
				ObjectId documentId = foundDocument.getObjectId("_id");
				Document updateQuery = new Document("_id", documentId);
				Document updateCommand = new Document("$set", newValues);
				collection.updateOne(updateQuery, updateCommand);
			}else {
				Document newDocument = Document.parse(body);
				collection.insertOne(newDocument);
			}					
			mongo.close();	
		} catch( com.mongodb.MongoWriteException | MongoWriteConcernException  e) {
			System.out.println(e.getMessage());
		}		
	}
	
	
	@Override
	public ResponseDocumento getData(String idDocumento) {
		
		System.out.println("Get data "+idDocumento);
		
		ResponseDocumento salida = new ResponseDocumento(); 
		
		try(
			DBmongo mongo = new DBmongo();
		){
			MongoCollection<Document> collection = mongo.getShaioDB().getCollection("registro_consentimiento");		
		    //BasicDBObject query = new BasicDBObject("paciente.ingreso", nroIngreso).append("idDocumento", idDocumento);
			BasicDBObject query = new BasicDBObject("idDocumento", idDocumento); 
		    FindIterable<Document> documents = collection.find(query);
			try (
				MongoCursor<Document> cursor = documents.iterator();	
			) {					
				if (cursor.hasNext()) {	
					String info = cursor.next().toJson();
					System.out.println(info);
					StructConsentimiento cons = new StructConsentimiento().fromJson(info);				
					salida.setData(cons);
					
				}
				cursor.close();
			}catch( com.mongodb.MongoWriteException | MongoWriteConcernException  e) {
				System.out.println(e.getMessage());
				mongo.close();
			}
			mongo.close();
		}
		catch( com.mongodb.MongoWriteException | MongoWriteConcernException  e) {
			System.out.println(e.getMessage());
		}
		
		return salida;
	}
	
	
	
	
	@Override
	public boolean documentExist(String idDocumento) {
		boolean response = false;

		try(
			DBmongo mongo = new DBmongo();
		){
			
			MongoCollection<Document> collection = mongo.getShaioDB().getCollection("registro_consentimiento");		
			BasicDBObject query = new BasicDBObject("idDocumento", idDocumento); 
		    FindIterable<Document> documents = collection.find(query);
			try (
				MongoCursor<Document> cursor = documents.iterator();
			) {					
				if (cursor.hasNext()) {		     
					response = true;
					
				}
				cursor.close();
			}catch( com.mongodb.MongoWriteException | MongoWriteConcernException  e) {
				System.out.println(e.getMessage());
				mongo.close();
			}
			mongo.close();
			
		}catch( com.mongodb.MongoWriteException | MongoWriteConcernException  e) {
			System.out.println(e.getMessage());
		}
		
		return response;
	}
	
	
	
	@Override
	public String saveData(String body) {
		String result = "";
		try(
			DBmongo mongo = new DBmongo();
		) {
		 mongo.getShaioDB().getCollection("registro_consentimiento").insertOne(Document.parse(body));
		 mongo.close();
		} catch( com.mongodb.MongoWriteException | MongoWriteConcernException  e) {
			System.out.println(e.getMessage());
			result = e.getMessage();
		}
		return result;
	}




	@Override
	public ResponseListConsentimientosPac listConsentimientosPacientes(String nroIngreso) {
		ResponseListConsentimientosPac salida = new ResponseListConsentimientosPac(); 
		
		try(
			DBmongo mongo = new DBmongo();
		){
			
			MongoCollection<Document> collection = mongo.getShaioDB().getCollection("registro_consentimiento");		
		    BasicDBObject query = new BasicDBObject("paciente.ingreso", nroIngreso);
		    FindIterable<Document> documents = collection.find(query);
			try (
					MongoCursor<Document> cursor = documents.iterator();
					
			    ) {					
				while (cursor.hasNext()) {	
					String json = cursor.next().toJson();
					StructListConsentimiento cons = new StructListConsentimiento();
					JSONObject jo = new JSONObject(json); 
					
					cons.setPlantilla((String)jo.get("plantilla"));
					 try {		            
			            cons.setFecha((String) jo.get("fecha"));
					    cons.setIdDocumento((String)jo.get("idDocumento"));
					 } catch(Exception e) {
						 System.out.println(e.getMessage());
					 } 
					cons.setNombrePlantilla(getNombrePlantilla(cons.getPlantilla()));
					cons.setRmMedico((String)jo.getJSONObject("medico").getString("registroMedico").trim());
					cons.setNombreMedico((String)jo.getJSONObject("medico").getString("nombre ").trim());
					salida.data.add(cons);
					
				}
				cursor.close();
			}catch( com.mongodb.MongoWriteException | MongoWriteConcernException  e) {
				System.out.println(e.getMessage());
			}
			mongo.close();
			
		}catch( com.mongodb.MongoWriteException | MongoWriteConcernException  e) {
			System.out.println(e.getMessage());
		}
		
		return salida;
	}
	
	
	
	
	private String getNombrePlantilla(String id) {
		String nombre = "";
		ResponseListConsentimientosPac salida = new ResponseListConsentimientosPac(); 

		try(
			DBmongo mongo = new DBmongo();
		){
			
			MongoCollection<Document> collection = mongo.getShaioDB().getCollection("plantilla_consentimiento");		
		    BasicDBObject query = new BasicDBObject("id_document", id);
		    FindIterable<Document> documents = collection.find(query);
			try (
					MongoCursor<Document> cursor = documents.iterator();
					
			    ) {					
				if (cursor.hasNext()) {					
					String json = cursor.next().toJson();		
					JSONObject jo = new JSONObject(json); 
					nombre = (String)jo.get("title");
				}
				mongo.close();
			}catch( com.mongodb.MongoWriteException | MongoWriteConcernException  e) {
				mongo.close();
				System.out.println(e.getMessage());
			}
			
		}catch( com.mongodb.MongoWriteException | MongoWriteConcernException  e) {
			System.out.println(e.getMessage());
		}
		
		return nombre;
	}
}
