package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 
 * Classe para criação do banco de dados, aconselhavel que só instancie ela no
 * inicio da execução do sistema, como as funções são estaticas, podem usar elas
 * mesmo Até agora tem função de retorno de conexão
 * 
 * @author jefter66
 *
 */
public class Database {

	private final static String SERVER = "127.0.0.1";
	private final static String DB_NAME = "CYBER_LIFE"; // só para testes mesmo
	private final static String USER = "root";
	private final static String PASSWORD = "1234";
	private final static String CONNECTION_STRING = "jdbc:mysql://" + SERVER + "/" + DB_NAME + "?user=" + USER
			+ "&password=" + PASSWORD + "&useTimezone=true&serverTimezone=UTC";

	private static Connection connection;
	public Database() throws SQLException {
		try {
			/*
			 * procura a biblioteca do drive do mysql no projeto
			 */
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			 connection = DriverManager.getConnection(CONNECTION_STRING);
		} catch (Exception e) {
				
			System.out.println(e.getStackTrace());
		}
		
	}

	public static Connection getConnection() throws SQLException {
		return connection;
	}

//	public void queryLembrete(String nome, String descricao, LocalDate data, int repetir) throws SQLException {
//		Statement cmd = getConnection().createStatement();
//
//		/* insert */
//		cmd.execute("insert into lembrete(LNOME, LDESCRICAO, LDATE_LEMBRETE, LQTD_REPETE, FK_USUARIO) " + "value ('"
//				+ nome + "', '" + descricao + "', '" + data + "', " + repetir + "," + 1 + ");");
//	}
}


















