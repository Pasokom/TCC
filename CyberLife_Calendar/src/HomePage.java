import java.io.FileNotFoundException;

import component.ListReminders;
import component.ProfileComponent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class HomePage extends Scene {

	private HBox h_content;
	
	private VBox vLeft, vRight;
	
	private ListReminders reminderList;
	
	private ProfileComponent profileContent;
	
	private AnchorPane layout;
	
	public HomePage() throws FileNotFoundException {
		super (new VBox());
		
		Main.main_stage.setWidth(800);
		Main.main_stage.setHeight(700);
		
		this.layout = new AnchorPane();

		this.reminderList = new ListReminders();	
		this.profileContent=new ProfileComponent();
		
		this.h_content=new HBox();
		
		this.vLeft = new VBox();
		this.vRight = new VBox();
		
		vRight.getChildren().add(profileContent);
		vRight.setAlignment(Pos.TOP_RIGHT);
		
		
		h_content.getChildren().addAll(reminderList, vRight);
		
		
		
		AnchorPane.setRightAnchor(vRight, 0d);
		
		layout.getChildren().add(vRight);
		
		
		
		
		
		
		
		
		
		
		AnchorPane.setBottomAnchor(h_content, 0d);
		AnchorPane.setTopAnchor(h_content, 0d);
		AnchorPane.setRightAnchor(h_content, 0d);
		AnchorPane.setLeftAnchor(h_content, 0d);
		
		layout.getChildren().add(h_content);
		
		
		
		
		
		this.setRoot(layout);
	}
}
