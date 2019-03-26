package component.appointment;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import statics.Enums.DialogResult;

/**
 * DeleteConfirmation
 */
public class DeleteConfirmation extends Stage {

    Label lbl_title;
    RadioButton rad_one, rad_all;
    Button btn_cancel;
    Button btn_delete;

    DialogResult result;
    boolean all;

    public DeleteConfirmation() {

        lbl_title = new Label("Excluir");
        lbl_title.setId("title");

        ToggleGroup gp_radio = new ToggleGroup();

        rad_one = new RadioButton("Apenas esse");
        rad_one.setSelected(true);
        rad_one.setToggleGroup(gp_radio);
        rad_all = new RadioButton("Excluir e parar de marcar");
        rad_all.setToggleGroup(gp_radio);

        btn_cancel = new Button("Cancelar");
        btn_delete = new Button("Deletar");
        btn_delete.setId("btn_delete");

        btn_cancel.setOnAction(e -> {

            this.result = DialogResult.CANCEL;
            this.close();
        });

        btn_delete.setOnAction(e -> {

            this.all = rad_all.isSelected() ? true : false; 
            this.result = DialogResult.OK;
            this.close();
        });

        HBox hb_options = new HBox();
        hb_options.getStyleClass().add("hbox");
        hb_options.setAlignment(Pos.CENTER_RIGHT);
        hb_options.getChildren().addAll(btn_cancel, btn_delete);

        VBox root = new VBox();
        root.getStyleClass().add("vbox");
        root.getChildren().addAll(lbl_title, rad_one, rad_all, hb_options);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(this.getClass().getResource("../../css/delete_confirmation.css").toExternalForm());

        this.setWidth(400);
        this.setHeight(190);
        this.setScene(scene);
        this.initStyle(StageStyle.UTILITY);
    }

    /**
     * @return the result
     */
    public DialogResult getResult() {
        return result;
    }

    /**
     * @return the all
     */
    public boolean isAll() {
        return all;
    }
}