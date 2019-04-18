package db.functions.appointment;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.Database;
import db.pojo.goalPOJO.GoalDB;

/**
 * CreateAppointment
 */
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
}