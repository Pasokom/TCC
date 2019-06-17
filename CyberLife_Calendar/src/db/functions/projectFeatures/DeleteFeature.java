package db.functions.projectFeatures;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.Database;
import db.pojo.LabelDB;
import db.pojo.UserDB;

public class DeleteFeature {

    public void delete(LabelDB label) {

        String sql = "DELETE FROM MARCADOR WHERE NOME_MARCADOR = ? AND FK_PROJETO = ?";

        try {

            PreparedStatement statement = Database.get_connection().prepareStatement(sql);

            statement.setString(1, label.getNome_marcador());
            statement.setInt(2, label.getFk_projeto());
            statement.execute();

        } catch (ClassNotFoundException | SQLException e) {

            e.printStackTrace();
        }
    }
    
    public void delete(UserDB user, int cod_project) {

        String sql = "DELETE FROM USUARIO_PROJETO WHERE FK_USUARIO = ? AND FK_PROJETO = ?";

        try {

            PreparedStatement statement = Database.get_connection().prepareStatement(sql);

            statement.setInt(1, user.getCod_usuario());
            statement.setInt(2, cod_project);
            statement.execute();

        } catch (ClassNotFoundException | SQLException e) {

            e.printStackTrace();
        }
    }
}