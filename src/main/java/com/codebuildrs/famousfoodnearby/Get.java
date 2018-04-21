package com.codebuildrs.famousfoodnearby;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;




public class Get extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private URL myURL;
	private URLConnection myURLConnection;
	private BufferedReader reader;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Float lat=Float.valueOf(request.getParameter("lat"));
		Float log=Float.valueOf(request.getParameter("log"));
		MongoCollection<Document> collection=Database.database.getCollection("famous");
		FindIterable<Document> iterable = collection.find(
		        new Document("lat", new Document("$gte",lat-0.3).append("$lte", lat+0.3)).append("log", new Document("$gte",log-0.3).append("$lte", log+0.3)));
		Iterator<Document> iterator=iterable.iterator();
		final JSONArray json=new JSONArray();
		while(iterator.hasNext()) {
			             Document d=iterator.next();
                         String res = "";
			             try
			             {
			                 //prepare connection
			                 myURL = new URL("https://maps.googleapis.com/maps/api/directions/json?origin="+lat+","+log+"&destination="+d.get("lat")+","+d.get("log")+"&key=AIzaSyCUPHvDyNWRiOtEWQaO5UEmGksFZGmTlsw");
			                 myURLConnection = myURL.openConnection();
			                 myURLConnection.connect();
			                 reader= new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));

			                 //reading response
			                 String r="";
			                 while ((r = reader.readLine()) != null) {
			                    // System.out.println(r);
			                     res+=r;
			                 }
                                
			                 //finally close connection
			                 reader.close();

			                
			             }
			             catch (IOException e)
			             {
			                 e.printStackTrace();
			                 
			             }
						JSONObject json1 = new JSONObject(res);
		                JSONArray routeArray = json1.getJSONArray("routes");
		                JSONObject routes = routeArray.getJSONObject(0);

		                JSONArray newTempARr = routes.getJSONArray("legs");
		                JSONObject newDisTimeOb = newTempARr.getJSONObject(0);

		                JSONObject distOb = newDisTimeOb.getJSONObject("distance");
		                JSONObject timeOb = newDisTimeOb.getJSONObject("duration");

		               String Distance =String.valueOf(distOb.get("value"));
		               String Time=timeOb.getString("text");
		               json.put(d.append("dis",Distance).append("time", Time));
					
			             
	                
	               
	            }
		
		
		response.getWriter().append(json.toString());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
