package com.impulse_spring_app.service;

import com.impulse_spring_app.dto.EventoDTO;
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

    public Page<EventoDTO> listar(Pageable pageable) {

        return repository.findAll(pageable)
                .map(mapper::toDTO);
    }

    public EventoDTO buscar(Long id) {

        Evento evento = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado"));

        return mapper.toDTO(evento);
    }

    public EventoDTO criar(EventoDTO dto) {

        Evento evento = mapper.toEntity(dto);

        return mapper.toDTO(repository.save(evento));
    }

    public EventoDTO atualizar(Long id, EventoDTO dto) {

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
