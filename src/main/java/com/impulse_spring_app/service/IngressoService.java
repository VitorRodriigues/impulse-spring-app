package com.impulse_spring_app.service;

import com.impulse_spring_app.repository.EventoRepository;
import com.impulse_spring_app.repository.ParticipanteRepository;
import com.impulse_spring_app.repository.VendaIngressoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IngressoService {

    private final EventoRepository eventoRepository;
    private final ParticipanteRepository participanteRepository;
    private final VendaIngressoRepository ingressoRepository;



}
