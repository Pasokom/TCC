package component.homepage;

import java.util.ArrayList;

import component.goal.GoalComponent;
import db.functions.appointment.LoadAppointment;
import db.pojo.goalPOJO.GoalDB;
import javafx.scene.layout.FlowPane;

public class Goals extends FlowPane {

    public Goals() {
        
        this.getStylesheets().add(this.getClass().getResource("../../css/goals.css").toExternalForm());
        this.getStyleClass().add("this");

        LoadAppointment load = new LoadAppointment();
        ArrayList<GoalDB> goals = load.loadGoals();

        for (GoalDB goal : goals) {
            
            this.getChildren().add(new GoalComponent(goal));
        }
    }

    public void update() {

        this.getChildren().clear();

        LoadAppointment load = new LoadAppointment();
        ArrayList<GoalDB> goals = load.loadGoals();

        for (GoalDB goal : goals) {
            
            this.getChildren().add(new GoalComponent(goal));
        }
    }
}