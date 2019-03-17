package display.scenes;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Calendar;

import component.Recurrence;
import component.TimePicker;
import db.functions.event.CreateEvent;
import db.pojo.eventPOJO.EventDB;
import db.pojo.eventPOJO.EventEndSchedule;
import db.pojo.eventPOJO.EventSchedule;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import statics.SESSION;

public class Event extends Scene {

	private TextField txt_title;

	private Label img_schedule;
	private Label lbl_allday;
	private CheckBox cbx_allday;
	private DatePicker dt_start;
	private TimePicker t_start;
	private DatePicker dt_end;
	private TimePicker t_end;

	private Label img_place;
	private TextField txt_place;

	private Label img_note;
	private TextArea txt_description;
	private AnchorPane container;

	private StackPane pane;
	private VBox main;
	private HBox tabSelector;

	private Recurrence repetition;

	public Event() {
		super(new VBox());

		this.getStylesheets().add(this.getClass().getResource("../../css/event.css").toExternalForm());

		main = vbox_mainContent();
		repetition = new Recurrence();

		container = new AnchorPane();

		pane = new StackPane();
		pane.getChildren().add(main);

		tabSelector = hbox_tabSelector();

		AnchorPane.setTopAnchor(pane, 5d);
		AnchorPane.setRightAnchor(pane, 0d);
		AnchorPane.setLeftAnchor(pane, 0d);
		AnchorPane.setBottomAnchor(tabSelector, 0d);

		container.getChildren().addAll(pane, tabSelector);

		this.setRoot(container);
	}

