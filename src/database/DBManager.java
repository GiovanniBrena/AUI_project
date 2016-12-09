package database;

import java.sql.*;

import client.Constants;

public class DBManager {

	private Connection con;
	private Statement st;
	private ResultSet rs;
	private String driver = Constants.DATABASE_DRIVER;
	private String url = Constants.DATABASE_URL;
	private String username = Constants.DATABASE_USER;
	private String password = Constants.DATABASE_PSW;

	private Timestamp timestamp;

	public DBManager(){
		try{
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, password);
			
			System.out.println("Connected to DB");

		}catch(Exception ex){
			System.out.println("Error: " +ex);
		}
	}

	public int getMaxId(){
		int maxIdConvers = 0;
		try{

			String query = "SELECT MAX(idConvers) FROM frase";
			st = con.prepareStatement(query);
			rs = st.executeQuery(query);
			while(rs.next()){
				maxIdConvers = rs.getInt(1);
			}
			System.out.println("ID massimo conversazione: " +maxIdConvers);

		}catch(Exception ex){
			System.out.println(ex);
		}
		return maxIdConvers;
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

	//creare una nuova tabella da java, non serve
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

	//non serve
	public void newDbConvers (){

		String inizioC = "***INIZIO NUOVA CONVERSAZIONE***";
		String user = "DATABASE";
		timestamp = new Timestamp(System.currentTimeMillis());
		String audio = null;
		int newId = getMaxId() +1;

		postConversation(newId, timestamp);

		try{
			String query = "INSERT INTO Frase (contenuto, user, timestamp, fileaudio, idConvers) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, inizioC);
			ps.setString(2, user);
			ps.setTimestamp(3, timestamp);
			ps.setString(4, audio);
			ps.setInt(5, newId);
			
			ps.executeUpdate();

			System.out.println("Starting a new conv into DB");
		}catch(Exception ex) {
			System.out.println("Error: " +ex);
		} 
	}

	public void postPhrase (String myContent, String myUser){

		String fileaudio = null; //null per ora
		int id = getMaxId();
		timestamp = new Timestamp(System.currentTimeMillis());

		try{
			String query = "INSERT INTO Frase (contenuto, user, timestamp, fileaudio, idConvers) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement ps = con.prepareStatement(query);
					
			ps.setString(1,myContent);
			ps.setString(2, myUser);
			ps.setTimestamp(3, timestamp);
			ps.setString(4, fileaudio);
			ps.setInt(5, id);
			ps.executeUpdate();

			System.out.println("Values inserted into DB");
		}catch(Exception ex) {
			System.out.println("Error: " +ex);
		} 
	}

	public void postConversation (int myId, Timestamp myTs){
		String date = myTs.toString().substring(0, 10);
		String beginHour = myTs.toString().substring(11, 19);
		String endHour = null;
		try{
			PreparedStatement posted = con.prepareStatement("INSERT INTO conversazione (id, data, oraInizio, oraFine) VALUES "
					+ "('"+myId+"','"+date+"','"+beginHour+"','"+endHour+"')");
			posted.executeUpdate();

			System.out.println("Values inserted into DB");
		}catch(Exception ex) {
			System.out.println("Error: " +ex);
		} 
	}

	public void updateOraFine(){
		timestamp = new Timestamp(System.currentTimeMillis());
		String endHour = timestamp.toString().substring(11, 19);
		try
		{
			// create our java preparedstatement using a sql update query
			PreparedStatement ps = con.prepareStatement(
					"UPDATE conversazione SET oraFine = ? WHERE id = ?");

			// set the preparedstatement parameters
			ps.setString(1,endHour);
			ps.setLong(2,getMaxId());

			// call executeUpdate to execute our sql update statement
			ps.executeUpdate();
			ps.close();
		}catch (SQLException ex)
		{
			System.out.println("Error: " +ex);
		}
	}
}
