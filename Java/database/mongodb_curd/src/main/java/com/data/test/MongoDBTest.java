package com.data.test;

import org.bson.Document;
import org.junit.Test;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class MongoDBTest {

	@Test
	public void createCollection() {
		try {
			@SuppressWarnings("resource")
			MongoClient mongoClient = new MongoClient("127.0.0.1",27017);
			MongoDatabase mongoDatabase = mongoClient.getDatabase("admin");
			System.out.println("Connect to database successfully");
			mongoDatabase.createCollection("test");
			System.out.println("Collection create successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	@Test
	public void getCollection() {
		@SuppressWarnings("resource")
		MongoClient mongoClient = new MongoClient("127.0.0.1",27017);
		MongoDatabase mongoDatabase = mongoClient.getDatabase("admin");
		@SuppressWarnings("unused")
		MongoCollection<Document> collection = mongoDatabase.getCollection("test");
		System.out.println("Collection select successfully");
	}
	
	@Test
	public void insertDocument() {
		@SuppressWarnings("resource")
		MongoClient mongoClient = new MongoClient("127.0.0.1",27017);
		MongoDatabase mongoDatabase = mongoClient.getDatabase("admin");
		MongoCollection<Document> collection = mongoDatabase.getCollection("test");
		Document docuemnt = new Document("title","MongoDB").append("description", "database");
		collection.insertOne(docuemnt);
		System.out.println("Docuemnt insert successfully");
	}
	
	@Test
	public void getDocument() {
		@SuppressWarnings("resource")
		MongoClient mongoClient = new MongoClient("127.0.0.1",27017);
		MongoDatabase mongoDatabase = mongoClient.getDatabase("admin");
		MongoCollection<Document> collection = mongoDatabase.getCollection("test");
		FindIterable<Document> find = collection.find();
		MongoCursor<Document> iterator = find.iterator();
		while(iterator.hasNext()) {
			System.out.println(iterator.next());
		}
	}
	
	@Test
	public void updateDocument() {
		@SuppressWarnings("resource")
		MongoClient mongoClient = new MongoClient("127.0.0.1",27017);
		MongoDatabase mongoDatabase = mongoClient.getDatabase("admin");
		MongoCollection<Document> collection = mongoDatabase.getCollection("test");
		collection.updateMany(Filters.eq("title","MongoDB"), new Document("$set",new Document("title","MongoDB Database")));
		FindIterable<Document> find = collection.find();
		MongoCursor<Document> iterator = find.iterator();
		while(iterator.hasNext()) {
			System.out.println(iterator.next());
		}
	}
	
	@Test
	public void deleteDocument() {
		@SuppressWarnings("resource")
		MongoClient mongoClient = new MongoClient("127.0.0.1",27017);
		MongoDatabase mongoDatabase = mongoClient.getDatabase("admin");
		MongoCollection<Document> collection = mongoDatabase.getCollection("test");
		collection.deleteOne(Filters.eq("title", "MongoDB Database"));
		FindIterable<Document> find = collection.find();
		MongoCursor<Document> iterator = find.iterator();
		while(iterator.hasNext()) {
			System.out.println(iterator.next());
		}
	}
	
	
}
