package component.projectFeatures;

import db.pojo.LabelDB;
import db.pojo.UserDB;
import display.poupoup.RemoveUser;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import statics.SESSION;

public class FeatureComponent extends VBox {

    private Label lbl_titulo;

    public FeatureComponent(LabelDB label) {
        
        this.getStylesheets().add(this.getClass().getResource("/css/feature_component.css").toExternalForm());
        this.setId("this");
        
        lbl_titulo = new Label(label.getNome_marcador());
        lbl_titulo.setId("titulo");

        VBox card = new VBox();

        card.getChildren().add(lbl_titulo);
        card.setId("label_card");

        this.setOnMouseClicked(e -> {

            Stage stage = new Stage();
            stage.initStyle(StageStyle.UTILITY);
			stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new display.scenes.Label(label));
            stage.show();
        });

        this.getChildren().add(card);
    }

    public FeatureComponent(UserDB member, int cod_project) {

        this.getStylesheets().add(this.getClass().getResource("/css/feature_component.css").toExternalForm());
        this.setId("this");
        
        lbl_titulo = new Label(member.getNome() + " " + member.getSobrenome());
        lbl_titulo.setId("titulo");

        VBox card = new VBox();

        card.getChildren().add(lbl_titulo);
        card.setId("user_card");

        if(member.getCod_usuario() != (int)SESSION.get_user_cod()) {

            this.setOnMouseClicked(e -> {
    
                Point2D point = this.localToScreen(0d, 0d);
    
                RemoveUser removeUser = new RemoveUser(member, cod_project);
                removeUser.setX(point.getX() + 10);
                removeUser.setY(point.getY() + this.getHeight());
    
                removeUser.show(this.getScene().getWindow());
            });
        }

        this.getChildren().add(card);
    }
}