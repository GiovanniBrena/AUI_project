package database;

import java.sql.*;

public class ConnectionProvider {
	
	private static Connection con=null;
	static{
		try{
			Class.forName(ConstantsDB.DATABASE_DRIVER);
			con=DriverManager.getConnection(ConstantsDB.DATABASE_URL,ConstantsDB.DATABASE_USER,ConstantsDB.DATABASE_PSW);
			System.out.println("Connected to DB");
		}catch(Exception e){}
	}

	public static Connection getCon(){
		return con;
	}    
}