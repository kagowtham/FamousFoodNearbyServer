package com.codebuildrs.famousfoodnearby;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;

import com.mongodb.client.MongoCollection;


public class Insert extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String lat=request.getParameter("lat");
		String log=request.getParameter("log");
		String famous=request.getParameter("famous");
		MongoCollection<Document> collection=Database.database.getCollection("famous");
		collection.insertOne(new Document("lat",Float.valueOf(lat)).append("log",Float.valueOf(log)).append("famous", famous));
		response.getWriter().append("success");
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
