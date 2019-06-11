package component.project;

import db.pojo.UserDB;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;

public class MemberProgress extends StackPane {

    public MemberProgress(UserDB user, int cod_projeto) {

        String url = "http://localhost/cyberlife/calendar/API/charts/member_progress.php";
        url += ("?usuario=" + user.getCod_usuario());
        url += ("&projeto=" + cod_projeto);
        url += ("&nome=" + user.getNome() + " " + user.getSobrenome());

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