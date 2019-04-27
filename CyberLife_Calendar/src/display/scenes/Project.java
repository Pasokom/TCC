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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Project extends Scene {

    private TextField txt_title;
    private DatePicker dt_final;
    private TextField txt_add_task;
    private Button btn_add_task;
    private ArrayList<String> tasks;
    private Button btn_create_project;

    public Project() {
        super(new VBox());

        tasks = new ArrayList<>();

        txt_title = new TextField();
        dt_final = new DatePicker();

        FlowPane fpn_tasks = new FlowPane();

        txt_add_task = new TextField();
        btn_add_task = new Button("+");

        btn_add_task.setOnAction(e -> {
            
            tasks.add(txt_add_task.getText());

            Label label = new Label(txt_add_task.getText());
            fpn_tasks.getChildren().add(label);
        });

        HBox hb_add_tarefa = new HBox();
        hb_add_tarefa.getChildren().addAll(txt_add_task, btn_add_task);

        btn_create_project = new Button("Criar projeto");

        btn_create_project.setOnAction(e -> {

            createProject();
        });

        VBox vBox = new VBox();
        vBox.getChildren().addAll(txt_title, dt_final, fpn_tasks, hb_add_tarefa, btn_create_project);

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