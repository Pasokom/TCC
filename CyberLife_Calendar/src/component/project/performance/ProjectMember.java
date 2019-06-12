package component.project.performance;

import db.pojo.UserDB;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;

public class ProjectMember extends StackPane{

    public ProjectMember(UserDB user, int cod_projeto) {
        
        String url = "http://localhost/cyberlife/calendar/API/charts/member_performance.php";
        url += ("?usuario=" + user.getCod_usuario());
        url += ("&projeto=" + cod_projeto);
        url += ("&nome=" + user.getNome() + " " + user.getSobrenome());

        WebView webView = new WebView();
        webView.getEngine().load(url);

        this.setPrefSize(500, 300);

        this.getChildren().add(webView);

        DropShadow shadow = new DropShadow();
		shadow.setOffsetX(0);
		shadow.setOffsetY(0);

        this.setEffect(shadow);
    }
}