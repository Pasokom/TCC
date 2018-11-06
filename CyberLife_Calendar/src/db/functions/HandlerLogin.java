package db.functions;

import java.sql.ResultSet;
import java.sql.SQLException;

import db.Database;
import statics.SESSION;

public class HandlerLogin {
	/**
	 * Return false had to be handled back on the login page
	 * @param email
	 * @param password
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean do_login(String email, String password) throws ClassNotFoundException, SQLException {

		String query = "SELECT FROM VIEW_INICIA_SESSAO WHERE UEMAIL ='" + email + "' AND USENHA ='" + password +"';" ;
			
		ResultSet result = Database.get_connection().createStatement().executeQuery(query);
		
		System.out.println(!result.first() ? "[WARNING] : no data found" : "[CONFIRMATION] : work");
		if (!result.first()) return false;
		
		SESSION.start_session(result.getInt(1), result.getString(2), result.getString(3),result.getString(4));
		return true	;
	}
}