package com.impulse_spring_app.controller;

import com.impulse_spring_app.dto.VendaIngressoDTO;
import com.impulse_spring_app.model.VendaIngresso;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface VendaIngressoMapper {

    @Mapping(source = "evento.nome", target = "nomeEvento")
    @Mapping(source = "participante.nome", target = "participanteNome")
    @Mapping(source = "dataCompra", target = "dataCompra")
    VendaIngressoDTO toDTO(VendaIngresso ticket);
}