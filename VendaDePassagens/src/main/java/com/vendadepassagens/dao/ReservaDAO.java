package com.vendadepassagens.dao;

import com.vendadepassagens.config.ConfiguracaoDB;
import com.vendadepassagens.model.Reserva;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;
import com.vendadepassagens.model.MinhaReservaDTO;

public class ReservaDAO {

    private JdbcTemplate jdbcTemplate = ConfiguracaoDB.getJdbcTemplate();

    public void criarReserva(Reserva reserva) {
        String sql = "INSERT INTO reservas (id_usuario, id_voo, assento, status_reserva) " +
                "VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                reserva.getIdUsuario(),
                reserva.getIdVoo(),
                reserva.getAssento(),
                reserva.getStatusReserva() // Ex: "CONFIRMADA"
        );
    }
    public List<MinhaReservaDTO> buscarReservasPorUsuario(int idUsuario) {
        String sql = "SELECT v.codigo_voo, v.origem, v.destino, v.data_partida, r.assento, r.status_reserva " +
                "FROM reservas r " +
                "JOIN voos v ON r.id_voo = v.id_voo " +
                "WHERE r.id_usuario = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> new MinhaReservaDTO(
                rs.getString("codigo_voo"),
                rs.getString("origem"),
                rs.getString("destino"),
                rs.getTimestamp("data_partida").toLocalDateTime(),
                rs.getString("assento"),
                rs.getString("status_reserva")
        ), idUsuario);
    }
}