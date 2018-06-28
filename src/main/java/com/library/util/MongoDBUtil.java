package com.library.util;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDBUtil
{
	static public MongoDatabase getDataBase()
	{
		MongoDatabase database = null;
		MongoClient mongoClient = new MongoClient(new ServerAddress("localhost", 27017));
		if (mongoClient != null)
			database = mongoClient.getDatabase("local");
		return database;
	}
	
	static public MongoCollection<Document> getCollection(String collName)
	{
		MongoCollection<Document> coll = null;
		MongoDatabase database = getDataBase();
		if (database != null)
			coll = database.getCollection(collName);
		return coll;
	}
}
