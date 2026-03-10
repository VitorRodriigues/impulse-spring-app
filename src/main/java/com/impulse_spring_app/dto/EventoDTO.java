package com.impulse_spring_app.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

public record EventoDTO(
        Long id,

        @NotBlank(message = "Nome não pode estar vazio")
        String nome,

        @Future(message = "A data deve ser no futuro")
        LocalDate data,

        @NotBlank(message = "Local não pode estar vazio")
        String local,

        @Positive(message = "Capacidade deve ser maior que zero")
        int capacidade
) {}