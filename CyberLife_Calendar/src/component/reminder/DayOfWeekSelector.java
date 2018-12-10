package component.reminder;

import java.util.ArrayList;

import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;

public class DayOfWeekSelector extends HBox {

	private CheckBox cbxDom, cbxSeg, cbxTer, cbxQua, cbxQui, cbxSex, cbxSab;

	public DayOfWeekSelector() {

		// criando checkbox para todos os dias da semana
		cbxDom = new CheckBox("Domingo");
		cbxSeg = new CheckBox("Segunda");
		cbxTer = new CheckBox("Terça");
		cbxQua = new CheckBox("Quarta");
		cbxQui = new CheckBox("Quinta");
		cbxSex = new CheckBox("Sexta");
		cbxSab = new CheckBox("Sábado");

		this.setSpacing(10);
		this.getChildren().addAll(cbxDom, cbxSeg, cbxTer, cbxQua, cbxQui, cbxSex, cbxSab);
	}

	/**
	 * adiciona os dias da semana selecionados em uma lista e retorna ela
	 * 
	 * tem que tratar isso do jeito certo, ou vai dar muita bosta
	 * 
	 * @return
	 */
	public ArrayList<Boolean> selected_day() {
		ArrayList<Boolean> list = new ArrayList<>();
		list.add(cbxSeg.selectedProperty().get());
		list.add(cbxTer.selectedProperty().get());
		list.add(cbxQua.selectedProperty().get());
		list.add(cbxQui.selectedProperty().get());
		list.add(cbxSex.selectedProperty().get());
		list.add(cbxSab.selectedProperty().get());
		list.add(cbxDom.selectedProperty().get());
		return list;
	}

	public boolean[] selected_days() {

		boolean[] days = new boolean[7];

		days[0] = cbxDom.isSelected();
		days[1] = cbxSeg.isSelected();
		days[2] = cbxTer.isSelected();
		days[3] = cbxQua.isSelected();
		days[4] = cbxQui.isSelected();
		days[5] = cbxSex.isSelected();
		days[6] = cbxSab.isSelected();

		return days;
	}

	// TODO funÃ§Ã£o para selecionar dia passado por parametro

	public void setSelect(int day) {

		if (day == 0)
			this.cbxDom.setSelected(true);
		if (day == 1)
			this.cbxSeg.setSelected(true);
		if (day == 2)
			this.cbxTer.setSelected(true);
		if (day == 3)
			this.cbxQua.setSelected(true);
		if (day == 4)
			this.cbxQui.setSelected(true);
		if (day == 5)
			this.cbxSex.setSelected(true);
		if (day == 6)
			this.cbxSab.setSelected(true);

	}

}
