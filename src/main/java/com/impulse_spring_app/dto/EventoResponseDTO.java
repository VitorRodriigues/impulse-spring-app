package com.impulse_spring_app.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class EventoResponseDTO {

    private Long id;
    private String nome;
    private LocalDate data;
    private String local;
    private Integer capacidade;
}