package db.functions.reminderFUNCTIONS;

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
    private final String activeColumn = "ATIVO = ";
    private final String titleColumn = "TITULO = '";
    private final String typeRecurrenceColumn = "TIPO_RECORRENCIA = ";
    private final String typeRepetitionColumn = "TIPO_REPETICAO = ";

    public static enum changeThe {
        TITLE, TYPE_OF_RECURRENCE, TYPE_OF_REPETITION, STATE
    }

    public ManageReminder() {
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
     * @param           int reminderID <i> -> the ID of the reminder . </i>
     * @param Object    newValue <i> -> this is the value that are going to be
     *                  updated in the DB . </i>
     * @param changeThe whatsGoingToChange <i> -> this is one values from the enum
     *                  changeThe (is in the top of this class) and is with this
     *                  value that the function will know where (in what field of
     *                  the table LEMBRETE) have to change . </i>
     *
     * @example changeReminder(newValue, 1,ManageReminder.changeThe.TITLE);
     */
    public void changeReminder(Object newValue, int reminderID, changeThe whatsGoingToChange)
            throws SQLException, ClassNotFoundException {

        // one of these variables going to have a TRUE values
        // and it's her that will be used to build the query
        boolean l_changeState = whatsGoingToChange == changeThe.STATE;
        boolean l_changeTitle = whatsGoingToChange == changeThe.TITLE;
        boolean l_changeTypeOfRecurrence = whatsGoingToChange == changeThe.TYPE_OF_RECURRENCE;
        boolean l_changeTypeOfRepetition = whatsGoingToChange == changeThe.TYPE_OF_REPETITION;

        String l_sql = "UPDATE LEMBRETE SET ";
        /**
         * the end of the query are the same in all cases, soo, this make my life easer
         */
        final String query_end = " WHERE LCOD_LEMBRETE = " + reminderID + ";";

        /*
         * all of these conditionals do the same verification, and change the query
         * string according to what boolean variable are true soo, if for example, the
         * l_changeState are true, the code inside the condition will just alter the
         * l_sql string to a complete query, casting the expected value from the
         * parameter Object ( that's one of the more important reasons that the value
         * passed in this parameter have to be right) and putting this value in the
         * field that are going to be updated
         */
        if (l_changeState) {
            l_sql = l_sql + this.activeColumn + (boolean) newValue + query_end;
        }
        if (l_changeTitle) {
            l_sql = l_sql + this.titleColumn + (String) newValue.toString() + "'" + query_end;
        }
        if (l_changeTypeOfRecurrence) {
            l_sql = l_sql + this.typeRecurrenceColumn + (int) newValue + query_end;
        }
        if (l_changeTypeOfRepetition) {
            l_sql = l_sql + this.typeRepetitionColumn + (int) newValue + query_end;
        }
        try {
            /**
             * here just use the connection that this class have because the connection is
             * used only in the functions of this class and think that are not that
             * important have a instance of this running always in the class, soo, when i
             * need to use this connection, i check first if is NULL, if it is, i
             * instantiate (btw, always will be NULL, then, always instantiate),
             * instantiate, use, then destroy
             */
            System.out.print(l_sql);
            this.connection = this.connection == null ? Database.get_connection() : this.connection;
            this.connection.createStatement().execute(l_sql);
        } finally {
            /* closing the connection and setting the object back to NULL */
            this.connection.close();
            this.connection = null;
        }
    }

    public void changeReminderSchedule() {
    }
}