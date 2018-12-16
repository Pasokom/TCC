package db.functions.reminderFUNCTIONS;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import db.Database;
import db.pojo.reminderPOJO.ReminderDB;

public class CreateReminder {

	private int reminder_cod;
	private Connection connection;

	public CreateReminder() throws ClassNotFoundException, SQLException {
		this.connection = Database.get_connection();
	}

	/**
	 * insere os horarios ( com time picker ou sem )
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void shedule_whithout_repetition(boolean interval_by_minute, String begin_in, String end_in, int recurrence,
			int interval, int amount_repetition) throws ClassNotFoundException, SQLException {

		String begin = interval_by_minute ? this.format(begin_in) : begin_in;
		String end = end_in == new String() ? null : format(end_in);

		String sql = "{CALL HORARIO_SEM_RECORRENCIA(?,?,?)}";

		CallableStatement stmt = Database.get_connection().prepareCall(sql);

		stmt.setString(1, begin);
		stmt.setString(2, end);
		stmt.setInt(3, recurrence == 0 ? null : recurrence);
		stmt.setInt(4, interval);
		stmt.setInt(5, amount_repetition == 0 ? null : amount_repetition);

		stmt.execute();
	}

	/**
	 * <H2>É importe que ao usar a função, de acordo com o tipo de horario passar os
	 * parametros corretos</H2>
	 * <p>
	 * 
	 * @example schedule_without_recurrence (date, new String(), false, 0); se o
	 *          tipo de horario for lembrete com time picker, deve ser usado assim
	 *          dessa forma dentro da função ele vai alterar os parametros que vão
	 *          para o banco
	 * @example schedule_whithout_recurrence (date_begin,date_end, false,
	 *          some_value); se o tipo de horario for por intervalo de tempo tem que
	 *          usar a função dessa forma
	 * 
	 * @example schedule_whithout_recurrence(date, new String, true, 0); como usar a
	 *          função para quando o horario de lembrete for dia todo
	 * 
	 * @param        boolean all_day
	 * @param        int interval
	 * @param String date_time_begin
	 * @param String date_time_end
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 *                                <p>
	 * 
	 */
	public void schedule_without_recurrence(String date_time_begin, String time_begin, String time_end, boolean all_day,
			int interval) throws ClassNotFoundException, SQLException {

		String datetime = all_day ? format(date_time_begin) : date_time_begin;

		String sql = "{CALL HORARIO_SEM_RECORRENCIA(?,?,?,?,?)}";

		CallableStatement stmt = connection.prepareCall(sql);
		stmt.setString(1, datetime);
		stmt.setString(2, time_begin);
		stmt.setString(3, time_end);
		stmt.setInt(4, interval);
		stmt.setInt(5, this.get_reminder_cod());
		stmt.execute();
	}

	/**
	 * @param date_begin
	 * @param date_end
	 * @param all_day
	 * @param interval
	 * @param recurrence
	 * @param week_day
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void schedule_recurrence_never_end(String date_begin, String time_begin, String time_end, boolean all_day,
			int interval, int recurrence, int week_day) throws SQLException, ClassNotFoundException {

		date_begin = all_day ? this.format(date_begin) : date_begin;

		String sql = "{CALL RECORRENCIA_SEM_FIM(?,?,?,?,?,?,?)}";

		CallableStatement stmt = connection.prepareCall(sql);

		stmt.setString(1, date_begin);
		stmt.setString(2, time_begin);
		stmt.setString(3, time_end);
		stmt.setInt(4, interval);
		stmt.setInt(5, recurrence);
		stmt.setInt(6, week_day);
		stmt.setInt(7, this.get_reminder_cod());

		stmt.execute();
	}

	public void schedule_recurrence_by_choosed_date(String date_begin, String date_end, String time_begin,
			String time_end, boolean is_all_day, int interval, int recurrence, int week_day)
			throws SQLException, ClassNotFoundException {

		date_begin = is_all_day ? this.format(date_begin) : date_begin;
		date_end = is_all_day ? this.format(date_end) : date_end;

		String sql = "{ CALL RECORRENCIA_DATA_DEFINIDA(?,?,?,?,?,?,?,?)}";

		CallableStatement stmt = this.connection.prepareCall(sql);

		stmt.setString(1, date_begin);
		stmt.setString(2, date_end);
		stmt.setString(3, time_begin);
		stmt.setString(4, time_end);
		stmt.setInt(5, interval);
		stmt.setInt(6, recurrence);
		stmt.setInt(7, week_day);
		stmt.setInt(8, this.get_reminder_cod());

		stmt.execute();
	}

	public void schedule_by_amount(String date_begin, String time_begin, String time_end, boolean is_all_day,
			int interval, int week_day, int amount) throws SQLException, ClassNotFoundException {

		date_begin = is_all_day ? this.format(date_begin) : date_begin;

		String sql = "{CALL RECORRENCIA_POR_QTDE_REPETICAO(?,?,?,?,?,?,?)}";

		CallableStatement stmt = this.connection.prepareCall(sql);

		stmt.setString(1, date_begin);
		stmt.setString(2, time_begin);
		stmt.setString(3, time_end);
		stmt.setInt(4, interval);
		stmt.setInt(5, week_day);
		stmt.setInt(6, amount);
		stmt.setInt(7, this.get_reminder_cod());

		stmt.execute();
		return;
	}

	public boolean insert_reminder(ReminderDB reminder) throws ClassNotFoundException, SQLException {

		String sql = "{CALL ADICIONAR_LEMBRETE(?,?,?,?,?,?,?)}";

		CallableStatement stmt = Database.get_connection().prepareCall(sql);

		stmt.setString(/* "LEMBRETE" */ 1, reminder.getTitle());
		stmt.setBoolean(/* "ATIVO" */2, reminder.isActive());
		stmt.setInt(/* "TIPO_REPETICAO" */ 3, reminder.getRepetitionType());
		stmt.setInt(/* "TIPO_RECORRENCIA" */4, reminder.getRecurrenceType());
		stmt.setInt(/* "RECORRENCIA" */5, reminder.getRecurrence());
		stmt.setInt(/* "USUARIO" */6, reminder.getUserID());
		stmt.setString(/* "CODIGO_LEMBRETE" */7, "@returned_value");

		stmt.registerOutParameter(7, Types.INTEGER);

		stmt.execute();

		this.set_reminder_cod(stmt.getInt(7));

		System.out.println("[INFO] codigo do lembrete : " + get_reminder_cod());

		return this.get_reminder_cod() == 0 ? false : true;
	}

	public int get_reminder_cod() {
		return reminder_cod;
	}

	private void set_reminder_cod(int reminder_cod) {
		this.reminder_cod = reminder_cod;
	}

	private String format(String str) {
		String t = str;
		StringBuilder sb = new StringBuilder();

		if (t.length() > 10) {
			for (int i = 0; i <= 10; i++) {
				sb.append(t.charAt(i));
			}
			return sb.toString();
		}
		return t;
	}

}
