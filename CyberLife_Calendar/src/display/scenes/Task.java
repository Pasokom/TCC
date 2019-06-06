package display.scenes;

import java.util.ArrayList;

import component.TimePicker;
import db.functions.appointment.CreateAppointment;
import db.functions.appointment.LoadAppointment;
import db.functions.projectFeatures.LoadFeature;
import db.pojo.LabelDB;
import db.pojo.UserDB;
import db.pojo.projectPOJO.TarefaDB;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import statics.SESSION;

public class Task extends Scene {

    private TextField txt_title;
    private Label lbl_duration;
    private ChoiceBox<String> cbx_duration;
    private Label lbl_labels;
    private ChoiceBox<String> cbx_labels;
    private Label lbl_importance;
    private Spinner<Integer> spn_importance;
    private Label lbl_dependency;
    private ChoiceBox<TarefaDB> cbx_depedency;
    private Label lbl_member;
    private ChoiceBox<UserDB> cbx_member;

    private int cod_project;

    public Task(int cod_project) {
        super(new VBox());

        this.cod_project = cod_project;

        this.getStylesheets().add(this.getClass().getResource("../../css/task.css").toExternalForm());

        txt_title = new TextField();
        txt_title.setId("title");
        txt_title.setPromptText("Título");

        lbl_duration = new Label("Duração");
        cbx_duration = new ChoiceBox<>();
        cbx_duration.getItems().add("30 minutos");
        cbx_duration.getItems().add("1 hora");
        cbx_duration.getItems().add("2 horas");
        cbx_duration.setPadding(new Insets(2));
        cbx_duration.getSelectionModel().selectFirst();

        VBox vb_duration = new VBox(lbl_duration, cbx_duration);

        lbl_labels = new Label("Marcador");
        cbx_labels = new ChoiceBox<>();
        cbx_labels.setPrefWidth(100);
        cbx_labels.setPadding(new Insets(2));

        cbx_labels.getItems().add("Nenhum");
        cbx_labels.getSelectionModel().select(0);

        LoadFeature feature = new LoadFeature();
        ArrayList<LabelDB> labels = feature.loadLabels(this.cod_project);

        for (LabelDB label : labels) {
            cbx_labels.getItems().add(label.getNome_marcador());
        }

        VBox vb_marcador = new VBox(lbl_labels, cbx_labels);

        lbl_importance = new Label("Importância");
        spn_importance = new Spinner<>(1, 5, 1);

        VBox vb_importace = new VBox(lbl_importance, spn_importance);

        lbl_dependency = new Label("Dependência");
        cbx_depedency = new ChoiceBox<>();
        cbx_depedency.setPrefWidth(100);
        cbx_depedency.setPadding(new Insets(2));

        TarefaDB nothingTask = new TarefaDB();
        nothingTask.setNome_tarefa("Nenhuma");

        cbx_depedency.getItems().add(nothingTask);
        cbx_depedency.getSelectionModel().select(0);

        LoadAppointment appointment = new LoadAppointment();
        ArrayList<TarefaDB> tasks = appointment.loadAllTasks(this.cod_project);

        for (TarefaDB task : tasks) {
            cbx_depedency.getItems().add(task);
        }

        VBox vb_dependecy = new VBox(lbl_dependency, cbx_depedency);

        HBox hb_lv1 = new HBox(vb_duration, vb_importace);
        hb_lv1.setSpacing(30);

        HBox hb_lv2 = new HBox(vb_marcador, vb_dependecy);
        hb_lv2.setSpacing(30);

        Button btn_add = new Button("Adicionar tarefa");

        btn_add.setOnAction(e -> {

            CreateAppointment create = new CreateAppointment();
            create.create(this.getTarefa());

            ((Stage)this.getWindow()).close();
        });

        /* Membros do projeto */
        lbl_member = new Label("Integrante");
        cbx_member = new ChoiceBox<>();
        cbx_member.setPadding(new Insets(2));

        UserDB me = new UserDB();
        me.setNome("Eu");
        me.setSobrenome("");
        me.setCod_usuario((int)SESSION.get_user_cod());
        cbx_member.getItems().add(me);

        cbx_member.getSelectionModel().select(0);

        LoadFeature features = new LoadFeature();
        ArrayList<UserDB> members = features.loadMembers(this.cod_project);

        for (UserDB member : members) {
            cbx_member.getItems().add(member);
        }

        VBox vb_member = new VBox(lbl_member, cbx_member);

        VBox vb_root = new VBox();
        vb_root.setSpacing(10);
        vb_root.getChildren().addAll(txt_title, hb_lv1, hb_lv2, vb_member, btn_add);

        this.setRoot(vb_root);
    }
    
    private TarefaDB getTarefa() {

        TarefaDB tarefa = new TarefaDB();
        tarefa.setNome_tarefa(this.txt_title.getText());
        tarefa.setDuracao(this.getDuration());
        tarefa.setImportancia(this.spn_importance.getValue());
        tarefa.setDependencia(cbx_depedency.getSelectionModel().getSelectedItem().getCod_tarefa());

        if(cbx_labels.getSelectionModel().getSelectedIndex() > 0)
            tarefa.setFk_nome_marcador(cbx_labels.getSelectionModel().getSelectedItem());

        tarefa.setFk_projeto(this.cod_project);
        tarefa.setFk_usuario(this.cbx_member.getSelectionModel().getSelectedItem().getCod_usuario());

        return tarefa;
    }

    private int getDuration() {

        switch (cbx_duration.getSelectionModel().getSelectedIndex()) {
            case 0:
                return 30;
            case 1:
                return 60;
            case 2:
                return 120;
            default:
                return 30;
        }
    }
}