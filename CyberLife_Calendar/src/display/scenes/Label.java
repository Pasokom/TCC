package display.scenes;

import db.functions.projectFeatures.CreateFeature;
import db.pojo.LabelDB;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Label extends Scene {

    private TextField txt_name;
    private Button btn_create;

    private int project;

    public Label(int project) {
        super(new VBox());

        this.project = project;

        txt_name = new TextField();
        txt_name.setPromptText("Nome");

        btn_create = new Button("Criar");
        btn_create.setOnAction(e -> {
           
            CreateFeature feature = new CreateFeature();
            feature.create(this.getLabel());

            ((Stage)this.getWindow()).close();
        });

        VBox vb_content = new VBox(txt_name, btn_create);
        this.setRoot(vb_content);
    }
    
    private LabelDB getLabel() {

        LabelDB label = new LabelDB();
        label.setNome_marcador(txt_name.getText());
        label.setFk_projeto(this.project);

        return label;
    }
}