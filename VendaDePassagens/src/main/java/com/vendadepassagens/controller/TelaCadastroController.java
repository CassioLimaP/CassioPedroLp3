package com.vendadepassagens.controller;

import com.vendadepassagens.dao.UsuarioDAO;
import com.vendadepassagens.model.Usuario;
import com.vendadepassagens.util.Navegador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;

public class TelaCadastroController {

    @FXML private TextField nomeField;
    @FXML private TextField emailField;
    @FXML private PasswordField senhaField;
    @FXML private TextField documentoField;
    @FXML private Label mensagemLabel;

    private UsuarioDAO usuarioDAO;

    @FXML
    public void initialize() {
        this.usuarioDAO = new UsuarioDAO();
        mensagemLabel.setText("");
    }

    @FXML
    protected void handleSalvarButtonAction(ActionEvent event) {
        String nome = nomeField.getText();
        String email = emailField.getText();
        String senha = senhaField.getText();
        String documento = documentoField.getText();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || documento.isEmpty()) {
            mensagemLabel.setText("Todos os campos são obrigatórios.");
            return;
        }

        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(nome);
        novoUsuario.setEmail(email);
        novoUsuario.setSenha(senha); // Lembre-se: em um app real, use HASH de senha!
        novoUsuario.setDocumento(documento);
        novoUsuario.setSaldoMilhas(0); // Valor inicial

        try {
            // Tenta cadastrar usando o DAO
            usuarioDAO.cadastrarUsuario(novoUsuario);

            // Sucesso!
            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso",
                    "Usuário cadastrado!", "Você já pode fazer o login.");

            // Volta para a tela de login
            Navegador.voltarParaLogin();

        } catch (DuplicateKeyException e) {
            // Erro se o email ou documento já existir
            mensagemLabel.setText("Erro: Email ou Documento já cadastrado.");
        } catch (DataAccessException e) {
            // Erro genérico de banco
            mensagemLabel.setText("Erro de banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleCancelarButtonAction(ActionEvent event) {
        // Simplesmente usa o navegador para voltar
        Navegador.voltarParaLogin();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String cabecalho, String conteudo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecalho);
        alert.setContentText(conteudo);
        alert.showAndWait();
    }
}