package com.codebuildrs.famousfoodnearby;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

/**
 * Servlet implementation class Send
 */
public class Send extends HttpServlet {
	private static final long serialVersionUID = 1L;
  @Override
public void init() throws ServletException {
	
	super.init();
	InputStream serviceAccount = null;
	FirebaseOptions options = null;
	 ServletContext context = getServletContext();
	try {
		serviceAccount = context.getResourceAsStream("/WEB-INF/serviceAccountKey.json");
		options = new FirebaseOptions.Builder()
				  .setCredentials(GoogleCredentials.fromStream(serviceAccount))
				  .setDatabaseUrl("https://famousfoodnearby.firebaseio.com")
				  .build();
		FirebaseApp.initializeApp(options);
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}catch(IOException e){
		e.printStackTrace();
	}
}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name=request.getParameter("name");
		String to=request.getParameter("to");
		String msg=request.getParameter("msg");
		MongoCollection<Document> collection=Database.database.getCollection("tokens");
        FindIterable<Document> i=collection.find(new Document("name",name));
        Document d=i.first();
		Message message = Message.builder()
			    .putData("to", to)
			    .putData("msg", msg)
			    .setToken(d.getString("id"))
			    .build();
		String res="";
		try {
			res = FirebaseMessaging.getInstance().sendAsync(message).get();
			System.out.println("Successfully sent message: " + res);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.getWriter().append(res);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
