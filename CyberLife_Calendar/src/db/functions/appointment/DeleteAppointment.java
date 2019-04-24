package db.functions.appointment;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.Database;
import db.pojo.eventPOJO.EventDB;
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

    public void delete(EventDB event, boolean all){

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
}