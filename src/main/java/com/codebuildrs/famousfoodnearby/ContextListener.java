package com.codebuildrs.famousfoodnearby;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class ContextListener implements ServletContextListener {

    
	
    public void contextDestroyed(ServletContextEvent arg0)  { 
        
    }

	
    public void contextInitialized(ServletContextEvent arg0)  { 
		Database db=new Database();
    }
	
}
