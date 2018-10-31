import java.io.FileNotFoundException;

import component.ListReminders;
import component.ProfileComponent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class HomePage extends Scene {

	private VBox vLeft, vRight;
	private HBox hContent;
	
	private ListReminders reminderList;
	
	
	private ProfileComponent profileContent;
	
	public HomePage() throws FileNotFoundException {
		super (new VBox());
		
		this.vLeft = new VBox();
		this.vRight = new VBox();
		
		this.hContent = new HBox();
		
		this.profileContent = new ProfileComponent();
		
		
		this.reminderList=new ListReminders();
		vLeft.getChildren().add(reminderList);
		
		
		vRight.getChildren().add(profileContent);
		
		this.hContent.getChildren().addAll(vLeft, vRight);
		
		this.setRoot(hContent);
	}








}
