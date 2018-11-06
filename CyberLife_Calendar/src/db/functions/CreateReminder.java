package db.functions;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.util.ArrayList;

import db.Database;
import db.pojo.Schedule;

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
	
	public void set_reminder_hour_date (Time time, Date date) throws ParseException, ClassNotFoundException, SQLException   { 
		Schedule sc = new Schedule();
		sc.setDate(date);
		sc.setHour(time);
		
		System.out.println(sc.getDate());
//		System.out.println(sc.getHour());
		
		// cod_user, hour, date
		String query = "{CALL ADICIONAR_DATA_HORARIO(?,?,?)}";
		
		CallableStatement stmt = Database.get_connection().prepareCall(query);
		
		stmt.setInt(1, 3);//(int) SESSION.get_user_cod());
		stmt.setTime(2, sc.getHour());
		stmt.setDate(3, (java.sql.Date) sc.getDate());
	
		
		
		stmt.execute();	
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
