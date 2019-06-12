package component.project.performance;

import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;

public class ProjectTeam extends StackPane {

    public ProjectTeam(int project) {
        
        String url = "http://localhost/cyberlife/calendar/API/charts/team_performance.php?";
        url += ("projeto=" + project);

        WebView webView = new WebView();
        webView.getEngine().load(url);

        this.getChildren().add(webView);
        this.setPrefHeight(400);

        DropShadow shadow = new DropShadow();
		shadow.setOffsetX(0);
		shadow.setOffsetY(0);

        this.setEffect(shadow);
    }
}