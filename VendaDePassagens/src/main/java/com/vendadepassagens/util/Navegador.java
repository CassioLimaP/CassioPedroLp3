package com.vendadepassagens.util;

import com.vendadepassagens.controller.TelaReservarPassagemController;
import com.vendadepassagens.model.Voo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Modality;


public class Navegador {
    private static Stage stagePrincipal;
    private static Scene cenaLogin; // Guarda a cena de login para reuso


    public static void setStagePrincipal(Stage stage) {
        stagePrincipal = stage;
    }
    public static void setCenaLogin(Scene scene) {
        cenaLogin = scene;
    }
    public static void voltarParaLogin() {
        if (stagePrincipal != null && cenaLogin != null) {
            stagePrincipal.setScene(cenaLogin);
            stagePrincipal.setTitle("Login - Sistema de Passagens");
        }
    }
    public static void mudarTela(String nomeArquivoFXML, String titulo) {
        try {
            // Constrói o caminho completo
            String caminhoAbsoluto = "/com/vendadepassagens/fxml/" + nomeArquivoFXML;

            FXMLLoader fxmlLoader = new FXMLLoader(Navegador.class.getResource(caminhoAbsoluto));
            Parent root = fxmlLoader.load();
            Scene novaCena = new Scene(root);
            stagePrincipal.setScene(novaCena);
            stagePrincipal.setTitle(titulo);

        } catch (Exception e) {
            System.err.println("Erro ao carregar FXML: " + nomeArquivoFXML);
            e.printStackTrace();
        }
    }
    public static void abrirPopUpReserva(Voo voo) {
        try {
            // Constrói o caminho completo
            String fxmlPath = "/com/vendadepassagens/fxml/TelaReservarPassagem.fxml";

            FXMLLoader loader = new FXMLLoader(Navegador.class.getResource(fxmlPath));
            Parent root = loader.load();

            TelaReservarPassagemController controller = loader.getController();
            controller.carregarDadosDoVoo(voo);

            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Fazer Reserva");
            popupStage.setScene(new Scene(root));
            popupStage.showAndWait();

        } catch (Exception e) {
            System.err.println("Falha ao carregar o FXML do pop-up de reserva!");
            e.printStackTrace();
        }
    }
}

