package com.vendadepassagens.controller;

import com.vendadepassagens.dao.ReservaDAO;
import com.vendadepassagens.dao.UsuarioDAO;
import com.vendadepassagens.model.MinhaReservaDTO;
import com.vendadepassagens.model.Usuario;
import com.vendadepassagens.util.Navegador;
import com.vendadepassagens.util.SessaoUsuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;
import java.util.List;

public class TelaMeusVoosController {

    @FXML private Label milhasLabel;
    @FXML private TableView<MinhaReservaDTO> tabelaVoos;
    @FXML private TableColumn<MinhaReservaDTO, String> colCodigo;
    @FXML private TableColumn<MinhaReservaDTO, String> colOrigem;
    @FXML private TableColumn<MinhaReservaDTO, String> colDestino;
    @FXML private TableColumn<MinhaReservaDTO, LocalDateTime> colData;
    @FXML private TableColumn<MinhaReservaDTO, String> colAssento;
    @FXML private TableColumn<MinhaReservaDTO, String> colStatus;

    private ReservaDAO reservaDAO;

    @FXML
    public void initialize() {
        this.reservaDAO = new ReservaDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        // 2. Verifica segurança
        if (!SessaoUsuario.isLogado()) {
            Navegador.voltarParaLogin();
            return;
        }

        // 3. Carrega as Milhas
        Usuario usuario = SessaoUsuario.getUsuarioLogado();
        // Busca saldo atualizado do banco para garantir
        int saldoAtual = usuarioDAO.buscarSaldoMilhas(usuario.getIdUsuario());
        milhasLabel.setText(String.valueOf(saldoAtual));

        // 4. Configura as Colunas da Tabela (liga às variáveis do DTO)
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigoVoo"));
        colOrigem.setCellValueFactory(new PropertyValueFactory<>("origem"));
        colDestino.setCellValueFactory(new PropertyValueFactory<>("destino"));
        colData.setCellValueFactory(new PropertyValueFactory<>("dataPartida"));
        colAssento.setCellValueFactory(new PropertyValueFactory<>("assento"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        // 5. Carrega os Dados
        carregarTabela(usuario.getIdUsuario());
    }

    private void carregarTabela(int idUsuario) {
        List<MinhaReservaDTO> lista = reservaDAO.buscarReservasPorUsuario(idUsuario);
        ObservableList<MinhaReservaDTO> dados = FXCollections.observableArrayList(lista);
        tabelaVoos.setItems(dados);
    }

    @FXML
    protected void handleVoltarAction(ActionEvent event) {
        Navegador.mudarTela("TelaPrincipal.fxml", "Painel de Voos");
    }
}