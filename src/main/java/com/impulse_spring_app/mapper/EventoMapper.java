package com.impulse_spring_app.mapper;

import com.impulse_spring_app.dto.EventoRequestDTO;
import com.impulse_spring_app.dto.EventoResponseDTO;
import com.impulse_spring_app.model.Evento;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EventoMapper {

    Evento toEntity(EventoRequestDTO dto);

    EventoResponseDTO toDTO(Evento evento);

    void updateEntity(EventoRequestDTO dto, @MappingTarget Evento evento);
}