package com.vendadepassagens;

import com.vendadepassagens.dao.UsuarioDAO;
import com.vendadepassagens.model.Usuario;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage; // Para fechar a janela

import org.springframework.dao.DataAccessException;

public class TelaLoginController {

    // 1. O "@FXML" conecta estas variáveis aos "fx:id" do FXML
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField senhaField;

    @FXML
    private Button loginButton;

    @FXML
    private Label mensagemLabel;

    // 2. Referência ao nosso DAO (a "ponte" para o banco)
    private UsuarioDAO usuarioDAO;

    // Construtor
    public TelaLoginController() {
        this.usuarioDAO = new UsuarioDAO();
    }

    // 3. Este método é chamado pelo "onAction" do botão no FXML
    @FXML
    protected void handleLoginButtonAction(ActionEvent event) {
        String email = emailField.getText();
        String senha = senhaField.getText();

        if (email.isEmpty() || senha.isEmpty()) {
            mensagemLabel.setText("Email e senha são obrigatórios.");
            return;
        }

        try {
            Usuario usuario = usuarioDAO.buscarUsuarioPorLogin(email, senha);

            if (usuario != null) {
                // SUCESSO!
                mensagemLabel.setText(""); // Limpa o erro

                // Mostra um pop-up de sucesso
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Login");
                alert.setHeaderText("Login bem-sucedido!");
                alert.setContentText("Bem-vindo, " + usuario.getNome());
                alert.showAndWait();

                // TODO: Fechar a janela de login e abrir a tela principal
                // (ex: (Stage) loginButton.getScene().getWindow()).close();

            } else {
                // FALHA (login/senha errados)
                mensagemLabel.setText("Email ou senha inválidos.");
            }

        } catch (DataAccessException e) {
            // ERRO DE BANCO (pego pelo Spring JDBC)
            mensagemLabel.setText("Erro de banco de dados. Tente mais tarde.");

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro Crítico");
            alert.setHeaderText("Erro de Conexão");
            alert.setContentText("Não foi possível conectar ao banco: " + e.getMessage());
            alert.showAndWait();

            e.printStackTrace();
        }
    }
}