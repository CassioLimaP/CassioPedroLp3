package com.vendadepassagens.controller;

import com.vendadepassagens.dao.ReservaDAO;
import com.vendadepassagens.dao.VooDAO;
import com.vendadepassagens.model.MinhaReservaDTO;
import com.vendadepassagens.model.Voo;
import com.vendadepassagens.util.SessaoUsuario;
import com.vendadepassagens.util.Navegador;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.springframework.dao.DataAccessException;

import java.time.LocalDateTime;
import java.util.List;


public class TelaPrincipalController {
    @FXML private Button loginButton;
    @FXML private Label statusLoginLabel;
    @FXML private TextField origemField;
    @FXML private TextField destinoField;
    @FXML private Button buscarButton;
    @FXML private FlowPane painelDeVoos; // O container das "caixas"
    @FXML private Button adminButton;
    @FXML private VBox painelMinhasReservas;
    @FXML private TableView<MinhaReservaDTO> tabelaReservas;
    @FXML private TableColumn<MinhaReservaDTO, String> colCodigo;
    @FXML private TableColumn<MinhaReservaDTO, String> colOrigem;
    @FXML private TableColumn<MinhaReservaDTO, String> colDestino;
    @FXML private TableColumn<MinhaReservaDTO, LocalDateTime> colData;
    @FXML private TableColumn<MinhaReservaDTO, String> colAssento;
    @FXML private TableColumn<MinhaReservaDTO, String> colStatus;

    private ReservaDAO reservaDAO; // Adicione o DAO
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
        this.reservaDAO = new ReservaDAO();
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigoVoo"));
        colOrigem.setCellValueFactory(new PropertyValueFactory<>("origem"));
        colDestino.setCellValueFactory(new PropertyValueFactory<>("destino"));
        colData.setCellValueFactory(new PropertyValueFactory<>("dataPartida"));
        colAssento.setCellValueFactory(new PropertyValueFactory<>("assento"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        if (SessaoUsuario.isLogado()) {
            String nome = SessaoUsuario.getUsuarioLogado().getNome();

            statusLoginLabel.setText("Logado como: " + nome);
            statusLoginLabel.setStyle("-fx-text-fill: green;");
            loginButton.setText("Logout");

            painelMinhasReservas.setVisible(true);
            painelMinhasReservas.setManaged(true);
            atualizarTabelaReservas();
        } else {
            // USUÁRIO É VISITANTE
            statusLoginLabel.setText("Você está navegando como visitante.");
            statusLoginLabel.setStyle("-fx-text-fill: #888888;"); // Cinza
            loginButton.setText("Fazer Login");

            painelMinhasReservas.setVisible(false);
            painelMinhasReservas.setManaged(false);
        }
        carregarVoos();
    }
    private void atualizarTabelaReservas() {
        if (SessaoUsuario.isLogado()) {
            int idUsuario = SessaoUsuario.getUsuarioLogado().getIdUsuario();
            List<MinhaReservaDTO> reservas = reservaDAO.buscarReservasPorUsuario(idUsuario);
            tabelaReservas.setItems(FXCollections.observableArrayList(reservas));
        }
    }
    @FXML
    protected void handleBuscarVoosAction() {
        String origem = origemField.getText().trim();
        String destino = destinoField.getText().trim();

        try {
            // Limpa o painel atual
            painelDeVoos.getChildren().clear();

            List<Voo> voosEncontrados;

            // Se ambos estiverem vazios, carrega tudo (comportamento padrão)
            if (origem.isEmpty() && destino.isEmpty()) {
                voosEncontrados = vooDAO.buscarTodosOsVoos();
            } else {
                // Se tiver texto, usa o novo método de filtro
                voosEncontrados = vooDAO.buscarVoosFiltrados(origem, destino);
            }

            if (voosEncontrados.isEmpty()) {
                // Opcional: Mostrar um aviso visual que nada foi encontrado
                Label aviso = new Label("Nenhum voo encontrado para esta rota.");
                aviso.setStyle("-fx-font-size: 16px; -fx-text-fill: gray;");
                painelDeVoos.getChildren().add(aviso);
            } else {
                // Cria os cards para os voos encontrados
                for (Voo voo : voosEncontrados) {
                    VBox cardVoo = criarCardVoo(voo);
                    painelDeVoos.getChildren().add(cardVoo);
                }
            }

        } catch (DataAccessException e) {
            mostrarAlerta("Erro", "Erro ao buscar voos: " + e.getMessage());
        }
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
            atualizarTabelaReservas();
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
    @FXML private void handleLoginLogoutAction() {
        if (SessaoUsuario.isLogado()) {
            SessaoUsuario.limparSessao();
        }
        Navegador.voltarParaLogin();
    }
    @FXML private void handleAdminButtonAction() {
        Navegador.mudarTela("TelaAdminVoo.fxml", "Admin: Cadastrar Voo");
    }
}