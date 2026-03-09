package com.impulse_spring_app.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EventoRequestDTO {

    @NotBlank
    private String nome;

    @NotNull
    @FutureOrPresent
    private LocalDate data;

    @NotBlank
    private String local;

    private Integer capacidade;
}