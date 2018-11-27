package db.function.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.Database;
import db.pojo.ReminderDB;
import statics.SESSION;

public class CreateReminder {

	private int reminder_cod;
	private Connection connection;
	public CreateReminder() throws ClassNotFoundException, SQLException {
		this.connection = Database.get_connection();
	}
	
	public void insert_reminder(ReminderDB reminder) throws ClassNotFoundException, SQLException {

		String sql = "insert into LEMBRETE (LEMBRETE, LDIA_TODO, L_STATUS, LRECORRENCIA_TIPO, FK_USUARIO) "
				+ "values(?,?,?,?,?)";
		
		PreparedStatement statement = Database.get_connection().prepareStatement(sql);
		statement.setString(1, reminder.getReminder().getText());
		statement.setBoolean(2, reminder.isAll_day());
		statement.setString(3, reminder.getStatus());
		statement.setInt(4, reminder.getType_recurrence());
		statement.setInt(5, (int)SESSION.get_user_cod());
		
	}
}
