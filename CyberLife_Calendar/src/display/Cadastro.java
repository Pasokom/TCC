package display;


import java.sql.SQLException;

import db.functions.HandlerRegistration;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import main.Main;

public class Cadastro extends Scene {
	
	private Label lblTitle;
	private Label lblNome;
	private Label lblSobrenome;
	private Label lblEmail;
	private Label lblSenha;
	private Label lblSenhaConfirm;
	
	private TextField txtNome;
	private TextField txtSobrenome;
	private TextField txtEmail;
	private PasswordField pswSenha;
	private PasswordField pswSenhaConfirm;
	
	private Button btnCadastrar;
	//private Button btnVoltar;
	
	public Cadastro() {
		/** 
		 * quando voce extender Scene tem que colocar isso aqui, OBRIGATORIO
		 	n�o precisa necessariamente ser uma hbox, qualquer componente serve
		 */
		super(new HBox());
		
		lblNome = new Label("Nome: ");
		lblSobrenome = new Label("Sobrenome: ");
		lblEmail = new Label("E-mail: ");
		lblSenha = new Label("Senha: ");
		lblSenhaConfirm = new Label("Confirmar Senha: ");
		
		Button btnVoltar = new Button("Voltar");
		btnVoltar.setOnAction(event->{
			Main.main_stage.setScene(new Login());
		});
		btnCadastrar = new Button("Cadastrar");
		btnCadastrar.setOnAction(event->{
//			Optional<ButtonType> result = new Alert(AlertType.CONFIRMATION,"Cadastrado! Voltar ao login?").showAndWait();
			
//			if(result.get() == ButtonType.OK) 
//				Main.main_stage.setScene(new Login());
				
			HandlerRegistration hr = new HandlerRegistration();
			
			try {
				hr.email_exists(txtEmail.getText());
			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			
			
		});
		
		txtNome = new TextField();
		txtSobrenome = new TextField();
		txtEmail = new TextField();
		
		pswSenha = new PasswordField();
		pswSenhaConfirm = new PasswordField();
		
		lblTitle = new Label("Cadastro");
		lblTitle.setFont(new Font(45));
		
		GridPane pnlLayout = new GridPane();

		pnlLayout.setPadding(new Insets(50));
		
		pnlLayout.setVgap(10);
		pnlLayout.setHgap(10);
	
		pnlLayout.add(lblTitle, 0, 0, 2, 1);
		pnlLayout.add(lblNome, 0, 1);
		pnlLayout.add(lblSobrenome, 0, 2);
		pnlLayout.add(lblEmail, 0, 3);
		pnlLayout.add(lblSenha, 0, 4);
		pnlLayout.add(lblSenhaConfirm, 0, 5);
		
		pnlLayout.add(btnCadastrar, 1, 6, 1, 1);
		pnlLayout.add(btnVoltar, 3, 6, 1, 1);		
		
		pnlLayout.add(txtNome, 1, 1, 3, 1);
		pnlLayout.add(txtSobrenome, 1, 2, 3, 1);
		pnlLayout.add(txtEmail, 1, 3, 3, 1);
		pnlLayout.add(pswSenha, 1, 4, 3, 1);
		pnlLayout.add(pswSenhaConfirm, 1, 5, 3, 1);
		
		this.setRoot(pnlLayout);
		
	}

}
