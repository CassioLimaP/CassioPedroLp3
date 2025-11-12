package com.vendadepassagens.controller;

import com.vendadepassagens.dao.UsuarioDAO;
import com.vendadepassagens.model.Usuario;
import com.vendadepassagens.util.Navegador;
import com.vendadepassagens.util.SessaoUsuario;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.springframework.dao.DataAccessException;

import static com.vendadepassagens.util.Navegador.mudarTela;

public class TelaLoginController {

    // 1. O "@FXML" conecta estas variáveis aos "fx:id" do FXML
    @FXML
    private VBox rootVBox;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField senhaField;

    @FXML
    private Label mensagemLabel;

    // 2. Referência ao nosso DAO (a "ponte" para o banco)
    private UsuarioDAO usuarioDAO;

    @FXML
    public void initialize() {
        this.usuarioDAO = new UsuarioDAO();
        mensagemLabel.setText("");

    }

    // 3. Este método é chamado pelo "onAction" do botão no FXML
    @FXML
    protected void handleLoginButtonAction() {
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

                SessaoUsuario.setUsuarioLogado(usuario);

                // Mostra um pop-up de sucesso
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Login");
                alert.setHeaderText("Login bem-sucedido!");
                alert.setContentText("Bem-vindo, " + usuario.getNome());
                alert.showAndWait();

                // TODO: Navegar para a tela principal
                Navegador.mudarTela("TelaPrincipal.fxml", "Painel de Voos");

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

            //e.printStackTrace();
        }
    }

    @FXML
    private void handleCadastroButtonAction() {
        // Usa o navegador para carregar a tela de cadastro
        mudarTela("TelaCadastro.fxml", "Cadastro de Usuário");
        //Navegador.mudarTela("com/vendadepassagens/view/Test.fxml", "TESTE");
    }

    @FXML
    protected void handleVisitanteButtonAction() {
        SessaoUsuario.setUsuarioLogado(null);//garante que é visitante
        // TODO: Navegar para a tela principal (modo visitante)
        Navegador.mudarTela("TelaPrincipal.fxml", "Painel de Voos (Visitante)");
    }
}