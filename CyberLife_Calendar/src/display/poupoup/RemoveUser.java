package display.poupoup;

import java.util.Optional;

import db.functions.projectFeatures.DeleteFeature;
import db.pojo.UserDB;
import display.scenes.HomePage;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.DropShadow;
import javafx.stage.Popup;

public class RemoveUser extends Popup {

    private Label lbl_remove;

    public RemoveUser(UserDB user, int cod_project) {

        lbl_remove = new Label("Remover usuário");

        lbl_remove.setStyle("-fx-background-color: #ffffff");
        lbl_remove.setPadding(new Insets(10, 10, 10, 10));

        lbl_remove.setOnMouseEntered(e -> {

            lbl_remove.setStyle("-fx-background-color: #d0d0d0");
        });

        lbl_remove.setOnMouseExited(e -> {

            lbl_remove.setStyle("-fx-background-color: #eeeeee");
        });

        lbl_remove.setOnMouseClicked(e -> {

            this.hide();

            Alert alert = new Alert(AlertType.CONFIRMATION, 
                "Deseja realmente remover este membro? as tarefa atribuidas a ele ainda poderão ser finalizadas", 
                ButtonType.OK,
                ButtonType.CANCEL);

            Optional<ButtonType> result = alert.showAndWait();

            if(result.get() == ButtonType.OK) {

                DeleteFeature feature = new DeleteFeature();
                feature.delete(user, cod_project);
                
                HomePage.project.update();
            }
        });

        DropShadow shadow = new DropShadow();

        this.getContent().add(lbl_remove);
        lbl_remove.setEffect(shadow);
        this.setAutoHide(true);
    }
}