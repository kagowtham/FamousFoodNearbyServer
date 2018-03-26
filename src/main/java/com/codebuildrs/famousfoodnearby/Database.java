package com.codebuildrs.famousfoodnearby;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

public class Database {
   static MongoClient mongo;
   static MongoDatabase database;
   Database(){
	   MongoClientURI uri = new MongoClientURI(
				"mongodb://mydb:mydb@mongodb/mydb");
	   mongo=new MongoClient(uri);
	   database=mongo.getDatabase("mydb");
   }
}
