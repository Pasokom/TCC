package component.appointment;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

import db.functions.appointment.DeleteAppointment;
import db.functions.appointment.EditAppointment;
import db.pojo.eventPOJO.EventDB;
import db.pojo.projectPOJO.TarefaDB;
import db.pojo.reminderPOJO.ReminderDB;
import display.scenes.Event;
import display.scenes.HomePage;
import display.scenes.Reminder;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import statics.Enums;
import statics.Enums.DialogResult;

/**
 * AppointmentInfo
 */
public class AppointmentInfo extends Popup {

    Label lbl_title;
    Label lbl_date;
    Label lbl_end_date;

    Label lbl_place;
    Label lbl_description;
    Label lbl_repetition;
    Label lbl_end_repetition;
    Label dom, seg, ter, qua, qui, sex, sab;
    
    HBox hb_week;

    Button btn_delete;
    Button btn_edit;
    Button btn_done;

    public AppointmentInfo(ReminderDB reminder) {

        DeleteAppointment deleter = new DeleteAppointment();

        VBox root = new VBox();
        root.getStyleClass().add("vbox");

        root.getStylesheets().add(this.getClass().getResource("/css/appointment_info.css").toExternalForm());

        /* titulo */
        lbl_title = new Label(reminder.getTitulo());
        lbl_title.setId("title");
        lbl_title.setWrapText(true);
        
        /* horario */
        lbl_date = new Label(buildDate(reminder.getHorario(), reminder.isDia_todo()));
        lbl_date.setId("date");

        if(reminder.getHorario_fim() != null){

            lbl_end_date = new Label(buildInterval(reminder.getHorario_fim(), reminder.getIntervalo_minutos(), reminder.getHorario()));
            lbl_end_date.getStyleClass().add("inside_label");
        }

        Separator separator_title_bar = new Separator();
        Label lbl_interval = new Label("Intervalo");
        lbl_interval.getStyleClass().add("pane_label");

        /* repeticao */
        lbl_repetition = new Label(buildRecurrenceType(reminder.getTipo_repeticao(), reminder.getSchedule().getIntervalo()));
        lbl_repetition.getStyleClass().add("inside_label");

        dom = new Label("D");
        seg = new Label("S");
        ter = new Label("T");
        qua = new Label("Q");
        qui = new Label("Q");
        sex = new Label("S");
        sab = new Label("S");

        hb_week = new HBox(dom, seg, ter, qua, qui, sex, sab);
        hb_week.getStyleClass().add("inside_label");
        hb_week.setId("week");

        buildWeek(reminder.getSchedule().getDias_semana());

        lbl_end_repetition = new Label(buildRecurrenceEnd(reminder.getTipo_fim_repeticao(), 
            reminder.getReminderEndSchedule().getQtd_recorrencia(), reminder.getReminderEndSchedule().getDia_fim()));
        lbl_end_repetition.getStyleClass().add("inside_label");

        Separator separator_repetition = new Separator();
        Label lbl_recurrence = new Label("Recorrência");
        lbl_recurrence.getStyleClass().add("pane_label");
        
        /* Botões de controle */
        btn_delete = new Button();
        btn_delete.setId("btn_delete");
        btn_edit = new Button();
        btn_edit.setId("btn_edit");
        btn_done = new Button();
        btn_done.setId("btn_done");

        HBox hb_control_buttons = new HBox();
        hb_control_buttons.setAlignment(Pos.CENTER_RIGHT);
        hb_control_buttons.getChildren().addAll(btn_delete, btn_edit, btn_done);
        hb_control_buttons.setId("hb_buttons");

        btn_delete.setOnAction(e -> {

            DeleteConfirmation confirmation = new DeleteConfirmation();
            confirmation.showAndWait();

            if (confirmation.result == DialogResult.OK) {

                if (confirmation.isAll())
                    deleter.delete(reminder, true);
                else
                    deleter.delete(reminder, false);

                HomePage.listCalendar.update(HomePage.listCalendar.getCurrentDate());
                HomePage.calendarComponent.createCalendar(HomePage.calendarComponent.getDate());
            }
        });

        btn_edit.setOnAction(e -> {
            Stage st = new Stage();
			st.setWidth(300);
			st.setHeight(400);
			st.initStyle(StageStyle.UTILITY);
			st.initModality(Modality.APPLICATION_MODAL);
			st.setScene(new Reminder(reminder));
			st.show();
        });

        btn_done.setOnAction(e -> {
            EditAppointment edit = new EditAppointment();
            edit.markAsDone(reminder);
            HomePage.listCalendar.update(HomePage.listCalendar.getCurrentDate());
            HomePage.calendarComponent.createCalendar(HomePage.calendarComponent.getDate());
        });

        root.getChildren().add(lbl_title);
        root.getChildren().add(lbl_date);

        if(reminder.getHorario_fim() != null){

            root.getChildren().add(separator_title_bar);
            root.getChildren().add(lbl_interval);
            root.getChildren().add(lbl_end_date);
        }

        if(reminder.getTipo_repeticao() != 0) {

            root.getChildren().add(separator_repetition);
            root.getChildren().add(lbl_recurrence);
            root.getChildren().add(lbl_repetition);

            if(reminder.getTipo_repeticao() == 2)
                root.getChildren().add(hb_week);

            if(reminder.getTipo_fim_repeticao() != 0)
                root.getChildren().add(lbl_end_repetition);
        }

        root.getChildren().addAll(hb_control_buttons);

        DropShadow shadow = new DropShadow();
		shadow.setOffsetX(0);
		shadow.setOffsetY(0);

        root.setEffect(shadow);
        root.setMinWidth(300);
        root.setMaxWidth(450);

        this.setAutoHide(true);
        this.getContent().add(root);
    }

