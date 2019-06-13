package db.functions.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

import db.Database;
import main.Main;
import statics.SESSION;

public class AccountSettings {

	private final String EMAIL = "UEMAIL='";
	private final String NOME = "UNOME='";
	private final String SOBRENOME = "USOBRENOME='";
	private final String SENHA = "USENHA='";
	// private final String IMAGE = "UFOTO=''";
	private final String ATIVO = "UATIVO=";

	private Connection connection;

	public AccountSettings() {
	}

	public static enum changeThe {
		EMAIL, NAME, LAST_NAME, PASSWORD, ACTIVE
	}

	public void changeProfile(Object newValue, int userID, changeThe whatChange) {
		String sql = "UPDATE USUARIO SET ";
		final String query_end = "' WHERE UCODIGO = " + userID + ";";
		if (whatChange == changeThe.EMAIL)
			sql = sql + this.EMAIL + (String) newValue.toString() + query_end;
		if (whatChange == changeThe.NAME)
			sql = sql + this.NOME + (String) newValue.toString() + query_end;
		if (whatChange == changeThe.LAST_NAME)
			sql = sql + this.SOBRENOME + (String) newValue.toString() + query_end;
		if (whatChange == changeThe.PASSWORD)
			sql = sql + this.SENHA + (String) newValue.toString() + query_end;
		if (whatChange == changeThe.ACTIVE)
			sql = sql + this.ATIVO + (String) newValue.toString() + query_end;
		System.out.println(sql);
		execute(sql);
	}

	private void execute(String sql) {
		try {
			this.connection = Database.get_connection();
			this.connection.createStatement().execute(sql);
			this.connection.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Muda a imagem no sistema de arquivos
	 * 
	 */
	public void changeImage() {
		/*
		 * salva a imagem na pasta bin
		 */
		PictureSettings ps = new PictureSettings();
		ps.chooseImage(Main.main_stage, 1); // (int) SESSION.get_user_cod());
		// SESSION.setImage(ps.getImageInFolder((int) SESSION.get_user_cod()));
	}

	public void removeImage() {

	}

	public Calendar startTask() {

		Calendar calendar = Calendar.getInstance();
		String sql = "SELECT HORARIO_TAREFA_INICIO FROM USUARIO_CONFIGURACOES WHERE FK_USUARIO = ?";

		try {

			PreparedStatement statement = Database.get_connection().prepareStatement(sql);

			statement.setInt(1, (int) SESSION.get_user_cod());

			ResultSet rSet = statement.executeQuery();

			if (rSet.next()) {

				Timestamp time = rSet.getTimestamp("HORARIO_TAREFA_INICIO", calendar);
				calendar.setTime(time);
			}

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return calendar;
	}

	public Calendar endTask() {

		Calendar calendar = Calendar.getInstance();
		String sql = "SELECT HORARIO_TAREFA_FIM FROM USUARIO_CONFIGURACOES WHERE FK_USUARIO = ?";

		try {

			PreparedStatement statement = Database.get_connection().prepareStatement(sql);

			statement.setInt(1, (int) SESSION.get_user_cod());

			ResultSet rSet = statement.executeQuery();

			if (rSet.next()) {

				Timestamp time = rSet.getTimestamp("HORARIO_TAREFA_FIM", calendar);
				calendar.setTime(time);
			}

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();
		}

		return calendar;
	}

	public void saveScheduleTask(Time t_begin, Time t_end) {

		String sql = "UPDATE USUARIO_CONFIGURACOES SET HORARIO_TAREFA_INICIO = ?, HORARIO_TAREFA_FIM = ? WHERE FK_USUARIO = ?";

		try {

			PreparedStatement statement = Database.get_connection().prepareStatement(sql);

			statement.setTime(1, t_begin, Calendar.getInstance());
			statement.setTime(2, t_end, Calendar.getInstance());
			statement.setInt(3, (int)SESSION.get_user_cod());

			statement.execute();

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();
		}
	}
}








