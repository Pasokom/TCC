package component.Instructions;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Shadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

/**
 * Intro_Foda
 */
public class Intro_Foda extends Popup{

    Label lbl_title;
    Label lbl_intro;

    Button btnNext;
    Button btnPreview;

    String pag[] = {instructionOne(), instructionTwo(), instructionThree()};
    int pos = 0;

    public Intro_Foda() {

        VBox vb_intro = new VBox();
        HBox hb_intro = new HBox();

        vb_intro.getStylesheets().add(this.getClass().getResource("../../css/introduction.css").toExternalForm());
        vb_intro.getStyleClass().add("vbox");
        vb_intro.setPrefWidth(350);

        DropShadow shadow = new DropShadow();
        vb_intro.setEffect(shadow);

        btnNext = new Button(">");
        btnPreview = new Button("<");

        lbl_title = new Label("Bem vindo!");
        lbl_title.setId("title");
        lbl_intro = new Label(pag[pos]);
        lbl_intro.setWrapText(true);

        Region region = new Region();

        hb_intro.getChildren().addAll(btnPreview, region, btnNext);
        HBox.setHgrow(region, Priority.ALWAYS);
        btnNext.setAlignment(Pos.CENTER_RIGHT);

        vb_intro.getChildren().addAll(lbl_title, lbl_intro, hb_intro);

        btnNext.setOnMouseClicked(e ->{
            btnPreview.setDisable(false);

            pos ++;
            lbl_intro.setText(pag[pos]);

            if(pos == 2){
                btnNext.setDisable(true);
            }
        });

        btnPreview.setDisable(true);

        btnPreview.setOnMouseClicked(e ->{
            btnNext.setDisable(false);
            pos --;
            lbl_intro.setText(pag[pos]);
            
            if(pos == 0){
                btnPreview.setDisable(true);
            }
        });

        vb_intro.setSpacing(15);

        this.getContent().add(vb_intro);
        this.setAutoHide(true);
    }

    public String instructionOne(){
        return "Você acaba de entrar em nosso sistema, o CyberLife. \nO CyberLife foi desenvolvido pela empresa" + 
        " CyberSystem\nNossa empresa foi criada " + 
        "em 2018 e segue tendo váriass ideias de inovação (você vai ouvir falar bastante da gente).";
   
    }

    public String instructionTwo(){
        return "Modo de uso \n\nNa página principal você tem a homepage, onde você consultará seus eventos, lembretes, metas e projetos. Os " +
        "Eventos estarão em roxo, os lembretes em azul...";
    }

    public String instructionThree(){
        return "LOGOUT \n\nVocê também pode visualizar uma seta para a direita que indica a 'saída' do programa. Lá você terá " +
        "as opções sair, sair e fechar programa e ajuda. \n A opção sair, sai do seu usuário porém o programa voltará para a tela de login." +
        "\n A opção sair e fechar o programa te dá a possibilidade de sair do usuário e encerrar o programa, tornando assim, " +
        "o login obrigatório da próxima vez que entrar. \n" +
        "A opção ajuda, bom, você está nela agora mesmo! \n\n" +
        "Vamos lá?";
    }

    
}