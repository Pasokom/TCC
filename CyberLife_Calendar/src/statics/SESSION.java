package statics;

import java.sql.SQLException;
import java.util.ArrayList;

import db.pojo.reminderPOJO.ReminderDB;
import javafx.scene.image.Image;

public class SESSION {

	private static long user_cod;
	private static String user_name;
	private static String user_last_name;
	private static String user_email;
	private static Image user_photo;

	private static ArrayList<ReminderDB> list_user_reminders;

	public static void set_user_cod(int cod) {
		SESSION.user_cod = cod;
	}

	public static void start_session(int cod, String email, String name, String last_name)
			throws ClassNotFoundException, SQLException {
		SESSION.user_cod = cod;
		SESSION.user_name = name;
		SESSION.user_last_name = last_name;
		SESSION.user_email = email;
	}

	public static ArrayList<ReminderDB> user_reminders() {
		return SESSION.list_user_reminders == null ? new ArrayList<ReminderDB>() : list_user_reminders;
	}

	public static void setImage(Image image) {
		SESSION.user_photo = image;
	}

	public static Image get_user_image() {
		return user_photo;
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
