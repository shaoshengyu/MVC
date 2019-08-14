package com.cha5.listener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.sun.imageio.plugins.common.InputStreamAdapter;

/**
 * Application Lifecycle Listener implementation class CountListener
 *
 */
//����WebӦ�ó���Ӧ�ü���
@WebListener("��¼ҳ����ʴ���")
public class CountListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public CountListener() {
       
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
         ServletContext context=sce.getServletContext();   
         context.log(context.getServletContextName()+"Ӧ��ֹͣ:");
         Integer counter=(Integer)context.getAttribute("count");
         if(counter!=null) {
        	 try {
        		 String filepath="F:/count.txt";
        		 //����·��д��count��ֵ
        		 PrintWriter pw=new PrintWriter(filepath);
        		 pw.println(counter.intValue());
        		 pw.close();
        	 }catch(IOException e) {
        		 e.printStackTrace();
        	 }
         }
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
    	  ServletContext context=sce.getServletContext();
    	  context.log(context.getServletContextName()+"Ӧ�ó�ʼ��:");
    	  try {
    		  Reader file=new FileReader("F:/count.txt");
    		  BufferedReader reader=new BufferedReader(file);
    		  String c=reader.readLine();
    		  if(c==null||"".equals(c)){
    			  c="0";
    		  }
    		  int count=Integer.parseInt(c);
    		  reader.close();
              context.setAttribute("count", count);
    	  }catch (Exception e) {
			e.printStackTrace();
		}
    }
	
}
