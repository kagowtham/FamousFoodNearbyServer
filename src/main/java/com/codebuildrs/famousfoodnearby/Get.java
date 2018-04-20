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
		JSONArray json=new JSONArray();
		while(iterator.hasNext()) {
			Document d=iterator.next();
			 String mainUrl="http://maps.googleapis.com/maps/api/directions/json?";


	            StringBuilder sbPostData= new StringBuilder(mainUrl);
	            sbPostData.append("origin="+lat+","+log);
	            sbPostData.append("&destination="+d.get("lat")+","+d.get("log"));
	            sbPostData.append("&sensor=false&units=metric");
	            mainUrl = sbPostData.toString();
	            try
	            {
	                //prepare connection
	                myURL = new URL(mainUrl);
	                myURLConnection = myURL.openConnection();
	                myURLConnection.connect();
	                reader= new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));

	                //reading response
	                String response1;
	                while ((response1 = reader.readLine()) != null)
	                    //print response
	                System.out.println("response from "+mainUrl+"\n"+response1);
	                JSONObject json1 = new JSONObject(response1);
	                JSONArray routeArray = json1.getJSONArray("routes");
	                JSONObject routes = routeArray.getJSONObject(0);

	                JSONArray newTempARr = routes.getJSONArray("legs");
	                JSONObject newDisTimeOb = newTempARr.getJSONObject(0);

	                JSONObject distOb = newDisTimeOb.getJSONObject("distance");
	                JSONObject timeOb = newDisTimeOb.getJSONObject("duration");

	               String Distance =distOb.getString("text");
	               String Time=timeOb.getString("text");
	               json.put(d.append("dis",Distance).append("time", Time));
	                //finally close connection
	                reader.close();
	            }
	            catch (IOException e)
	            {
	                e.printStackTrace();
	            }
			
		}
		response.getWriter().append(json.toString());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
