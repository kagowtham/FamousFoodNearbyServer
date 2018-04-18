package com.codebuildrs.famousfoodnearby;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.json.JSONArray;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;


public class Get extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Float lat=Float.valueOf(request.getParameter("lat"));
		Float log=Float.valueOf(request.getParameter("log"));
		MongoCollection<Document> collection=Database.database.getCollection("famous");
		FindIterable<Document> iterable = collection.find(
		        new Document("lat", new Document("$gte",lat-0.3).append("$lte", lat+0.3)).append("log", new Document("$gte",log-0.3).append("$lte", log+0.3)));
		Iterator<Document> iterator=iterable.iterator();
		JSONArray json=new JSONArray();
		while(iterator.hasNext()) {
			Document d=iterator.next();
			json.put(d);
		}
		response.getWriter().append(json.toString());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
