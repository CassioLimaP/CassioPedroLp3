package com.vendadepassagens.controller;

import com.vendadepassagens.dao.VooDAO;
import com.vendadepassagens.model.Voo;
import com.vendadepassagens.util.Navegador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TelaAdminVooController {

    @FXML private TextField codigoField;
    @FXML private TextField origemField;
    @FXML private TextField destinoField;
    @FXML private TextField partidaField;
    @FXML private TextField chegadaField;
    @FXML private TextField capacidadeField;
    @FXML private TextField precoField;
    @FXML private Label mensagemLabel;

    private VooDAO vooDAO;
    // Define o formato que esperamos da data/hora
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @FXML
    public void initialize() {
        this.vooDAO = new VooDAO();
        mensagemLabel.setText("");
    }

    @FXML
    protected void handleSalvarVooAction(ActionEvent event) {
        try {
            Voo novoVoo = getVoo();
            vooDAO.inserirVoo(novoVoo);
            mostrarAlerta("Sucesso", "Novo voo cadastrado!");
            limparCampos();

        } catch (NumberFormatException e) {
            mensagemLabel.setText("Erro: Capacidade e Preço devem ser números.");
        } catch (java.time.format.DateTimeParseException e) {
            mensagemLabel.setText("Erro: Formato de data inválido. Use AAAA-MM-DD HH:MM");
        } catch (Exception e) {
            mensagemLabel.setText("Erro ao salvar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Voo getVoo() {
        String codigo = codigoField.getText();
        String origem = origemField.getText();
        String destino = destinoField.getText();
        LocalDateTime partida = LocalDateTime.parse(partidaField.getText(), formatter);
        LocalDateTime chegada = LocalDateTime.parse(chegadaField.getText(), formatter);
        int capacidade = Integer.parseInt(capacidadeField.getText());
        BigDecimal preco = new BigDecimal(precoField.getText());
        Voo novoVoo = new Voo();
        novoVoo.setCodigoVoo(codigo);
        novoVoo.setOrigem(origem);
        novoVoo.setDestino(destino);
        novoVoo.setDataPartida(partida);
        novoVoo.setDataChegada(chegada);
        novoVoo.setCapacidade(capacidade);
        novoVoo.setPrecoAssento(preco);
        return novoVoo;
    }

    @FXML
    protected void handleVoltarAction(ActionEvent event) {
        // Assume que você tem um FXML principal
        Navegador.mudarTela("TelaPrincipal.fxml","tela principal");
    }

    private void limparCampos() {
        codigoField.clear();
        origemField.clear();
        destinoField.clear();
        partidaField.clear();
        chegadaField.clear();
        capacidadeField.clear();
        precoField.clear();
    }

    private void mostrarAlerta(String titulo, String conteudo) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(conteudo);
        alert.showAndWait();
    }
}