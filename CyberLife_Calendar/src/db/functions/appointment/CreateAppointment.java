package db.functions.appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Calendar;

import db.Database;
import db.pojo.goalPOJO.GoalDB;
import db.pojo.projectPOJO.ProjectDB;
import db.pojo.projectPOJO.TarefaDB;
import statics.SESSION;

public class CreateAppointment {

    public void create(GoalDB goal) {

        String sql = "INSERT INTO META (TITULO, QTD_SEMANA, DURACAO_MINUTOS, PERIODO, FK_USUARIO) VALUES (?,?,?,?,?)";

        try {

            PreparedStatement statement = Database.get_connection().prepareStatement(sql);

            statement.setString(1, goal.getTitulo());
            statement.setInt(2, goal.getQtd_semana());
            statement.setInt(3, goal.getDuracao_minutos());
            statement.setInt(4, goal.getPeriodo());
            statement.setInt(5, goal.getFk_usuario());

            statement.execute();

        } catch (ClassNotFoundException | SQLException e) {

            e.printStackTrace();
        }
    }

    public void create(ProjectDB project) {

        String sql = "INSERT INTO PROJETO (TITULO, DATA_INICIO, DATA_ENTREGA, FK_USUARIO) VALUES (?, NOW(), ?, ?)";

        Calendar timezone = Calendar.getInstance();

        try {

            PreparedStatement statement = Database.get_connection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, project.getTitulo());
            statement.setTimestamp(2, project.getData_entrega(), timezone);
            statement.setInt(3, (int)SESSION.get_user_cod());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {

                for (TarefaDB task : project.getTarefas()) {

                    task.setFk_projeto(generatedKeys.getInt(1));
                    this.create(task);
                }
            }

        } catch (ClassNotFoundException | SQLException e) {

            e.printStackTrace();
        }
    }

    public void create(TarefaDB task) {

        String sql = "INSERT INTO TAREFA (NOME_TAREFA, DURACAO_MINUTOS, IMPORTANCIA, DEPENDENCIA, FK_NOME_MARCADOR, FK_PROJETO, FK_USUARIO) VALUES (?, ?, ?, ?, ?, ?, ?)";

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
            statement.setInt(6, task.getFk_projeto());
            statement.setInt(7, task.getFk_usuario());

            statement.execute();

        } catch (ClassNotFoundException | SQLException e) {

            e.printStackTrace();
        }
    }
}