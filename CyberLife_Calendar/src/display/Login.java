package display;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Optional;

import db.functions.HandlerLogin;
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
import main.Main;

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

	GridPane pnlLayout = new GridPane();
	VBox vbLogin = new VBox();
	VBox vbCadastro = new VBox();
	HBox hCadastro = new HBox();
	HBox hLogin = new HBox();

	AnchorPane aPane = new AnchorPane();

	GridPane pnlCadastro = new GridPane();

	private HandlerLogin login;
	private HBox h_wrong_login;
	private Label lbl_error_message;

	public Login() {

		/**
		 * quando voce extender Scene tem que colocar isso aqui, OBRIGATORIO nï¿½o
		 * precisa necessariamente ser uma hbox, qualquer componente serve
		 */
		super(new HBox());

		this.getStylesheets().add(this.getClass().getResource("/css/login-cadastro.css").toExternalForm());
		this.login = new HandlerLogin();

		lblTitle = new Label("Login");
		lblTitle.setFont(new Font(25));

		lblTitleCadast = new Label("Cadastro");
		lblTitleCadast.setFont(new Font(25));

		/* Email */
		HBox hbEmail = new HBox();

		lblEmail = new Label();
		lblEmail.setId("lblEmail");

		txtEmail = new TextField();
		txtEmail.setPromptText("Email");

		hbEmail.getChildren().addAll(lblEmail, txtEmail);

		/* Senha */
		HBox hbPwd = new HBox();

		lblSenha = new Label();
		lblSenha.setId("lblPsw");

		pswSenha = new PasswordField();
		pswSenha.setPromptText("Senha");

		hbPwd.getChildren().addAll(lblSenha, pswSenha);

		/* Cadastro nome */
		HBox hbNome = new HBox();

		lblNomeCadast = new Label();
		lblNomeCadast.setId("lblNomeCadast");
		txtNomeCadast = new TextField();
		txtNomeCadast.setPromptText("Nome");

		hbNome.setVisible(true);
		hbNome.getChildren().addAll(lblNomeCadast, txtNomeCadast);

		/* Cadastro sobrenome */
		txtSobrenomeCadast = new TextField();
		txtSobrenomeCadast.setPromptText("Sobrenome");

		/* Cadastro email */
		HBox hbEmailCadast = new HBox();

		lblEmailCadast = new Label();
		lblEmailCadast.setId("lblEmail");

		txtEmailCadast = new TextField();
		txtEmailCadast.setPromptText("Email");

		hbEmailCadast.getChildren().addAll(lblEmailCadast, txtEmailCadast);

		/* Cadastro senha */
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

		this.h_wrong_login = new HBox();
		this.lbl_error_message = new Label("Login ou senha incorretos");
		this.lbl_error_message.setVisible(false);
		h_wrong_login.getChildren().add(this.lbl_error_message);
		h_wrong_login.setAlignment(Pos.CENTER);

		btnEntrar = new Button("Entrar");
		this.btnEntrar.setOnAction(e -> {

			boolean is_email_empty = txtEmail.getText().trim().isEmpty();
			boolean is_password_empy = pswSenha.getText().isEmpty();

			if (!is_email_empty && !is_password_empy) {
				try {
					if (login.do_login(txtEmail.getText(), pswSenha.getText())) {
						Main.main_stage.setScene(new HomePage());
						return;
					}
				} catch (ClassNotFoundException | SQLException e1) {
					e1.printStackTrace();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
			this.lbl_error_message.setVisible(true);
		});
		this.setOnMouseClicked(e -> {
			this.lbl_error_message.setVisible(false);
		});
		this.txtEmail.setOnMouseClicked(e -> {
			this.lbl_error_message.setVisible(false);
		});
		this.pswSenha.setOnMouseClicked(e -> {
			this.lbl_error_message.setVisible(false);
		});

		btnCadastrar = new Button("Cadastrar-se");
		btnCadastrar.setOnAction(event -> {
			Optional<ButtonType> vOptional = new Alert(AlertType.CONFIRMATION,
					"Você foi cadastrado " + txtNomeCadast.getText() + " " + txtSobrenomeCadast.getText())
							.showAndWait();

			if (vOptional.get() == ButtonType.OK) {
				componenteLogin();
			}
		});

		vbLogin.getChildren().addAll(lblTitle, hbEmail, hbPwd, h_wrong_login, hLogin);
		vbCadastro.getChildren().addAll(lblTitleCadast, hbNome, txtSobrenomeCadast, hbEmailCadast, hbPwdCadast,
				hCadastro);

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
		vbLogin.setPadding(new Insets(25, 35, 25, 25));
		vbCadastro.setSpacing(7);
		vbCadastro.setPadding(new Insets(25, 35, 25, 25));

		pnlLayout.setAlignment(Pos.CENTER_LEFT);
		pnlLayout.setPadding(new Insets(25, 35, 25, 25));

		pnlLayout.setHgap(10);
		pnlLayout.setVgap(10);

		pnlLayout.add(lblTitleCadast, 0, 0, 2, 1);
		pnlLayout.add(lblNomeCadast, 0, 1);
		pnlLayout.add(txtNomeCadast, 1, 1);
		pnlLayout.add(txtSobrenomeCadast, 1, 2);
		pnlLayout.add(lblEmailCadast, 0, 3);
		pnlLayout.add(txtEmailCadast, 1, 3);
		pnlLayout.add(lblSenhaCadast, 0, 4);
		pnlLayout.add(txtSenhaCadast, 1, 4);
		pnlLayout.add(txtSenhaConfirmCadast, 1, 5);
		pnlLayout.add(hCadastro, 0, 6, 2, 1);

		AnchorPane.setTopAnchor(vbLogin, 0d);
		AnchorPane.setBottomAnchor(vbLogin, 0d);
		AnchorPane.setRightAnchor(vbLogin, 0d);

		AnchorPane.setTopAnchor(pnlLayout, 0d);
		AnchorPane.setBottomAnchor(pnlLayout, 0d);
		AnchorPane.setRightAnchor(pnlLayout, 0d);

		aPane.requestFocus();

		this.setRoot(aPane);
	}

	private void componenteLogin() {

		if (aPane.getChildren().size() > 0) {

			boolean x = aPane.getChildren().get(0) == pnlLayout;

			if (!x) {
				aPane.getChildren().set(0, pnlLayout);

			} else {
				aPane.getChildren().set(0, vbLogin);
			}
		}

		aPane.requestFocus();

		if (Main.main_stage.getHeight() < this.getHeight())
			Main.main_stage.sizeToScene();
	}

}
