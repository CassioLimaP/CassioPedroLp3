package com.vendadepassagens.dao;

import com.config.ConfiguracaoDB; // Importe sua config
import com.vendadepassagens.model.Voo;           // Importe seu modelo Voo
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.vendadepassagens.model.Voo; // Importe o modelo
import java.sql.Timestamp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class VooDAO {

    private JdbcTemplate jdbcTemplate = ConfiguracaoDB.getJdbcTemplate();

    /**
     * Mapeador: Ensina o Spring a converter uma linha do ResultSet
     * em um objeto "Voo".
     */
    private class VooRowMapper implements RowMapper<Voo> {
        @Override
        public Voo mapRow(ResultSet rs, int rowNum) throws SQLException {
            // Crie sua classe Voo.java com estes campos
            Voo voo = new Voo();
            voo.setIdVoo(rs.getInt("id_voo"));
            voo.setCodigoVoo(rs.getString("codigo_voo"));
            voo.setOrigem(rs.getString("origem"));
            voo.setDestino(rs.getString("destino"));
            voo.setDataPartida(rs.getTimestamp("data_partida").toLocalDateTime());
            voo.setDataChegada(rs.getTimestamp("data_chegada").toLocalDateTime());
            voo.setCapacidade(rs.getInt("capacidade"));
            voo.setPrecoAssento(rs.getBigDecimal("preco_assento"));
            return voo;
        }
    }

    /**
     * Busca TODOS os voos do banco de dados.
     * (Mais tarde, você pode adicionar filtros de origem/destino aqui)
     */
    public List<Voo> buscarTodosOsVoos() {
        String sql = "SELECT * FROM voos";

        // O jdbcTemplate.query cuida de tudo e retorna a lista
        return jdbcTemplate.query(sql, new VooRowMapper());
    }
    public void inserirVoo(Voo novoVoo) {
        String sql = "INSERT INTO voos " +
                "(codigo_voo, origem, destino, data_partida, data_chegada, capacidade, preco_assento) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        // Converte LocalDateTime para Timestamp do SQL
        Timestamp partida = Timestamp.valueOf(novoVoo.getDataPartida());
        Timestamp chegada = Timestamp.valueOf(novoVoo.getDataChegada());

        jdbcTemplate.update(sql,
                novoVoo.getCodigoVoo(),
                novoVoo.getOrigem(),
                novoVoo.getDestino(),
                partida,
                chegada,
                novoVoo.getCapacidade(),
                novoVoo.getPrecoAssento()
        );
    }

    // TODO: Adicionar métodos como buscarVooPorId(int id), etc.
}
