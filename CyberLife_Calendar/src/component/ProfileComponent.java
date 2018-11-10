package component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ProfileComponent extends HBox {

	
	private Label lblName, lblEmail;
	private ImageView image;
	private VBox vLabels;
	
	public ProfileComponent () throws FileNotFoundException { 
		
		this.lblName = new Label("aqui vai o nome");
		this.lblEmail = new Label(" aqui vai o email");
		
		this.image = new ImageView();
		int size = 230;
		
		image.setImage(new Image(new FileInputStream(new File("resources/tests.png"))));
		
		image.setFitWidth(size);
		image.setFitHeight(size);
		
		this.vLabels = new VBox();
		
		vLabels.setAlignment(Pos.CENTER);
		vLabels.getChildren().addAll(lblName, lblEmail);
	
		this.setAlignment(Pos.CENTER);
				
		this.getChildren().addAll(vLabels, image);
	}
}










