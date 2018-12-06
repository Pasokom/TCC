package component.profileImage;

import display.poupoup.ShowPicture;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.Main;

public class ImageContent extends VBox {

    private VBox container;
    private HBox h_labelContent;
    private ImageView view;

    public ImageContent(Image image) {

        this.getStylesheets().add(this.getClass().getResource("/css/image_content.css").toExternalForm());
        this.container = new VBox();
        this.h_labelContent = new HBox();
        this.view = new ImageView();
        view.setImage(image);

        this.view.setFitWidth(100);
        this.view.setFitHeight(100);

        container.setPrefHeight(50);

        container.setTranslateY(-20);

        container.getStyleClass().add("vbox");

        h_labelContent.setAlignment(Pos.CENTER);
        h_labelContent.setVisible(false);

        view.setOnMouseMoved(e -> {
            container.setVisible(true);
        });
        this.setOnMouseMoved(e -> {
            h_labelContent.setVisible(true);
        });
        this.setOnMouseExited(e -> {
            h_labelContent.setVisible(false);
        });

        view.setOnMouseClicked(e -> {
            new ShowPicture(view.getImage(), Main.main_stage);
        });

        h_labelContent.setOnMouseClicked(e -> {
            System.out.println("funciona bitch");
        });

        h_labelContent.getChildren().add(new Label("Perfil"));

        container.getChildren().add(h_labelContent);

        this.getChildren().addAll(view, container);

    }

}