package display.scenes;

import java.sql.SQLException;
import java.util.Optional;

import db.functions.registrationAndLogin.HandlerLogin;
import db.functions.registrationAndLogin.HandlerRegistration;
import db.pojo.UserSession;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
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

	private Label lblLog;

	private Button btnEntrar;
	private Button btnCadastrar;
	private Button rdHabilitarCadast;
	private Button backLogin;
	private TextField txtEmail;
	private PasswordField txtSenha;

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

	private HBox h_stayConnected;
	private CheckBox cb_stayConnected;

	private HandlerRegistration registration;

	public Login() {

		/**
		 * quando voce extender Scene tem que colocar isso aqui, OBRIGATORIO nÃ£o
		 * precisa necessariamente ser uma hbox, qualquer componente serve
		 */
		super(new HBox());

		this.getStylesheets().add(this.getClass().getResource("/css/login_cadastro.css").toExternalForm());

		this.login = new HandlerLogin();
		this.registration = new HandlerRegistration();

		this.lblTitle = new Label("Login");
		this.lblTitle.setFont(new Font(25));

		this.lblTitleCadast = new Label("Cadastro");
		this.lblTitleCadast.setFont(new Font(25));

		this.lblLog = new Label();
		this.lblLog.setWrapText(true);
		this.lblLog.setVisible(false);
		this.lblLog.setManaged(false);

		/* Email */
		HBox hbEmail = new HBox();

		this.lblEmail = new Label();
		this.lblEmail.setId("lblEmail");

		this.txtEmail = new TextField();
		this.txtEmail.setPromptText("Email");

		hbEmail.getChildren().addAll(lblEmail, txtEmail);

		/* Senha */
		HBox hbPwd = new HBox();

		this.lblSenha = new Label();
		this.lblSenha.setId("lblPsw");

		this.txtSenha = new PasswordField();
		this.txtSenha.setPromptText("Senha");

		hbPwd.getChildren().addAll(lblSenha, txtSenha);

		/* Cadastro nome */
		HBox hbNome = new HBox();

		this.lblNomeCadast = new Label();
		this.lblNomeCadast.setId("lblNomeCadast");
		this.txtNomeCadast = new TextField();
		this.txtNomeCadast.setPromptText("Nome");

		hbNome.setVisible(true);
		hbNome.getChildren().addAll(lblNomeCadast, txtNomeCadast);

		/* Cadastro sobrenome */
		this.txtSobrenomeCadast = new TextField();
		this.txtSobrenomeCadast.setPromptText("Sobrenome");

		/* Cadastro email */
		HBox hbEmailCadast = new HBox();

		this.lblEmailCadast = new Label();
		this.lblEmailCadast.setId("lblEmail");

		this.txtEmailCadast = new TextField();
		this.txtEmailCadast.setPromptText("Email");

		hbEmailCadast.getChildren().addAll(lblEmailCadast, txtEmailCadast);

		/* Cadastro senha */
		HBox hbPwdCadast = new HBox();

		this.lblSenhaCadast = new Label();
		this.lblSenhaCadast.setId("lblPsw");

		this.txtSenhaCadast = new PasswordField();
		this.txtSenhaCadast.setPromptText("Senha");

		hbPwdCadast.getChildren().addAll(lblSenhaCadast, txtSenhaCadast);

		this.txtSenhaConfirmCadast = new PasswordField();
		this.txtSenhaConfirmCadast.setPromptText("Confirmar senha");

		this.rdHabilitarCadast = new Button("Cadastrar-se");
		this.rdHabilitarCadast.setOnAction(e -> {
			componenteLogin();
		});

		this.backLogin = new Button("Voltar");
		this.backLogin.setOnAction(e -> {
			componenteLogin();
		});

		this.lbl_error_message = new Label();
		this.lbl_error_message.setVisible(false);
		this.lbl_error_message.setManaged(false);

		this.h_stayConnected = new HBox();
		this.cb_stayConnected = new CheckBox();
		this.h_stayConnected.getChildren().addAll(cb_stayConnected, new Label("Mantenha-me conectado"));

		this.btnEntrar = new Button("Entrar");
		this.btnEntrar.setOnAction(e -> {
			login();
		});

		this.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER)
				login();
		});

		this.setOnMouseClicked(e -> {
			this.lbl_error_message.setVisible(false);
			this.lbl_error_message.setManaged(false);

			this.lblLog.setVisible(false);
			this.lblLog.setManaged(false);
		});

		this.txtEmail.setOnMouseClicked(e -> {
			this.lbl_error_message.setVisible(false);
			this.lbl_error_message.setManaged(false);
		});
		
		this.txtSenha.setOnMouseClicked(e -> {
			this.lbl_error_message.setVisible(false);
			this.lbl_error_message.setManaged(false);
		});

		ChangeListener<String> changeText = new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				
				StringProperty textProperty = (StringProperty) observable ;
				TextField textField = (TextField) textProperty.getBean();

				textField.setStyle("-fx-focus-color: #304FFE;");
			}
		};

		this.txtEmail.textProperty().addListener(changeText);
		this.txtSenha.textProperty().addListener(changeText);

		this.txtNomeCadast.textProperty().addListener(changeText);
		this.txtEmailCadast.textProperty().addListener(changeText);
		this.txtSenhaCadast.textProperty().addListener(changeText);
		this.txtSenhaConfirmCadast.textProperty().addListener(changeText);

		this.btnCadastrar = new Button("Cadastrar-se");
		this.btnCadastrar.setOnAction(event -> {
			try {
				registration();
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
		});

		vbLogin.getChildren().addAll(lblTitle, hbEmail, hbPwd, h_stayConnected, lbl_error_message, hLogin);
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
		pnlLayout.setPrefWidth(250);

		pnlLayout.add(lblTitleCadast, 0, 0, 2, 1);
		pnlLayout.add(lblNomeCadast, 0, 1);
		pnlLayout.add(txtNomeCadast, 1, 1);
		pnlLayout.add(txtSobrenomeCadast, 1, 2);
		pnlLayout.add(lblEmailCadast, 0, 3);
		pnlLayout.add(txtEmailCadast, 1, 3);
		pnlLayout.add(lblSenhaCadast, 0, 4);
		pnlLayout.add(txtSenhaCadast, 1, 4);
		pnlLayout.add(txtSenhaConfirmCadast, 1, 5);
		pnlLayout.add(lblLog, 0, 6, 2, 1);
		pnlLayout.add(hCadastro, 0, 7, 2, 1);

		AnchorPane.setTopAnchor(vbLogin, 0d);
		AnchorPane.setBottomAnchor(vbLogin, 0d);
		AnchorPane.setRightAnchor(vbLogin, 0d);

		AnchorPane.setTopAnchor(pnlLayout, 0d);
		AnchorPane.setBottomAnchor(pnlLayout, 0d);
		AnchorPane.setRightAnchor(pnlLayout, 0d);

		aPane.requestFocus();

		UserSession session = UserSession.fromSerialization();
		this.txtEmail.setText(session.getUser_email());

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

	/**
	 * usa a classe {@link HandlerLogin} para fazer o login, se os dados informados
	 * estiverem errados a label de mensagem de erro vai ficar visivel se o login
	 * for feito com sucesso irão iniciar as variaveis globais da classe
	 * {@link statics.SESSION} e abrir a tela principal
	 * 
	 */
	private void login() {
		boolean is_email_empty = txtEmail.getText().trim().isEmpty();
		boolean is_password_empty = txtSenha.getText().isEmpty();

		if (!is_email_empty && !is_password_empty) {

			if (login.login(txtEmail.getText(), txtSenha.getText(), this.cb_stayConnected.selectedProperty().get())) {

				Main.main_stage.setScene(new HomePage());
			}
			else{

				this.lbl_error_message.setText("Login ou senha incorretos");

				this.lbl_error_message.setVisible(true);
				this.lbl_error_message.setManaged(true);
			}
		}
		else {

			if(is_password_empty){

				txtSenha.setStyle("-fx-focus-color: red;");
				txtSenha.requestFocus();
			}
			if(is_email_empty) {

				txtEmail.setStyle("-fx-focus-color: red;");
				txtEmail.requestFocus();
			}
			this.lbl_error_message.setText("*Campo não preenchido");
		}
		this.lbl_error_message.setVisible(true);
		this.lbl_error_message.setManaged(true);
	}

	/**
	 * usa a classe {@link HandlerRegistration} para fazer o cadastro e a validação
	 * de email já cadastrado no banco
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private void registration() throws ClassNotFoundException, SQLException {

		this.lblLog.setVisible(true);
		this.lblLog.setManaged(true);

		boolean is_email_field_empty = txtEmailCadast.getText().isEmpty();
		boolean is_name_field_empty = txtNomeCadast.getText().isEmpty();

		if(is_name_field_empty){
			txtNomeCadast.setStyle("-fx-focus-color: red;");
			txtNomeCadast.requestFocus();
			lblLog.setText("*Campo não preenchido!");
			return;
		}

		if (is_email_field_empty) {
			txtEmailCadast.setStyle("-fx-focus-color: red;");
			txtEmailCadast.requestFocus();
			lblLog.setText("*Campo não preenchido!");
			return;
		}

		if (txtSenhaCadast.getText().isEmpty()) {
			txtSenhaCadast.setStyle("-fx-focus-color: red;");
			txtSenhaCadast.requestFocus();
			lblLog.setText("*Campo não preenchido!");
			return;
		}

		if(txtSenhaConfirmCadast.getText().isEmpty()){
			txtSenhaConfirmCadast.setStyle("-fx-focus-color: red;");
			txtSenhaConfirmCadast.requestFocus();
			lblLog.setText("*Campo não preenchido!");
			return;
		}

		if (txtSenhaCadast.getText().length() < 4) { // 8) {
			txtSenhaCadast.setStyle("-fx-focus-color: red;");
			txtSenhaCadast.requestFocus();
			//lblLog.setText("Senha deve conter no mínimo 3 caracteres!");
			//Optional<ButtonType> result = new Alert(AlertType.WARNING, "Senha deve conter no mínimo 3 caracteres!").showAndWait();
			
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("CyberLife");
			alert.setHeaderText("Dados inválidos!");
			alert.setContentText("Senha deve conter no mínimo 3 caracteres!");
			alert.showAndWait();

			return;
		}
		boolean password_are_diferent = txtSenhaCadast.getText().equals(txtSenhaConfirmCadast.getText());

		if (!password_are_diferent) {
			txtSenhaConfirmCadast.setStyle("-fx-focus-color: red;");
			txtSenhaConfirmCadast.requestFocus();
			//lblLog.setText("Senhas informadas não correspondem!");
			//Optional<ButtonType> result = new Alert(AlertType.WARNING,"Senhas informadas não correspondem!").showAndWait();
			
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("CyberLife");
			alert.setHeaderText("Dados inválidos!");
			alert.setContentText("Senhas informadas não correspondem!");
			alert.showAndWait();
			
			return;
		}

		if (!txtEmailCadast.getText().contains("@")
				&& (!txtEmailCadast.getText().contains(".com") || !txtEmailCadast.getText().contains(".br"))) {
			
			txtEmailCadast.setStyle("-fx-focus-color: red;");
			txtEmailCadast.requestFocus();
			//lblLog.setText("Formato de e-mail não reconhecido!");
			//Optional<ButtonType> result = new Alert(AlertType.WARNING,"Formato de e-mail não reconhecido!").showAndWait();

			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("CyberLife");
			alert.setHeaderText("Dados inválidos!");
			alert.setContentText("Formato de email não reconhecido!");
			alert.showAndWait();
			
			return;
		}

		if (!this.registration.email_exists(txtEmailCadast.getText())) {

			String name = txtNomeCadast.getText();
			String last_name = txtSobrenomeCadast.getText();
			String email = txtEmailCadast.getText();
			String password = txtSenhaCadast.getText();

			/*
			 * insere o usuario
			 * 
			 * aparentemente o valor que a query retorna para quando o insert dá certo é
			 * false heuehueh
			 */
			/*if (this.registration.insert_user(name, last_name, email, password)) {
				Optional<ButtonType> vOptional = new Alert(AlertType.CONFIRMATION, "Você foi cadastrado com sucesso! "
						+ txtNomeCadast.getText() + " " + txtSobrenomeCadast.getText()).showAndWait();
				if (vOptional.get() == ButtonType.OK) {
					componenteLogin();
				}
			}*/
			
			//Optional<ButtonType> result = new Alert(AlertType.CONFIRMATION,"Cadastrado! Voltar ao login?").showAndWait();

			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("CyberLife");
			alert.setHeaderText("Novo usuário!");
			alert.setContentText("Cadastrado! Voltar ao login?");
			Optional<ButtonType> result = alert.showAndWait();

			if(result.get() == ButtonType.OK)
				Main.main_stage.setScene(new Login());
				
			return;
		}
		//lblLog.setText("Email informado já foi cadastrado");

		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("CyberLife");
		alert.setHeaderText("Dados inválidos!");
		alert.setContentText("Email informado já foi cadastrado");
		alert.showAndWait();
		
		return;
	}
}
