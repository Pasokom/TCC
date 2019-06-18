package db.functions.appointment;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

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

            delete.delete(reminder, true);
            create.insert_reminder(reminder);

        } catch (ClassNotFoundException | SQLException e) {

            e.printStackTrace();
        }
    }

    public void edit(EventDB event) {

        CreateEvent create = new CreateEvent();
        DeleteAppointment delete = new DeleteAppointment();

        try {

            delete.delete(event, true);
            create.insert_event(event);

        } catch (ClassNotFoundException | SQLException e) {

            e.printStackTrace();
        }
    }

    public void edit(TarefaDB task) {

        String sql = "UPDATE TAREFA SET NOME_TAREFA = ?, DURACAO_MINUTOS = ?, IMPORTANCIA = ?, DEPENDENCIA = ?, FK_NOME_MARCADOR = ?, FK_USUARIO = ? WHERE COD_TAREFA = ?";

        try {

            PreparedStatement statement = Database.get_connection().prepareStatement(sql);

            statement.setString(1, task.getNome_tarefa());
            statement.setInt(2, task.getDuracao());
            statement.setInt(3, task.getImportancia());

            if (task.getDependencia() > 0)
                statement.setInt(4, task.getDependencia());
            else
                statement.setNull(4, Types.INTEGER);
                
            statement.setString(5, task.getFk_nome_marcador());
            statement.setInt(6, task.getFk_usuario());
            statement.setInt(7, task.getCod_tarefa());

            statement.execute();

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