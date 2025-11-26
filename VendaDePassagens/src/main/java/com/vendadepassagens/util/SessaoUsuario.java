package com.vendadepassagens.util;

import com.vendadepassagens.model.Usuario;

public class SessaoUsuario {

    private static Usuario usuarioLogado;

    // Construtor privado para que ninguém possa instanciar esta classe
    private SessaoUsuario() {}
    public static void setUsuarioLogado(Usuario usuario) {
        usuarioLogado = usuario;
    }
    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }
    public static boolean isLogado() {
        return usuarioLogado != null;
    }
    public static boolean isadmin() {
        return usuarioLogado != null;
    }
    public static void limparSessao() {
        usuarioLogado = null;
    }
}