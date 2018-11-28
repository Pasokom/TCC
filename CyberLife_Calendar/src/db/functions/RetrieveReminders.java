package db.functions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import db.Database;
import db.pojo.ReminderBanco;
import db.pojo.ReminderDB;
import statics.SESSION;

public class RetrieveReminders {

	private String query_string;
	public static ArrayList<ReminderBanco> listReminders = new ArrayList<>();

	public RetrieveReminders() {
		
	}
	
	public void update(){
			
		if(query_string == null) 
			query_string = "select * from lembrete where FK_USUARIO = " + SESSION.get_user_cod();

		try {
			
			Statement statement = Database.get_connection().createStatement();
			ResultSet r7 = statement.executeQuery(query_string);
			
			while(r7.next()) {
				
				ReminderBanco reminder = new ReminderBanco();
				reminder.setCod_lembrete(r7.getInt(1));
				reminder.setTitulo(r7.getString(2));
				reminder.setDia_todo(r7.getBoolean(3));
				reminder.setStatus(r7.getString(4));
				reminder.setRecorrencia_minutos(r7.getBoolean(5));
				
				listReminders.add(reminder);
			}
			
		} 
		catch (ClassNotFoundException | SQLException e) {
			
			e.printStackTrace();
		}
		
	}
	
	public void setQueryString(String query_string) {
		this.query_string = query_string;
	}
}
