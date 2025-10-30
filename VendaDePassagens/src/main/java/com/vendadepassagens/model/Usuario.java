package com.vendadepassagens.model;

public class Usuario {
    private Integer idUsuario;
    private String nome;
    private String email;
    private String senha;
    private String documento;
    private int saldoMilhas; // Corresponde a 'saldo_milhas'

    //metodos de usuario
    public Boolean login(String email, String senha){
        /*
        verificar se usuario consta no banco de dados.
        verificar senha correta
        se estiver: recuperar estado do usuario, da valor true a Cadastro
        se nao: mensagem de erro no login ou senha
        */
        return true ;
    }
    public void deslogar(){
        //desloga o usuario
    }

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

    public int getSaldoMilhas() {
        return saldoMilhas;
    }

    public void setSaldoMilhas(int saldoMilhas) {
        this.saldoMilhas = saldoMilhas;
    }

}
