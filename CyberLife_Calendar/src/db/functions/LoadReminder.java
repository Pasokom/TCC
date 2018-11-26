package db.functions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

import db.Database;
import db.pojo.ReminderDB;
import db.pojo.ReminderSchedule;

public class LoadReminder {

	private Connection connection;

	public LoadReminder() throws ClassNotFoundException, SQLException {
		this.connection = Database.get_connection();
	}

	public void get_user_reminders() throws SQLException, ClassNotFoundException {

		String sql = "SELECT * FROM VIEW_CARREGAR_LEMBRETES WHERE UCODIGO = 1 "; // + SESSION.get_user_cod();

		ResultSet result = Database.get_connection().createStatement().executeQuery(sql);

		ArrayList<ReminderDB> l_reminder = new ArrayList<ReminderDB>();

//		System.out.println(!result.first() ? "[WARNING] : no data found" : "[CONFIRMATION] : work");
//		if (!result.first())
//			return;

		try {

			ReminderDB reminder = new ReminderDB();
			
			while (result.next()) {
				
				final int current_id = result.getInt(1);
				
				
				/* starts with one because the index 0 is just to the where clause*/
				int r_id = result.getInt(1);
				String r_title = result.getString(2);
				boolean r_active = result.getBoolean(3);
				int r_recurrenceType  = result.getInt(4);
				int r_repetitionType = result.getInt(5);
				
				int h_id = result.getInt(6);
				Date h_dateBegin = result.getDate(5);
				Date h_finalDate = result.getDate(6);
				Time h_timeBegin = result.getTime(7);
				Time h_timeEnd = result.getTime(8);
				int h_minuteInterval = result.getInt(9);
				int h_recurrence = result.getInt(10);
				int h_weekDay = result.getInt(11);
				int h_repetitionAmount = result.getInt(12);
				
				reminder.setReminderId(r_id);
				if (reminder.getReminderId() == current_id) {

					reminder.setReminderId(h_id);	
					reminder.setActive(r_active);
					reminder.setTitle(r_title);
					reminder.setRecurrenceType(r_recurrenceType);
					reminder.setRepetitionType(r_repetitionType);
					
					ReminderSchedule rs = new ReminderSchedule();
					
					
					
					
					
					reminder.getlReminderSchedule().add(rs);
					
		
				}
				
				
				
				
				
				
				
				System.out.println(result.getString("TITULO"));
				
			}
		} finally {
			connection.close();
		}
	}
}















