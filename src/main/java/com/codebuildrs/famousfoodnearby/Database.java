package com.codebuildrs.famousfoodnearby;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

public class Database {
   static MongoClient mongo;
   static MongoDatabase database;
   Database(){
	   MongoClientURI uri = new MongoClientURI(
				"mongodb+srv://mydb:mydb@cluster0-hmcgs.mongodb.net/test");
	  // MongoClientURI uri = new MongoClientURI(
			//	"mongodb://mydb:mydb@mydb/mydb");
	   mongo=new MongoClient(uri);
	   database=mongo.getDatabase("mydb");
   }
}
