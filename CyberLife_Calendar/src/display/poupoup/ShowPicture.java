package display.poupoup;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import listeners.windows.CloseWindowEsc;;

public class ShowPicture extends Stage {

    public ShowPicture(Image image, Stage owner) {

        VBox container = new VBox();
        ImageView view = new ImageView();

        view.setFitWidth(500);
        view.setFitHeight(500);
        view.setImage(image);

        container.getChildren().add(view);

        owner.setOnCloseRequest(e -> {
            this.close();
        });
        // TODO close window on click out of her
        Scene scene = new Scene(container);

        scene.setOnKeyPressed(new CloseWindowEsc(this));

        this.setScene(scene);
        this.setResizable(false);
        this.show();
    }
}