    public AppointmentInfo(EventDB event) {

        DeleteAppointment deleter = new DeleteAppointment();

        VBox root = new VBox();
        root.getStyleClass().add("vbox");

        root.getStylesheets().add(this.getClass().getResource("/css/appointment_info.css").toExternalForm());

        /* titulo */
        lbl_title = new Label(event.getTitulo());
        lbl_title.setId("event_title");
        lbl_title.setWrapText(true);
        
        /* horario */
        lbl_date = new Label(buildDate(event.getData_inicio(), event.isDia_todo()));
        lbl_date.setId("date");

        lbl_end_date = new Label(buildDate(event.getData_fim(), event.isDia_todo()));
        lbl_end_date.setId("date");

        /* local */
        Separator separator_place = new Separator();
        Label lbl_local = new Label("Local");
        lbl_local.getStyleClass().add("pane_label");
        lbl_place = new Label(event.getLocal_evento());
        lbl_place.getStyleClass().add("inside_label");

        /* descricao */
        Separator separator_description = new Separator();
        Label lbl_description_pane = new Label("Descrição");
        lbl_description_pane.getStyleClass().add("pane_label");
        lbl_description = new Label(event.getDescricao());
        lbl_description.setWrapText(true);
        lbl_description.getStyleClass().add("inside_label");

        /* repeticao */
        lbl_repetition = new Label(buildRecurrenceType(event.getTipo_repeticao(), event.getHorario_evento().getIntervalo()));
        lbl_repetition.getStyleClass().add("inside_label");

        dom = new Label("D");
        seg = new Label("S");
        ter = new Label("T");
        qua = new Label("Q");
        qui = new Label("Q");
        sex = new Label("S");
        sab = new Label("S");

        hb_week = new HBox(dom, seg, ter, qua, qui, sex, sab);
        hb_week.getStyleClass().add("inside_label");
        hb_week.setId("event_week");

        buildWeek(event.getHorario_evento().getDias_semana());

        /* Botões de controle */
        btn_delete = new Button();
        btn_delete.setId("btn_delete");
        btn_edit = new Button();
        btn_edit.setId("btn_edit");

        btn_delete.setOnAction(e -> {

            DeleteConfirmation confirmation = new DeleteConfirmation();
            confirmation.showAndWait();

            if (confirmation.result == DialogResult.OK) {

                if (confirmation.isAll())
                    deleter.delete(event, true);
                else
                    deleter.delete(event, false);

                HomePage.listCalendar.update(HomePage.listCalendar.getCurrentDate());
                HomePage.calendarComponent.createCalendar(HomePage.calendarComponent.getDate());
            }
        });

        btn_edit.setOnAction(e -> {
            Stage st = new Stage();
			st.setWidth(300);
			st.setHeight(400);
			st.initStyle(StageStyle.UTILITY);
			st.initModality(Modality.APPLICATION_MODAL);
			st.setScene(new Event(event));
			st.show();
        });

        HBox hb_control_buttons = new HBox();
        hb_control_buttons.setAlignment(Pos.CENTER_RIGHT);
        hb_control_buttons.getChildren().addAll(btn_delete, btn_edit);
        hb_control_buttons.setId("hb_buttons");

        lbl_end_repetition = new Label(buildRecurrenceEnd(event.getTipo_fim_repeticao(), 
            event.getHorario_fim_evento().getQtd_recorrencias(), event.getHorario_fim_evento().getDia_fim()));
        lbl_end_repetition.getStyleClass().add("inside_label");

        Separator separator_repetition = new Separator();
        Label lbl_recurrence = new Label("Recorrência");
        lbl_recurrence.getStyleClass().add("pane_label");

        root.getChildren().add(lbl_title);
        root.getChildren().add(lbl_date);
        root.getChildren().add(lbl_end_date);

        if(!event.getLocal_evento().equals("")) {
            root.getChildren().addAll(separator_place, lbl_local, lbl_place);
        }

        if(!event.getDescricao().equals("")) {
            root.getChildren().addAll(separator_description, lbl_description_pane, lbl_description);
        }

        if(event.getTipo_repeticao() != 0) {

            root.getChildren().add(separator_repetition);
            root.getChildren().add(lbl_recurrence);
            root.getChildren().add(lbl_repetition);

            if(event.getTipo_repeticao() == 2)
                root.getChildren().add(hb_week);

            if(event.getTipo_fim_repeticao() != 0)
                root.getChildren().add(lbl_end_repetition);
        }

        root.getChildren().addAll(hb_control_buttons);

        DropShadow shadow = new DropShadow();
		shadow.setOffsetX(0);
		shadow.setOffsetY(0);

        root.setEffect(shadow);
        root.setMinWidth(300);
        root.setMaxWidth(450);

        this.setAutoHide(true);
        this.getContent().add(root);
    }

