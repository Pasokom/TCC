package db.functions.registrationAndLogin;

import java.sql.ResultSet;
import java.sql.SQLException;

import db.Database;
import db.functions.user.PictureSettings;
import javafx.scene.image.Image;
import listeners.IOFunctions;
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
			IOFunctions s = new IOFunctions();
			s.serializationTempDir(result.getInt(1), "stay_connected");
		}

		SESSION.start_session(result.getInt(1), result.getString(2), result.getString(3), result.getString(4));

		PictureSettings ps = new PictureSettings();
		SESSION.setImage(ps.getImageInFolder((int) SESSION.get_user_cod()));
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
			SESSION.setImage(ps.getImageInFolder((int) SESSION.get_user_cod()));
			return true;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}