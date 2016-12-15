package database;

import java.sql.*;

public class DBManager {

	Connection con=ConnectionProvider.getCon();
	private Statement st;
	private ResultSet rs;

	private Timestamp timestamp;

	
	public int getMaxId(){
		int maxIdConvers = 0;
		try{

			String query = "SELECT MAX(idConvers) FROM frase";
			st = con.prepareStatement(query);
			rs = st.executeQuery(query);
			while(rs.next()){
				maxIdConvers = rs.getInt(1);
			}
		}catch(Exception ex){
			System.out.println("Errore qua:" +ex);
		}
		return maxIdConvers;
	}

	//record from db and print them out, non serve
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


	public void newDbConvers (){

		String inizioC = ConstantsDB.NEW_CONVERSATION;
		String user = ConstantsDB.DB_USER;
		timestamp = new Timestamp(System.currentTimeMillis());
		String audio = "...";
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

	public int postPhrase (String myContent, String myUser){

		String fileaudio = "..."; //null per ora
		int id = getMaxId();
		timestamp = new Timestamp(System.currentTimeMillis());

		try{
			String query = "INSERT INTO Frase (contenuto, user, timestamp, fileaudio, idConvers) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement ps = con.prepareStatement(query);

			ps.setString(1,parseContent(myContent));
			ps.setString(2, myUser);
			ps.setTimestamp(3, timestamp);
			ps.setString(4, fileaudio);
			ps.setInt(5, id);
			ps.executeUpdate();

			System.out.println("Values inserted into DB");
		}catch(Exception ex) {
			System.out.println("Error: " +ex);
		} 
		return getId(timestamp.toString());
	}

	private int getId(String myTimestamp) {
		String query = null;
		int result = 0; 
		myTimestamp = myTimestamp.substring(0, 19);

		try{
			query = "SELECT id FROM frase WHERE timestamp = '"+myTimestamp+"'";
			
			rs = st.executeQuery(query);
			while(rs.next()){
				result = rs.getInt(1);
			}
		}catch(Exception ex) {
			System.out.println("Error: " +ex);
		}
		System.out.println("ID:" +result);
		return result;
	}

	public void postConversation (int myId, Timestamp myTs){
		String date = myTs.toString().substring(0, 10);
		String beginHour = myTs.toString().substring(11, 19);
		String endHour = null;
		try{
			String query = "INSERT INTO conversazione (id, data, oraInizio, oraFine) VALUES (?, ?, ?, ?)";
			PreparedStatement posted = con.prepareStatement(query);

			posted.setInt(1, myId);
			posted.setString(2, date);
			posted.setString(3, beginHour);
			posted.setString(4, endHour);
			dbThread.start();

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
			// create our java preparedstatement
			PreparedStatement ps = con.prepareStatement(
					"UPDATE conversazione SET oraFine = ? WHERE id = ?");

			// set the prepared statement parameters
			ps.setString(1,endHour);
			ps.setLong(2,getMaxId());

			ps.executeUpdate();
			ps.close();
		}catch (SQLException ex){
			System.out.println("Error: " +ex);
		}
	}
	
	public void updateSentiment(Double sentimentScore, int myId){
		try
		{
			PreparedStatement ps = con.prepareStatement(
					"UPDATE frase SET sentiment = ? WHERE id = ?");

			ps.setDouble(1,sentimentScore);
			ps.setLong(2,myId);

			ps.executeUpdate();
			System.out.println("Aggiornati i sentimenti");
			ps.close();
		}catch (SQLException ex){
			System.out.println("Error: " +ex);
		}
	}
	
	private String parseContent(String myContent) {
		myContent = myContent.replaceAll("&", "&amp;");
		myContent = myContent.replaceAll("à", "&agrave;");
		myContent = myContent.replaceAll("è", "&egrave;");
		myContent = myContent.replaceAll("é", "&eacute;");
		myContent = myContent.replaceAll("É", "&Eacute;");
		myContent = myContent.replaceAll("È", "&Egrave;");
		myContent = myContent.replaceAll("ò", "&ògrave;");
		myContent = myContent.replaceAll("ì", "&igrave;");
		myContent = myContent.replaceAll("ù", "&ùgrave;");
		return myContent;
	}
	
	Thread dbThread = new Thread() {
		
		@SuppressWarnings("unused")
		public void run(PreparedStatement threadStatement){
			try {
				threadStatement.executeUpdate();
			} catch (SQLException ex) {
				System.out.println("Error: " +ex);
			}
		};
	};


	public void updateEmotion(Double angerValue, Double disgustValue, Double fearValue, Double joyValue,
			Double sadnessValue, int id) {
		try
		{
			PreparedStatement ps = con.prepareStatement(
					"UPDATE frase SET anger = ?, disgust = ?, fear = ?, joy = ?, sadness = ? WHERE id = ?");

			ps.setDouble(1,angerValue);
			ps.setDouble(2, disgustValue);
			ps.setDouble(3,fearValue);
			ps.setDouble(4,joyValue);
			ps.setDouble(5,sadnessValue);
			ps.setLong(6,id);

			ps.executeUpdate();
			System.out.println("Aggiornate le emozioni");
			ps.close();
			
		}catch (SQLException ex){
			System.out.println("Error: " +ex);
		}
		
	}
}
