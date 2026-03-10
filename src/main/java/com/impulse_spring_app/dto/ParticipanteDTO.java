package com.impulse_spring_app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ParticipanteDTO(
        Long id,

        @NotBlank(message = "Nome não pode estar vazio")
        String nome,

        @Email(message = "Email inválido")
        String email
) {}