package component.homepage;

import java.util.ArrayList;

import component.appointment.AppointmentMini;
import db.pojo.AppointmentDB;
import db.pojo.HolidayDB;
import db.pojo.eventPOJO.EventDB;
import db.pojo.reminderPOJO.ReminderDB;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import statics.Enums.DayType;

public class CalendarDayComponent extends AnchorPane {

    private Label more;
    private int maxCards;
    private ArrayList<AppointmentDB> appointments;
    private VBox container;

    public CalendarDayComponent(int day, ArrayList<AppointmentDB> appointments, DayType type) {
        
        this.appointments = appointments;

        this.setMinSize(0, 0);

        this.getStylesheets().add(this.getClass().getResource("/css/calendar_day.css").toExternalForm());
        this.setId("this");
        this.setPadding(new Insets(3));

        Circle profileImg = new Circle();
		profileImg.setRadius(15);
		profileImg.setFill(Color.rgb(0, 0, 0, 0.0));
		profileImg.setCenterX(100);
		profileImg.setCenterY(100);

		StackPane userImg = new StackPane();
        Label userInitial = new Label(String.valueOf(day));
        userInitial.setPrefWidth(20);
        userInitial.setId("day");

        if(type == DayType.OTHER_MONTH)
            userInitial.setId("other_month");
        if(type == DayType.HOLIDAY)
            userInitial.setId("holiday_month");
        if(type == DayType.TODAY)
            userInitial.setId("today");

		userImg.getChildren().addAll(profileImg, userInitial);
        
        StackPane menu = new StackPane();
        ImageView item = new ImageView();
        item.setFitHeight(20);
        item.setPreserveRatio(true);
        item.setId("menu");
        menu.getChildren().add(item);
        menu.setAlignment(Pos.CENTER_RIGHT);
        menu.prefWidthProperty().bind(this.widthProperty());
        menu.setVisible(false);

        this.setOnMouseEntered(e -> {
            //menu.setVisible(true);
        });

        this.setOnMouseExited(e -> {
            //menu.setVisible(false);
        });
        
        VBox content = new VBox();

        HBox header = new HBox();
        header.setId("header");
        header.getChildren().addAll(userImg, menu);

        more = new Label("+" + (appointments.size() - maxCards));
        more.prefWidthProperty().bind(this.widthProperty());
        more.setAlignment(Pos.CENTER_RIGHT);

        container = new VBox();
        container.setSpacing(2);

        content.setSpacing(4);
        content.getChildren().addAll(header, container);

        AnchorPane.setBottomAnchor(content, 0d);
        AnchorPane.setTopAnchor(content, 0d);
        AnchorPane.setLeftAnchor(content, 0d);
        AnchorPane.setRightAnchor(content, 0d);

        this.getChildren().add(content);

        AnchorPane.setBottomAnchor(more, 1d);
        AnchorPane.setRightAnchor(more, 1d);
        this.getChildren().add(more);

        this.heightProperty().addListener((obs, oldVal, newVal) -> {

            this.maxCards = (int)((this.getHeight() - 50) / 20);
            addAppointments();
        });
    }

    private void addAppointments(){

        container.getChildren().clear();

        more.setText("+" + (appointments.size() - maxCards));

        for (int i = 0; i < appointments.size(); i++) {
            
            if(i < maxCards){

                switch (appointments.get(i).getType()) {
                    case "reminder":
                        container.getChildren().add(new AppointmentMini((ReminderDB)appointments.get(i)));
                        break;
                    case "event":
                        container.getChildren().add(new AppointmentMini((EventDB)appointments.get(i)));
                        break;
                    case "holiday":
                        container.getChildren().add(new AppointmentMini((HolidayDB)appointments.get(i)));
                        break;
                    default:
                        container.getChildren().add(new AppointmentMini());
                        break;
                }
            }
        }

        if(appointments.size() > maxCards && maxCards > 0){

            more.setVisible(true);
        }else{
            more.setVisible(false);
        }
    }
}