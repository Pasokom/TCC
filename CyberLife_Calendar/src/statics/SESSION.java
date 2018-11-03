package statics;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.Database;
import db.pojo.UserReminders;

public class SESSION {

	private static long user_cod;
	private static String user_name;
	private static String user_last_name;
	private static String user_email;
	
	private static ArrayList<UserReminders> list_user_reminders;

	public static void set_user_cod(int cod) {
		SESSION.user_cod = cod;
	}
	public static void start_session(int cod, String name, String last_name, String email)
			throws ClassNotFoundException, SQLException {
		SESSION.user_cod = cod;
		SESSION.user_name = name;
		SESSION.user_last_name = last_name;
		SESSION.user_email = email;
	}
	public static void load_reminders () throws ClassNotFoundException, SQLException { 
		
		String sql = "SELECT * FROM VIEW_LEMBRETES WHERE L_STATUS = 'ATIVADO' AND FK_USUARIO =  " + SESSION.get_user_cod() + "';";
			
		ResultSet result = Database.get_connection().createStatement().executeQuery(sql);
		
		System.out.println(!result.first() ? "[INFORMATION] the user has no reminders" : "[CONFIRMATION] user reminders go now be loaded");
		
		while(result.next()) { 
			UserReminders r = new UserReminders();
			
			r.setCod_reminder_repetition(1);
			r.setTime(result.getTime(2));
			r.setDate(result.getDate(3));
			r.setCod_reminder(result.getInt(4));
			r.setName(result.getString(5));
			r.setDescription(result.getString(6) == null ? new String() : result.getString(6));
			r.setStatus(result.getString(7));
			r.setAll_day(result.getBoolean(8));		
		
			SESSION.user_reminders().add(r);
		}
	}
	
	
	public static ArrayList<UserReminders> user_reminders() { 
		return SESSION.list_user_reminders == null ? new ArrayList<UserReminders>() : list_user_reminders ;
	}
	public static long get_user_cod() {
		return user_cod;
	}

	public static String get_user_name() {
		return user_name;
	}

	public static String get_user_last_name() {
		return user_last_name;
	}

	public static String get_user_email() {
		return user_email;
	}
}
