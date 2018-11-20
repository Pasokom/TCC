package db.functions;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import db.Database;
import db.pojo.ReminderBanco;
import db.pojo.ReminderDB;
import statics.Enums;

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
	public void shedule_repetition(boolean interval_by_minute, String begin_in, String end_in, int recurrence,
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
	 * <h1>É importe que ao usar a função, de acordo com o tipo de horario passar os
	 * parametros corretos</h1>
	 * <p>
	 * 
	 * @example schedule_without_recurrence (date, new String(), false, 0); se o
	 *          tipo de horario for lembrete com time picker, deve ser usado assim
	 *          dessa forma dentro da função ele vai alterar os parametros que vão
	 *          para o banco
	 * 
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
	 *
	 * 
	 * @param date_begin
	 * @param date_end
	 * @param all_day
	 * @param interval
	 * @param recurrence
	 * @param week_day
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void schedule_recurrence_never_end(String date_begin, String time_begin,String time_end, boolean all_day, int interval,
			int recurrence, int week_day) throws SQLException, ClassNotFoundException {

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

	public void schedule_recurrence_by_choosed_date (String date_begin, String date_end, String time_begin, String time_end, boolean is_all_day, int interval, int recurrence, int week_day) throws SQLException, ClassNotFoundException { 

		 date_begin  = is_all_day ? this.format(date_begin) : date_begin;
		 date_end = is_all_day ? this.format(date_end) : date_end;

		 String sql = "{ CALL RECORRENCIA_DATA_DEFINIDA(?,?,?,?,?,?,?,?,)}";

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
	public void schedule_by_amount(String date_begin,String time_begin, String time_end, boolean is_all_day, int interval, int recurrence,int week_day, int amount) throws SQLException, ClassNotFoundException{ 

		date_begin = is_all_day ? this.format(date_begin) : date_begin;


		String sql = "{CALL RECORRENCIA_QTD_REPETICAO(?,?,?,?,?,?,?,?)}";

		CallableStatement stmt  = this.connection.prepareCall(sql);

		stmt.setString(1, date_begin);
		stmt.setString(2, time_begin);
		stmt.setString(3, time_end);
		stmt.setInt(4, interval);
		stmt.setInt(5, recurrence);
		stmt.setInt(6, amount);
		stmt.setInt(7, this.get_reminder_cod());

		stmt.execute();
		return;
	}


	@SuppressWarnings("resource")
	public void schedule_recurrence(String date_begin, String date_end, String time_begin, String time_end,
			boolean all_day, int interval, int recurrence, int week_day, int amount_repetition)
			throws SQLException, ClassNotFoundException {

		String date_time_begin = all_day ? this.format(date_begin) : date_begin;
		String date_time_end = date_end.equals(new String()) ? null : all_day ? this.format(date_end) : date_end;

		String sql = null;
		PreparedStatement stmt;

		boolean is_never_end = date_end.equals(new String());
		boolean is_by_interval = time_begin.equals(new String()) || time_end.equals(new String());

		boolean is_by_amount_of_repetition = amount_repetition > 0;

		boolean have_week_days_selected = week_day <= 7;

		if (is_by_amount_of_repetition) {

			sql = "INSERT INTO HORARIO_LEMBRETE(DATA_LEMBRETE,HORARIO_INICIO, HORARIO_FIM,HL_INTERVALO_MINUTOS,HL_RECORRENCIA,HL_SEMANA_DIA, HL_QTD_REPETE ,FK_LEMBRETE) VALUES (?,?,?,?,?,?,?,?)";
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, date_time_begin);
			stmt.setString(2, is_by_interval ? time_begin : null);
			stmt.setString(3, is_by_interval ? time_end : null);
			stmt.setInt(4, interval);
			stmt.setInt(5, recurrence);
			stmt.setInt(6, have_week_days_selected ? week_day : null);
			stmt.setInt(7, amount_repetition);
			stmt.setInt(8, this.get_reminder_cod());
			stmt.execute();
			return;
		}
		if (is_never_end) {
			sql = "INSERT INTO HORARIO_LEMBRETE(DATA_LEMBRETE,HORARIO_INICIO, HORARIO_FIM,HL_INTERVALO_MINUTOS,HL_RECORRENCIA, HL_SEMANA_DIA, FK_LEMBRETE) VALUES (?,?,?,?,?,?,?)";

			stmt = connection.prepareStatement(sql);
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, date_time_begin);
			stmt.setString(2, is_by_interval ? time_begin : null);
			stmt.setString(3, is_by_interval ? time_end : null);
			stmt.setInt(4, interval);
			stmt.setInt(5, recurrence);
			stmt.setInt(6, have_week_days_selected ? week_day : null);
			stmt.setInt(7, this.get_reminder_cod());
			stmt.execute();

		}
		if (have_week_days_selected) {
			sql = "INSERT INTO HORARIO_LEMBRETE(DATA_LEMBRETE, DATA_FINAL_LEMBRETE,HORARIO_INICIO, HORARIO_FIM,HL_INTERVALO_MINUTOS,HL_RECORRENCIA,HL_SEMANA_DIA,FK_LEMBRETE) VALUES (?,?,?,?,?,?,?,?)";
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, date_time_begin);
			stmt.setString(2, date_time_end);
			stmt.setString(3, is_by_interval ? time_begin : null);
			stmt.setString(4, is_by_interval ? time_end : null);
			stmt.setInt(5, interval);
			stmt.setInt(6, recurrence);
			stmt.setInt(7, week_day);
			stmt.setInt(8, this.get_reminder_cod());
			stmt.execute();
			return;
		}
		sql = "INSERT INTO HORARIO_LEMBRETE(DATA_LEMBRETE, DATA_FINAL_LEMBRETE,HORARIO_INICIO, HORARIO_FIM,HL_INTERVALO_MINUTOS,HL_RECORRENCIA,FK_LEMBRETE) VALUES (?,?,?,?,?,?,?)";
		stmt = connection.prepareStatement(sql);
		stmt.setString(1, date_time_begin);
		stmt.setString(2, date_time_end);
		stmt.setString(3, is_by_interval ? time_begin : null);
		stmt.setString(4, is_by_interval ? time_end : null);
		stmt.setInt(5, interval);
		stmt.setInt(7, this.get_reminder_cod());
		stmt.execute();
		return;

	}

	public void shedule_repetition(boolean interval_by_minute, String begin_in, String end_in, int recurrence,
			int week_day, int interval, int amount_repetition) throws ClassNotFoundException, SQLException {

		String begin = interval_by_minute ? this.format(begin_in) : begin_in;
		String end = end_in == new String() ? null : format(end_in);

		String sql = "";

		CallableStatement stmt = connection.prepareCall(sql);

		stmt.setString(1, begin);
		stmt.setString(2, end);
		stmt.setInt(3, recurrence == 0 ? null : recurrence);
		stmt.setInt(4, interval);
		stmt.setInt(5, week_day);
		stmt.setInt(6, amount_repetition == 0 ? null : amount_repetition);

		stmt.execute();
	}

	/**
	 * usado para inserir horarios para quando a opção "dia todo" estiver
	 * selecionado e também para a recorrencia com dias da semana escolhidos
	 * 
	 * @param begin_in
	 * @param end_in
	 * @param recurrence
	 * @param amount_repetition
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void all_day_shedule(String begin_in, String end_in, int recurrence, int week_day, int amount_repetition)
			throws ClassNotFoundException, SQLException {

		String begin = format(begin_in);
		String end = end_in == new String() ? null : format(end_in);

		String sql = "";

		CallableStatement stmt = connection.prepareCall(sql);

		stmt.setString(1, begin);
		stmt.setString(2, end);
		stmt.setInt(3, recurrence == 0 ? null : recurrence);
		stmt.setInt(4, week_day);
		stmt.setInt(5, amount_repetition == 0 ? null : amount_repetition);
		stmt.setInt(6, get_reminder_cod());

		stmt.execute();
	}

	public boolean insert_reminder(ReminderDB reminder) throws ClassNotFoundException, SQLException {

		String sql = "{CALL ADICIONAR_LEMBRETE(?,?,?,?,?,?)}";

		CallableStatement stmt = Database.get_connection().prepareCall(sql);

		stmt.setString(1, reminder.getReminder().getText());
		stmt.setBoolean(2, reminder.isAll_day());
		stmt.setString(3, reminder.getStatus());
		stmt.setString(4, reminder.getType_recurrence() == 0 ? null : String.valueOf(reminder.getType_recurrence()));
		stmt.setString(5, "@returned_value");
		stmt.setInt(6, 3);// (int) SESSION.get_user_cod());

		stmt.registerOutParameter(5, Types.INTEGER);

		stmt.execute();

		this.set_reminder_cod(stmt.getInt(5));

		System.out.println("[INFO] codigo do lembrete : " + get_reminder_cod());

		return this.get_reminder_cod() == 0 ? false : true;
	}

	public void insert_into_lembrete(ReminderBanco reminder) throws ClassNotFoundException, SQLException {

		/* insert sem procedures */
		String sql = "insert into LEMBRETE(LEMBRETE, LDIA_TODO, L_STATUS, LRECORRENCIA_MINUTOS, LRECORRENCIA_TIPO) values (?,?,?,?,?)";

		PreparedStatement stmt = Database.get_connection().prepareStatement(sql);
		stmt.setString(1, reminder.getTitulo());
		stmt.setBoolean(2, reminder.isDia_todo());
		stmt.setString(3, reminder.getStatus());
		stmt.setBoolean(4, reminder.isRecorrencia_minutos());
		stmt.setString(5, reminder.getRecorrencia_tipo());

		stmt.execute();
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
