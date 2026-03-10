package com.impulse_spring_app.mapper;

import com.impulse_spring_app.dto.EventoDTO;
import com.impulse_spring_app.model.Evento;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EventoMapper {

    Evento toEntity(EventoDTO dto);

    EventoDTO toDTO(Evento evento);

    void updateEntity(EventoDTO dto, @MappingTarget Evento evento);
}