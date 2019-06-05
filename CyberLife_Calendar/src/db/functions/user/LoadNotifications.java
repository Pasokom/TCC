package db.functions.user;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.Database;
import db.pojo.NotificationDB;
import statics.SESSION;

public class LoadNotifications {

    public ArrayList<NotificationDB> load() {

        ArrayList<NotificationDB> notifications = new ArrayList<>();

        String sql = "{ CALL CARREGAR_NOTIFICACAO(?) }";

        try {

            PreparedStatement statement = Database.get_connection().prepareStatement(sql);
            statement.setInt(1, (int)SESSION.get_user_cod());

            ResultSet rSet = statement.executeQuery();

            while (rSet.next()) {
                
                NotificationDB notification = new NotificationDB();
                notification.setTitle(rSet.getString("MENSAGEM"));
                notification.setProject_cod(rSet.getInt("FK_PROJETO"));
                notification.setUser_cod(rSet.getInt("FK_USUARIO"));
            
                notifications.add(notification);
            }

        } catch (ClassNotFoundException | SQLException e) {

            e.printStackTrace();
        }

        return notifications;
    }     
}