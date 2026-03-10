package com.impulse_spring_app.dto;

import java.time.LocalDateTime;

public record VendaIngressoDTO(
        Long id,

        String nomeEvento,

        String participanteNome,

        LocalDateTime dataCompra

) {}
