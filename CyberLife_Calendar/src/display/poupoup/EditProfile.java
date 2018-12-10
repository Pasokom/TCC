package display.poupoup;

import java.sql.ResultSet;
import java.sql.SQLException;

import db.Database;
import db.functions.registrationAndLogin.HandlerRegistration;
import db.functions.user.AccountSettings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import statics.SESSION;

public class EditProfile extends Stage {

	private Label lblTitulo;

	private Label lblNome;
	private Label lblSobrenome;
	private Label lblEmail;
	private Label lblSenhaAtual;
	private Label lblNovaSenha;

	private TextField txtNome;
	private TextField txtSobrenome;
	private TextField txtEmail;
	private PasswordField txtSenhaAtual;
	private PasswordField txtNovaSenha;

	private Button btnSalvar;
	private Button btnSair;

	public EditProfile() {

		lblTitulo = new Label("Editar perfil");
		lblTitulo.setFont(new Font(25));

		lblNome = new Label("Nome: ");
		lblSobrenome = new Label("Sobrenome: ");
		lblEmail = new Label("E-mail: ");
		lblSenhaAtual = new Label("Senha Atual: ");// campo pode ficar vázio
		lblNovaSenha = new Label("Nova Senha: ");// campo pode ficar vázio

		txtNome = new TextField();
		txtSobrenome = new TextField();
		txtEmail = new TextField();
		txtSenhaAtual = new PasswordField();
		txtNovaSenha = new PasswordField();

		this.txtNome.setText(SESSION.get_user_name());
		this.txtEmail.setText(SESSION.get_user_email());
		this.txtSobrenome.setText(SESSION.get_user_last_name());

		btnSair = new Button("Sair");
		btnSalvar = new Button("Salvar");

		btnSair.setOnAction(e -> {
			this.close();
		});

		btnSalvar.setOnAction(e -> {
			int userID = (int) SESSION.get_user_cod();
			AccountSettings as = new AccountSettings();
			HandlerRegistration hr = new HandlerRegistration();

			if (!compare(SESSION.get_user_name(), txtNome.getText(), 0))
				as.changeProfile(txtNome.getText(), userID, AccountSettings.changeThe.NAME);
			if (!compare(SESSION.get_user_last_name(), txtSobrenome.getText(), 0))
				as.changeProfile(txtSobrenome.getText(), userID, AccountSettings.changeThe.LAST_NAME);

			try {
                if ((!compare(SESSION.get_user_email(), txtEmail, 0) && (!hr.email_exists(this.txtEmail.getText()))))
                    as.changeProfile(txtEmail.getText(), userID, AccountSettings.changeThe.EMAIL);
            } catch (ClassNotFoundException | SQLException e1) {
                e1.printStackTrace();
            }


			if (txtSenhaAtual.getText().equals(bringUserPassword()))
				as.changeProfile(this.txtNovaSenha.getText(), userID, AccountSettings.changeThe.PASSWORD);

			this.close();

		});

		GridPane pnlLayout = new GridPane();

		pnlLayout.setHgap(10);
		pnlLayout.setVgap(10);

		pnlLayout.setAlignment(Pos.CENTER_LEFT);
		pnlLayout.setPadding(new Insets(25, 35, 25, 25));

		pnlLayout.add(lblTitulo, 0, 0, 2, 1);
		pnlLayout.add(lblNome, 0, 1);
		pnlLayout.add(lblSobrenome, 0, 2);
		pnlLayout.add(lblEmail, 0, 3);
		pnlLayout.add(lblSenhaAtual, 0, 4);
		pnlLayout.add(lblNovaSenha, 0, 5);

		pnlLayout.add(txtNome, 1, 1, 2, 1);
		pnlLayout.add(txtSobrenome, 1, 2, 2, 1);
		pnlLayout.add(txtEmail, 1, 3, 2, 1);
		pnlLayout.add(txtSenhaAtual, 1, 4, 2, 1);
		pnlLayout.add(txtNovaSenha, 1, 5, 2, 1);

		pnlLayout.add(btnSair, 1, 6);
		pnlLayout.add(btnSalvar, 2, 6);

		Scene scene = new Scene(pnlLayout);
		this.setScene(scene);
	}

	private String bringUserPassword() {

		final String sql = "SELECT USENHA FROM USUARIO WHERE UCODIGO  = " + (int) SESSION.get_user_cod();
		System.out.println(sql);
		try {
			ResultSet rs = Database.get_connection().createStatement().executeQuery(sql);
			if (rs.isBeforeFirst())
                rs.first();
            System.out.println(rs.getString(1));
			return rs.getString(1);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return new String();
	}

	private boolean compare(Object v1, Object v2, int type) {
		if (type == 0)
			return String.valueOf(v1).equals(String.valueOf(v2));
		if (type == 1)
			return (int) v1 == (int) v2;
		if (type == 2)
			return (boolean) v1 == (boolean) v2;
		return false;
	}

}
