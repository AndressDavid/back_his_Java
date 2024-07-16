package com.shaio.his.revistaMedica;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.shaio.his.BusPaciente.struct_revista;
import com.shaio.his.mongo.DBmongo;

@Service
public class XRevistaMedica implements IRevistaMedica{

	
	@Override
	public struct_revista ultimoDx(long numeroIngreso,String usuario) {
		struct_revista rv = null;
		DBmongo mongo = new DBmongo();
		MongoCollection<Document> collection = mongo.getShaioDB().getCollection("revistaMedica");		
	    BasicDBObject query = new BasicDBObject("usuario", usuario).append("numeroIngreso", numeroIngreso);
	    FindIterable<Document> documents = collection.find(query).sort(new BasicDBObject("fecha",-1));
		try (
				MongoCursor<Document> cursor = documents.iterator();
		    ) {
					
			if (cursor.hasNext()) {		     
				rv = new struct_revista().fromJson(cursor.next().toJson());
			}
			cursor.close();
		}
		mongo.close();
		return rv!=null?rv:null;
	}
	
	
	
	@Override
	public void saveData(String body) {
		DBmongo mongo = new DBmongo();
		mongo.getShaioDB().getCollection("revistaMedica").insertOne(Document.parse(body));
		
		JSONObject obj = new JSONObject(body);
		String usuario = obj.getString("usuario");
		long numeroIngreso = obj.getLong("numeroIngreso");
		
				

		MongoCollection<Document> collection = mongo.getShaioDB().getCollection("mispacientes");

		BasicDBObject query = new BasicDBObject("usuario", usuario);		
		FindIterable<Document> documents = collection.find(query);
		
		try (
				MongoCursor<Document> cursor = documents.iterator();
		    ) {
					
			if (cursor.hasNext()) {
				
			} else {
				
				List<Long> lingresos = new ArrayList();
				lingresos.add(numeroIngreso);
				
				Document mispacientes = new Document("usuario", usuario);
				mispacientes.append("ingresos", lingresos);
				       				       
				collection.insertOne(mispacientes);
			}
		}
		mongo.close();
	}
	
	

	@Override
	public List<struct_revista> listar(String usuario, long numeroDocumento) {
		List<struct_revista> lrevista = new ArrayList();
		
		DBmongo mongo = new DBmongo();
		MongoCollection<Document> collection = mongo.getShaioDB().getCollection("revistaMedica");		
	    BasicDBObject query = new BasicDBObject("usuario", usuario).append("numeroDocumento", numeroDocumento);		
		FindIterable<Document> documents = collection.find(query);
		try (
				MongoCursor<Document> cursor = documents.iterator();
		    ) {
					
			while (cursor.hasNext()) {		     
				struct_revista rv = new struct_revista().fromJson(cursor.next().toJson());
			    lrevista.add(rv);
			}
			cursor.close();
		}
		mongo.close();
	
		return lrevista;
	}

}
