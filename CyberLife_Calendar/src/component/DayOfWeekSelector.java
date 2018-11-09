package component;

import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;

public class DayOfWeekSelector extends HBox {

	private CheckBox cbxDom, cbxSeg, cbxTer, cbxQua, cbxQui, cbxSex, cbxSab;
	
	public DayOfWeekSelector() {
		
		//criando checkbox para todos os dias da semana
		cbxDom = new CheckBox("Domingo");
		cbxSeg = new CheckBox("Segunda");
		cbxTer = new CheckBox("Terça");
		cbxQua = new CheckBox("Quarta");
		cbxQui = new CheckBox("Quinta");
		cbxSex = new CheckBox("Sexta");
		cbxSab = new CheckBox("Sábado");
		
		this.setSpacing(10);
		this.getChildren().addAll(cbxDom, cbxSeg, cbxTer, cbxQua, cbxQui,cbxSex,cbxSab);
	}
}
