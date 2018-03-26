package com.codebuildrs.famousfoodnearby;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;


public class MyServletContextListener implements ServletContextListener {

    
    public MyServletContextListener() {
        
    }

	
    public void contextDestroyed(ServletContextEvent arg0)  { 
    	
    }

	
    public void contextInitialized(ServletContextEvent arg0)  { 
    	
      Database db=new Database();
    			
    }
	
}
