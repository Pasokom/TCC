package component.project;

import java.util.ArrayList;

import component.appointment.AppointmentComponent;
import db.functions.appointment.LoadAppointment;
import db.functions.projectFeatures.LoadFeature;
import db.pojo.LabelDB;
import db.pojo.projectPOJO.TarefaDB;
import display.scenes.Task;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
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
    private ComboBox<String> cbx_labels;

    private FlowPane fp_taks;

    private int cod_project;

    public Tasks(int cod_project) {

        this.cod_project = cod_project;

        this.getStylesheets().add(this.getClass().getResource("../../css/project_component.css").toExternalForm());

        btn_add_task = new Button("+ adicionar tarefa");
        btn_add_task.setId("button_add_task");

        btn_add_task.setOnAction(e -> {

            Stage st = new Stage();
            st.setWidth(300);
            st.setHeight(350);
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

        if (task.getCod_tarefa() != 0) {

            AppointmentComponent tarefa_atual = new AppointmentComponent(task);
            tarefa_atual.setMaxWidth(200);
            this.getChildren().addAll(lbl_current_task, tarefa_atual);
        }

        lbl_all_tasks = new Label("Todas as tarefas");

        cbx_labels = new ComboBox<>();
        cbx_labels.setPrefWidth(100);
        cbx_labels.setPadding(new Insets(2));

        cbx_labels.getItems().add("Todos");
        cbx_labels.getSelectionModel().select(0);

        LoadFeature feature = new LoadFeature();
        ArrayList<LabelDB> labels = feature.loadLabels(this.cod_project);

        for (LabelDB label : labels) {
            cbx_labels.getItems().add(label.getNome_marcador());
        }

        cbx_labels.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                
                if(((Integer)newValue) > 0)
                    updateTasks(cbx_labels.getItems().get((Integer)newValue));
                else
                    loadTasks();
            }
        });

        HBox hb_all_tasks = new HBox(lbl_all_tasks, cbx_labels);
        hb_all_tasks.setSpacing(15);
        this.getChildren().add(hb_all_tasks);

        fp_taks = new FlowPane();
        fp_taks.setHgap(10);
        fp_taks.setVgap(10);

        loadTasks();

        this.getChildren().add(fp_taks);
    }

    private void updateTasks(String label) {

        LoadAppointment loader = new LoadAppointment();
        ArrayList<TarefaDB> tasks = loader.loadTasksFiltered(cod_project, label);

        fp_taks.getChildren().clear();

        for (TarefaDB oTask : tasks) {
            
            AppointmentComponent ac_task = new AppointmentComponent(oTask);
            ac_task.setPrefWidth(200);
            fp_taks.getChildren().add(ac_task);
        }
    }

    private void loadTasks() {

        LoadAppointment loader = new LoadAppointment();
        ArrayList<TarefaDB> tasks = loader.loadAllTasks(cod_project);

        fp_taks.getChildren().clear();

        for (TarefaDB oTask : tasks) {
            
            AppointmentComponent ac_task = new AppointmentComponent(oTask);
            ac_task.setPrefWidth(200);
            fp_taks.getChildren().add(ac_task);
        }
    }
}