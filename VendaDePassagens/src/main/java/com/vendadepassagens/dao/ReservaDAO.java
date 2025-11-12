package com.vendadepassagens.dao;

import com.vendadepassagens.config.ConfiguracaoDB;
import com.vendadepassagens.model.Reserva;
import org.springframework.jdbc.core.JdbcTemplate;

public class ReservaDAO {

    private JdbcTemplate jdbcTemplate = ConfiguracaoDB.getJdbcTemplate();

    /**
     * Insere uma nova reserva no banco de dados.
     * @param reserva O objeto Reserva preenchido
     */
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

    // TODO: Adicionar métodos futuros (ex: cancelarReserva, buscarReservasPorUsuario)
}