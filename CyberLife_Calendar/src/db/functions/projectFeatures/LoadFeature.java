package db.functions.projectFeatures;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.Database;
import db.pojo.LabelDB;
import db.pojo.UserDB;

public class LoadFeature {

    public ArrayList<LabelDB> loadLabels(int project) {

        ArrayList<LabelDB> labels = new ArrayList<>();

        String sql = "SELECT * FROM MARCADOR WHERE FK_PROJETO = ?";

        try {

            PreparedStatement statement = Database.get_connection().prepareStatement(sql);
            statement.setInt(1, project);

            ResultSet rSet = statement.executeQuery();

            while (rSet.next()) {

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

    public ArrayList<UserDB> loadMembers(int project) {

        ArrayList<UserDB> members = new ArrayList<>();

        String sql = "{ CALL CARREGA_MEMBROS(?) }";

        try {

            PreparedStatement statement = Database.get_connection().prepareStatement(sql);
            statement.setInt(1, project);

            ResultSet rSet = statement.executeQuery();

            while (rSet.next()) {
                
                UserDB member = new UserDB();
                member.setCod_usuario(rSet.getInt("COD_USUARIO"));
                member.setNome(rSet.getString("NOME"));
                member.setSobrenome(rSet.getString("SOBRENOME"));
                member.setEmail(rSet.getString("EMAIL"));

                members.add(member);
            }

        } catch (ClassNotFoundException | SQLException e) {
            
            e.printStackTrace();
        }

        return members;
    }

    public UserDB loadUser(int cod_user) {

        UserDB member = new UserDB();

        String sql = "SELECT * FROM USUARIO WHERE COD_USUARIO = ?";

        try {

            PreparedStatement statement = Database.get_connection().prepareStatement(sql);
            statement.setInt(1, cod_user);

            ResultSet rSet = statement.executeQuery();

            if (rSet.next()) {
                
                member.setCod_usuario(rSet.getInt("COD_USUARIO"));
                member.setNome(rSet.getString("NOME"));
                member.setSobrenome(rSet.getString("SOBRENOME"));
                member.setEmail(rSet.getString("EMAIL"));
            }

        } catch (ClassNotFoundException | SQLException e) {
            
            e.printStackTrace();
        }

        return member;
    }
}