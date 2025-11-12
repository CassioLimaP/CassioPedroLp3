package com.vendadepassagens.controller;

import com.vendadepassagens.dao.VooDAO;
import com.vendadepassagens.model.Voo;
import com.vendadepassagens.util.SessaoUsuario;
import com.vendadepassagens.util.Navegador;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import org.springframework.dao.DataAccessException;
import java.util.List;


public class TelaPrincipalController {
    @FXML private Button loginButton;
    @FXML private Label statusLoginLabel;
    @FXML private TextField origemField;
    @FXML private TextField destinoField;
    @FXML private Button buscarButton;
    @FXML private FlowPane painelDeVoos; // O container das "caixas"
    @FXML private Button adminButton;
    @FXML private BorderPane rootBorderPane;
    private VooDAO vooDAO;


    @FXML
    public void initialize() {
        adminButton.setVisible(false);
        if (SessaoUsuario.isLogado()) {
            if (SessaoUsuario.getUsuarioLogado().isAdmin()) {
                adminButton.setVisible(true);
            }
        }

        this.vooDAO = new VooDAO();
        if (SessaoUsuario.isLogado()) {
            String nome = SessaoUsuario.getUsuarioLogado().getNome();

            statusLoginLabel.setText("Logado como: " + nome);
            statusLoginLabel.setStyle("-fx-text-fill: green;");
            loginButton.setText("Logout");

        } else {
            // USUÁRIO É VISITANTE
            statusLoginLabel.setText("Você está navegando como visitante.");
            statusLoginLabel.setStyle("-fx-text-fill: #888888;"); // Cinza
            loginButton.setText("Fazer Login");
        }
        carregarVoos();
    }

    @FXML
    protected void handleBuscarVoosAction() {
        // TODO: Implementar a lógica de busca com filtros
        // String origem = origemField.getText();
        // String destino = destinoField.getText();
        // carregarVoos(origem, destino);

        System.out.println("Buscando voos...");
        // Por enquanto, apenas recarrega todos
        carregarVoos();
    }
    private void carregarVoos() {
        try {
            painelDeVoos.getChildren().clear();
            List<Voo> voos = vooDAO.buscarTodosOsVoos();
            for (Voo voo : voos) {
                VBox cardVoo = criarCardVoo(voo);
                painelDeVoos.getChildren().add(cardVoo);
            }
        } catch (DataAccessException e) {
            mostrarAlerta("Erro de Banco", "Não foi possível carregar os voos.");
            e.printStackTrace();
        }
    }
    private VBox criarCardVoo(Voo voo) {
        VBox card = new VBox(10.0);
        card.setPadding(new Insets(10));
        card.setStyle("-fx-background-color: #F0F0F0; -fx-border-color: #CCC; -fx-border-width: 1; -fx-border-radius: 5;");
        card.setPrefWidth(310.0);
        // Conteúdo da caixa
        Label destinoLabel = new Label("EMBARQUE: "+voo.getOrigem() + " \nDESTINO: " + voo.getDestino());
        destinoLabel.setFont(new Font("System Bold", 14.0));
        Label precoLabel = new Label("R$ " + voo.getPrecoAssento().toString());
        precoLabel.setStyle("-fx-text-fill: #008000; -fx-font-weight: bold;");
        Label detalhesLabel = new Label(voo.getCodigoVoo() + "\nPartida: " + voo.getDataPartida().toLocalDate());

        Button reservarButton = new Button("Reservar");
        reservarButton.setOnAction(event -> {
            if (!SessaoUsuario.isLogado()) {
                mostrarAlerta("Acesso Negado", "Você precisa estar logado para fazer uma reserva.");
                Navegador.voltarParaLogin();
                return;
            }
            Navegador.abrirPopUpReserva(voo);
        });
        card.getChildren().addAll(destinoLabel, precoLabel, detalhesLabel, reservarButton);
        return card;
    }

    private void mostrarAlerta(String titulo, String conteudo) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(conteudo);
        alert.showAndWait();
    }
    @FXML
    private void handleLoginLogoutAction() {
        if (SessaoUsuario.isLogado()) {
            SessaoUsuario.limparSessao();
        }
        Navegador.voltarParaLogin();
    }
    @FXML
    private void handleAdminButtonAction() {
        Navegador.mudarTela("TelaAdminVoo.fxml", "Admin: Cadastrar Voo");
    }
}