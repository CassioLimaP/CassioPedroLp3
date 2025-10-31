package com.vendadepassagens.controller;

import com.vendadepassagens.dao.VooDAO;
import com.vendadepassagens.model.Voo;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox; // Usaremos VBox para criar as "caixas"
import javafx.scene.text.Font;
import org.springframework.dao.DataAccessException;

import java.util.List;

public class TelaPrincipalController {

    @FXML private TextField origemField;
    @FXML private TextField destinoField;
    @FXML private Button buscarButton;
    @FXML private FlowPane painelDeVoos; // O container das "caixas"

    private VooDAO vooDAO;

    @FXML
    public void initialize() {
        this.vooDAO = new VooDAO();
        // Carrega todos os voos assim que a tela abre
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

    /**
     * Busca os voos no DAO e chama o método para criar as "caixas" (cards).
     */
    private void carregarVoos() {
        try {
            // 1. Limpa o painel antes de adicionar novos voos
            painelDeVoos.getChildren().clear();

            // 2. Busca os dados no banco
            List<Voo> voos = vooDAO.buscarTodosOsVoos();

            // 3. Cria uma "caixa" (card) para cada voo encontrado
            for (Voo voo : voos) {
                VBox cardVoo = criarCardVoo(voo);
                painelDeVoos.getChildren().add(cardVoo);
            }

        } catch (DataAccessException e) {
            mostrarAlerta("Erro de Banco", "Não foi possível carregar os voos.");
            e.printStackTrace();
        }
    }

    /**
     * Este é o método que cria sua "pequena caixa" (o card) para um Voo.
     */
    private VBox criarCardVoo(Voo voo) {
        // VBox é a "caixa"
        VBox card = new VBox(10.0); // 10px de espaçamento vertical
        card.setPadding(new Insets(10));
        card.setStyle("-fx-background-color: #F0F0F0; -fx-border-color: #CCC; -fx-border-width: 1; -fx-border-radius: 5;");
        card.setPrefWidth(200.0); // Largura fixa para cada caixa

        // Conteúdo da caixa
        Label destinoLabel = new Label(voo.getOrigem() + " -> " + voo.getDestino());
        destinoLabel.setFont(new Font("System Bold", 14.0));

        Label precoLabel = new Label("R$ " + voo.getPrecoAssento().toString());
        precoLabel.setStyle("-fx-text-fill: #008000; -fx-font-weight: bold;");

        Label detalhesLabel = new Label(voo.getCodigoVoo() + "\nPartida: " + voo.getDataPartida().toLocalDate());

        Button reservarButton = new Button("Reservar");
        reservarButton.setOnAction(event -> {
            // Ação do botão "Reservar"
            System.out.println("Reservando voo: " + voo.getIdVoo());
            // TODO: Abrir pop-up de reserva
        });

        // Adiciona todos os componentes na "caixa"
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
}