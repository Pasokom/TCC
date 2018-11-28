package db.functions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.Database;
import db.pojo.ReminderDB;
import db.pojo.ReminderSchedule;

public class LoadReminder {

	private Connection connection;

	public LoadReminder() throws ClassNotFoundException, SQLException {
		this.connection = Database.get_connection();
	}

	public void get_user_reminders() throws SQLException, ClassNotFoundException {

		// String sql = "SELECT * FROM VIEW_CARREGAR_LEMBRETES WHERE UCODIGO = 1 "; // +
		// SESSION.get_user_cod();

		final String sql = "SELECT LCOD_LEMBRETE, GROUP_CONCAT(HL_CODIGO) CODIGOS FROM VIEW_CARREGAR_LEMBRETES  WHERE UCODIGO = 1  GROUP BY LCOD_LEMBRETE; ";

		ResultSet result = this.connection.createStatement().executeQuery(sql);

		ArrayList<ReminderDB> l_listReminders = new ArrayList<ReminderDB>();

		System.out.println(!result.first() ? "[WARNING] : no data found" : "[CONFIRMATION] : work");
		if (!result.first())
			return;

		while (result.next()) {

			System.out.println("dentro");

			int l_reminderId = result.getInt(1);

			final String l_queryReminder = "SELECT * FROM LEMBRETE WHERE LCOD_LEMBRETE = " + l_reminderId + " ;";

			// System.out.println(l_queryReminder);

			ResultSet l_bringReminder = this.connection.createStatement().executeQuery(l_queryReminder);

			ReminderDB l_reminder = new ReminderDB();

			if (!l_bringReminder.first())
				return;

			l_reminder.setReminderId(l_bringReminder.getInt(1));
			l_reminder.setTitle(l_bringReminder.getString(2));
			l_reminder.setActive(l_bringReminder.getBoolean(3));
			l_reminder.setRecurrenceType(l_bringReminder.getInt(4));
			l_reminder.setRepetitionType(l_bringReminder.getInt(5));

			int[] l_scheduleIds = schedulesIDs(result.getString(2));
			for (int i = 0; i < l_scheduleIds.length; i++) {

				final String l_querySchedule = "SELECT  * FROM HORARIO_LEMBRETE WHERE HL_CODIGO =" + l_scheduleIds[i]
						+ ";";

				// System.out.println(l_querySchedule);

				ResultSet l_bringSchedules = this.connection.createStatement().executeQuery(l_querySchedule);

				ReminderSchedule rs = new ReminderSchedule();

				while (l_bringSchedules.next()) {

					rs.setCod(l_bringSchedules.getInt(1));
					rs.setDatetime_begin(l_bringSchedules.getDate(2));
					rs.setDatetime_end(l_bringSchedules.getDate(3));
					rs.setTimeBegin(l_bringSchedules.getTime(4));
					rs.setTimeEnd(l_bringSchedules.getTime(5));
					rs.setMinutesInterval(l_bringSchedules.getInt(6));
					rs.setRecurrence(l_bringSchedules.getInt(7));
					rs.setWeekDay(l_bringSchedules.getInt(8));
					rs.setAmount_of_repetition(l_bringSchedules.getInt(9));
					rs.setFk_reminder(l_bringSchedules.getInt(10));

					l_reminder.getlReminderSchedule().add(rs);

					// System.out.println(rs.getCod());

				}
				l_listReminders.add(l_reminder);
			}
		}
		ArrayList<ReminderSchedule> x = l_listReminders.get(0).getlReminderSchedule();
		for (int i = 0; i < x.size(); i++) {
			System.out.println(x.get(i).getCod());
		}
	}
	/**
	 * Formata os valores vindos da consulta (os ids dos lembretes) função para
	 * recuperar cada um deles por vez do banco
	 */
	private int[] schedulesIDs(String reference) {

		String[] x = reference.split(",");
		int l_listReturn[] = new int[x.length];
		for (int i = 0; i < x.length; i++) {
			l_listReturn[i] = Integer.valueOf(String.valueOf(x[i]));
		}
		return l_listReturn;
	}
}
