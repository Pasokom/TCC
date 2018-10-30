package component;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ProfileComponent extends HBox {

	
	private Label lblName, lblEmail;
	private ImageView image;
	private VBox vLabels;
	
	public ProfileComponent () { 
		
		this.lblName = new Label("aqui vai o nome ");
		this.lblEmail = new Label(" aqui vai o email	  ");
		
		this.image = new ImageView();
		
		int size = 50;
		
		image.setFitWidth(size);
		image.setFitHeight(size);
		
		this.vLabels = new VBox();
		
		
		vLabels.getChildren().addAll(lblName, lblEmail);
	
		this.setAlignment(Pos.CENTER_RIGHT);
				
				
		this.getChildren().addAll(vLabels, image);
	}
}
