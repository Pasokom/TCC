package db.functions.appointment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import org.json.*;

import db.Database;
import db.pojo.AppointmentDB;
import db.pojo.DayDB;
import db.pojo.HolidayDB;
import db.pojo.Moon;
import db.pojo.eventPOJO.EventDB;
import db.pojo.reminderPOJO.ReminderDB;

/**
 * LoadAppointment
 */
public class LoadAppointment {

    public ArrayList<AppointmentDB> loadFromDay(Calendar date) {

        ArrayList<AppointmentDB> appointments = new ArrayList<>();

        Moon moon = getMoonPhase(date);
        appointments.add(moon);

        ArrayList<HolidayDB> holidays = loadHolidays(date, "daily");

        for(HolidayDB holiday : holidays){
            appointments.add(holiday);
        }

        String sql = "{CALL CARREGAR_DIA(?)}";

        try {

            PreparedStatement statement = Database.get_connection().prepareStatement(sql);
            statement.setTimestamp(1, new Timestamp(date.getTimeInMillis()), Calendar.getInstance());

            ResultSet rSet = statement.executeQuery();

            while(rSet.next()){

                AppointmentDB appointment;

                switch (rSet.getInt("TIPO")) {
                    case 1:
                        appointment = createDailyReminder(rSet);
                        break;
                    case 2:
                        appointment = createDailyEvent(rSet);
                        break;
                    default:
                        appointment = createDailyReminder(rSet);
                        break;
                }

                appointments.add(appointment);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return appointments;
    }

    public ArrayList<DayDB> loadFromMonth(Calendar date) {

        ArrayList<DayDB> days = new ArrayList<>();

        for(int i = 0; i < 31; i++) {

            DayDB day = new DayDB();
            day.setDay(i + 1);
            day.setAppointments(new ArrayList<AppointmentDB>());
            days.add(day);
        }

        ArrayList<HolidayDB> holidays = loadHolidays(date, "monthly");

        for(HolidayDB holiday : holidays){
            days.get(holiday.getDia_mes() - 1).getAppointments().add(holiday);
        }

        String sql = "{CALL CARREGAR_MES(?)}";

        try {

            PreparedStatement statement = Database.get_connection().prepareStatement(sql);
            statement.setTimestamp(1, new Timestamp(date.getTimeInMillis()), Calendar.getInstance());

            ResultSet rSet = statement.executeQuery();

            while (rSet.next()){

                AppointmentDB appointment;

                switch (rSet.getInt("TIPO")) {
                    case 1:
                        appointment = createMonthlyReminder(rSet);
                        break;
                    case 2:
                        appointment = createMonthlyEvent(rSet);
                        break;
                    default:
                        appointment = createMonthlyReminder(rSet);
                        break;
                }

                days.get(rSet.getInt("DIA") - 1).getAppointments().add(appointment);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return days;
    }

    private EventDB createMonthlyEvent(ResultSet rSet) throws SQLException {

        EventDB event = new EventDB();

        Calendar timezone = Calendar.getInstance();

        event.setCod_evento(rSet.getInt("CODIGO"));
        event.setTitulo(rSet.getString("TITULO"));
        event.setData_inicio(rSet.getTimestamp("DATA_INICIO", timezone));
        event.setData_fim(rSet.getTimestamp("DATA_FIM", timezone));

        return event;
    }

    private ReminderDB createMonthlyReminder(ResultSet rSet) throws SQLException {

        ReminderDB reminder = new ReminderDB();

        Calendar timezone = Calendar.getInstance();

        reminder.setCod_lembrete(rSet.getInt("CODIGO"));
        reminder.setTitulo(rSet.getString("TITULO"));
        reminder.setHorario(rSet.getTimestamp("DATA_INICIO", timezone));

        return reminder;
    }

    private EventDB createDailyEvent(ResultSet rSet) throws SQLException {

        EventDB event = new EventDB();

        Calendar timezone = Calendar.getInstance();

        event.setCod_evento(rSet.getInt("CODIGO"));
        event.setTitulo(rSet.getString("TITULO"));
        event.setData_inicio(rSet.getTimestamp("DATA_INICIO", timezone));
        event.setData_fim(rSet.getTimestamp("DATA_FIM", timezone));
        event.setDia_todo(rSet.getBoolean("DIA_TODO"));

        return event;
    }

    private ReminderDB createDailyReminder(ResultSet rSet) throws SQLException {

        ReminderDB reminder = new ReminderDB();

        Calendar timezone = Calendar.getInstance();

        reminder.setCod_lembrete(rSet.getInt("CODIGO"));
        reminder.setTitulo(rSet.getString("TITULO"));
        reminder.setHorario(rSet.getTimestamp("DATA_INICIO", timezone));
        reminder.setDia_todo(rSet.getBoolean("DIA_TODO"));

        return reminder;
    }

    private ArrayList<HolidayDB> loadHolidays(Calendar date, String qtype){

        ArrayList<HolidayDB> holidays = new ArrayList<>();

        try {

            String protocol = "http://";
            String host = "localhost/cyberlife/calendar/API/query/holidays.php";
            String type = "?type=" + qtype;
            String day = "&date=" + date.getTime().getTime() / 1000;
            
            URL url = new URL(protocol+host+type+day);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            reader.close();
            con.disconnect();

            JSONObject jsonResponse = new JSONObject(buffer.toString());
            JSONArray data = jsonResponse.getJSONArray("data");
            
            for(int i = 0; i < data.length(); i++){

                HolidayDB holiday = new HolidayDB();
                holiday.setNome(data.getJSONObject(i).getString("name"));
                holiday.setDia_mes(data.getJSONObject(i).getInt("day"));

                holidays.add(holiday);
            }


        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        
        return holidays;
    }

    private Moon getMoonPhase(Calendar date){

        Moon moon = new Moon();

        try {

            String protocol = "http://";
            String host = "localhost/cyberlife/calendar/API/query/moons.php";
            String day = "?date=" + date.getTime().getTime() / 1000;
            
            URL url = new URL(protocol+host+day);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            reader.close();
            con.disconnect();

            JSONObject jsonResponse = new JSONObject(buffer.toString());

            moon.setPhase(jsonResponse.getInt("phase"));
            moon.setDescription(jsonResponse.getString("description"));

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return moon;
    }
}