package db.functions.reminderFUNCTIONS;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import db.Database;
import db.pojo.reminderPOJO.ReminderDB;
import db.pojo.reminderPOJO.ReminderSchedule;

public class LoadReminder {
	private Connection connection;

	public LoadReminder() throws ClassNotFoundException, SQLException {
		this.connection = Database.get_connection();
	}

	public static enum TypeOfQuery {
		ALL_REMINDERS, REMINDER_FOR_TODAY
	}

	/**
	 * <h3>
	 * 
	 * <pre>
	 * Return a list of ReminderDB contain the reminders that the user have on db
	 * </p
	 * <p>
	 * To bring those reminders from the db, is necessary for you to inform what
	 * kind </p
	 * <p>
	 * of reminder you want, for now, there are only two of them, choose wisely
	 * </p>
	 * <p>
	 * <br></br>
	 * <i> Above you will find examples of how to use the function </i>
	 * </h3>
	 * <h3>
	 * <p>
	 * If there are no reminders or no schedules for the reminders that was found 
	 * the return of the function going to be NULL, so, treat this when use the function
	 * </br>
	 * Lembrar de tratar isso no lugar em que usar a fun��o</h3>
	 * 
	 * <p>
	 * <h4><i> This function use the VIEW VIEW_LEMBRETES_DO_DIA stored on the DB You
	 * can find this view on the file VIEW_LEMBRETE.sql in the folder scripts
	 * </i></h4>
	 * </pre>
	 * 
	 * <p>
	 * <i> Use the class {@link Database} </i>
	 * <p>
	 * 
	 * @return ArrayList<ReminderDB> value
	 * 
	 * @example ArrayList<ReminderDB> listReminder = obj.getReminderForToday(id,
	 *          LoadReminder.TypeOfQuery.ALL_REMINDERS); Will return a list with all
	 *          the reminders that the user have stored
	 * @example ArrayList<ReminderDB> listReminder = obj.getReminderForToday(id,
	 *          LoadReminder.TypeOfQuery.REMINDERS_FOR_TODAY); Will return a list
	 *          whith all the reminders setted for today
	 * @author jefter66
	 * @throws SQLException
	 */
	public ArrayList<ReminderDB> getReminders(int userID, TypeOfQuery type) throws SQLException {

		/* lista que vai ser retornada (se tiver registros no banco) */

		ArrayList<ReminderDB> l_listReminders = new ArrayList<ReminderDB>();

		// define a query no banco de dados
		/** Usa a view VIEW_CARREGAR_LEMBRETES -- scripts/views.sql */
		String sql = new String();

		if (type == TypeOfQuery.ALL_REMINDERS)
			sql = "SELECT LCOD_LEMBRETE, GROUP_CONCAT(HL_CODIGO) CODIGOS FROM VIEW_CARREGAR_TODOS_LEMBRETES  WHERE UCODIGO = "
					+ userID + " GROUP BY LCOD_LEMBRETE; ";
		if (type == TypeOfQuery.REMINDER_FOR_TODAY)
			sql = "SELECT LCOD_LEMBRETE, GROUP_CONCAT(HL_CODIGO) CODIGOS_HORARIOS FROM VIEW_LEMBRETES_DO_DIA  WHERE UCODIGO = "
					+ userID + " GROUP BY LCOD_LEMBRETE; ";

		System.out.println(sql);
		ResultSet result = this.connection.createStatement().executeQuery(sql);

		final String final_queryReminder = "SELECT * FROM LEMBRETE WHERE LCOD_LEMBRETE = ";
		final String final_querySchedule = "SELECT  * FROM HORARIO_LEMBRETE WHERE HL_CODIGO = ";
		String sqlReminder = new String();
		String sqlSchedule = new String();

		/* sai da função se o resultSet estiver vazio */
		if (!result.first())
			return null;
		/*
		 * going to start this loop if the result set has more than one record if it
		 * have more only one record will entry a conditional at the block above the
		 * loop
		 */
		if (result.first())
			result.beforeFirst();
		int j = 0;
		while (result.next()) {

			// pega o ID do lembrete no loop atual
			int l_reminderId = result.getInt(1);

			sqlReminder = final_queryReminder + l_reminderId + ";";

			System.out.println(sqlReminder);

			ResultSet l_bringReminder = this.connection.createStatement().executeQuery(sqlReminder);

			if (l_bringReminder.first())
				l_bringReminder.beforeFirst();
			l_bringReminder.next();

			ReminderDB l_reminder = getReminder(l_bringReminder.getInt(1), l_bringReminder.getString(2),
					l_bringReminder.getBoolean(3), l_bringReminder.getInt(4), l_bringReminder.getInt(5));

			/*
			 * this is a int array with the ids of the shedules records going to be used for
			 * bring this records from the database to the application
			 * 
			 * the fuction schedulesIDs are in the bottom of this class is a private
			 * function used only in this class
			 */
			int[] l_scheduleIds = schedulesIDs(result.getString(2));
			/*
			 * this loop will happen according with the size of the array setted for the
			 * schedules ids
			 */
			for (int i = 0; i < l_scheduleIds.length; i++) {

				sqlSchedule = final_querySchedule + l_scheduleIds[i] + ";";

				// System.out.println(sqlSchedule);
				ResultSet rs = this.connection.createStatement().executeQuery(sqlSchedule);

				Calendar calendar = Calendar.getInstance();

				if (rs.isBeforeFirst()) /* this is fucking important */
					rs.next();
				ReminderSchedule rse = getSchedule(rs.getInt(1), rs.getTimestamp(2, calendar), rs.getTimestamp(3, calendar),
						rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9),
						rs.getBoolean(10), rs.getInt(11));
				/*
				 * the reminder that are in the scope of the WHILE loop ( the loop that happen
				 * on the first resultSet) are the current reminder of the list AND the record
				 * inside this loop are the current record of the SECOND list therefore, the
				 * current ReminderSchedule had to be ADDED on the list of remindersSchedules ON
				 * the current ReminderDB of the WHILE loop
				 */
				l_reminder.getlReminderSchedule().add(rse);
			}
			System.out.println("Interador (LOOP PRINCIPAL) : " + j++);
			System.out.println("Tamanho da lista (LISTA DE REMINDERDB ): " + l_listReminders.size());

			System.out.println(
					"Lista de horarios do lembrete (LISTA DE REMINDERSCHEDULE) : " + l_reminder.getReminderId());
			for (int k = 0; k < l_reminder.getlReminderSchedule().size(); k++) {
				System.out.println(
						"Interador lista de horarios do lembrete : " + l_reminder.getReminderId() + "\n LOOP :  " + k);
			}
			l_listReminders.add(l_reminder);
		}
		return l_listReminders;
	}

	/**
	 * i wrote those two because a wanna the code where i use them to be the most
	 * cleaner as possible
	 * 
	 */
	private ReminderSchedule getSchedule(int cod, Timestamp dateBegin, Timestamp dateEnd, String timeBegin,
			String timeEnd, int minutesInterval, int recurrence, int weekDay, int amountRepetition, boolean isActive,
			int fkReminder) {
		ReminderSchedule rs = new ReminderSchedule();
		rs.setDatetime_begin(dateBegin);
		rs.setDatetime_end(dateEnd);
		rs.setTimeBegin(timeBegin);
		rs.setTimeEnd(timeEnd);
		rs.setMinutesInterval(minutesInterval);
		rs.setRecurrence(recurrence);
		rs.setWeekDay(weekDay);
		rs.setAmount_of_repetition(amountRepetition);
		rs.setFk_reminder(fkReminder);
		rs.setActive(isActive);
		return rs;
	}

	private ReminderDB getReminder(int cod, String title, boolean active, int typeRecurrence, int typeRepetition) {
		ReminderDB reminder = new ReminderDB();
		reminder.setReminderId(cod);
		reminder.setTitle(title);
		reminder.setActive(active);
		reminder.setRecurrenceType(typeRecurrence);
		reminder.setRepetitionType(typeRepetition);
		return reminder;
	}

	/**
	 * Formata os valores vindos da consulta (os ids dos lembretes) fun��o para
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