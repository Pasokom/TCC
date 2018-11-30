package db.functions.event;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import db.Database;
import db.pojo.eventPOJO.EventDB;
import statics.SESSION;

public class RetrieveEvents {

	public static ArrayList<EventDB> listEvents = new ArrayList<>();
	
	public void updateList() {
		
		listEvents.clear();
		
		try {
			Statement statement = Database.get_connection().createStatement();
		
			 ResultSet r7 = statement.executeQuery("SELECT * FROM EVENTO " + 
			 		"    LEFT JOIN E_REPETIR ON EVENTO.COD_EVENTO = E_REPETIR.FK_EVENTO" + 
			 		"    LEFT JOIN E_FIM_REPETICAO ON EVENTO.COD_EVENTO = E_FIM_REPETICAO.FK_EVENTO WHERE FK_USUARIO = " + SESSION.get_user_cod());
			
//			final String sql = " SELECT * FROM EVENTO  LEFT JOIN E_REPETIR ON EVENTO.COD_EVENTO = E_REPETIR.FK_EVENTO  LEFT JOIN E_FIM_REPETICAO ON EVENTO.COD_EVENTO = E_FIM_REPETICAO.FK_EVENTO WHERE  FK_USUARIO = 1";
//			ResultSet r7 = statement.executeQuery(sql);

			Calendar calendar = Calendar.getInstance();
			
			while(r7.next()) {
				
				EventDB event = new EventDB();
				event.setCod_evento(r7.getInt(1));
				event.setTitulo(r7.getString(2));
				event.setData_inicio(r7.getTimestamp(3, calendar));
				event.setData_fim(r7.getTimestamp(4, calendar));
				event.setLocal_evento(r7.getString(5));
				event.setDescricao(r7.getString(6));
				event.setTipo_repeticao(r7.getInt(7));
				event.setTipo_fim_repeticao(r7.getInt(8));
				
				listEvents.add(event);
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
