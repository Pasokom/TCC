package db.functions.projectFeatures;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.Database;

public class UpdateFeature {

    public void finishProject(int cod_project) {

        String sql = "UPDATE PROJETO SET FINALIZADO = 1 WHERE COD_PROJETO = ?";

        try {

            PreparedStatement statement = Database.get_connection().prepareStatement(sql);

            statement.setInt(1, cod_project);
            statement.execute();

        } catch (ClassNotFoundException | SQLException e) {

            e.printStackTrace();
        }
    }
}