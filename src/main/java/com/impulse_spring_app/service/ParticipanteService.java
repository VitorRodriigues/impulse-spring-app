package com.impulse_spring_app.service;

import com.impulse_spring_app.dto.CompraIngressoDTO;
import com.impulse_spring_app.dto.ParticipanteDTO;
import com.impulse_spring_app.exception.BusinessException;
import com.impulse_spring_app.exception.ResourceNotFoundException;
import com.impulse_spring_app.mapper.ParticipanteMapper;
import com.impulse_spring_app.model.Evento;
import com.impulse_spring_app.model.Participante;
import com.impulse_spring_app.repository.ParticipanteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipanteService {

    private final ParticipanteRepository participanteRepository;
    private final ParticipanteMapper participanteMapper;

    @Transactional
    public Participante criarParticipante(ParticipanteDTO dto){
        Participante participante = participanteMapper.toEntity(dto);
        return participanteRepository.save(participante);
    }

    public List<Participante> listarParticipantes(){
        return participanteRepository.findAll();
    }

    public Participante buscarPorId(Long id){
        return participanteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Participante não encontrado"));
    }
}
