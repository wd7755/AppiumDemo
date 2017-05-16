package com.shizu.appium.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class OperationDB {
	private static String url = "jdbc:mysql://192.168.8.222:3306/core?useUnicode=true&characterEncoding=UTF8";
    private static String user = "root";
    private static String password = "ccdeal2013";
    
    static{
    	
        try {
             Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
             throw new ExceptionInInitializerError(e);
        }
    }
    
    public static String queryData(String sql){
    	return queryData(sql,null);
    }
    
	public static String queryData(String sql, String[] values){
	    Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String value = "";
        try {
             conn = getConnection();
             
             ps = conn.prepareStatement(sql);             
           
             if(values != null){
	             for(int i = 0;i < values.length; i++){            	 
	            	 ps.setObject(i + 1, values[i]);
	             }     
             }
             rs = ps.executeQuery();    
             if(rs.next())
            	 value = rs.getString(1);
        } catch (Exception e) {
             e.printStackTrace();
        }finally{
             free(rs, ps, conn);
        }
		return value;
	}	
    
    public static Connection getConnection() throws SQLException{   	    	
    
         return DriverManager.getConnection(url, user, password);
    }
    
    public static void free(ResultSet rs, Statement st, Connection conn){
    	
         try {
              if(rs!=null) 
                   rs.close();
         } catch (Exception e) {
              e.printStackTrace();
         }finally{
              try {
                   if(st!=null)
                        st.close();
              } catch (Exception e2) {
                   e2.printStackTrace();
              }finally{
                   try {
                        conn.close();
                   } catch (Exception e3) {
                        e3.printStackTrace();
                   }
              }
         }
    }	   
}
