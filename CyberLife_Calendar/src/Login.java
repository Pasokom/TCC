

import org.omg.CosNaming._BindingIteratorImplBase;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class Login extends Scene{

	private Label lblTitle;
	private Label lblEmail;
	private Label lblSenha;
	
	private Label lblTitleCadast;
	private Label lblNomeCadast;
	private Label lblSobrenomeCadast;
	private Label lblEmailCadast;
	private Label lblSenhaCadast;
	private Label lblSenhaConfirmCadast;
	
	private Button btnEntrar;
	private Button btnCadastrar;
	
	private TextField txtNome;
	private PasswordField pswSenha;
	
	private TextField txtNomeCadast;
	private TextField txtSobrenomeCadast;
	private TextField txtEmailCadast;
	private	PasswordField pswSenhaCadast;
	private	PasswordField pswSenhaConfirmCadast;
	
	
	public Login() {
		
		/** 
		 * quando voce extender Scene tem que colocar isso aqui, OBRIGATORIO
		 	nï¿½o precisa necessariamente ser uma hbox, qualquer componente serve
		 */
		super(new HBox());
		
		
		lblTitle = new Label("Login");
		lblTitle.setFont(new Font(45));
		lblTitleCadast = new Label("Cadastro");
		lblTitleCadast.setFont(new Font(45));
		
		lblEmail = new Label("Nome: ");
		lblSenha = new Label("Senha: ");
		
		btnEntrar = new Button("Entrar");
		btnEntrar.setOnAction(event->{
			new Alert(AlertType.CONFIRMATION,"Usuário conectado: " + txtNome.getText()).show();
			
		});
		btnCadastrar = new Button("Cadastrar-se");
		btnCadastrar.setOnAction(event -> {
			new Alert(AlertType.CONFIRMATION, "Você foi cadastrado " + txtNomeCadast.getText()).show();
		});
		
		txtNome = new TextField();
	    pswSenha = new PasswordField();
		
		GridPane pnlLayout = new GridPane();
		
		pnlLayout.setPadding(new Insets(50));

		pnlLayout.setVgap(10);
		pnlLayout.setHgap(10);
		
		pnlLayout.add(lblTitle, 0, 0, 2, 1);	
		
		pnlLayout.add(lblEmail, 0, 1);
		pnlLayout.add(lblSenha, 0, 2);
		
		pnlLayout.add(btnEntrar, 1, 3);
		pnlLayout.add(btnCadastrar, 2, 3);
		
		pnlLayout.add(txtNome, 1, 1, 2, 1);
		pnlLayout.add(pswSenha, 1, 2, 2, 1);

		this.setRoot(pnlLayout);
		
	}

}
