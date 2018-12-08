package statics;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

	private static final String PATH = SESSION.class.getResource("").getPath() + "../../resources/images/person.png";

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
		if (image == null) {
			SESSION.user_photo = image;
			return;
		}
		try {
			SESSION.user_photo = new Image(new FileInputStream(new File(PATH)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
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

	public static void END_SESSION() {
		SESSION.list_user_reminders = null;
		SESSION.user_cod = 0;
		SESSION.user_email = null;
		SESSION.user_name = null;
		SESSION.user_last_name = null;
		SESSION.user_photo = null;

	}

}