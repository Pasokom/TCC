import java.util.Optional;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class Login extends Scene {

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
	private Button rdHabilitarCadast;
	private Button backLogin;

	private TextField txtEmail;
	private PasswordField pswSenha;

	private TextField txtNomeCadast;
	private TextField txtSobrenomeCadast;
	private TextField txtEmailCadast;
	private PasswordField txtSenhaCadast;
	private PasswordField txtSenhaConfirmCadast;

	VBox vbLogin = new VBox();
	VBox vbCadastro = new VBox();
	HBox hCadastro = new HBox();
	HBox hLogin = new HBox();

	AnchorPane aPane = new AnchorPane();

	GridPane pnlCadastro = new GridPane();

	public Login() {

		/**
		 * quando voce extender Scene tem que colocar isso aqui, OBRIGATORIO nï¿½o precisa
		 * necessariamente ser uma hbox, qualquer componente serve
		 */
		super(new HBox());
		
		this.getStylesheets().add(this.getClass().getResource("css/login-cadastro.css").toExternalForm());
		
		lblTitle = new Label("Login");
		lblTitle.setFont(new Font(25));
		
		lblTitleCadast = new Label("Cadastro");
		lblTitleCadast.setFont(new Font(25));
		
		lblEmail = new Label("E-mail ");
		txtEmail = new TextField();
		lblEmailCadast = new Label("E-mail");	
		txtEmailCadast = new TextField();
		lblNomeCadast = new Label("Nome");
		txtNomeCadast = new TextField();
		lblSobrenomeCadast = new Label("Sobrenome");
		txtSobrenomeCadast = new TextField();
		lblSenhaCadast = new Label("Senha: ");
		txtSenhaCadast = new PasswordField();
		lblSenhaConfirmCadast = new Label("Confirmar senha");
		txtSenhaConfirmCadast = new PasswordField();
		
		
		lblSenha = new Label("Senha: ");
		pswSenha = new PasswordField();
		
		lblEmailCadast = new Label("E-mail: ");
		lblSenhaCadast = new Label("Senha: ");
		
		rdHabilitarCadast = new Button("Cadastrar-se");
		rdHabilitarCadast.setOnAction(e -> {
			componenteLogin();
		});
		
		backLogin = new Button("Voltar");
		backLogin.setOnAction(e -> {
				componenteLogin();
		});
		
		btnEntrar = new Button("Entrar");
		btnEntrar.setOnAction(event->{
			
			
		});
		btnCadastrar = new Button("Cadastrar-se");
		btnCadastrar.setOnAction(event -> {
			Optional<ButtonType> vOptional = new Alert(AlertType.CONFIRMATION, "Você foi cadastrado " + txtNomeCadast.getText() + " " + txtSobrenomeCadast.getText()).showAndWait();

			if(vOptional.get() == ButtonType.OK){
				componenteLogin();
			}
		});  
		
		GridPane pnlLayout = new GridPane();
	    
	    AnchorPane.setRightAnchor(pnlLayout, 5d);
	    AnchorPane.setTopAnchor(pnlLayout, 5d);
	    
		pnlCadastro.add(lblTitleCadast, 0, 6, 2, 1);	
		pnlCadastro.add(lblNomeCadast, 0, 7, 2, 1);
		pnlCadastro.add(txtNomeCadast, 0, 8, 2, 1);
		pnlCadastro.add(lblSobrenomeCadast, 0, 9, 2, 1);
		pnlCadastro.add(txtSobrenomeCadast, 0, 10, 2, 1);
		pnlCadastro.add(lblEmailCadast, 0, 11, 2, 1);
		pnlCadastro.add(txtEmailCadast, 0, 12, 2, 1);
		pnlCadastro.add(lblSenhaCadast, 0, 13, 2, 1);
		pnlCadastro.add(txtSenhaCadast, 0, 14, 2, 1);
		pnlCadastro.add(lblSenhaConfirmCadast, 0, 15, 2, 1);
		pnlCadastro.add(txtSenhaConfirmCadast, 0, 16, 2, 1);
		pnlCadastro.add(btnCadastrar, 0, 17, 2, 2);
		
		vbLogin.getChildren().addAll(lblTitle, lblEmail, txtEmail, lblSenha, pswSenha, hLogin);
		vbCadastro.getChildren().addAll(lblTitleCadast, lblNomeCadast, txtNomeCadast, lblSobrenomeCadast, txtSobrenomeCadast, lblEmailCadast, txtEmailCadast, lblSenhaCadast, txtSenhaCadast, lblSenhaConfirmCadast, txtSenhaConfirmCadast, hCadastro);
			    
		hCadastro.getChildren().addAll(backLogin, btnCadastrar);
		hCadastro.setAlignment(Pos.CENTER_LEFT);
		hCadastro.setSpacing(10);
		vbCadastro.setAlignment(Pos.CENTER_LEFT);
		
		hLogin.getChildren().addAll(btnEntrar, rdHabilitarCadast);
		hLogin.setAlignment(Pos.CENTER_LEFT);
		hLogin.setSpacing(10);
		vbLogin.setAlignment(Pos.CENTER_LEFT);

		aPane.getChildren().add(vbLogin);
		vbLogin.setSpacing(10);
		vbLogin.setPadding(new Insets(25,100,25,25));
		vbCadastro.setSpacing(7);
		vbCadastro.setPadding(new Insets(25,100,25,25));
		

		AnchorPane.setTopAnchor(vbLogin, 0d);
		AnchorPane.setBottomAnchor(vbLogin, 0d);
		AnchorPane.setRightAnchor(vbLogin, 0d);
		
		AnchorPane.setTopAnchor(vbCadastro, 0d);
		AnchorPane.setBottomAnchor(vbCadastro, 0d);
		AnchorPane.setRightAnchor(vbCadastro, 0d);
		
		this.setRoot(aPane);
		
	}

	private void componenteLogin() {
		
		if ( aPane.getChildren().size() > 0) { 
			
			boolean x  = aPane.getChildren().get(0) == vbCadastro;
			
			if (! x ) { 
				aPane.getChildren().set(0, vbCadastro);
				
			}else {
				aPane.getChildren().set(0, vbLogin);
			}
		}
		
		if(Main.main_stage.getHeight() < this.getHeight()) 
			Main.main_stage.sizeToScene();

	}

}
