package display.scenes;

import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class Project extends Scene {

    private TextField txt_titulo;
    private DatePicker dt_entrega;

    public Project() {
        super(new VBox());

        txt_titulo = new TextField();
        dt_entrega = new DatePicker();

        VBox vBox = new VBox();
        vBox.getChildren().addAll(txt_titulo, dt_entrega);

        this.setRoot(vBox);
    }
}