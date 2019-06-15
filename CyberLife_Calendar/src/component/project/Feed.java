package component.project;

import java.util.ArrayList;

import db.functions.appointment.LoadAppointment;
import db.pojo.FeedTaskDB;
import javafx.geometry.Pos;
import javafx.scene.control.Separator;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;

public class Feed extends VBox {

    private int cod_project;

    public Feed(int cod_project) {

        this.cod_project = cod_project;

        LoadAppointment appointment = new LoadAppointment();
        ArrayList<FeedTaskDB> tasks = appointment.loadFinishedTasks(this.cod_project);

        for (FeedTaskDB task : tasks) {
            
            FeedComponent feedComponent = new FeedComponent(task);
            feedComponent.setMaxWidth(800);

            Separator separator = new Separator();
            separator.setMaxWidth(800);

            this.getChildren().addAll(feedComponent, separator);
        }

        //this.setSpacing(10);
        DropShadow shadow = new DropShadow();
		shadow.setOffsetX(0);
		shadow.setOffsetY(0);

        this.setEffect(shadow);
        this.setAlignment(Pos.CENTER);
    }
}