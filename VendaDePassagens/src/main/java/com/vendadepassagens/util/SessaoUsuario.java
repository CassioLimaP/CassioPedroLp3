package com.vendadepassagens.util;

import com.vendadepassagens.model.Usuario;

/**
 * Classe Singleton (estática) para guardar o estado da sessão do usuário.
 * Ela vai nos dizer quem está logado em qualquer parte do app.
 */
public class SessaoUsuario {

    private static Usuario usuarioLogado;

    // Construtor privado para que ninguém possa instanciar esta classe
    private SessaoUsuario() {}

    /**
     * Define o usuário que acabou de logar.
     * @param usuario O usuário vindo do banco de dados.
     */
    public static void setUsuarioLogado(Usuario usuario) {
        usuarioLogado = usuario;
    }

    /**
     * Retorna o objeto do usuário que está logado.
     * @return O usuário logado, or null se for um visitante.
     */
    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    /**
     * Um atalho para verificar se há alguém logado.
     * @return true se usuarioLogado NÃO for null.
     */
    public static boolean isLogado() {
        return usuarioLogado != null;
    }

    /**
     * Limpa a sessão (para um futuro botão de "Logout").
     */
    public static void limparSessao() {
        usuarioLogado = null;
    }
}