package db.functions;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	
	public void set_date_hour (Time time, Date date) throws ParseException, ClassNotFoundException, SQLException   { 
		Schedule sc = new Schedule();
		sc.setDate(date);
		sc.setHour(time);
		// cod_user, hour, date
		String query = "{CALL ADICIONAR_DATA_HORARIO(?,?,?)}";
		
		CallableStatement stmt = Database.get_connection().prepareCall(query);
		
		stmt.setInt(1, 3);//(int) SESSION.get_user_cod());
		stmt.setString(2, return_time(time.toString()));
		stmt.setDate(3, (java.sql.Date) sc.getDate());
		stmt.execute();	
		
		System.out.println("[INFO] success");
	}
	
	/* 
	 * the only way that i be able to insert the right time in the db
	 * , remove the : of the string  
	 */
	public String return_time (String time) throws ParseException { 
		  String t = time;
		  StringBuilder sb = new StringBuilder();
		  
		  for (int i = 0 ; i < t.length(); i ++) { 
			  
			  char a = t.charAt(i);
			  	
			  if(!String.valueOf(a).equals(":")) { 
				  sb.append(a);
			  }
		  }
		  return sb.toString();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
