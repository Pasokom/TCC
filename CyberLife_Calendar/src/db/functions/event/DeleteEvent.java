package db.functions.event;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.Database;
import db.pojo.eventPOJO.EventDB;

/**
 * DeleteEvent
 */
public class DeleteEvent {

    EventDB event;

    public DeleteEvent(EventDB event) {
        this.event = event;
    }
    
    public void delete() throws ClassNotFoundException, SQLException {
        
        PreparedStatement statement;

        String deleteFimRepeticao = "DELETE FROM E_FIM_REPETICAO WHERE COD_FIM_REPETICAO = ?";
        statement = Database.get_connection().prepareStatement(deleteFimRepeticao);
        statement.setInt(1, event.getHorario_fim_evento().getCod_fim_repeticao());
        statement.execute();

        String deleteRepeticao = "DELETE FROM E_REPETIR WHERE COD_REPETICAO = ?";
        statement = Database.get_connection().prepareStatement(deleteRepeticao);
        statement.setInt(1, event.getHorario_evento().getCod_repeticao());
        statement.execute();

        String delete = "DELETE FROM EVENTO WHERE COD_EVENTO = ?";
        statement = Database.get_connection().prepareStatement(delete);
        statement.setInt(1, event.getCod_evento());
        statement.execute();
    }
}