    public AppointmentInfo(TarefaDB task) {

        VBox root = new VBox();
        root.getStyleClass().add("vbox");

        root.getStylesheets().add(this.getClass().getResource("/css/appointment_info.css").toExternalForm());

        /* titulo */
        lbl_title = new Label(task.getNome_tarefa());
        lbl_title.setId("task_title");
        lbl_title.setWrapText(true);

        root.getChildren().add(lbl_title);

        /* marcador */
        if(task.getFk_nome_marcador() != null) {

            Label lbl_label = new Label(task.getFk_nome_marcador());
            root.getChildren().add(lbl_label);
        }

        Separator separator = new Separator();

        /* importância */
        Label lbl_importancy = new Label("Importância");
        lbl_importancy.getStyleClass().add("pane_label");
        Label lbl_importancy_value = new Label("nível " + task.getImportancia());
        lbl_importancy_value.getStyleClass().add("inside_label");

        root.getChildren().addAll(separator, lbl_importancy, lbl_importancy_value);

        /* dependência */
        if(task.getDependencia() > 0) {

            Label lbl_dependency = new Label("Depende de");
            lbl_dependency.getStyleClass().add("pane_label");
            Label lbl_dependency_value = new Label(String.valueOf(task.getDependencia()));
            lbl_dependency_value.getStyleClass().add("inside_label");

            root.getChildren().addAll(lbl_dependency, lbl_dependency_value);
        }

        btn_done = new Button();
        btn_done.setId("btn_done");

        btn_done.setOnAction(e -> {

            EditAppointment edit = new EditAppointment();
            edit.markAsDone(task);
        });

        HBox hb_control_buttons = new HBox();
        hb_control_buttons.setAlignment(Pos.CENTER_RIGHT);
        hb_control_buttons.getChildren().add(btn_done);
        hb_control_buttons.setId("hb_buttons");

        root.getChildren().add(hb_control_buttons);

        DropShadow shadow = new DropShadow();
		shadow.setOffsetX(0);
		shadow.setOffsetY(0);

        root.setEffect(shadow);
        root.setMinWidth(300);
        root.setMaxWidth(450);

        this.setAutoHide(true);
        this.getContent().add(root);
    }

