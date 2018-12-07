package db.functions.event;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.mysql.cj.xdevapi.Statement;

import db.Database;
import db.pojo.eventPOJO.EventDB;
import db.pojo.eventPOJO.EventEndSchedule;
import db.pojo.eventPOJO.EventSchedule;
import statics.SESSION;

public class RetrieveEvents {

	public static ArrayList<EventDB> listEvents = new ArrayList<>();
	
	public void updateList(Calendar date) {

		listEvents.clear();
		
		try {
			CallableStatement statement = Database.get_connection().prepareCall("call spRepete(?,?)");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

			Calendar limit;

			if(date == null){
				limit = Calendar.getInstance();
				limit.add(Calendar.DATE, limit.getActualMaximum(Calendar.DATE) - limit.get(Calendar.DATE));
			}
			else
				limit = date;
				limit.add(Calendar.DATE, limit.getActualMaximum(Calendar.DATE) - limit.get(Calendar.DATE));

			statement.setString(1, formatter.format(limit.getTime()));
			statement.setInt(2, (int)SESSION.get_user_cod());
			
			ResultSet r7 = statement.executeQuery();
			
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
				schedule.setFk_evento(r7.getInt(1));
				
				EventEndSchedule endSchedule = new EventEndSchedule();
				endSchedule.setCod_fim_repeticao(r7.getInt(14));
				endSchedule.setDia_fim(r7.getDate(15));
				endSchedule.setQtd_recorrencias(r7.getInt(16));
				endSchedule.setFk_evento(r7.getInt(1));

				event.setHorario_evento(schedule);
				event.setHorario_fim_evento(endSchedule);
				
				listEvents.add(event);
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<EventDB> getNowEvents(){

		ArrayList<EventDB> events = new ArrayList<>();

		try {
			CallableStatement statement = Database.get_connection().prepareCall("call spNowEvents(?)");
			statement.setInt(1,(int)SESSION.get_user_cod());

			Calendar calendar = Calendar.getInstance();			

			ResultSet r7 = statement.executeQuery();

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
				schedule.setFk_evento(r7.getInt(1));
				
				EventEndSchedule endSchedule = new EventEndSchedule();
				endSchedule.setCod_fim_repeticao(r7.getInt(14));
				endSchedule.setDia_fim(r7.getDate(15));
				endSchedule.setQtd_recorrencias(r7.getInt(16));
				endSchedule.setFk_evento(r7.getInt(1));

				event.setHorario_evento(schedule);
				event.setHorario_fim_evento(endSchedule);
				
				events.add(event);
			}
			

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return events;
	}
}
