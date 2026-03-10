package com.impulse_spring_app.service;

import com.impulse_spring_app.dto.CompraIngressoDTO;
import com.impulse_spring_app.dto.VendaIngressoDTO;
import com.impulse_spring_app.exception.BusinessException;
import com.impulse_spring_app.exception.ResourceNotFoundException;
import com.impulse_spring_app.model.Evento;
import com.impulse_spring_app.model.Participante;
import com.impulse_spring_app.model.VendaIngresso;
import com.impulse_spring_app.repository.EventoRepository;
import com.impulse_spring_app.repository.ParticipanteRepository;
import com.impulse_spring_app.repository.VendaIngressoRepository;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VendaIngressoService {

    private final VendaIngressoRepository vendaIngressoRepository;
    private final EventoRepository eventoRepository;
    private final ParticipanteRepository participanteRepository;

    @Transactional
    public VendaIngresso comprarIngresso(CompraIngressoDTO dto) {
        try {
            // Busca evento com Lock Otimista
            Evento evento = eventoRepository.findById(dto.eventoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado"));

            // Valida data do evento
            if (evento.getData().isBefore(java.time.LocalDate.now())) {
                throw new BusinessException("Não é possível comprar ingressos para eventos já realizados");
            }

            // Busca participante
            Participante participante = participanteRepository.findById(dto.participanteId())
                    .orElseThrow(() -> new ResourceNotFoundException("Participante não encontrado"));

            // Verifica se participante já comprou ingresso para o mesmo evento
            boolean jaComprou = vendaIngressoRepository.findAllByParticipanteId(participante.getId())
                    .stream()
                    .anyMatch(t -> t.getEvento().getId().equals(evento.getId()));
            if (jaComprou) {
                throw new BusinessException("Participante já possui ingresso para este evento");
            }

            // Verifica capacidade
            if (evento.getCapacidade() <= 0) {
                throw new BusinessException("Evento lotado");
            }

            // Decrementa capacidade e salva (lock otimista garante consistência)
            evento.setCapacidade(evento.getCapacidade() - 1);
            eventoRepository.save(evento);

            // Cria ticket
            VendaIngresso vendaIngresso = new VendaIngresso();
            vendaIngresso.setEvento(evento);
            vendaIngresso.setParticipante(participante);
            vendaIngresso.setDataCompra(LocalDateTime.now());

            return vendaIngressoRepository.save(vendaIngresso);

        } catch (OptimisticLockException e) {
            throw new BusinessException("Conflito de concorrência: tente novamente");
        }
    }

    public java.util.List<VendaIngresso> listarIngressosPorParticipante(Long participanteId) {
        return vendaIngressoRepository.findAllByParticipanteId(participanteId);
    }
}
