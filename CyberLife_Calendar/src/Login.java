import java.io.FileNotFoundException;
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
	private Label lblEmailCadast;
	private Label lblSenhaCadast;

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
		
		/* Email*/
		HBox hbEmail = new HBox();
		
		lblEmail = new Label();
		lblEmail.setId("lblEmail");
		
		txtEmail = new TextField();
		txtEmail.setPromptText("Email");
		
		hbEmail.getChildren().addAll(lblEmail, txtEmail);
		
		/* Senha*/
		HBox hbPwd = new HBox();
		
		lblSenha = new Label();
		lblSenha.setId("lblPsw");
		
		pswSenha = new PasswordField();
		pswSenha.setPromptText("Senha");
		
		hbPwd.getChildren().addAll(lblSenha, pswSenha);
		
		/* Cadastro nome*/
		HBox hbNome = new HBox();
		
		lblNomeCadast = new Label();
		lblNomeCadast.setId("lblNomeCadast");
		txtNomeCadast = new TextField();
		txtNomeCadast.setPromptText("Nome");
		
		hbNome.setVisible(true);
		hbNome.getChildren().addAll(lblNomeCadast, txtNomeCadast);
		
		/* Cadastro sobrenome*/
		txtSobrenomeCadast = new TextField();
		txtSobrenomeCadast.setPromptText("Sobrenome");
		
		/* Cadastro email*/
		HBox hbEmailCadast = new HBox();
		
		lblEmailCadast = new Label();
		lblEmailCadast.setId("lblEmail");
		
		txtEmailCadast = new TextField();
		txtEmailCadast.setPromptText("Email");
		
		hbEmailCadast.getChildren().addAll(lblEmailCadast, txtEmailCadast);
		
		/* Cadastro senha*/
		HBox hbPwdCadast = new HBox();
		
		lblSenhaCadast = new Label();
		lblSenhaCadast.setId("lblPsw");
		
		txtSenhaCadast = new PasswordField();
		txtSenhaCadast.setPromptText("Senha");
		
		hbPwdCadast.getChildren().addAll(lblSenhaCadast, txtSenhaCadast);
		
		txtSenhaConfirmCadast = new PasswordField();
		txtSenhaConfirmCadast.setPromptText("Confirmar senha");
		
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
			try {
				Main.main_stage.setScene(new HomePage());
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		btnCadastrar = new Button("Cadastrar-se");
		btnCadastrar.setOnAction(event -> {
			Optional<ButtonType> vOptional = new Alert(AlertType.CONFIRMATION, "Você foi cadastrado " + txtNomeCadast.getText() + " " + txtSobrenomeCadast.getText()).showAndWait();

			if(vOptional.get() == ButtonType.OK){
				componenteLogin();
			}
		});  
		
		vbLogin.getChildren().addAll(lblTitle, hbEmail, hbPwd, hLogin);
		vbCadastro.getChildren().addAll(lblTitleCadast, hbNome, txtSobrenomeCadast, hbEmailCadast, hbPwdCadast, txtSenhaConfirmCadast, hCadastro);
			    
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
		vbLogin.setPadding(new Insets(25,35,25,25));
		vbCadastro.setSpacing(7);
		vbCadastro.setPadding(new Insets(25,35,25,25));
		

		AnchorPane.setTopAnchor(vbLogin, 0d);
		AnchorPane.setBottomAnchor(vbLogin, 0d);
		AnchorPane.setRightAnchor(vbLogin, 0d);
		
		AnchorPane.setTopAnchor(vbCadastro, 0d);
		AnchorPane.setBottomAnchor(vbCadastro, 0d);
		AnchorPane.setRightAnchor(vbCadastro, 0d);
		
		aPane.requestFocus();
		
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
		
		aPane.requestFocus();
		
		if(Main.main_stage.getHeight() < this.getHeight()) 
			Main.main_stage.sizeToScene();

	}

}
