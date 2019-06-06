package db.functions.user;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.Database;
import db.pojo.NotificationDB;

public class ManageNotifications {

    public void accept(NotificationDB notification) {

        String sql = "UPDATE USUARIO_PROJETO SET ACEITO = 1 WHERE FK_USUARIO = ? AND FK_PROJETO = ?";

        try {

            PreparedStatement statement = Database.get_connection().prepareStatement(sql);
            statement.setInt(1, notification.getUser_cod());
            statement.setInt(2, notification.getProject_cod());

            statement.execute();

        } catch (ClassNotFoundException | SQLException e) {
            
            e.printStackTrace();
        }
    }

    public void dismiss(NotificationDB notification) {

        String sql = "DELETE FROM USUARIO_PROJETO WHERE FK_USUARIO = ? AND FK_PROJETO = ?";

        try {

            PreparedStatement statement = Database.get_connection().prepareStatement(sql);
            statement.setInt(1, notification.getUser_cod());
            statement.setInt(2, notification.getProject_cod());

            statement.execute();

        } catch (ClassNotFoundException | SQLException e) {
            
            e.printStackTrace();
        }
    }       
}