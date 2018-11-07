package db.functions;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Types;
import java.text.ParseException;
import java.util.ArrayList;

import db.Database;
import db.pojo.ReminderDB;
import db.pojo.Schedule;
import statics.Enums;
import statics.SESSION;

public class CreateReminder {

	
	private ArrayList<Integer> list_cod_schedule;
	private int reminder_cod = 0 ;
	public CreateReminder() {
		this.list_cod_schedule = new ArrayList<Integer>();
	}
	
	/**
	 * insere o codigo do lembrete e dos horarios na tabela HORARIO_LEMBRETE ( relaciona as tabelas) 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public boolean insert_reminder_and_date () throws ClassNotFoundException, SQLException, ParseException { 
		
		for ( int i = 0 ; i <  list_cod_schedule.size(); i ++) { 
			
			String sql = "{CALL ADICIONAR_LEMBRETE_HORARIO(?,?)}";
			
			CallableStatement stmt = Database.get_connection().prepareCall(sql);
			
			stmt.setInt(1, this.getReminder_cod());
			stmt.setInt(2, this.list_cod_schedule.get(i));
			
			boolean error = stmt.execute() == false;
			
			if ( error ) System.out.println("[WARNING] insert not working");
		}
		return true;
	}
		
	/** 
	 * Insere um novo horario no banco de dados e adicionar o codigo desse horario na lista de inteiros
	 * @param time
	 * @param date
	 * @throws ParseException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void set_date_hour (Time time, Date date) throws ParseException, ClassNotFoundException, SQLException   { 
		Schedule sc = new Schedule();
		sc.setDate(date);
		sc.setHour(time);
		// cod_user, hour, date
		String query = "{CALL ADICIONAR_DATA_HORARIO(?,?,?,?)}";
		
		CallableStatement stmt = Database.get_connection().prepareCall(query);
		
		stmt.setInt(1, 3);//(int) SESSION.get_user_cod());
		stmt.setString(2, return_time(time.toString()));
		stmt.setDate(3, (java.sql.Date) sc.getDate());
		stmt.setString(4, "@returned_value");
		
		stmt.registerOutParameter(4, Types.INTEGER);
		stmt.executeUpdate();	
		
		
		add_to_list(stmt.getInt(4));
		
		System.out.println("[INFO] success");
	}
	public boolean insert_reminder (ReminderDB reminder) throws ClassNotFoundException, SQLException { 
			
		String sql = "{CALL ADICIONAR_LEMBRETE(?,?,?,?,?)}";
		
		CallableStatement stmt = Database.get_connection().prepareCall(sql);
		
		stmt.setString(1, reminder.getReminder().getText());
		stmt.setBoolean(2, reminder.isAll_day());

		reminder.setStatus(Enums.ReminderStatus.ENABLED.toString());
		
		stmt.setString(3, reminder.getStatus());
		stmt.setInt(4, 3);// (int) SESSION.get_user_cod());
		stmt.setString(5, "@returned_value");
		
		stmt.registerOutParameter(5,Types.INTEGER);
		
		stmt.executeUpdate();
		
		this.setReminder_cod(stmt.getInt(5));
		
		return this.getReminder_cod() == 0 ? false : true ;
	}
	public int getReminder_cod() {
		return reminder_cod;
	}
	public void setReminder_cod(int reminder_cod) {
		this.reminder_cod = reminder_cod;
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
	public void add_to_list(int cod) { 
		this.list_cod_schedule.add(cod);
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
