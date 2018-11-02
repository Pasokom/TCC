package db.functions;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import db.Database;

public class Login {

	
	public boolean do_login ( String email, String password ) throws ClassNotFoundException, SQLException  { 
			
		String query = "{CALL LOGIN(?,?,?}";
		
		CallableStatement stmt = Database.get_connection().prepareCall(query);
		
		stmt.setString(1, email);
		stmt.setString(2, password);
		stmt.setString(3, "@result");	
			
		stmt.registerOutParameter(3, Types.BIT);
		
		stmt.executeUpdate();
		
		if ( ! stmt.getBoolean(3) ) return false;
		
		
		
		
		
		
		
		return false;
	}
	
		
	
	
	
	
	
	
	
	
	
	
}
