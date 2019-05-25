package component.project;

import java.util.ArrayList;

import component.appointment.AppointmentComponent;
import db.functions.appointment.LoadAppointment;
import db.pojo.projectPOJO.TarefaDB;
import display.scenes.Task;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Tasks extends VBox {

    private Button btn_add_task;
    private Label lbl_current_task;
    private Label lbl_all_tasks;
    private Button btn_filter;

    private int cod_project;

    public Tasks(int cod_project) {
        
        this.cod_project = cod_project;

        btn_add_task = new Button("+ adicionar tarefa");

        btn_add_task.setOnAction(e -> {

            Stage st = new Stage();
			st.setWidth(300);
			st.setHeight(300);
			st.initStyle(StageStyle.UTILITY);
			st.initModality(Modality.APPLICATION_MODAL);
			st.setScene(new Task(this.cod_project));
			st.show();
        });

        lbl_current_task = new Label("Tarefa atual");

        LoadAppointment loader = new LoadAppointment();

        TarefaDB task = loader.loadCurrentTask(cod_project);

        this.setSpacing(10);
        this.getChildren().add(btn_add_task);
        
        if(task.getCod_tarefa() != 0) {

            AppointmentComponent tarefa_atual = new AppointmentComponent(task);
            tarefa_atual.setMaxWidth(200);
            this.getChildren().addAll(lbl_current_task, tarefa_atual);
        }

        lbl_all_tasks = new Label("Todas as tarefas");
        btn_filter = new Button("Todos");

        HBox hb_all_tasks = new HBox(lbl_all_tasks, btn_filter);

        ArrayList<TarefaDB> tasks = loader.loadAllTasks(cod_project);
        this.getChildren().add(hb_all_tasks);

        FlowPane fp_taks = new FlowPane();
        fp_taks.setHgap(10);
        fp_taks.setVgap(10);

        for (TarefaDB oTask : tasks) {
            
            AppointmentComponent ac_task = new AppointmentComponent(oTask);
            ac_task.setPrefWidth(200);
            fp_taks.getChildren().add(ac_task);
        }

        this.getChildren().add(fp_taks);
    }
}