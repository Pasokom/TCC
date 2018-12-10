package component.Info;

import db.pojo.eventPOJO.EventDB;
import db.pojo.reminderPOJO.ReminderDB;
import javafx.scene.control.Label;

/**
 * @author Manoel
 * uma label que mostra ao usuario a repeticao de qualquer compromisso
 */
public class RecurrenceInfo extends Label {

    public RecurrenceInfo(EventDB event) {
        
        String tipoRepeticao = "A cada " + event.getHorario_evento().getIntervalo();
        int hr_intervalo = event.getHorario_evento().getIntervalo();
			
		switch (event.getTipo_repeticao()) {
		
		case 1:
			if(hr_intervalo > 1)
				tipoRepeticao += " dias";
			else 
				tipoRepeticao = "Todo dia";
			break;
		case 2:
			if(hr_intervalo > 1)
				tipoRepeticao += " semanas";
			else 
				tipoRepeticao = "Toda semana";
			break;
		case 3:
			if(hr_intervalo > 1)
				tipoRepeticao += " meses";
			else 
				tipoRepeticao = "Todo mês";
			break;
		case 4:
			if(hr_intervalo > 1)
				tipoRepeticao += " anos";
			else 
				tipoRepeticao = "Todo ano";
			break;
		default:
			tipoRepeticao = "";
			break;
        }
        
        this.setText(tipoRepeticao);
    }

    public RecurrenceInfo(ReminderDB reminder) {
        
        String tipoRepeticao = "A cada " + reminder.getlReminderSchedule().get(0).getRecurrence();
        int hr_intervalo = reminder.getRecurrenceType();
			
		switch (reminder.getRecurrenceType()) {
		
		case 1:
			if(hr_intervalo > 1)
				tipoRepeticao += " dias";
			else 
				tipoRepeticao = "Todo dia";
			break;
		case 2:
			if(hr_intervalo > 1)
				tipoRepeticao += " semanas";
			else 
				tipoRepeticao = "Toda semana";
			break;
		case 3:
			if(hr_intervalo > 1)
				tipoRepeticao += " meses";
			else 
				tipoRepeticao = "Todo mês";
			break;
		case 4:
			if(hr_intervalo > 1)
				tipoRepeticao += " anos";
			else 
				tipoRepeticao = "Todo ano";
			break;
		default:
			tipoRepeticao = "";
			break;
        }
        
        this.setText(tipoRepeticao);
    }
}