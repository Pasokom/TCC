package component.goal;

import component.homepage.Goals;
import db.functions.appointment.DeleteAppointment;
import db.functions.appointment.EditAppointment;
import db.pojo.goalPOJO.GoalDB;
import display.scenes.HomePage;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

public class GoalComponent extends VBox {

    private Label lbl_title;
    private Label lbl_qtd_week;
    private Label lbl_duration;
    private Label lbl_more;
    private GoalDB goal;
    private EditAppointment edit = new EditAppointment();

    public GoalComponent(GoalDB goal) {
        
        this.goal = goal;

        this.getStylesheets().add(this.getClass().getResource("../../css/goal_component.css").toExternalForm());
        this.getStyleClass().add("this");

        lbl_title = new Label(goal.getTitulo());
        lbl_title.setId("title");
        lbl_title.setPrefWidth(200);
        lbl_more = new Label();
        lbl_more.setVisible(false);
        lbl_more.setId("lbl_more");

        HBox hb_title = new HBox();
        hb_title.getChildren().addAll(lbl_title, lbl_more);

        lbl_qtd_week = new Label(getLabelQtdWeek(goal.getQtd_semana()));
        lbl_duration = new Label(getLabelDuration(goal.getDuracao_minutos()));

        GoalProgressBar bar = new GoalProgressBar(goal.getQtd_semana(), goal.getQtd_concluido());

        this.getChildren().addAll(hb_title, lbl_qtd_week, lbl_duration, bar);

        lbl_more.setOnMouseClicked(e -> {

            Popup popup = getMorePopup();

            Point2D point2d = this.localToScreen(0d, 0d);

			popup.setX(point2d.getX() + 260);
			popup.setY(point2d.getY() - 9);

            popup.show(this.getScene().getWindow());
        });

        this.setOnMouseEntered(e -> {

            lbl_more.setVisible(true);
        });

        this.setOnMouseExited(e -> {

            lbl_more.setVisible(false);
        });
    }

    private Popup getMorePopup() {

        Popup popup = new Popup();

        Label lbl_done = new Label("Marcar concluído");
        Label lbl_remove = new Label("Remover concluído");
        Label lbl_delete = new Label("Excluir meta");

        if (goal.getQtd_concluido() < 1) 
            lbl_remove.setDisable(true);

        if (goal.getQtd_concluido() >= goal.getQtd_semana())
            lbl_done.setDisable(true);

        lbl_done.setOnMouseClicked(e -> {

            goal.setQtd_concluido(goal.getQtd_concluido() + 1);
            edit.updateGoalWeek(goal.getCod_semana(), goal.getQtd_concluido());
            HomePage.goals.update();
            popup.hide();
        });

        lbl_remove.setOnMouseClicked(e -> {

            goal.setQtd_concluido(goal.getQtd_concluido() - 1);
            edit.updateGoalWeek(goal.getCod_semana(), goal.getQtd_concluido());
            HomePage.goals.update();
            popup.hide();
        });

        lbl_delete.setOnMouseClicked(e -> {
            
            DeleteAppointment appointment = new DeleteAppointment();
            appointment.delete(goal);
            HomePage.goals.update();
            popup.hide();
        });

        VBox vb_options = new VBox();
        vb_options.getChildren().addAll(lbl_done, lbl_remove, lbl_delete);
        vb_options.getStylesheets().add(this.getClass().getResource("../../css/options_popup.css").toExternalForm());
        vb_options.getStyleClass().add("this");

        DropShadow shadow = new DropShadow();
		shadow.setOffsetX(0);
		shadow.setOffsetY(0);

        vb_options.setEffect(shadow);

        popup.getContent().add(vb_options);
        popup.setAutoHide(true);

        return popup;
    }

    private String getLabelQtdWeek(int qtd) {

        if (qtd > 1) {

            return qtd + " vezes por semana";
        }

        return qtd + " vez por semana";
    }

    private String getLabelDuration(int minutes) {

        int hours = minutes / 60;

        if (hours > 0) {

            if (hours > 1) {
                
                return hours + " horas";
            }

            return hours + " hora";
        }

        return minutes + " minutos";
    }
}