	private VBox vbox_mainContent() {

		VBox content = new VBox();
		content.setSpacing(5);

		/* TÃ­tulo */
		txt_title = new TextField();
		txt_title.setId("title");
		txt_title.setPromptText("Titulo");

		/* Horario */
		GridPane pnl_schedule = new GridPane();
		pnl_schedule.setHgap(5);
		pnl_schedule.setVgap(5);

		img_schedule = new Label();
		img_schedule.setId("img_schedule");
		img_schedule.setPrefSize(10, 10);

		lbl_allday = new Label("Dia inteiro");
		cbx_allday = new CheckBox();

		cbx_allday.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				t_start.setDisable(newValue);
				t_end.setDisable(newValue);
			}
		});

		GridPane.setHalignment(cbx_allday, HPos.RIGHT);

		Calendar time = Calendar.getInstance();

		dt_start = new DatePicker(LocalDate.now());
		dt_start.setPromptText("Inicio");
		t_start = new TimePicker(time);
		t_start.setPrefWidth(80);

		time.add(Calendar.HOUR, 1);

		dt_end = new DatePicker(LocalDate.now());
		dt_end.setPromptText("Fim");
		t_end = new TimePicker(time);
		t_end.setPrefWidth(80);

		pnl_schedule.add(img_schedule, 0, 0);
		pnl_schedule.add(lbl_allday, 1, 0);
		pnl_schedule.add(cbx_allday, 2, 0);
		pnl_schedule.add(dt_start, 1, 1);
		pnl_schedule.add(t_start, 2, 1);
		pnl_schedule.add(dt_end, 1, 2);
		pnl_schedule.add(t_end, 2, 2);

		/* Local */
		img_place = new Label();
		img_place.setId("img_place");

		txt_place = new TextField();
		txt_place.setPromptText("Local");

		pnl_schedule.add(img_place, 0, 3);
		pnl_schedule.add(txt_place, 1, 3, 2, 1);

		/* nota */
		img_note = new Label();
		img_note.setId("img_note");
		GridPane.setValignment(img_note, VPos.TOP);

		txt_description = new TextArea();
		txt_description.setPromptText("Nota");
		txt_description.setPrefRowCount(5);

		pnl_schedule.add(img_note, 0, 4);
		pnl_schedule.add(txt_description, 1, 4, 2, 1);

		content.getChildren().addAll(txt_title, pnl_schedule);

		return content;
	}

	private HBox hbox_tabSelector() {

		HBox content = new HBox();

		content.getStylesheets().add(this.getClass().getResource("../../css/event_tab_layout.css").toExternalForm());

		content.setId("tab_layout");

		ToggleGroup opt_group = new ToggleGroup();

		ToggleButton opt_1 = new ToggleButton();
		opt_1.setToggleGroup(opt_group);
		opt_1.setId("opt_1");
		opt_1.setSelected(true);
		ToggleButton opt_2 = new ToggleButton();
		opt_2.setToggleGroup(opt_group);
		opt_2.setId("opt_2");

		Button btn_done = new Button();
		btn_done.setId("btn_done");

		opt_1.setOnMouseClicked(e -> {

			pane.getChildren().clear();
			pane.getChildren().add(main);
		});

		opt_2.setOnMouseClicked(e -> {

			pane.getChildren().clear();
			pane.getChildren().add(repetition);
		});

		btn_done.setOnMouseClicked(e -> {
			createEvent();
		});

		Region region = new Region();
		HBox.setHgrow(region, Priority.ALWAYS);

		content.prefWidthProperty().bind(container.widthProperty());
		content.getChildren().addAll(opt_1, opt_2, region, btn_done);

		return content;
	}

	private void createEvent() {

		String titulo = this.txt_title.getText();
		Timestamp data_inicio = new Timestamp(getDate(this.dt_start, this.t_start));
		Timestamp data_fim = new Timestamp(getDate(this.dt_end, this.t_end));
		boolean dia_todo = this.cbx_allday.isSelected();
		String local_evento = this.txt_place.getText();
		String descricao = this.txt_description.getText();
		int tipo_repeticao = this.repetition.getTypeRecurrence();
		int tipo_fim_repeticao = this.repetition.getTypeEndRecurrence();
		int fk_usuario = (int) SESSION.get_user_cod();

		int intervalo = this.repetition.getInterval();
		boolean[] dias_semana = this.repetition.getWeek();

		Date dia_fim = this.repetition.getEndDay();
		int qtd_recorrencias = this.repetition.getQtdRecurrences();

		EventDB event = new EventDB();

		event.setTitulo(titulo);
		event.setData_inicio(data_inicio);
		event.setData_fim(data_fim);
		event.setDia_todo(dia_todo);
		event.setLocal_evento(local_evento);
		event.setDescricao(descricao);
		event.setTipo_repeticao(tipo_repeticao);
		event.setTipo_fim_repeticao(tipo_fim_repeticao);
		event.setFk_usuario(fk_usuario);

		EventSchedule schedule = new EventSchedule();

		schedule.setIntervalo(intervalo);
		schedule.setDias_semana(dias_semana);

		EventEndSchedule endSchedule = new EventEndSchedule();

		endSchedule.setDia_fim(dia_fim);
		endSchedule.setQtd_recorrencias(qtd_recorrencias);

		event.setHorario_evento(schedule);
		event.setHorario_fim_evento(endSchedule);

		CreateEvent create = new CreateEvent();

		try {

			create.insert_event(event);
			HomePage.listCalendar.update(HomePage.listCalendar.getCurrentDate());
			HomePage.calendarComponent.createCalendar(HomePage.calendarComponent.getDate());

		} catch (ClassNotFoundException | SQLException e) {
			
			e.printStackTrace();
		}

	}

	private long getDate(DatePicker date, TimePicker time){

		Calendar c = Calendar.getInstance();

		c.set(Calendar.DAY_OF_MONTH, date.getValue().getDayOfMonth());
		c.set(Calendar.MONTH, date.getValue().getMonthValue() - 1);
		c.set(Calendar.YEAR, date.getValue().getYear());

		if(!this.cbx_allday.isSelected()){

			c.set(Calendar.HOUR_OF_DAY, time.getHours());
			c.set(Calendar.MINUTE, time.getMinutes());
		}
		else {

			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
		}

		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);

		return c.getTimeInMillis();
	}
}
