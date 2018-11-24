package db.function.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.Database;

public class HandlerRegistration {

	public boolean email_exists(String email) throws SQLException, ClassNotFoundException {
		
		String command = "select * from usuario where UEMAIL = '" + email + "'";
		
		Statement statement = Database.get_connection().createStatement();
		ResultSet result = statement.executeQuery(command);
		
		if(result.next()) 
			return true;
		
		return false;
	}
	
	public boolean insert_user(String nome, String sobrenome, String email, String senha) throws SQLException, ClassNotFoundException {
		
		String command = "insert into usuario(UEMAIL, UNOME, USOBRENOME, USENHA) values (?,?,?,?)";
		
		PreparedStatement statement = Database.get_connection().prepareStatement(command);
		statement.setString(1, email);
		statement.setString(2, nome);
		statement.setString(3, sobrenome);
		statement.setString(4, senha);
		
		return statement.execute();
	}
}
