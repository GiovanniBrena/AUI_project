package database;

import java.sql.*;

public class DBManager {

	private Connection con;
	private Statement st;
	private ResultSet rs;
	private String driver = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://us-cdbr-iron-east-04.cleardb.net/ad_85f9c5363d162e8";
	private String username = "b0179319c7b2ef";
	private String password = "d319ebe7";
	
	private Timestamp timestamp;
	
	public DBManager(){
		try{
			Class.forName(driver);
			
			con = DriverManager.getConnection(url, username, password);
			//con = DriverManager.getConnection("jdbc:mysql://localhost:3306/prova", "root","");
			st = con.createStatement();
			System.out.println("Connected to DB");
			
		}catch(Exception ex){
			System.out.println("Error: " +ex);
		}
	}
	
	//record from db and print them out
	public void getData(){
		try{
			
			String query = "select * from test";
			rs = st.executeQuery(query);
			System.out.println("Records from DB");
			while(rs.next()){
				String nome = rs.getString("nome");
				String eta = rs.getString("eta");
				String sesso = rs.getString("sesso");
				System.out.println("Name: " +nome+ "  " +"Età: "+eta+ "    "+ "Sesso: " +sesso);
			}
			
		}catch(Exception ex){
			System.out.println(ex);
		}
	}

	public void createTable() throws Exception{
		 try {
			
			 PreparedStatement create = con.prepareStatement("CREATE TABLE IF NOT EXISTS provazza(id int NOT NULL AUTO_INCREMENT, first varchar(255), last varchar(255), PRIMARY KEY(id))");
			 create.executeUpdate();
					 
		 }catch(Exception ex){
			 System.out.println("Error: " +ex);
			 }finally{
			 System.out.println("function completed");
			 }
	}
	
	public void postPhrase (String content, String user){
		 
		String fileaudio = null; //null per ora
		int idConvers = 99; //trovare un modo
		timestamp = new Timestamp(System.currentTimeMillis());
		
		try{
		PreparedStatement posted = con.prepareStatement("INSERT INTO Frase (contenuto, user, timestamp, fileaudio, idConvers) VALUES " + "('"+content+"','"+user+"','"+timestamp+"','"+fileaudio+"','"+idConvers+"')");
		posted.executeUpdate();
		
		System.out.println("Values inserted into DB");
			}catch(Exception ex) {
				System.out.println("Error: " +ex);
			} 
	}
}
