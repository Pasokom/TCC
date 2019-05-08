package component.homepage;

import db.pojo.projectPOJO.ProjectDB;
import javafx.scene.control.ToggleButton;

public class ProjectButton extends ToggleButton {

    private ProjectDB projeto;

    /**
     * @return the projeto
     */
    public ProjectDB getProjeto() {
        return projeto;
    }

    /**
     * @param projeto the projeto to set
     */
    public void setProjeto(ProjectDB projeto) {
        this.projeto = projeto;
    }
}