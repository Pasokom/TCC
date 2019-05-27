package db.functions.projectFeatures;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.Database;
import db.pojo.LabelDB;

public class CreateFeature {

    public void create(LabelDB label) {

        String sql = "INSERT INTO MARCADOR (NOME_MARCADOR, FK_PROJETO) VALUES (?, ?)";

        try {

            PreparedStatement statement = Database.get_connection().prepareStatement(sql);

            statement.setString(1, label.getNome_marcador());
            statement.setInt(2, label.getFk_projeto());

            statement.execute();

        } catch (ClassNotFoundException | SQLException e) {
            
            e.printStackTrace();
        }
    }    
}