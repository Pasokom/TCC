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
import db.pojo.eventPOJO.EventEndSchedule;
import db.pojo.eventPOJO.EventSchedule;
import db.pojo.goalPOJO.GoalDB;
import db.pojo.projectPOJO.ProjectDB;
import db.pojo.projectPOJO.TarefaDB;
import db.pojo.reminderPOJO.ReminderDB;
import db.pojo.reminderPOJO.ReminderEndSchedule;
import db.pojo.reminderPOJO.ReminderSchedule;
import statics.SESSION;

/**
 * LoadAppointment
 */
public class LoadAppointment {

    public ArrayList<AppointmentDB> loadFromDay(Calendar date) {

        ArrayList<AppointmentDB> appointments = new ArrayList<>();

        Moon moon = getMoonPhase(date);
        appointments.add(moon);

        ArrayList<HolidayDB> holidays = loadHolidays(date, "daily");

        for (HolidayDB holiday : holidays) {
            appointments.add(holiday);
        }

        String sql = "{CALL CARREGAR_DIA(?, ?)}";

        try {

            PreparedStatement statement = Database.get_connection().prepareStatement(sql);
            statement.setTimestamp(1, new Timestamp(date.getTimeInMillis()), Calendar.getInstance());
            statement.setInt(2, (int) SESSION.get_user_cod());

            ResultSet rSet = statement.executeQuery();

            while (rSet.next()) {

                AppointmentDB appointment;

                switch (rSet.getInt("TIPO")) {
                case 1:
                    appointment = createDailyReminder(rSet);
                    break;
                case 2:
                    appointment = createDailyEvent(rSet);
                    break;
                case 3:
                    appointment = createDailyTask(rSet);
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

        for (int i = 0; i < 31; i++) {

            DayDB day = new DayDB();
            day.setDay(i + 1);
            day.setAppointments(new ArrayList<AppointmentDB>());
            days.add(day);
        }

        ArrayList<HolidayDB> holidays = loadHolidays(date, "monthly");

        for (HolidayDB holiday : holidays) {
            days.get(holiday.getDia_mes() - 1).getAppointments().add(holiday);
        }

        String sql = "{CALL CARREGAR_MES(?, ?)}";

        try {

            PreparedStatement statement = Database.get_connection().prepareStatement(sql);
            statement.setTimestamp(1, new Timestamp(date.getTimeInMillis()), Calendar.getInstance());
            statement.setInt(2, (int) SESSION.get_user_cod());

            ResultSet rSet = statement.executeQuery();

            while (rSet.next()) {

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

    public ReminderDB loadReminder(int cod) {

        ReminderDB reminder = new ReminderDB();

        String sql = "{CALL CARREGAR_LEMBRETE(?)}";

        try {

            PreparedStatement statement = Database.get_connection().prepareStatement(sql);

            statement.setInt(1, cod);

            ResultSet rSet = statement.executeQuery();

            while (rSet.next()) {

                reminder = createReminder(rSet);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return reminder;
    }

    public ReminderDB loadReminder(int cod, ReminderDB recurrence) {

        ReminderDB reminder = new ReminderDB();

        String sql = "{CALL CARREGAR_LEMBRETE(?)}";

        try {

            PreparedStatement statement = Database.get_connection().prepareStatement(sql);

            statement.setInt(1, cod);

            ResultSet rSet = statement.executeQuery();

            while (rSet.next()) {

                reminder = createReminder(rSet, recurrence);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return reminder;
    }

    public EventDB loadEvent(int cod, EventDB recurrence) {

        EventDB event = new EventDB();

        String sql = "{CALL CARREGAR_EVENTO(?)}";

        try {

            PreparedStatement statement = Database.get_connection().prepareStatement(sql);

            statement.setInt(1, cod);

            ResultSet rSet = statement.executeQuery();

            while (rSet.next()) {

                event = createEvent(rSet, recurrence);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return event;
    }

    public TarefaDB loadTask(int cod) {

        TarefaDB task = new TarefaDB();

        String sql = "SELECT * FROM TAREFA WHERE COD_TAREFA = ?";

        try {

            PreparedStatement statement = Database.get_connection().prepareStatement(sql);
            statement.setInt(1, cod);

            ResultSet rSet = statement.executeQuery();

            if(rSet.next()) {

                task.setCod_tarefa(rSet.getInt("COD_TAREFA"));
                task.setNome_tarefa(rSet.getString("NOME_TAREFA"));
                task.setDuracao(rSet.getInt("DURACAO_MINUTOS"));
                task.setImportancia(rSet.getInt("IMPORTANCIA"));
                task.setDependencia(rSet.getInt("DEPENDENCIA"));
                task.setConcluido(rSet.getBoolean("CONCLUIDO"));
                task.setFk_nome_marcador(rSet.getString("FK_NOME_MARCADOR"));
                task.setFk_projeto(rSet.getInt("FK_PROJETO"));
                task.setFk_usuario(rSet.getInt("FK_USUARIO"));
            }

        } catch (ClassNotFoundException | SQLException e) {

            e.printStackTrace();
        }

        return task;
    }

    public ArrayList<GoalDB> loadGoals() {

        ArrayList<GoalDB> goals = new ArrayList<GoalDB>();

        String sql = "SELECT * FROM META"
                + " LEFT JOIN META_SEMANA ON COD_META = FK_META WHERE INICIO_SEMANA <= NOW() AND FIM_SEMANA >= NOW() AND FK_USUARIO = ?";

        try {

            PreparedStatement statement = Database.get_connection().prepareStatement(sql);
            statement.setInt(1, (int) SESSION.get_user_cod());
            ResultSet rSet = statement.executeQuery();

            while (rSet.next()) {

                GoalDB goal = new GoalDB();
                goal.setCod_meta(rSet.getInt("COD_META"));
                goal.setTitulo(rSet.getString("TITULO"));
                goal.setQtd_semana(rSet.getInt("QTD_SEMANA"));
                goal.setDuracao_minutos(rSet.getInt("DURACAO_MINUTOS"));
                goal.setPeriodo(rSet.getInt("PERIODO"));
                goal.setFk_usuario(rSet.getInt("FK_USUARIO"));
                goal.setCod_semana(rSet.getInt("COD_SEMANA"));
                goal.setQtd_concluido(rSet.getInt("QTD_CONCLUIDO"));

                goals.add(goal);
            }

        } catch (ClassNotFoundException | SQLException e) {

            e.printStackTrace();
        }

        return goals;
    }

    public ArrayList<ProjectDB> loadProjects() {

        ArrayList<ProjectDB> projects = new ArrayList<>();

        String sql = "SELECT * FROM PROJETO WHERE FK_USUARIO = ?";

        try {

            PreparedStatement statement = Database.get_connection().prepareStatement(sql);

            statement.setInt(1, (int) SESSION.get_user_cod());

            ResultSet rSet = statement.executeQuery();

            while (rSet.next()) {

                ProjectDB project = new ProjectDB();
                project.setCod_projeto(rSet.getInt("COD_PROJETO"));
                project.setTitulo(rSet.getString("TITULO"));
                project.setData_inicio(rSet.getTimestamp("DATA_INICIO"));
                project.setData_entrega(rSet.getTimestamp("DATA_ENTREGA"));

                projects.add(project);
            }

        } catch (ClassNotFoundException | SQLException e) {

            e.printStackTrace();
        }

        return projects;
    }

    public ProjectDB loadProject(int cod) {

        String sql = "SELECT * FROM PROJETO WHERE COD_PROJETO = ?";

        ProjectDB project = new ProjectDB();

        try {

            PreparedStatement statement = Database.get_connection().prepareStatement(sql);

            statement.setInt(1, cod);

            ResultSet rSet = statement.executeQuery();

            if (rSet.next()) {

                Calendar timezone = Calendar.getInstance();

                project.setCod_projeto(rSet.getInt("COD_PROJETO"));
                project.setTitulo(rSet.getString("TITULO"));
                project.setData_inicio(rSet.getTimestamp("DATA_INICIO", timezone));
                project.setData_entrega(rSet.getTimestamp("DATA_ENTREGA", timezone));
            }

        } catch (ClassNotFoundException | SQLException e) {

            e.printStackTrace();
        }

        return project;
    }

    public TarefaDB loadCurrentTask(int cod_project) {

        TarefaDB tarefa = new TarefaDB();

        String sql = "{ CALL TAREFA_ATUAL(?, ?) }";

        try {

            PreparedStatement statement = Database.get_connection().prepareStatement(sql);
            statement.setInt(1, cod_project);
            statement.setInt(2, (int)SESSION.get_user_cod());

            ResultSet rSet = statement.executeQuery();

            if (rSet.next()) {

                tarefa.setCod_tarefa(rSet.getInt("COD_TAREFA"));
                tarefa.setNome_tarefa(rSet.getString("NOME_TAREFA"));
                tarefa.setFk_projeto(rSet.getInt("FK_PROJETO"));
            }

        } catch (ClassNotFoundException | SQLException e) {

            e.printStackTrace();
        }

        return tarefa;
    }

    public ArrayList<TarefaDB> loadAllTasks(int cod_project) {

        ArrayList<TarefaDB> tasks = new ArrayList<>();

        String sql = "SELECT * FROM TAREFA WHERE FK_PROJETO = ?";

        try {

            PreparedStatement statement = Database.get_connection().prepareStatement(sql);
            statement.setInt(1, cod_project);

            ResultSet rSet = statement.executeQuery();

            while (rSet.next()) {
                
                TarefaDB task = new TarefaDB();
                task.setCod_tarefa(rSet.getInt("COD_TAREFA"));
                task.setNome_tarefa(rSet.getString("NOME_TAREFA"));
                task.setDuracao(rSet.getInt("DURACAO_MINUTOS"));
                task.setImportancia(rSet.getInt("IMPORTANCIA"));
                task.setDependencia(rSet.getInt("DEPENDENCIA"));
                task.setConcluido(rSet.getBoolean("CONCLUIDO"));
                task.setFk_nome_marcador(rSet.getString("FK_NOME_MARCADOR"));
                task.setFk_projeto(rSet.getInt("FK_PROJETO"));

                tasks.add(task);
            }
            
        } catch (ClassNotFoundException | SQLException e) {

            e.printStackTrace();
        }

        return tasks;
    }

    public ArrayList<TarefaDB> loadTasksFiltered(int cod_project, String label) {

        ArrayList<TarefaDB> tasks = new ArrayList<>();

        String sql = "SELECT * FROM TAREFA WHERE FK_NOME_MARCADOR = ? AND FK_PROJETO = ?";

        try {

            PreparedStatement statement = Database.get_connection().prepareStatement(sql);
            statement.setString(1, label);
            statement.setInt(2, cod_project);

            ResultSet rSet = statement.executeQuery();

            while (rSet.next()) {
                
                TarefaDB task = new TarefaDB();
                task.setCod_tarefa(rSet.getInt("COD_TAREFA"));
                task.setNome_tarefa(rSet.getString("NOME_TAREFA"));
                task.setDuracao(rSet.getInt("DURACAO_MINUTOS"));
                task.setImportancia(rSet.getInt("IMPORTANCIA"));
                task.setDependencia(rSet.getInt("DEPENDENCIA"));
                task.setConcluido(rSet.getBoolean("CONCLUIDO"));
                task.setFk_nome_marcador(rSet.getString("FK_NOME_MARCADOR"));
                task.setFk_projeto(rSet.getInt("FK_PROJETO"));

                tasks.add(task);
            }
            
        } catch (ClassNotFoundException | SQLException e) {

            e.printStackTrace();
        }

        return tasks;
    }

    public ArrayList<String> loadNotifications() {

        ArrayList<String> appointments = new ArrayList<>();

        String sql = "{ CALL NOTIFICACAO(?) }";

        try {

            PreparedStatement statement = Database.get_connection().prepareStatement(sql);
            statement.setInt(1, (int)SESSION.get_user_cod());

            ResultSet rSet = statement.executeQuery();

            while (rSet.next()) {
                
                appointments.add(rSet.getString("TITULO"));
            }

        } catch (ClassNotFoundException | SQLException e) {

            e.printStackTrace();
        }

        return appointments;
    }

    private ReminderDB createReminder(ResultSet rSet) throws SQLException {

        ReminderDB reminder = new ReminderDB();

        Calendar timezone = Calendar.getInstance();

        reminder.setCod_lembrete(rSet.getInt("COD_LEMBRETE"));
        reminder.setTitulo(rSet.getString("TITULO"));
        reminder.setHorario(rSet.getTimestamp("HORARIO", timezone));
        reminder.setHorario_fim(rSet.getTimestamp("HORARIO_FIM", timezone));
        reminder.setIntervalo_minutos(rSet.getInt("INTERVALO_MINUTOS"));
        reminder.setDia_todo(rSet.getBoolean("DIA_TODO"));
        reminder.setTipo_repeticao(rSet.getInt("TIPO_REPETICAO"));
        reminder.setTipo_fim_repeticao(rSet.getInt("TIPO_FIM_REPETICAO"));
        reminder.setAtivo(rSet.getBoolean("ATIVO"));
        reminder.setFk_usuario(rSet.getInt("FK_USUARIO"));

        ReminderSchedule schedule = new ReminderSchedule();

        schedule.setCod_repeticao(rSet.getInt("COD_REPETICAO"));
        schedule.setIntervalo(rSet.getInt("INTERVALO"));
        schedule.setDias_semanaToArray(rSet.getString("DIAS_SEMANA"));
        schedule.setFk_lembrete(rSet.getInt("FK_LEMBRETE"));

        ReminderEndSchedule endSchedule = new ReminderEndSchedule();

        endSchedule.setCod_fim_repeticao(rSet.getInt("COD_FIM_REPETICAO"));
        endSchedule.setDia_fim(rSet.getDate("DIA_FIM", timezone));
        endSchedule.setQtd_recorrencia(rSet.getInt("QTD_RECORRENCIAS"));
        endSchedule.setFk_lembrete(rSet.getInt("FK_LEMBRETE"));

        reminder.setSchedule(schedule);
        reminder.setReminderEndSchedule(endSchedule);

        return reminder;
    }

    private ReminderDB createReminder(ResultSet rSet, ReminderDB recurrence) throws SQLException {

        ReminderDB reminder = new ReminderDB();

        Calendar timezone = Calendar.getInstance();

        reminder.setCod_lembrete(rSet.getInt("COD_LEMBRETE"));
        reminder.setTitulo(rSet.getString("TITULO"));
        reminder.setHorario(recurrence.getHorario());
        reminder.setHorario_fim(recurrence.getHorario_fim());
        reminder.setIntervalo_minutos(rSet.getInt("INTERVALO_MINUTOS"));
        reminder.setDia_todo(rSet.getBoolean("DIA_TODO"));
        reminder.setTipo_repeticao(rSet.getInt("TIPO_REPETICAO"));
        reminder.setTipo_fim_repeticao(rSet.getInt("TIPO_FIM_REPETICAO"));
        reminder.setAtivo(rSet.getBoolean("ATIVO"));
        reminder.setFk_usuario(rSet.getInt("FK_USUARIO"));
        reminder.setCod_recorrencia(recurrence.getCod_recorrencia());
        reminder.setConcluido(recurrence.isConcluido());

        ReminderSchedule schedule = new ReminderSchedule();

        schedule.setCod_repeticao(rSet.getInt("COD_REPETICAO"));
        schedule.setIntervalo(rSet.getInt("INTERVALO"));
        schedule.setDias_semanaToArray(rSet.getString("DIAS_SEMANA"));
        schedule.setFk_lembrete(rSet.getInt("FK_LEMBRETE"));

        ReminderEndSchedule endSchedule = new ReminderEndSchedule();

        endSchedule.setCod_fim_repeticao(rSet.getInt("COD_FIM_REPETICAO"));
        endSchedule.setDia_fim(rSet.getDate("DIA_FIM", timezone));
        endSchedule.setQtd_recorrencia(rSet.getInt("QTD_RECORRENCIAS"));
        endSchedule.setFk_lembrete(rSet.getInt("FK_LEMBRETE"));

        reminder.setSchedule(schedule);
        reminder.setReminderEndSchedule(endSchedule);

        return reminder;
    }

    private EventDB createEvent(ResultSet rSet, EventDB recurrence) throws SQLException {

        EventDB event = new EventDB();

        Calendar timezone = Calendar.getInstance();

        event.setCod_evento(rSet.getInt("COD_EVENTO"));
        event.setTitulo(rSet.getString("TITULO"));
        event.setData_inicio(recurrence.getData_inicio());
        event.setData_fim(recurrence.getData_fim());
        event.setDia_todo(rSet.getBoolean("DIA_TODO"));
        event.setLocal_evento(rSet.getString("LOCAL_EVENTO"));
        event.setDescricao(rSet.getString("DESCRICAO"));
        event.setTipo_repeticao(rSet.getInt("TIPO_REPETICAO"));
        event.setTipo_fim_repeticao(rSet.getInt("TIPO_FIM_REPETICAO"));
        event.setAtivo(rSet.getBoolean("ATIVO"));
        event.setFk_usuario(rSet.getInt("FK_USUARIO"));
        event.setCod_recorrencia(recurrence.getCod_recorrencia());

        EventSchedule schedule = new EventSchedule();

        schedule.setCod_repeticao(rSet.getInt("COD_REPETICAO"));
        schedule.setIntervalo(rSet.getInt("INTERVALO"));
        schedule.setDias_semanaToArray(rSet.getString("DIAS_SEMANA"));
        schedule.setFk_evento(rSet.getInt("FK_EVENTO"));

        EventEndSchedule endSchedule = new EventEndSchedule();

        endSchedule.setCod_fim_repeticao(rSet.getInt("COD_FIM_REPETICAO"));
        endSchedule.setDia_fim(rSet.getDate("DIA_FIM", timezone));
        endSchedule.setQtd_recorrencias(rSet.getInt("QTD_RECORRENCIAS"));
        endSchedule.setFk_evento(rSet.getInt("FK_EVENTO"));

        event.setHorario_evento(schedule);
        event.setHorario_fim_evento(endSchedule);

        return event;
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
        event.setCod_recorrencia(rSet.getInt("COD_RECORRENCIA"));
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
        reminder.setCod_recorrencia(rSet.getInt("COD_RECORRENCIA"));
        reminder.setTitulo(rSet.getString("TITULO"));
        reminder.setHorario(rSet.getTimestamp("DATA_INICIO", timezone));
        reminder.setHorario_fim(rSet.getTimestamp("DATA_FIM", timezone));
        reminder.setConcluido(rSet.getBoolean("CONCLUIDO"));
        reminder.setDia_todo(rSet.getBoolean("DIA_TODO"));

        return reminder;
    }

    private TarefaDB createDailyTask(ResultSet rSet) throws SQLException {

        TarefaDB task = new TarefaDB();

        Calendar timezone = Calendar.getInstance();

        task.setCod_tarefa(rSet.getInt("CODIGO"));
        task.setNome_tarefa(rSet.getString("TITULO"));
        task.setConcluido(rSet.getBoolean("CONCLUIDO"));
        task.setData_inicio(rSet.getTimestamp("DATA_INICIO", timezone));
        task.setData_fim(rSet.getTimestamp("DATA_FIM", timezone));

        return task;
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