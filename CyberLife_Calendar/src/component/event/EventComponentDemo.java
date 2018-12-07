package component.event;

import db.pojo.eventPOJO.EventDB;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class EventComponentDemo extends HBox {

    private Label lblTitulo;

    public EventComponentDemo(EventDB event) {
        
        this.getStylesheets().add(this.getClass().getResource("/css/eventComponent.css").toExternalForm());
		this.setId("this");

        lblTitulo = new Label(event.getTitulo());
        lblTitulo.prefWidthProperty().bind(this.widthProperty());
        lblTitulo.setId("demo_card");

        this.getChildren().add(lblTitulo);
    }
}