package listeners.windows;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class Close implements EventHandler<ActionEvent> {
	
	private Stage parent;
	
	public Close(Stage parent) {
		this.parent = parent;
	}
	@Override
	public void handle(ActionEvent event) {
		this.parent.close();
	}
}
