package component.projectFeatures;

import db.pojo.LabelDB;
import db.pojo.UserDB;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

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

        this.getChildren().add(card);
    }

    public FeatureComponent(UserDB member) {

        this.getStylesheets().add(this.getClass().getResource("/css/feature_component.css").toExternalForm());
        this.setId("this");
        
        lbl_titulo = new Label(member.getNome() + " " + member.getSobrenome());
        lbl_titulo.setId("titulo");

        VBox card = new VBox();

        card.getChildren().add(lbl_titulo);
        card.setId("user_card");

        this.getChildren().add(card);
    }
}