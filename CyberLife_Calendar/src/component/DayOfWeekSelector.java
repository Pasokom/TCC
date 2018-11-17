package component;

import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;

public class DayOfWeekSelector extends HBox {

	private ToggleButton cbxDom, cbxSeg, cbxTer, cbxQua, cbxQui, cbxSex, cbxSab;
	
	public DayOfWeekSelector() {
		
		//criando checkbox para todos os dias da semana
		cbxDom = new ToggleButton("Domingo");
		cbxSeg = new ToggleButton("Segunda");
		cbxTer = new ToggleButton("Ter�a");
		cbxQua = new ToggleButton("Quarta");
		cbxQui = new ToggleButton("Quinta");
		cbxSex = new ToggleButton("Sexta");
		cbxSab = new ToggleButton("S�bado");
		
		this.setSpacing(10);
		this.getChildren().addAll(cbxDom, cbxSeg, cbxTer, cbxQua, cbxQui,cbxSex,cbxSab);
	}
}
