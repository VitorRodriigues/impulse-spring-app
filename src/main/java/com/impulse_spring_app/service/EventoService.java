package com.impulse_spring_app.service;

import com.impulse_spring_app.dto.EventoRequestDTO;
import com.impulse_spring_app.dto.EventoResponseDTO;
import com.impulse_spring_app.exception.ResourceNotFoundException;
import com.impulse_spring_app.mapper.EventoMapper;
import com.impulse_spring_app.model.Evento;
import com.impulse_spring_app.repository.EventoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository repository;
    private final EventoMapper mapper;

    public Page<EventoResponseDTO> listar(Pageable pageable) {

        return repository.findAll(pageable)
                .map(mapper::toDTO);
    }

    public EventoResponseDTO buscar(Long id) {

        Evento evento = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado"));

        return mapper.toDTO(evento);
    }

    public EventoResponseDTO criar(EventoRequestDTO dto) {

        Evento evento = mapper.toEntity(dto);

        return mapper.toDTO(repository.save(evento));
    }

    public EventoResponseDTO atualizar(Long id, EventoRequestDTO dto) {

        Evento evento = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado"));

        mapper.updateEntity(dto, evento);

        return mapper.toDTO(repository.save(evento));
    }

    public void deletar(Long id) {

        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Evento não encontrado");
        }

        repository.deleteById(id);
    }
}
