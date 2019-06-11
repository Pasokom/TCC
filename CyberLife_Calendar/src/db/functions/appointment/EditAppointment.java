package db.functions.appointment;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.Database;
import db.functions.event.CreateEvent;
import db.functions.reminder.CreateReminder;
import db.pojo.eventPOJO.EventDB;
import db.pojo.projectPOJO.TarefaDB;
import db.pojo.reminderPOJO.ReminderDB;

/**
 * EditAppointment
 */
public class EditAppointment {

    public void edit(ReminderDB reminder) {

        CreateReminder create = new CreateReminder();
        DeleteAppointment delete = new DeleteAppointment();

        try {

            delete.delete(reminder, false);
            create.insert_reminder(reminder);

        } catch (ClassNotFoundException | SQLException e) {

            e.printStackTrace();
        }
    }

    public void edit(EventDB event) {

        CreateEvent create = new CreateEvent();
        DeleteAppointment delete = new DeleteAppointment();

        try {

            delete.delete(event, false);
            create.insert_event(event);

        } catch (ClassNotFoundException | SQLException e) {

            e.printStackTrace();
        }
    }

    public void markAsDone(ReminderDB reminder) {

        String sql = "{CALL LEMBRETE_CONCLUIDO(?)}";

        try {

            PreparedStatement statement = Database.get_connection().prepareStatement(sql);

            statement.setInt(1, reminder.getCod_recorrencia());

            statement.execute();

        } catch (ClassNotFoundException | SQLException e) {

            e.printStackTrace();
        }
    }

    public void markAsDone(TarefaDB task) {

        String sql = "{CALL TAREFA_CONCLUIDA(?)}";

        try {

            PreparedStatement statement = Database.get_connection().prepareStatement(sql);

            statement.setInt(1, task.getCod_tarefa());

            statement.execute();

        } catch (ClassNotFoundException | SQLException e) {

            e.printStackTrace();
        }
    }

    public void markAsUndone(TarefaDB task) {

        String sql = "{CALL TAREFA_NAO_CONCLUIDA(?)}";

        try {

            PreparedStatement statement = Database.get_connection().prepareStatement(sql);

            statement.setInt(1, task.getCod_tarefa());

            statement.execute();

        } catch (ClassNotFoundException | SQLException e) {

            e.printStackTrace();
        }
    }

    public void updateGoalWeek(int cod, int new_value) {

        String sql = "{CALL ATUALIZA_SEMANA_META(?,?)}";

        try {

            PreparedStatement statement = Database.get_connection().prepareStatement(sql);
            statement.setInt(1, cod);
            statement.setInt(2, new_value);

            statement.execute();

        } catch (ClassNotFoundException | SQLException e) {

            e.printStackTrace();
        }

    }
}