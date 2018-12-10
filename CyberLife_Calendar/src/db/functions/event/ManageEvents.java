package db.functions.event;

import java.sql.Connection;
import java.sql.SQLException;

import db.Database;

public class ManageEvents {
	/**
	 * tabela EVENTO
	 */
	private final String TITULO = "TITULO ='";
	private final String DATA_INICIO = "DATA_INICIO ='";
	private final String DATA_FIM = "DATA_FIM = '";
	private final String LOCAL_EVENTO = "LOCAL_EVENTO = '";
	private final String DESCRICAO = "DESCRICAO ='";
	private final String TIPO_REPETICAO = "TIPO_REPETICAO =";
	private final String TIPO_FIM_REPETICAO = "TIPO_FIM_REPETICAO = ";
	private final String DIA_TODO = "DIA_TODO = ";
	private final String ATIVO = "ATIVO = ";
	/**
	 * tabela E_FIM_REPETICAO
	 */
	private final String DIA_FIM = "DIA_FIM = ";
	private final String QTD_RECORRENCIAS = "QTD_RECORRENCIAS = ";

	/**
	 * tabela E_REPETIR
	 */
	private final String INTERVALO = "INTERVALO = ";
	private final String DIAS_SEMANA = "DIAS_SEMANA = '";

	private Connection connection;

	public ManageEvents() {
		try {
			this.connection = Database.get_connection();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("[ERROR] - Construtor- Classe ManageEvents");
		}
	}

	public static enum changeTheEvent {
		ACTIVE, TITLE, DATE_BEGIN, DATE_END, EVENT_LOCATION, DESCRIPTION, TYPE_OF_REPETITION, TYPE_OF_REPETITION_END,
		ALL_DAY
	}

	public static enum changeTheRepetition {
		END_DAY, AMOUNT_OF_RECURRENCES, INTERVAL, WEEK_DAYS
	}

	public void changeEvent(Object newValue, int eventID, changeTheEvent column) {
		String sql = "UPDATE EVENTO SET ";
		String sql_end = " WHERE COD_EVENTO= " + eventID + "";

		if (column == changeTheEvent.TITLE)
			sql = sql + this.TITULO + (String) newValue + "'" + sql_end;
		if (column == changeTheEvent.DATE_BEGIN)
			sql = sql + this.DATA_INICIO + (String) newValue + "'" + sql_end;
		if (column == changeTheEvent.DATE_END)
			sql = sql + this.DATA_FIM + (String) newValue + "'" + sql_end;
		if (column == changeTheEvent.EVENT_LOCATION)
			sql = sql + this.LOCAL_EVENTO + (String) newValue + "'" + sql_end;
		if (column == changeTheEvent.DESCRIPTION)
			sql = sql + this.DESCRICAO + (String) newValue + "'" + sql_end;
		if (column == changeTheEvent.TYPE_OF_REPETITION)
			sql = sql + this.TIPO_REPETICAO + (int) newValue + sql_end;
		if (column == changeTheEvent.TYPE_OF_REPETITION_END)
			sql = sql + this.TIPO_FIM_REPETICAO + (int) newValue + sql_end;
		if (column == changeTheEvent.ALL_DAY)
			sql = sql + this.DIA_TODO + (boolean) newValue + sql_end;
		if (column == changeTheEvent.ACTIVE)
			sql = sql + this.ATIVO + (boolean) newValue + sql_end;
		System.out.println(sql);
		execute(sql);
	}

	/**
	 * altera o campo escolhido da tabela OBS: o nome da tabela pode ser passado em
	 * lower case, dentro da função ela altera pra ficar igual ao banco de dados
	 * Passar valores que sejam do mesmo tipo ao que deseja mudar no banco de dados
	 * ex: não passar um inteiro pra um campo que lá no banco é varchar
	 */
	public void changeRepetition(Object newValue, int recordID, changeTheRepetition column, String tableName) {

		String sql = "UPDATE " + tableName.toUpperCase() + " SET ";
		String sql_end = null;
		if (tableName.toUpperCase().equals("E_FIM_REPETICAO")) {
			sql_end = " WHERE COD_FIM_REPETICAO = " + recordID;
			if (column == changeTheRepetition.END_DAY)
				sql = sql + this.DIA_FIM + (String) newValue + "" + sql_end;
			if (column == changeTheRepetition.AMOUNT_OF_RECURRENCES)
				sql = sql + this.QTD_RECORRENCIAS + (int) newValue + sql_end;
			System.out.println(sql);
			execute(sql);
			return;
		}
		sql_end = " WHERE COD_REPETICAO = " + recordID;
		if (column == changeTheRepetition.INTERVAL)
			sql = sql + this.INTERVALO + (int) newValue + sql_end;
		if (column == changeTheRepetition.WEEK_DAYS)
			sql = sql + this.DIAS_SEMANA + (String) newValue + "'" + sql_end;
		System.out.println(sql);
		execute(sql);
	}

	private void execute(String sql) {
		try {
			if (this.connection.isClosed())
				this.connection = Database.get_connection();
			this.connection.createStatement().execute(sql);
			this.connection.close();
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("[ERROR] - Função changeRemidner  - SQLException");
			e.printStackTrace();
		}
	}
}