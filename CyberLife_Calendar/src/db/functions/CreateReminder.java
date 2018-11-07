package db.functions;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import db.Database;
import db.pojo.ReminderDB;
import statics.Enums;

public class CreateReminder {

	private int reminder_cod;

	public CreateReminder() {
	}

	/**
	 * insere os horarios para cada lembrete deve ser usado só depois de inserir o
	 * lembrete, para poder pegar a chave primaria do lembrete
	 */
	public void insert_reminder_schedule(boolean is_all_day, String begin_in, String end_in, int minutes_interval,
			int reminder_cod) throws ClassNotFoundException, SQLException {
	
		String begin = is_all_day ? format(begin_in) : begin_in;
		String end = is_all_day ? format(end_in) : end_in;

		String sql = "{CALL ADICIONAR_HORARIO_LEMBRETE(?,?,?,?)}";

		CallableStatement stmt = Database.get_connection().prepareCall(sql);

		stmt.setString(1, begin);
		stmt.setString(2, end);
		stmt.setInt(3, minutes_interval);
		stmt.setInt(4, reminder_cod);

		stmt.execute();
	}

	public boolean insert_reminder(ReminderDB reminder) throws ClassNotFoundException, SQLException {

		String sql = "{CALL ADICIONAR_LEMBRETE(?,?,?,?,?)}";

		CallableStatement stmt = Database.get_connection().prepareCall(sql);

		stmt.setString(1, reminder.getReminder().getText());
		stmt.setBoolean(2, reminder.isAll_day());

		reminder.setStatus(Enums.ReminderStatus.ENABLED.toString());

		stmt.setString(3, reminder.getStatus());
		stmt.setInt(4, 3);// (int) SESSION.get_user_cod());
		stmt.setString(5, "@returned_value");

		stmt.registerOutParameter(5, Types.INTEGER);

		stmt.execute();

		this.set_reminder_cod(stmt.getInt(5));

		System.out.println("[INFO] codigo do lembrete : " + get_reminder_cod());

		return this.get_reminder_cod() == 0 ? false : true;
	}

	public int get_reminder_cod() {
		return reminder_cod;
	}

	public void set_reminder_cod(int reminder_cod) {
		this.reminder_cod = reminder_cod;
	}

	public String format(String str) {
		String t = str;
		StringBuilder sb = new StringBuilder();

		if (t.length() > 10)
			for (int i = 0; i <= 10; i++) {
				sb.append(t.charAt(i));
			}
		return sb.toString();
	}

}
