import java.io.FileNotFoundException;

import component.ListReminders;
import component.ProfileComponent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class HomePage extends Scene {

	private VBox vRight;
	private HBox hContent;
	private HBox hLeft, hRight;
	
	private ListReminders reminderList;
	
	private ProfileComponent profileContent;
	
	public HomePage() throws FileNotFoundException {
		super (new VBox());
		
		//Main.main_stage.setWidth(600);
		//Main.main_stage.setHeight(500);
		
		this.vRight = new VBox();
		
		this.hContent = new HBox();
		
		this.profileContent = new ProfileComponent();
		
		this.reminderList=new ListReminders();
		
		vRight.getChildren().add(profileContent);
		
		this.hRight = new HBox();
		this.hLeft = new HBox();

		hRight.getChildren().add(vRight);
		hLeft.getChildren().add(reminderList);
		hLeft.setPrefWidth(400);
		
		
		hRight.setAlignment(Pos.CENTER_RIGHT);
		hLeft.setAlignment(Pos.CENTER_LEFT);
		
		this.hContent.getChildren().addAll(hLeft, hRight);
		
		AnchorPane.setLeftAnchor(hRight, 0d);
		AnchorPane.setRightAnchor(hRight, 0d);
		AnchorPane.setTopAnchor(hRight, 0d);
		AnchorPane.setBottomAnchor(hRight, 0d);
		
		
		AnchorPane.setRightAnchor(hLeft, 0d);
		AnchorPane.setLeftAnchor(hLeft, 0d);
		AnchorPane.setTopAnchor(hLeft, 0d);
		AnchorPane.setBottomAnchor(hLeft, 0d);
		
		
		
		AnchorPane layout= new  AnchorPane();
		
		layout.getChildren().add(hRight);
		layout.getChildren().add(hLeft);
		
		layout.getChildren().add(hContent);
		this.setRoot(layout);
	}
}
