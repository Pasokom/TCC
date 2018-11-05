package db.functions;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import db.Database;
import db.pojo.Schedule;
import statics.SESSION;

public class CreateReminder {

	
	private static ArrayList<Schedule> list_schedules;
	
	public static ArrayList<Schedule> get_shedule() { 
		return list_schedules == null ? list_schedules = new ArrayList<Schedule>() : list_schedules;
	}
	
	public boolean create_reminder_with_repetition () { 
		
		return false;
	}
	public boolean create_reminder () throws ClassNotFoundException, SQLException, ParseException { 
		
		
		return false;
	}
	
	public void set_reminder_hour_date (String time, String date) throws ParseException, ClassNotFoundException, SQLException   { 
		Schedule sc = new Schedule();
		sc.setDate(date);
		sc.setHour(time);
						// cod_user, hour, date
		String query = "{CALL ADICIONAR_DATA_HORARIO(?,?,?}";
		
		CallableStatement stmt = Database.get_connection().prepareCall(query);
		
		stmt.setInt(1, (int) SESSION.get_user_cod());
		stmt.setTime(2, sc.getHour());
		stmt.setDate(3, sc.getDate());
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
