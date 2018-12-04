package db.functions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import db.Database;
import db.pojo.eventPOJO.EventDB;
import db.pojo.eventPOJO.EventEndSchedule;
import db.pojo.eventPOJO.EventSchedule;
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
				event.setDia_todo(r7.getBoolean(5));
				event.setLocal_evento(r7.getString(6));
				event.setDescricao(r7.getString(7));
				event.setTipo_repeticao(r7.getInt(8));
				event.setTipo_fim_repeticao(r7.getInt(9));
				event.setFk_usuario(r7.getInt(10));
				
				EventSchedule schedule = new EventSchedule();
				schedule.setCod_repeticao(r7.getInt(11));
				schedule.setIntervalo(r7.getInt(12));
				schedule.setDias_semanaToArray(r7.getString(13));
				schedule.setFk_evento(r7.getInt(14));
				
				EventEndSchedule endSchedule = new EventEndSchedule();
				endSchedule.setCod_fim_repeticao(r7.getInt(15));
				endSchedule.setDia_fim(r7.getDate(16));
				endSchedule.setQtd_recorrencias(r7.getInt(17));
				endSchedule.setFk_evento(r7.getInt(18));

				event.setHorario_evento(schedule);
				event.setHorario_fim_evento(endSchedule);
				
				listEvents.add(event);
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
