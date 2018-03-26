package com.codebuildrs.famousfoodnearby;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * Servlet implementation class Token
 */
public class Token extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name =request.getParameter("name");
		String id=request.getParameter("id");
		System.out.println("name :"+name);
		System.out.println("id :"+id);
		MongoCollection<Document> collection=Database.database.getCollection("tokens");
		collection.insertOne(new Document("name",name).append("id", id));
		response.getWriter().append("true");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
