package component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Recurrence extends VBox {

    private CheckBox chb_recurrence;
    private Label lbl_title;

    private Label lbl_repeat;
    private TextField txt_repeat_qtd;
    private ComboBox<String> chb_repeat_type;

    private HBox pnl_week;
    private ToggleButton dom, seg, ter, qua, qui, sex, sab;

    private Label lbl_end, lbl_ocurrence;
    private RadioButton rad_never, rad_in, rad_after;
    private DatePicker dt_end;
    private TextField txt_qtd_recurrence;

    public Recurrence() {

        this.getStylesheets().add(this.getClass().getResource("../css/recurrence.css").toExternalForm());

        /* title */
        chb_recurrence = new CheckBox();
        lbl_title = new Label("Recorrencia");
        lbl_title.setId("title");

        HBox hb_title = new HBox();
        hb_title.setAlignment(Pos.CENTER_LEFT);
        hb_title.getChildren().addAll(chb_recurrence, lbl_title);

        /* tipo de repetição */
        lbl_repeat = new Label("Repete a cada");
        txt_repeat_qtd = new TextField("1");
        txt_repeat_qtd.setPrefWidth(30);

        // força a caixa de texto a ser somente numérica
        txt_repeat_qtd.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    txt_repeat_qtd.setText(oldValue);
                }
            }
        });
        txt_repeat_qtd.focusedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(txt_repeat_qtd.getText().equals("") || txt_repeat_qtd.getText().equals("0")){
                    txt_repeat_qtd.setText("1");
                }
            }
        });

        chb_repeat_type = new ComboBox<String>();
        chb_repeat_type.setItems(FXCollections.observableArrayList("dia", "semana", "mes", "ano"));
        chb_repeat_type.getSelectionModel().select(1);

        HBox hb_type = new HBox();
        hb_type.setSpacing(5);
        hb_type.getChildren().addAll(lbl_repeat, txt_repeat_qtd, chb_repeat_type);

        /* dias da semana */
        dom = new ToggleButton("D");
        seg = new ToggleButton("S");
        ter = new ToggleButton("T");
        qua = new ToggleButton("Q");
        qui = new ToggleButton("Q");
        sex = new ToggleButton("S");
        sab = new ToggleButton("S");

        pnl_week = new HBox();
        pnl_week.setSpacing(8);
        pnl_week.getChildren().addAll(dom, seg, ter, qua, qui, sex, sab);

        ToggleButton week_now = (ToggleButton)pnl_week.getChildren().get(Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1);
        week_now.setSelected(true);

        /* fim da repetição */
        lbl_end = new Label("Termina");

        ToggleGroup rad_group = new ToggleGroup();

        rad_never = new RadioButton("Nunca");
        rad_never.setToggleGroup(rad_group);
        rad_never.setSelected(true);

        Calendar date = Calendar.getInstance();
        date.add(Calendar.MONTH, 1);

        rad_in = new RadioButton("Em");
        rad_in.setToggleGroup(rad_group);
        dt_end = new DatePicker(LocalDate.of(date.get(Calendar.YEAR), date.get(Calendar.MONTH) + 1, date.get(Calendar.DAY_OF_MONTH)));
        dt_end.setPrefWidth(120);
        
        HBox hb_end_in = new HBox();
        hb_end_in.setSpacing(5);
        hb_end_in.setAlignment(Pos.CENTER_LEFT);
        hb_end_in.getChildren().addAll(rad_in, dt_end);

        rad_after = new RadioButton("Apos");
        rad_after.setToggleGroup(rad_group);
        txt_qtd_recurrence = new TextField("1");
        txt_qtd_recurrence.setPrefWidth(30);

        // força a caixa de texto a ser somente numérica
        txt_qtd_recurrence.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    txt_qtd_recurrence.setText(oldValue);
                }
            }
        });
        txt_qtd_recurrence.focusedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(txt_qtd_recurrence.getText().equals("") || txt_qtd_recurrence.getText().equals("0")){
                    txt_qtd_recurrence.setText("1");
                }
            }
        });

        lbl_ocurrence = new Label("ocorrencia(s)");

        HBox hb_end_after = new HBox();
        hb_end_after.setSpacing(5);
        hb_end_after.setAlignment(Pos.CENTER_LEFT);
        hb_end_after.getChildren().addAll(rad_after, txt_qtd_recurrence, lbl_ocurrence);

        this.setSpacing(10);
        this.getChildren().addAll(hb_title, lbl_repeat, hb_type, pnl_week, lbl_end, rad_never, hb_end_in, hb_end_after);
    }

    public int getTypeRecurrence(){

        if (this.chb_recurrence.isSelected()) {
            
            return (this.chb_repeat_type.getSelectionModel().getSelectedIndex() + 1);
        }

        return 0;
    }

    public int getTypeEndRecurrence(){

        if(this.rad_never.isSelected())
            return 0;
        if(this.rad_in.isSelected())
            return 1;
        if(this.rad_after.isSelected())
            return 2;
        return 0;
    }

    public int getInterval(){

        return Integer.parseInt(this.txt_repeat_qtd.getText());
    }

    public boolean[] getWeek(){

        boolean[] week = new boolean[7];

        for (int i = 0; i < 7; i++) {
            
            week[i] = ((ToggleButton)pnl_week.getChildren().get(i)).isSelected();
        }

        return week;
    }

    public Date getEndDay(){

        return Date.valueOf(this.dt_end.getValue());
    }

    public int getQtdRecurrences(){

        return Integer.parseInt(this.txt_qtd_recurrence.getText());
    }
}