package component.project;

import java.util.Optional;

import db.functions.projectFeatures.UpdateFeature;
import display.scenes.HomePage;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;

public class Settings extends VBox{

    private Button btn_finish;

    private int cod_project;

    public Settings(int cod_project) {

        this.cod_project = cod_project;

        this.getStylesheets().add(this.getClass().getResource("../../css/project_component.css").toExternalForm());

        btn_finish = new Button("Finalizar projeto");

        btn_finish.setOnAction(e -> {

            Alert alert = new Alert(AlertType.CONFIRMATION, 
                "Deseja realmente finalizar este projeto?", 
                ButtonType.OK,
                ButtonType.CANCEL);

            Optional<ButtonType> result = alert.showAndWait();

            if(result.get() == ButtonType.OK) {

                UpdateFeature updateFeature = new UpdateFeature();
                updateFeature.finishProject(this.cod_project);
    
                HomePage.menu.updateProjects();
                HomePage.menu.goToCalendar();
            }
        });

        this.getChildren().add(btn_finish);
    }
}