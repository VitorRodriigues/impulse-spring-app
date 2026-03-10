package com.impulse_spring_app.dto;

import jakarta.validation.constraints.NotNull;

public record CompraIngressoDTO(
        @NotNull
        Long participanteId,

        @NotNull
        Long eventoId
) {}