    private String buildDate(Timestamp date, boolean allday) {

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(date.getTime());

        int day = c.get(Calendar.DAY_OF_MONTH);
        String month = Enums.Month.values()[c.get(Calendar.MONTH)].getValue();
        int year = c.get(Calendar.YEAR);

        String hours = String.format("%02d", c.get(Calendar.HOUR_OF_DAY));
        String minutes = String.format("%02d", c.get(Calendar.MINUTE));
        
        if(allday)
            return "" + day + " de " + month + " de " + year;

        return "" + day + " de " + month + " de " + year + " - " + hours + ":" + minutes;
    }

    private String buildInterval(Timestamp end_date, int interval_minutes, Timestamp start_date) {

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(end_date.getTime());

        int day = c.get(Calendar.DAY_OF_MONTH);
        String month = Enums.Month.values()[c.get(Calendar.MONTH)].getValue();
        int year = c.get(Calendar.YEAR);

        String hours = String.format("%02d", c.get(Calendar.HOUR_OF_DAY));
        String minutes = String.format("%02d", c.get(Calendar.MINUTE));

        if(equalDate(end_date, start_date))
            return "Até " + hours + ":" + minutes + " a cada " + interval_minutes + " minutos";
            
        return "Até " + day + " de " + month + " de " + year + " - " + hours + ":" + minutes + 
            " a cada " + interval_minutes + " minutos";
    }

    private String buildRecurrenceType(int type, int interval) {

        switch (type) {
            case 1:
                if(interval > 1)
                    return "A cada " + interval + " dias";
                return "Todos os dias";
            case 2:
                if(interval > 1)
                    return "A cada " + interval + " semanas";
                return "Toda semana";
            case 3:
                if(interval > 1)
                    return "A cada " + interval + " meses";
                return "Todo mês";
            case 4:
                if(interval > 1)
                    return "A cada " + interval + " anos";
                return "Todo ano";
            default:
                return "A cada " + interval + " dias";
        }
    }

    private String buildRecurrenceEnd(int type, int qtd_recurrences, Date date) {

        switch (type) {
            case 0:
                return "";
            case 1:
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(date.getTime());

                int day = c.get(Calendar.DAY_OF_MONTH);
                String month = Enums.Month.values()[c.get(Calendar.MONTH)].getValue();
                int year = c.get(Calendar.YEAR);

                return "Até " + day + " de " + month + " de " + year;
            case 2:
                return "" + qtd_recurrences + " vezes";
            default:
                return "";
        }
    }

    private void buildWeek(boolean[] week){

        for(int i = 0; i < week.length; i++){

            if(week[i])
                hb_week.getChildren().get(i).setId("selected");
        }
    }

    private boolean equalDate(Timestamp date_1, Timestamp date_2){

        Calendar c = Calendar.getInstance();
		c.setTimeInMillis(date_1.getTime());
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        
        Calendar c2 = Calendar.getInstance();
		c2.setTimeInMillis(date_2.getTime());
		c2.set(Calendar.HOUR_OF_DAY, 0);
		c2.set(Calendar.MINUTE, 0);
		c2.set(Calendar.SECOND, 0);
		c2.set(Calendar.MILLISECOND, 0);

        return c.compareTo(c2) == 0;
    }
}