package com.vendadepassagens.model;

public class Usuario {
    private Integer idUsuario;
    private String nome;
    private String email;
    private String senha;
    private String documento;
    private int saldoMilhas; // Corresponde a 'saldo_milhas'
    private boolean isAdmin;

    //getters e setters
    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public boolean isAdmin() {return isAdmin; }

    public void setAdmin(boolean admin) {isAdmin = admin; }

    public int getSaldoMilhas() {
        return saldoMilhas;
    }

    public void setSaldoMilhas(int saldoMilhas) {
        this.saldoMilhas = saldoMilhas;
    }

}
