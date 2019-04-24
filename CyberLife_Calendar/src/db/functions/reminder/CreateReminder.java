package db.functions.reminder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import db.Database;
import db.pojo.reminderPOJO.ReminderDB;
import db.pojo.reminderPOJO.ReminderEndSchedule;
import db.pojo.reminderPOJO.ReminderSchedule;

public class CreateReminder {

	public int insert_reminder(ReminderDB reminder) throws ClassNotFoundException, SQLException {

		String sql = "INSERT INTO LEMBRETE (TITULO, HORARIO, HORARIO_FIM, INTERVALO_MINUTOS, DIA_TODO, TIPO_REPETICAO, "
			+ "TIPO_FIM_REPETICAO, FK_USUARIO) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement statement = Database.get_connection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, reminder.getTitulo());
		statement.setTimestamp(2, reminder.getHorario(), Calendar.getInstance());
		statement.setTimestamp(3, reminder.getHorario_fim(), Calendar.getInstance());
		statement.setInt(4, reminder.getIntervalo_minutos());
		statement.setBoolean(5, reminder.isDia_todo());
		statement.setInt(6, reminder.getTipo_repeticao());
		statement.setInt(7, reminder.getTipo_fim_repeticao());
		statement.setInt(8, reminder.getFk_usuario());

		statement.executeUpdate();

		ResultSet generatedKeys = statement.getGeneratedKeys();

		if (generatedKeys.next()) {

			int codigo = generatedKeys.getInt(1);

			reminder.getSchedule().setFk_lembrete(codigo);
			reminder.getReminderEndSchedule().setFk_lembrete(codigo);

			insert_reminder_schedule(reminder.getSchedule());
			insert_reminder_end_schedule(reminder.getReminderEndSchedule());

			return codigo;
		}

		return 0;
	}

	public void insert_reminder_schedule(ReminderSchedule schedule) throws ClassNotFoundException, SQLException {

		String sql = "INSERT INTO LEMBRETE_REPETICAO (INTERVALO, DIAS_SEMANA, FK_LEMBRETE) "
			+ "VALUES (?, ?, ?)";

		PreparedStatement statement = Database.get_connection().prepareStatement(sql);
		statement.setInt(1, schedule.getIntervalo());
		statement.setString(2, schedule.getDias_semanaToString());
		statement.setInt(3, schedule.getFk_lembrete());

		statement.execute();
	}

	public void insert_reminder_end_schedule(ReminderEndSchedule schedule) throws ClassNotFoundException, SQLException {

		String sql = "INSERT INTO LEMBRETE_FIM_REPETICAO (DIA_FIM, QTD_RECORRENCIAS, FK_LEMBRETE) "
			+ "VALUES (?, ?, ?)";

		PreparedStatement statement = Database.get_connection().prepareStatement(sql);
		statement.setDate(1, schedule.getDia_fim());
		statement.setInt(2, schedule.getQtd_recorrencia());
		statement.setInt(3, schedule.getFk_lembrete());

		statement.execute();
	}
}
