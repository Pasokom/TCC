package display.scenes;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import db.functions.appointment.CreateAppointment;
import db.pojo.projectPOJO.ProjectDB;
import db.pojo.projectPOJO.TarefaDB;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Project extends Scene {

    private TextField txt_title;
    private Label lbl_data;
    private DatePicker dt_final;
    private ArrayList<String> tasks;
    private Button btn_create_project;

    public Project() {
        super(new VBox());

        this.getStylesheets().add(this.getClass().getResource("../../css/project.css").toExternalForm());

        tasks = new ArrayList<>();

        txt_title = new TextField();
        txt_title.setPromptText("Título");
        txt_title.setId("title");

        lbl_data = new Label("Data de entrega");
        dt_final = new DatePicker();

        btn_create_project = new Button("Criar projeto");

        btn_create_project.setOnAction(e -> {

            createProject();
            HomePage.menu.updateProjects();
            ((Stage)this.getWindow()).close();
        });

        VBox vBox = new VBox();
        vBox.getStyleClass().add("vbox");
        vBox.getChildren().addAll(txt_title, lbl_data, dt_final, btn_create_project);

        this.setRoot(vBox);
    }

    private void createProject() {

        CreateAppointment create = new CreateAppointment();
        create.create(this.getProject());
    }

    private ProjectDB getProject() {

        String titulo = txt_title.getText();
        Timestamp data_entrega = new Timestamp(getDate(dt_final));

        ProjectDB project = new ProjectDB();

        project.setTitulo(titulo);
        project.setData_entrega(data_entrega);

        for (String task : tasks) {
            
            TarefaDB tarefa = new TarefaDB();
            tarefa.setNome_tarefa(task);

            project.getTarefas().add(tarefa);
        }

        return project;
    }

    private long getDate(DatePicker date){

		Calendar c = Calendar.getInstance();

		c.set(Calendar.DAY_OF_MONTH, date.getValue().getDayOfMonth());
		c.set(Calendar.MONTH, date.getValue().getMonthValue() - 1);
		c.set(Calendar.YEAR, date.getValue().getYear());
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);

		return c.getTimeInMillis();
	}
}