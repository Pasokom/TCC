package component.project;

import java.util.ArrayList;

import db.functions.appointment.LoadAppointment;
import db.pojo.FeedTaskDB;
import javafx.scene.layout.VBox;

public class Feed extends VBox {

    private int cod_project;

    public Feed(int cod_project) {

        this.cod_project = cod_project;

        LoadAppointment appointment = new LoadAppointment();
        ArrayList<FeedTaskDB> tasks = appointment.loadFinishedTasks(this.cod_project);

        for (FeedTaskDB task : tasks) {
            
            FeedComponent feedComponent = new FeedComponent(task);
            this.getChildren().add(feedComponent);
        }

        this.setSpacing(10);
    }
}