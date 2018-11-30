package db.functions.reminder;

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
	 * Lembrar de tratar isso no lugar em que usar a função</h3>
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
	 */
	public ArrayList<ReminderDB> getReminderForToday(int userID, TypeOfQuery type)
			throws SQLException, ClassNotFoundException {

		/* lista que vai ser retornada (se tiver registros no banco) */
		ArrayList<ReminderDB> l_listReminders = new ArrayList<ReminderDB>();

		// define a query no banco de dados
		/** Usa a view VIEW_CARREGAR_LEMBRETES -- scripts/views.sql */
		String sql = null;

		if (type == TypeOfQuery.ALL_REMINDERS)
			sql = "SELECT LCOD_LEMBRETE, GROUP_CONCAT(HL_CODIGO) CODIGOS FROM VIEW_LEMBRETES_DO_DIA WHERE UCODIGO = "
					+ userID + " GROUP BY LCOD_LEMBRETE; ";
		if (type == TypeOfQuery.REMINDER_FOR_TODAY)
			sql = "SELECT LCOD_LEMBRETE, GROUP_CONCAT(HL_CODIGO) CODIGOS_HORARIOS FROM VIEW VIEW_CARREGAR_TODOS_LEMBRETES WHERE UCODIGO = "
					+ userID + " GROUP BY LCOD_LEMBRETE; ";

		ResultSet result = this.connection.createStatement().executeQuery(sql);

		// System.out.println(!result.first() ? "[WARNING] : no data found" :
		// "[CONFIRMATION] : work");

		/* sai da função se o resultSet estiver vazio */
		if (!result.first())
			return null;
		/* entra no loop para pegar os registros do banco */
		while (result.next()) {

			// pega o ID do lembrete no loop atual
			int l_reminderId = result.getInt(1);

			// query para trazer TODAS as informações do lembrete
			final String l_queryReminder = "SELECT * FROM LEMBRETE WHERE LCOD_LEMBRETE = " + l_reminderId + " ;";
			ResultSet l_bringReminder = this.connection.createStatement().executeQuery(l_queryReminder);

			ReminderDB l_reminder = new ReminderDB();

			/* se o resultSet que irá trazer 1 registro estiver vazio, sai da função */
			if (!l_bringReminder.first())
				return null;

			/**
			 * monta o POJO de lembrete
			 */
			l_reminder.setReminderId(l_bringReminder.getInt(1));
			l_reminder.setTitle(l_bringReminder.getString(2));
			l_reminder.setActive(l_bringReminder.getBoolean(3));
			l_reminder.setRecurrenceType(l_bringReminder.getInt(4));
			l_reminder.setRepetitionType(l_bringReminder.getInt(5));

			/***
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

				final String l_querySchedule = "SELECT  * FROM HORARIO_LEMBRETE WHERE HL_CODIGO =" + l_scheduleIds[i]
						+ ";";

				System.out.println(l_querySchedule);

				ResultSet l_bringSchedules = this.connection.createStatement().executeQuery(l_querySchedule);

				ReminderSchedule rs = new ReminderSchedule();

				/**
				 * build the POJO
				 */
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

				/*
				 * the reminder that are in the scope of the WHILE loop ( the loop that happen
				 * on the first resultSet) are the current reminder of the list AND the record
				 * inside this loop are the current record of the SECOND list therefore, the
				 * current ReminderSchedule had to be ADDED on the list of remindersSchedules ON
				 * the current ReminderDB of the WHILE loop
				 */
				l_reminder.getlReminderSchedule().add(rs);
			}
			/**
			 * at the final of the loop interation i added the ReminderDB in the list that
			 * are going to be returned by the function
			 */
			l_listReminders.add(l_reminder);
		}
		return l_listReminders;
		// TODO delete some day...
		// ArrayList<ReminderSchedule> x =
		// l_listReminders.get(0).getlReminderSchedule();
		// for (int i = 0; i < x.size(); i++) {
		// System.out.println(x.get(i).getCod());
		// }
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
