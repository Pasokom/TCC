package db.functions.registrationAndLogin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
			} else {

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

	public boolean userImageExists(int idInvite) {

		try {

			String protocol = "http://";
			String host = "localhost/cyberlife/calendar/API/query/photo.php";
			String id = "?id=" + idInvite;

			URL url = new URL(protocol + host + id);

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");

			BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

			String line;
			StringBuffer buffer = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			reader.close();
			con.disconnect();

			String response = buffer.toString();

			if (response.equals("existe")) {
				return true;
			} else {
				return false;
			}

		} catch (IOException e) {
			e.printStackTrace();

			return false;
		}
	}

	public boolean checkPassword(String password) {

		String sql = "SELECT * FROM USUARIO WHERE COD_USUARIO = ? AND SENHA = md5(?)";

		try {

			PreparedStatement statement = Database.get_connection().prepareStatement(sql);

			statement.setInt(1, (int)SESSION.get_user_cod());
			statement.setString(2, password);

			ResultSet rSet = statement.executeQuery();

			if(rSet.next()) {

				return true;
			}

		} catch (ClassNotFoundException | SQLException e) {
			
			e.printStackTrace();
		}

		return false;
	}
}