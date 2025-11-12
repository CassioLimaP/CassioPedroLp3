package com.vendadepassagens.dao;

import com.vendadepassagens.model.Usuario;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import com.vendadepassagens.config.ConfiguracaoDB; // Assumindo que sua classe está aqui

import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    // 1. Pegue o JdbcTemplate configurado
    private JdbcTemplate jdbcTemplate = ConfiguracaoDB.getJdbcTemplate();

    // 2. Crie o "Mapeador"
    // (Instrui o Spring como transformar uma linha do SQL em um objeto Usuario)
    private class UsuarioRowMapper implements RowMapper<Usuario> {
        @Override
        public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(rs.getInt("id_usuario"));
            usuario.setNome(rs.getString("nome"));
            usuario.setEmail(rs.getString("email"));
            usuario.setSenha(rs.getString("senha")); // Lembre-se que está em texto puro
            usuario.setDocumento(rs.getString("documento"));
            usuario.setSaldoMilhas(rs.getInt("saldo_milhas"));
            usuario.setAdmin(rs.getBoolean("is_admin"));
            return usuario;
        }
    }

    // --- Métodos de CRUD (Create, Read, Update, Delete) ---

    /**
     * Tenta encontrar um usuário pelo email e senha. (Para o Login)
     * Note: NENHUM try-catch de SQLException é necessário!
     */
    public Usuario buscarUsuarioPorLogin(String email, String senha) {
        String sql = "SELECT * FROM usuarios WHERE email = ? AND senha = ?";

        try {
            // queryForObject espera UM resultado.
            // Se não encontrar nada, ele lança a exceção EmptyResultDataAccessException
            return jdbcTemplate.queryForObject(sql, new UsuarioRowMapper(), email, senha);

        } catch (EmptyResultDataAccessException e) {
            // Isso é "normal" - significa que o login/senha está errado
            return null;
        }
        // Se o banco cair, uma DataAccessException (Runtime) será lançada
        // e tratada pelo seu Controller (o ActionListener do Swing)
    }

    /**
     * Insere um novo usuário no banco de dados. (Para o Cadastro)
     */
    public void cadastrarUsuario(Usuario novoUsuario) {
        String sql = "INSERT INTO usuarios (nome, email, senha, documento, saldo_milhas) VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                novoUsuario.getNome(),
                novoUsuario.getEmail(),
                novoUsuario.getSenha(),
                novoUsuario.getDocumento(),
                novoUsuario.getSaldoMilhas()
        );
        // Se o email/documento for duplicado, o Spring vai lançar
        // uma DuplicateKeyException (Runtime) que o Controller pode pegar!
    }
}