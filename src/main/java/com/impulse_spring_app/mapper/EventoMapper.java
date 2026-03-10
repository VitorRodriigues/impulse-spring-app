package com.impulse_spring_app.mapper;

import com.impulse_spring_app.dto.EventoDTO;
import com.impulse_spring_app.dto.VendaIngressoDTO;
import com.impulse_spring_app.model.Evento;
import com.impulse_spring_app.model.VendaIngresso;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EventoMapper {

    Evento toEntity(EventoDTO dto);

    EventoDTO toDTO(Evento evento);

    void updateEntity(EventoDTO dto, @MappingTarget Evento evento);

    @Mapper(componentModel = "spring")
    interface VendaIngressoMapper {

        @Mapping(source = "evento.nome", target = "nomeEvento")
        @Mapping(source = "participante.nome", target = "participanteNome")
        @Mapping(source = "dataCompra", target = "dataCompra")
        VendaIngressoDTO toDTO(VendaIngresso ticket);
    }
}