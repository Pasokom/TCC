package db.functions.appointment;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.Database;
import db.pojo.eventPOJO.EventDB;
import db.pojo.goalPOJO.GoalDB;
import db.pojo.projectPOJO.TarefaDB;
import db.pojo.reminderPOJO.ReminderDB;

/**
 * DeleteAppointment
 */
public class DeleteAppointment {

    public void delete(ReminderDB reminder, boolean all) {

        String sql = "{CALL DELETAR_LEMBRETE(?, ?)}";

        try {

            PreparedStatement statement = Database.get_connection().prepareStatement(sql);

            statement.setInt(1, reminder.getCod_recorrencia());
            statement.setBoolean(2, all);

            statement.execute();

        } catch (ClassNotFoundException | SQLException e) {

            e.printStackTrace();
        }
    }

    public void delete(EventDB event, boolean all) {

        String sql = "{CALL DELETAR_EVENTO(?, ?)}";

        try {

            PreparedStatement statement = Database.get_connection().prepareStatement(sql);

            statement.setInt(1, event.getCod_recorrencia());
            statement.setBoolean(2, all);

            statement.execute();

        } catch (ClassNotFoundException | SQLException e) {

            e.printStackTrace();
        }
    }

    public void delete(TarefaDB task) {

        String sql = "DELETE FROM TAREFA WHERE COD_TAREFA = ?";

        try {

            PreparedStatement statement = Database.get_connection().prepareStatement(sql);

            statement.setInt(1, task.getCod_tarefa());
            statement.execute();

        } catch (ClassNotFoundException | SQLException e) {

            e.printStackTrace();
        }
    }

    public void delete(GoalDB goal) {

        String sql = "DELETE FROM META WHERE COD_META = ?";

        try {

            PreparedStatement statement = Database.get_connection().prepareStatement(sql);

            statement.setInt(1, goal.getCod_meta());
            statement.execute();

        } catch (ClassNotFoundException | SQLException e) {

            e.printStackTrace();
        }
    }
}