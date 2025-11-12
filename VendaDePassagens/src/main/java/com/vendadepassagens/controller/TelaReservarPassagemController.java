package com.vendadepassagens.controller;

import com.vendadepassagens.dao.ReservaDAO;
import com.vendadepassagens.model.Reserva;
import com.vendadepassagens.model.Usuario;
import com.vendadepassagens.model.Voo;
import com.vendadepassagens.util.Navegador;
import com.vendadepassagens.util.SessaoUsuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage; // Para fechar o pop-up
import org.springframework.dao.DuplicateKeyException;

public class TelaReservarPassagemController {

    @FXML private VBox rootVBox;
    @FXML private Label infoVooLabel;
    @FXML private TextField assentoField;
    @FXML private Button confirmarButton;
    @FXML private Label mensagemLabel;

    private Voo vooSelecionado; // O voo que recebemos da TelaPrincipal
    private ReservaDAO reservaDAO;

    @FXML
    public void initialize() {
        this.reservaDAO = new ReservaDAO();
        mensagemLabel.setText("");
    }
    public void carregarDadosDoVoo(Voo voo) {
        this.vooSelecionado = voo;
        infoVooLabel.setText("Voo: " + voo.getCodigoVoo() +
                "\nDe: " + voo.getOrigem() +
                "\nPara: " + voo.getDestino());
    }
    @FXML
    protected void handleConfirmarReservaAction(ActionEvent event) {
        String assento = assentoField.getText();
        if (assento.isEmpty()) {
            mensagemLabel.setText("Por favor, digite um assento.");
            return;
        }

        try {
            Usuario usuarioLogado = SessaoUsuario.getUsuarioLogado();
            Reserva novaReserva = new Reserva();
            novaReserva.setIdUsuario(usuarioLogado.getIdUsuario());
            novaReserva.setIdVoo(vooSelecionado.getIdVoo());
            novaReserva.setAssento(assento);
            novaReserva.setStatusReserva("CONFIRMADA");
            reservaDAO.criarReserva(novaReserva);

            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso",
                    "Reserva confirmada no assento " + assento + "!");
            fecharJanela();
        } catch (DuplicateKeyException e) {
            mensagemLabel.setText("Erro: Este assento já está ocupado neste voo.");
        } catch (Exception e) {
            mensagemLabel.setText("Erro ao salvar: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private void fecharJanela() {
        Stage stage = (Stage) rootVBox.getScene().getWindow();
        stage.close();
    }
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String conteudo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(conteudo);
        alert.showAndWait();
    }
    @FXML
    public void handleCancelarReservaAction() {
        fecharJanela();
    }
}