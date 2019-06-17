package display.poupoup;

import db.functions.projectFeatures.DeleteFeature;
import db.pojo.UserDB;
import display.scenes.HomePage;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
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

            DeleteFeature feature = new DeleteFeature();
            feature.delete(user, cod_project);
            
            HomePage.project.update();
            this.hide();
        });

        DropShadow shadow = new DropShadow();

        this.getContent().add(lbl_remove);
        lbl_remove.setEffect(shadow);
        this.setAutoHide(true);
    }
}