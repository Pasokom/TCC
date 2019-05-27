package db.functions.projectFeatures;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.Database;
import db.pojo.LabelDB;

public class LoadFeature {

    public ArrayList<LabelDB> loadLabels(int project) {

        ArrayList<LabelDB> labels = new ArrayList<>();

        String sql = "SELECT * FROM MARCADOR WHERE FK_PROJETO = ?";

        try {

            PreparedStatement statement = Database.get_connection().prepareStatement(sql);
            statement.setInt(1, project);

            ResultSet rSet = statement.executeQuery();

            while(rSet.next()) {

                LabelDB label = new LabelDB();
                label.setNome_marcador(rSet.getString("NOME_MARCADOR"));
                label.setFk_projeto(rSet.getInt("FK_PROJETO"));

                labels.add(label);
            }

        } catch (ClassNotFoundException | SQLException e) {
            
            e.printStackTrace();
        }

        return labels;
    }   
}