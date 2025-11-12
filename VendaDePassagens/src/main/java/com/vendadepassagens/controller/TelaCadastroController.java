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

        if (mensagemLabel != null) {
            mensagemLabel.setText("");
        }
    }

    @FXML
    protected void handleSalvarButtonAction() {
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
        novoUsuario.setSenha(senha);
        novoUsuario.setDocumento(documento);
        novoUsuario.setSaldoMilhas(0);

        try {
            usuarioDAO.cadastrarUsuario(novoUsuario);

            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso",
                    "Usuário cadastrado!", "Você já pode fazer o login.");

            Navegador.voltarParaLogin();

        } catch (DuplicateKeyException e) {
            mensagemLabel.setText("Erro: Email ou Documento já cadastrado.");
        } catch (DataAccessException e) {
            mensagemLabel.setText("Erro de banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleCancelarButtonAction(ActionEvent event) {
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