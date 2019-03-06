package db.functions.reminder;

import java.sql.Connection;
import java.sql.SQLException;

import db.Database;

/**
 * @author jefter66
 *         <h2>This class going to do the management of the user reminder while
 *         he is online Offers functions to make updates on their reminders and
 *         reminders schedules in the DB</h2>
 */
public class ManageReminder {

    private Connection connection;

    /** Strings with the fields of the table */

    /* Reminder table */
    private final String activeColumn = "ATIVO = ";
    private final String titleColumn = "TITULO = '";
    private final String typeRecurrenceColumn = "TIPO_RECORRENCIA = ";
    private final String typeRepetitionColumn = "TIPO_REPETICAO = ";

    /* Schedule table */
    private final String date_begin = "DATA_LEMBRETE = ";
    private final String date_end = "DATA_FINAL_LEMBRETE = ";
    private final String time_begin = "HORARIO_INICIO = ";
    private final String time_end = "HORARIO_FIM = ";
    private final String minutes_interval = "HL_INTERVALO_MINUTOS = ";
    private final String recurrence = "HL_RECORRENCIA = ";
    private final String week_day = "HL_SEMANA_DIA = ";
    private final String amount_repetition = "HL_QTD_REPETE";
    private final String active = "HL_ATIVO = ";

    public static enum changeTheSchedule {
        DATE_BEGIN, DATE_END, TIME_BEGIN, TIME_END, MINUTES_INTERVAL, RECURRENCE, WEEK_DAY, AMOUNT_OF_REPETITION, ACTIVE
    }

    public static enum changeTheReminder {
        TITLE, TYPE_OF_RECURRENCE, TYPE_OF_REPETITION, STATE
    }

    public ManageReminder() {
        try {
            this.connection = Database.get_connection();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("[ERROR] - Construtor- Classe ManageReminder");
        }
    }

    /**
     * @author jefter66
     * 
     *         <h3>Use this to change some of the fields on the reminders table on
     *         the database this will only alter those fields
     *         <i>TITULO,ATIVO,TIPO_RECORRENCIA,TIPO_REPETICAO</i></h3>
     * 
     *         <h4>It's important that the first @param Object have a valid input
     *         ,this means that, before use this function, read what is made with
     *         the parameter inside the fuction If the value are a invalid input,
     *         like , a float value, or null ... this will not make the update.</h4>
     *         <h1>Make sure that the parameters are right</h1>
     * 
     * @param                   int reminderID <i> -> the ID of the reminder . </i>
     * @param Object            newValue <i> -> this is the value that are going to
     *                          be updated in the DB . </i>
     * @param changeTheReminder whatsGoingToChange <i> -> this is one values from
     *                          the enum changeThe (is in the top of this class) and
     *                          is with this value that the function will know where
     *                          (in what field of the table LEMBRETE) have to change
     *                          . </i>
     *
     * @example changeReminder(newValue, 1,ManageReminder.changeThe.TITLE);
     */
    public void changeReminder(Object newValue, int reminderID, changeTheReminder whatChange) {

        // one of these variables going to have a TRUE values
        // and it's her that will be used to build the query

        String l_sql = "UPDATE LEMBRETE SET ";
        /**
         * the end of the query are the same in all cases, soo, this make my life easer
         */
        final String query_end = " WHERE LCOD_LEMBRETE = " + reminderID;// + ";";
        /*
         * all of these conditionals do the same verification, and change the query
         * string according to what boolean variable are true soo, if for example, the
         * l_changeState are true, the code inside the condition will just alter the
         * l_sql string to a complete query, casting the expected value from the
         * parameter Object ( that's one of the more important reasons that the value
         * passed in this parameter have to be right) and putting this value in the
         * field that are going to be updated
         */
        if (whatChange == changeTheReminder.STATE) {
            l_sql = l_sql + this.activeColumn + (boolean) newValue + query_end;
        }
        if (whatChange == changeTheReminder.TITLE) {
            l_sql = l_sql + this.titleColumn + (String) newValue.toString() + "'" + query_end;
        }
        if (whatChange == changeTheReminder.TYPE_OF_RECURRENCE) {
            l_sql = l_sql + this.typeRecurrenceColumn + (int) newValue + query_end;
        }
        if (whatChange == changeTheReminder.TYPE_OF_REPETITION) {
            l_sql = l_sql + this.typeRepetitionColumn + (int) newValue + query_end;
        }
        try {
            this.connection.createStatement().execute(l_sql);
            this.connection.close();
        } catch (SQLException e) {
            System.out.println("[ERROR] - Função changeRemidner  - SQLException");
        }
    }

    /**
     * The usage of this function is very similar to the changeRemidner function I
     * don't want to expend time re-writing commets soo if you wanna use this, got
     * to the function {@link ManageReminder.changeTheReminder}
     */
    public void changeSchedule(Object newValue, int scheduleID, changeTheSchedule whatsChange) {

        String sql = "UPDATE HORARIO_LEMBRETE SET ";
        final String queryEnd = " WHERE HL_CODIGO = " + scheduleID + ";";

        if (whatsChange == changeTheSchedule.DATE_BEGIN) {
            sql = sql + this.date_begin + "'" + (String) newValue + "'" + queryEnd;
        }
        if (whatsChange == changeTheSchedule.DATE_END) {
            sql = sql + this.date_end + "'" + (String) newValue + "'" + queryEnd;
        }
        if (whatsChange == changeTheSchedule.TIME_BEGIN) {
            sql = sql + this.time_begin + "''" + (String) newValue + "'" + queryEnd;
        }
        if (whatsChange == changeTheSchedule.TIME_END) {
            sql = sql + this.time_end + "''" + (String) newValue + "'" + queryEnd;
        }
        if (whatsChange == changeTheSchedule.MINUTES_INTERVAL) {
            sql = sql + this.minutes_interval + (int) newValue + queryEnd;
        }
        if (whatsChange == changeTheSchedule.RECURRENCE) {
            sql = sql + this.recurrence + (int) newValue + queryEnd;
        }
        if (whatsChange == changeTheSchedule.WEEK_DAY) {
            sql = sql + this.week_day + (int) newValue + queryEnd;
        }
        if (whatsChange == changeTheSchedule.AMOUNT_OF_REPETITION) {
            sql = sql + this.amount_repetition + (int) newValue + queryEnd;
        }
        if (whatsChange == changeTheSchedule.ACTIVE) {
            sql = sql + this.active + (boolean) newValue + queryEnd;
        }
        // I prefer to let the try/catch ugly thing here that in the windows code
        // make him more cleaner
        try {
            this.connection = this.connection == null ? Database.get_connection() : this.connection;
            this.connection.createStatement().execute(sql);
        } catch (ClassNotFoundException | SQLException e) {
        } finally {
            try {
                /* closing the connection and setting the object back to NULL */
                this.connection.close();
                this.connection = null;
            } catch (SQLException e1) {
            }
        }
    }
}