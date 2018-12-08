package db.functions.event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import db.Database;
import db.pojo.eventPOJO.EventDB;
import db.pojo.eventPOJO.EventEndSchedule;
import db.pojo.eventPOJO.EventSchedule;
import statics.SESSION;

public class CreateEvent {
	public CreateEvent() throws ClassNotFoundException, SQLException {
	}

	public int insert_event(EventDB event) throws ClassNotFoundException, SQLException {

		String sql = "insert into EVENTO (TITULO, DATA_INICIO, DATA_FIM, DIA_TODO, LOCAL_EVENTO, DESCRICAO, TIPO_REPETICAO, TIPO_FIM_REPETICAO, FK_USUARIO) "
				+ "values(?,?,?,?,?,?,?,?,?)";

		PreparedStatement statement = Database.get_connection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, event.getTitulo());
		statement.setTimestamp(2, event.getData_inicio(), Calendar.getInstance());
		statement.setTimestamp(3, event.getData_fim(), Calendar.getInstance());
		statement.setBoolean(4, event.isDia_todo());
		statement.setString(5, event.getLocal_evento());
		statement.setString(6, event.getDescricao());
		statement.setInt(7, event.getTipo_repeticao());
		statement.setInt(8, event.getTipo_fim_repeticao());
		statement.setInt(9, (int) SESSION.get_user_cod());

		statement.executeUpdate();

		ResultSet generatedKeys = statement.getGeneratedKeys();
		if (generatedKeys.next())
			return generatedKeys.getInt(1);

		return 0;
	}

	public void insert_event_schedule(EventSchedule schedule) throws ClassNotFoundException, SQLException {

		String sql = "insert into E_REPETIR (INTERVALO, DIAS_SEMANA, FK_EVENTO)" + "values(?,?,?)";

		PreparedStatement statement = Database.get_connection().prepareStatement(sql);
		statement.setInt(1, schedule.getIntervalo());
		statement.setString(2, schedule.getDias_semanaToString());
		statement.setInt(3, schedule.getFk_evento());

		statement.execute();
	}

	public void insert_event_end_schedule(EventEndSchedule schedule) throws ClassNotFoundException, SQLException {

		String sql = "insert into E_FIM_REPETICAO(DIA_FIM, QTD_RECORRENCIAS, FK_EVENTO)" + "values(?,?,?)";

		PreparedStatement statement = Database.get_connection().prepareStatement(sql);
		statement.setDate(1, schedule.getDia_fim());
		statement.setInt(2, schedule.getQtd_recorrencias());
		statement.setInt(3, schedule.getFk_evento());

		statement.execute();
	}
}
