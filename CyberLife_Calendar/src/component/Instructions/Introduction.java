package component.Instructions;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Main;


public class Introduction extends Scene{

    private Label lblTitle;
    private Label lblText;

    private Button btnNext;
    private Button btnPreview;
    private Button btnCancel;
    private Button btnEnd;

    private AnchorPane container;

    String nome[] = {instructionOne(), instructionTwo(), instructionThree()};

    private StackPane pane;
	private VBox main;
	private HBox tabSelector;

    public static int i = 0; 

    public Introduction() {

        super(new VBox());

        Main.main_stage.setWidth(600);
		Main.main_stage.setHeight(400);

        container = new AnchorPane();

        main = taVBox();
        
		container = new AnchorPane();

		pane = new StackPane();
		pane.getChildren().add(main);
        
		tabSelector = tHBox();
        
		AnchorPane.setTopAnchor(pane, 5d);
		AnchorPane.setRightAnchor(pane, 0d);
		AnchorPane.setLeftAnchor(pane, 0d);
		AnchorPane.setBottomAnchor(tabSelector, 0d);
        
		container.getChildren().addAll(pane, tabSelector);
        
        pane.requestFocus();
        
		this.setRoot(container);

    }
    
    public VBox taVBox(){
        
        lblTitle = new Label();
        lblText = new Label();
        btnNext = new Button("Próximo");
        btnPreview = new Button("Anterior");
        btnCancel = new Button("Cancelar");
        btnEnd = new Button("Finalizar");
        
        VBox content = new VBox();
		content.setSpacing(5);

        GridPane pnl_schedule = new GridPane();
		pnl_schedule.setHgap(5);
        pnl_schedule.setVgap(5);
        
        pnl_schedule.add(lblTitle, 0, 0, 2, 1);
        pnl_schedule.add(lblText, 0, 2, 2, 1);
        pnl_schedule.add(btnPreview, 2, 4);
        pnl_schedule.add(btnNext, 3, 4);
        pnl_schedule.add(btnCancel, 4, 4);
        pnl_schedule.add(btnEnd, 5, 4);

        lblTitle = new Label("Instrodução");

        lblText.setText(nome[i]);

        btnNext.setVisible(true);
        btnPreview.setVisible(false);
        btnPreview.setVisible(false);

        if(i == 2){
            btnNext.setVisible(false);
            btnEnd.setVisible(true);
        }
        if(i == 0){
            btnPreview.setVisible(false);
            btnEnd.setVisible(false);
        }

        content.getChildren().addAll(lblText, pnl_schedule);

        return content;
    }

    public HBox tHBox(){

        HBox content = new HBox();
        content.setSpacing(5);

        btnNext.setOnAction(e ->{
            i++;
            lblText.setText(nome[i]);
        });

        btnPreview.setOnAction(e -> {
            i--;
            lblText.setText(nome[i--]);
        });

        btnCancel.setOnMouseClicked(e ->{
            ((Stage)this.getWindow()).close();
        });

        Region region = new Region();
		HBox.setHgrow(region, Priority.ALWAYS);

		content.prefWidthProperty().bind(container.widthProperty());
		content.getChildren().addAll(btnNext, btnPreview, region, btnCancel);

        return content;
    }

    public String instructionOne(){
        return "Bem-Vindo\n Você acaba de entrar em nosso sistema, o CyberLife. \n O CyberLife é desenvolvido pela empresa" + 
        " CyberSystem, que trabalha com diversos projetos na linguagem de programação JAVA.\n Nossa empresa foi criada " + 
        "em 2018 e segue tendo várias ideias de inovação (você vai ouvir falar bastante da gente). \n\n Sem enrolação, lhes apresento " +
        "CyberLife!";
   
    }

    public String instructionTwo(){
        return "Modo de uso \n Na página principal você tem a homepage, onde você consultará seus eventos, lembretes, metas e projetos. Os " +
        "Eventos estará em roxo, os lembretes em azul...";
    }

    public String instructionThree(){
        return "lOGOUT \n  Você também pode visualizar uma seta para a direita que indica a 'saída' do programa. Lá você terá " +
        "as opções sair, sair e fechar programa e ajuda. \n A opção sair, sai do seu usuário porém o programa voltará para a tela de login." +
        "\n A opção sair e fechar o programa te dá a possibilidade de sair do usuário e encerrar o programa, tornando assim, " +
        "o login obrigatório da próxima vez que entrar. \n" +
        "A opção ajuda, bom, você está nela agora mesmo! \n\n" +
        "Vamos lá?";
    }
}