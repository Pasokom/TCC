
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class Login extends Scene {

	private Label lblTitle;
	private Label lblEmail;
	private Label lblSenha;

	private Button btnEntrar;
	private Button btnCadastrar;
	
	private TextField txtEmail;
	private PasswordField pswSenha;
	
	private Label lblTitleCadast;
	private Label lblNome;
	private Label lblSobrenome;
	private Label lblEmailCadast;
	private Label lblSenhaCadast;
	private Label lblSenhaConfirm;
	
	private TextField txtNomeCadast;
	private TextField txtSobrenomeCadast;
	private TextField txtEmailCadast;
	private PasswordField pswCadast;
	private PasswordField pswSenhaConfirm;
	private TextField txtNome;
	
	
	public Login() {

		/**
		 * quando voce extender Scene tem que colocar isso aqui, OBRIGATORIO n�o precisa
		 * necessariamente ser uma hbox, qualquer componente serve
		 */
		super(new HBox());

		lblTitle = new Label("Login");
		lblTitle.setFont(new Font(45));

		lblNome = new Label("Nome: ");
		lblSenha = new Label("Senha: ");

		btnEntrar = new Button("Entrar");
		btnEntrar.setOnAction(event -> {
			new Alert(AlertType.CONFIRMATION, "Voc� t� dentro").show();
		});
		
		lblTitleCadast = new Label("Cadastre-se");
		lblTitleCadast.setFont(new Font(35));
		
		lblTitle = new Label("Login");
		lblTitle.setFont(new Font(35));
		
		lblEmail = new Label("E-mail: ");
		txtEmail = new TextField();
		
		lblEmailCadast = new Label("E-mail: ");
		lblSenhaCadast = new Label("Senha: ");
		
		
		txtNomeCadast = new TextField();
		txtSobrenomeCadast = new TextField();
		txtEmailCadast = new TextField();
		
		pswCadast = new PasswordField();
		pswSenhaConfirm = new PasswordField();

		btnEntrar = new Button("Entrar");
		btnEntrar.setOnAction(event->{
			new Alert(AlertType.CONFIRMATION, txtNomeCadast.getText() + " est� dentro!").show();
			
		});
		btnCadastrar = new Button("Cadastrar-se");
		btnCadastrar.setOnAction(event -> {
			
		});

		txtNome = new TextField();
		pswSenha = new PasswordField();

		GridPane pnlLayout = new GridPane();

		pnlLayout.setPadding(new Insets(50));

		pnlLayout.setVgap(10);
		pnlLayout.setHgap(10);


		pnlLayout.add(lblNome, 0, 1);
		pnlLayout.add(lblSenha, 0, 2);

		pnlLayout.add(btnEntrar, 1, 3);
		pnlLayout.add(btnCadastrar, 2, 3);

		pnlLayout.add(txtNome, 1, 1, 2, 1);
		pnlLayout.add(pswSenha, 1, 2, 2, 1);

		txtNomeCadast = new TextField();
	    pswCadast = new PasswordField();	    
		
		
	    AnchorPane aPane = new AnchorPane();
	    
	    AnchorPane.setRightAnchor(pnlLayout, 50d);
	    AnchorPane.setTopAnchor(pnlLayout, 20d);
		
		pnlLayout.setPadding(new Insets(180));

		pnlLayout.setVgap(10);
		pnlLayout.setHgap(10);
		
		pnlLayout.add(lblTitleCadast, 0, 4, 2, 1);	
		pnlLayout.add(lblTitle, 0, 0, 2, 1);
		
		pnlLayout.add(lblEmail, 0, 1);
		pnlLayout.add(txtEmail, 0, 2);
	    
	    aPane.getChildren().add(pnlLayout);
		
		this.setRoot(aPane);
		
	}
}
