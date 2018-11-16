package component.reminder;

import java.util.ArrayList;

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
	
	/** 
	 * adiciona os dias da semana selecionados em uma lista e retorna ela 
	 * @return
	 */
	public ArrayList<Boolean> test() { 
		ArrayList<Boolean> list = new ArrayList<>();
		list.add(cbxDom.selectedProperty().get());
		list.add(cbxSeg.selectedProperty().get());
		list.add(cbxTer.selectedProperty().get());
		list.add(cbxQua.selectedProperty().get());
		list.add(cbxQui.selectedProperty().get());
		list.add(cbxSex.selectedProperty().get());
		list.add(cbxSab.selectedProperty().get());
		return list;
	}
	
	
}
