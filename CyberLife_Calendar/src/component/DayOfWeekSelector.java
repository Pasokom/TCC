package component;

import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;

public class DayOfWeekSelector extends HBox {

	private ToggleButton cbxDom, cbxSeg, cbxTer, cbxQua, cbxQui, cbxSex, cbxSab;
	
	public DayOfWeekSelector() {
		
		//criando checkbox para todos os dias da semana
		cbxDom = new ToggleButton("Domingo");
		cbxSeg = new ToggleButton("Segunda");
		cbxTer = new ToggleButton("Terça");
		cbxQua = new ToggleButton("Quarta");
		cbxQui = new ToggleButton("Quinta");
		cbxSex = new ToggleButton("Sexta");
		cbxSab = new ToggleButton("Sábado");
		
		this.setSpacing(10);
		this.getChildren().addAll(cbxDom, cbxSeg, cbxTer, cbxQua, cbxQui,cbxSex,cbxSab);
	}
	
	public ArrayList<Integer> getSelectedDays() {
		
		ArrayList<Integer> list = new ArrayList<>();
		
		for (Node button : this.getChildren()) {
			if(((ToggleButton)button).isSelected()) {
				list.add(button.getParent().getChildrenUnmodifiable().indexOf(button));
			}
		}
		
		return list;
	}
}
