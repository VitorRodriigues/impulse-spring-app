package com.impulse_spring_app.mapper;

import com.impulse_spring_app.dto.ParticipanteDTO;
import com.impulse_spring_app.model.Participante;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ParticipanteMapper {

    ParticipanteMapper INSTANCE = Mappers.getMapper(ParticipanteMapper.class);

    Participante toEntity(ParticipanteDTO dto);

    ParticipanteDTO toDTO(Participante participante);

    void updateParticipanteFromDTO(ParticipanteDTO dto, @MappingTarget Participante participante);
}