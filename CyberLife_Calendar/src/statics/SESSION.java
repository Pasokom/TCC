package statics;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.Database;

public class SESSION {

	private static long user_cod;
	private static String user_name;
	private static String user_last_name;
	private static String user_email;

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

	public static void load_reminders () { 
		
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
