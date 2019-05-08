package display.scenes;

import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class Task extends Scene {

    private TextField txt_title;
    private Label lbl_importance;
    private Spinner<Integer> spn_importance;
    private Label lbl_dependency;
    private ChoiceBox<String> cbx_depedency;

    public Task() {
        super(new VBox());

        this.getStylesheets().add(this.getClass().getResource("../../css/task.css").toExternalForm());

        txt_title = new TextField();
        txt_title.setPromptText("Título");

        lbl_importance = new Label("Importância");
        spn_importance = new Spinner<>(0, 5, 0);

        lbl_dependency = new Label("Dependência");
        cbx_depedency = new ChoiceBox<>();

        VBox vb_root = new VBox();
        vb_root.getChildren().addAll(txt_title);

        this.setRoot(vb_root);
    }    
}