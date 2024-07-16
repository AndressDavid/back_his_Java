package com.shaio.his.AS400;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBAS400 implements AutoCloseable  {
    private Connection con = null;   
    private  String DB_URL = "jdbc:as400://172.20.10.20/ALPHILDAT";
    

    
    private final String USER="HCSHAIO";
    private final String PASS="FCSHC2011";
    
    
    public DBAS400()  { 
    	String env_value = System.getenv("shaio.enviroment");
		System.out.println(env_value);
		

		if (env_value==null) {
			DB_URL = "jdbc:as400://172.20.10.195";
			DB_URL = "jdbc:as400://172.20.10.195/ALPHILDAT";
		} else {

			switch(env_value) {
			   case "PRODUCCION" :
				   DB_URL = "jdbc:as400://172.20.10.20/ALPHILDAT";
				break;
			   case "PRUEBAS" :
				   DB_URL = "jdbc:as400://172.20.10.195/ALPHILDAT";
				break;
				default : 
					DB_URL = "jdbc:as400://172.20.10.195/ALPHILDAT";
				break;
					
			}
		}

		
        try {
			Class.forName("com.ibm.as400.access.AS400JDBCDriver"); 
			con = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}        
     
    }
    

    /**
     * @return the con
     */
    public Connection getCon() {
        return con;
    }

    /**
     * @param con the con to set
     */
    public void setCon(Connection con) {
        this.con = con;
    }


	@Override
	public void close() throws Exception {
		con.close();
		
	}
    
    
}
