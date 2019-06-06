package component.project;

import db.pojo.LabelDB;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;

public class LabelProgress extends StackPane {

    public LabelProgress(LabelDB label) {

        String url = "http://localhost/cyberlife/calendar/API/charts/label_progress.php?";
        url += ("marcador=" + label.getNome_marcador());
        url += ("&projeto=" + label.getFk_projeto());

        WebView webView = new WebView();
        webView.getEngine().load(url);

        this.getChildren().add(webView);
        this.setPrefSize(200, 250);

        DropShadow shadow = new DropShadow();
		shadow.setOffsetX(0);
		shadow.setOffsetY(0);

        this.setEffect(shadow);
    }
}