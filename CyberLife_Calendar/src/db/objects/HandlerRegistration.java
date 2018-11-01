package db.objects;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import db.Database;

public class HandlerRegistration {
	
	private Statement cmd;
		
	/** 
	 * @author jefter66
	 * @param email
	 * @return
	 * @throws SQLException
	 * Do a query for checking if the email is already on the database
	 * if the return are true the email exist
	 * also, if the passed parameter is either null or empty,
	 *  the return will be true 
	 */
	public boolean email_exists (String email) throws SQLException { 
		
		String query = "{CALL EMAIL_EXISTE(?, ?)}";
		
		CallableStatement stmt = Database.getConnection().prepareCall(query);
		
		stmt.setString(1, email);
		stmt.setString(2, "@result");
		
		stmt.registerOutParameter(2, Types.BIT);
		
		stmt.executeUpdate();
		
		return stmt.getBoolean(2);
	}
	
		
	public boolean insert_user (String name, String last_name, String email, String password) throws SQLException { 
									/* 1 - email, 2 - name, 3 last name, 4 - password  */
		String query = "{CALL ADICIONAR_USUARIO(?,?,?,?)}";
		
		CallableStatement stmt = Database.getConnection().prepareCall(query);
		
		stmt.setString(1, email);
		stmt.setString(2, name);
		stmt.setString(3, last_name);
		stmt.setString(4, password);

		return stmt.execute();
	}

}