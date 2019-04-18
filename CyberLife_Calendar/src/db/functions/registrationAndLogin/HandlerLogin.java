package db.functions.registrationAndLogin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.Database;
import db.functions.user.PictureSettings;
import db.pojo.UserSession;
import statics.SESSION;

public class HandlerLogin {
	/**
	 * Return false had to be handled back on the login page
	 * 
	 * @param email
	 * @param password
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean login(String email, String password, boolean serialize) {

		String sql = "SELECT COD_USUARIO, EMAIL, NOME, SOBRENOME, SENHA FROM USUARIO WHERE EMAIL = ? AND SENHA = md5(?)";

		try {
			PreparedStatement statement = Database.get_connection().prepareStatement(sql);
			statement.setString(1, email);
			statement.setString(2, password);

			ResultSet result = statement.executeQuery();

			if (!result.first())
				return false;

			if (serialize) {

				UserSession session = new UserSession(email, result.getInt(1));
				session.serialize();
			}
			else {

				UserSession session = new UserSession(email);
				session.serialize();
			}

			SESSION.start_session(result.getInt(1), result.getString(2), result.getString(3), result.getString(4));
			PictureSettings ps = new PictureSettings();
			SESSION.setImage(ps.getUserImage((int) SESSION.get_user_cod()));

			return true;

		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("[ERROR] função login() - classe HandlerLogin");
			e.printStackTrace();
		}
		return false;
	}

	public boolean loginBySerialization(int userID) {

		final String sql = "SELECT * FROM USUARIO WHERE COD_USUARIO = " + userID + " ;";
		try {
			ResultSet result = Database.get_connection().createStatement().executeQuery(sql);
			System.out.println(!result.first() ? "[WARNING] : no data found" : "[CONFIRMATION] : work");
			if (!result.first())
				return false;

			SESSION.start_session(result.getInt(1), result.getString(2), result.getString(3), result.getString(4));
			PictureSettings ps = new PictureSettings();
			SESSION.setImage(ps.getUserImage((int) SESSION.get_user_cod()));
			return true;
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("[ERROR] function : loginBySerialization - classe HandlerLogin");
		}
		return false;
	}
}