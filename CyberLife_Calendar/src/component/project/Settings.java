package component.project;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class Settings extends VBox{

    private Button btn_finish;
    private Button btn_delete;

    private int cod_project;

    public Settings(int cod_project) {

        this.cod_project = cod_project;

        btn_finish = new Button("Finalizar projeto");

        this.getChildren().add(btn_finish);
    }
}