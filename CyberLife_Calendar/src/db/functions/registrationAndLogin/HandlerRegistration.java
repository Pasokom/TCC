package db.functions.registrationAndLogin;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import db.Database;

public class HandlerRegistration {

	/**
	 * @author jefter66
	 * @param email
	 * @return
	 * @throws SQLException           Do a query for checking if the email is
	 *                                already on the database if the return are true
	 *                                the email exist ,also, if the passed parameter
	 *                                is either null or empty, the return will be
	 *                                true
	 * @throws ClassNotFoundException
	 */
	public boolean email_exists(String email) {
		try { 
		String query = "{CALL EMAIL_EXISTE(?, ?)}";

		CallableStatement stmt = Database.get_connection().prepareCall(query);
		
		stmt.setString(1, email);
		stmt.setString(2, "@result");
		stmt.registerOutParameter(2, Types.BIT);
		stmt.executeUpdate();
		return stmt.getBoolean(2);
		} catch (SQLException | ClassNotFoundException e) { 
			System.out.println("Exception  -- função  email_exists  - classe HandlerRegistration");
		}
		return true;
	}

	/**
	 * The last_name string had to passed by parameter, even if it is a empty
	 * string, this will be handled on the database
	 * 
	 * the returned value will be false if the query going fine
	 * 
	 * @param name
	 * @param last_name
	 * @param email
	 * @param password
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public boolean insert_user(String name, String last_name, String email, String password)
			throws SQLException, ClassNotFoundException {
		/* 1 - email, 2 - name, 3 last name, 4 - password */
		String query = "{CALL ADICIONAR_USUARIO(?,?,?,?)}";

		CallableStatement stmt = Database.get_connection().prepareCall(query);

		stmt.setString(1, email);
		stmt.setString(2, name);
		stmt.setString(3, last_name);
		stmt.setString(4, password);

		return stmt.execute();
	}
}
