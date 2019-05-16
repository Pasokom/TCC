package display.scenes;

import db.functions.appointment.CreateAppointment;
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

public class Task extends Scene {

    private TextField txt_title;
    private Label lbl_marcadores;
    private ChoiceBox<String> cbx_marcadores;
    private Label lbl_importance;
    private Spinner<Integer> spn_importance;
    private Label lbl_dependency;
    private ChoiceBox<String> cbx_depedency;

    private int cod_project;

    public Task(int cod_project) {
        super(new VBox());

        this.cod_project = cod_project;

        this.getStylesheets().add(this.getClass().getResource("../../css/task.css").toExternalForm());

        txt_title = new TextField();
        txt_title.setId("title");
        txt_title.setPromptText("Título");

        lbl_marcadores = new Label("Marcador");
        cbx_marcadores = new ChoiceBox<>();
        cbx_marcadores.setPrefWidth(100);
        cbx_marcadores.setPadding(new Insets(2));

        VBox vb_marcador = new VBox(lbl_marcadores, cbx_marcadores);

        lbl_importance = new Label("Importância");
        spn_importance = new Spinner<>(0, 5, 0);

        VBox vb_importace = new VBox(lbl_importance, spn_importance);

        lbl_dependency = new Label("Dependência");
        cbx_depedency = new ChoiceBox<>();
        cbx_depedency.setPrefWidth(100);
        cbx_depedency.setPadding(new Insets(2));

        VBox vb_dependecy = new VBox(lbl_dependency, cbx_depedency);

        HBox hb_lv1 = new HBox(vb_marcador, vb_importace);
        hb_lv1.setSpacing(30);

        Button btn_add = new Button("Adicionar tarefa");

        btn_add.setOnAction(e -> {

            CreateAppointment create = new CreateAppointment();
            create.create(this.getTarefa());

            ((Stage)this.getWindow()).close();
        });

        VBox vb_root = new VBox();
        vb_root.setSpacing(10);
        vb_root.getChildren().addAll(txt_title, hb_lv1, vb_dependecy, btn_add);

        this.setRoot(vb_root);
    }
    
    private TarefaDB getTarefa() {

        TarefaDB tarefa = new TarefaDB();
        tarefa.setNome_tarefa(this.txt_title.getText());
        tarefa.setFk_projeto(this.cod_project);

        return tarefa;
    }
}