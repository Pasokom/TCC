package display.poupoup;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

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

    public EditProfile(){

        lblTitulo = new Label("Editar perfil");
        lblTitulo.setFont(new Font(25));

        lblNome = new Label("Nome: ");
        lblSobrenome = new Label("Sobrenome: ");
        lblEmail = new Label("E-mail: ");
        lblSenhaAtual = new Label("Senha Atual: ");//campo pode ficar vázio
        lblNovaSenha = new Label("Nova Senha: ");//campo pode ficar vázio 

        txtNome = new TextField();
        txtSobrenome = new TextField();
        txtEmail = new TextField();
        txtSenhaAtual = new PasswordField();
        txtNovaSenha = new PasswordField();

        btnSair = new Button("Sair");
        btnSalvar = new Button("Salvar");

        btnSair.setOnAction(e ->{
            this.close();
        });

        btnSalvar.setOnAction(e ->{
            //alterar os dados no bancos
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
}