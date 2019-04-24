package component.goal;

import javafx.geometry.Insets;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;

public class GoalProgressBar extends StackPane {

    private int num_steps;
    private int complete_steps;

    public GoalProgressBar(int num_steps, int complete_steps) {
        this.num_steps = num_steps;
        this.complete_steps = complete_steps;

        ProgressBar pb = new ProgressBar(0);
        pb.prefWidthProperty().bind(this.widthProperty());

        double progress = this.complete_steps * 100d / this.num_steps / 100d;
        pb.setProgress(progress);
        
        this.setPadding(new Insets(15, 0, 0, 0));
        this.getChildren().add(pb);
    }
}