package component.homepage;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

/**
 * Goals
 */
public class Goals extends VBox {

    public Goals() {
        
        this.setBackground(new Background(new BackgroundFill(Paint.valueOf("#ffffff"), new CornerRadii(0d), new Insets(0d))));

        Label lbl_metas = new Label("Metas");
        lbl_metas.setFont(new Font(42));
        lbl_metas.setPadding(new Insets(20));

        this.getChildren().add(lbl_metas);
    }
}