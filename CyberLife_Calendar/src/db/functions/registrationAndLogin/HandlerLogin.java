package db.functions.registrationAndLogin;

import java.sql.ResultSet;
import java.sql.SQLException;

import db.Database;
import db.functions.user.PictureSettings;
import javafx.scene.image.Image;
import listeners.Serial;
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
	public boolean login(String email, String password, boolean serialize) throws ClassNotFoundException, SQLException {

		String sql = "SELECT UCODIGO, UEMAIL, UNOME, USOBRENOME, USENHA FROM USUARIO WHERE UEMAIL='" + email
				+ "' AND USENHA='" + password + "';";
		ResultSet result = Database.get_connection().createStatement().executeQuery(sql);

		System.out.println(!result.first() ? "[WARNING] : no data found" : "[CONFIRMATION] : work");
		if (!result.first())
			return false;
		if (serialize) {
			Serial s = new Serial();
			s.doSerialization(result.getInt(1), "stay_connected");
		}

		SESSION.start_session(result.getInt(1), result.getString(2), result.getString(3), result.getString(4));

		PictureSettings ps = new PictureSettings();
		Image image = ps.getImage((int) SESSION.get_user_cod());
		if (image != null)
			SESSION.setImage(image);
		return true;
	}

	public boolean loginBySerialization(int userID) {

		final String sql = "SELECT * FROM USUARIO WHERE UCODIGO = " + userID + " ;";
		try {
			ResultSet result = Database.get_connection().createStatement().executeQuery(sql);
			System.out.println(!result.first() ? "[WARNING] : no data found" : "[CONFIRMATION] : work");
			if (!result.first())
				return false;

			SESSION.start_session(result.getInt(1), result.getString(2), result.getString(3), result.getString(4));
			PictureSettings ps = new PictureSettings();
			Image image = ps.getImage((int) SESSION.get_user_cod());
			if (image != null)
				SESSION.setImage(image);
			return true;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}