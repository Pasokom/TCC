package display.scenes;

import db.functions.projectFeatures.CreateFeature;
import db.pojo.LabelDB;
import javafx.geometry.Insets;
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

        this.getStylesheets().add(this.getClass().getResource("../../css/labels.css").toExternalForm());

        txt_name = new TextField();
        txt_name.setPromptText("Nome");
        txt_name.setId("title");

        btn_create = new Button("Criar");
        btn_create.setPrefWidth(80);
        btn_create.setOnAction(e -> {
           
            CreateFeature feature = new CreateFeature();
            feature.create(this.getLabel());

            ((Stage)this.getWindow()).close();
        });

        VBox vb_content = new VBox(txt_name, btn_create);
        vb_content.setPadding(new Insets(15, 15, 20, 15));
        vb_content.setSpacing(5);
        this.setRoot(vb_content);
    }
    
    private LabelDB getLabel() {

        LabelDB label = new LabelDB();
        label.setNome_marcador(txt_name.getText());
        label.setFk_projeto(this.project);

        return label;
    }
}