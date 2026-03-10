package com.impulse_spring_app.service;

import com.impulse_spring_app.dto.CompraIngressoDTO;
import com.impulse_spring_app.exception.BusinessException;
import com.impulse_spring_app.exception.ResourceNotFoundException;
import com.impulse_spring_app.model.Evento;
import com.impulse_spring_app.model.Participante;
import com.impulse_spring_app.model.VendaIngresso;
import com.impulse_spring_app.repository.EventoRepository;
import com.impulse_spring_app.repository.ParticipanteRepository;
import com.impulse_spring_app.repository.VendaIngressoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VendaIngressoTest {

    @Mock
    private VendaIngressoRepository vendaIngressoRepository;

    @Mock
    private EventoRepository eventoRepository;

    @Mock
    private ParticipanteRepository participanteRepository;

    @InjectMocks
    private VendaIngressoService vendaIngressoService;

    private Evento evento;
    private Participante participante;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        evento = new Evento();
        evento.setId(1L);
        evento.setNome("Evento Teste");
        evento.setData(LocalDate.now().plusDays(5));
        evento.setLocal("Auditório");
        evento.setCapacidade(2);

        participante = new Participante();
        participante.setId(1L);
        participante.setNome("João");
        participante.setEmail("joao@email.com");
    }

    @Test
    void deveComprarIngressoComSucesso(){
        CompraIngressoDTO dto = new CompraIngressoDTO(participante.getId(), evento.getId());

        when(eventoRepository.findById(evento.getId())).thenReturn(Optional.of(evento));
        when(participanteRepository.findById(participante.getId())).thenReturn(Optional.of(participante));
        when(vendaIngressoRepository.findAllByParticipanteId(participante.getId())).thenReturn(new ArrayList<>());
        when(vendaIngressoRepository.save(any(VendaIngresso.class))).thenAnswer(i -> i.getArgument(0));
        when(eventoRepository.save(any(Evento.class))).thenAnswer(i -> i.getArgument(0));

        VendaIngresso vendaIngresso = vendaIngressoService.comprarIngresso(dto);

        assertNotNull(vendaIngresso);
        assertEquals(evento, vendaIngresso.getEvento());
        assertEquals(participante, vendaIngresso.getParticipante());
        assertEquals(1, evento.getCapacidade()); // capacidade decrementada
        verify(vendaIngressoRepository, times(1)).save(any(VendaIngresso.class));
    }

    @Test
    void naoDeveComprarIngressoEventoLotado(){
        evento.setCapacidade(0);
        CompraIngressoDTO dto = new CompraIngressoDTO(participante.getId(), evento.getId());

        when(eventoRepository.findById(evento.getId())).thenReturn(Optional.of(evento));

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () ->
                vendaIngressoService.comprarIngresso(dto)
        );

        // Vamos capturar BusinessException ao invés de ResourceNotFound
        evento.setCapacidade(0);
        when(participanteRepository.findById(participante.getId())).thenReturn(Optional.of(participante));
        BusinessException ex2 = assertThrows(BusinessException.class, () ->
                vendaIngressoService.comprarIngresso(dto)
        );

        assertEquals("Evento lotado", ex2.getMessage());
    }

    @Test
    void naoDeveComprarIngressoParticipanteJaComprou(){
        CompraIngressoDTO dto = new CompraIngressoDTO(participante.getId(), evento.getId());

        VendaIngresso vendaIngressoExistente = new VendaIngresso();
        vendaIngressoExistente.setEvento(evento);
        List<VendaIngresso> listaVendaIngresso = List.of(vendaIngressoExistente);

        when(eventoRepository.findById(evento.getId())).thenReturn(Optional.of(evento));
        when(participanteRepository.findById(participante.getId())).thenReturn(Optional.of(participante));
        when(vendaIngressoRepository.findAllByParticipanteId(participante.getId())).thenReturn(listaVendaIngresso);

        BusinessException ex = assertThrows(BusinessException.class, () ->
                vendaIngressoService.comprarIngresso(dto)
        );

        assertEquals("Participante já possui ingresso para este evento", ex.getMessage());
    }

    @Test
    void naoDeveComprarIngressoEventoPassado(){
        evento.setData(LocalDate.now().minusDays(1));
        CompraIngressoDTO dto = new CompraIngressoDTO(participante.getId(), evento.getId());

        when(eventoRepository.findById(evento.getId())).thenReturn(Optional.of(evento));
        when(participanteRepository.findById(participante.getId())).thenReturn(Optional.of(participante));

        BusinessException ex = assertThrows(BusinessException.class, () ->
                vendaIngressoService.comprarIngresso(dto)
        );

        assertEquals("Não é possível comprar ingressos para eventos já realizados", ex.getMessage());
    }

    @Test
    void deveListarIngressosPorParticipante(){
        VendaIngresso ticket1 = new VendaIngresso();
        ticket1.setEvento(evento);
        ticket1.setParticipante(participante);

        List<VendaIngresso> lista = List.of(ticket1);

        when(vendaIngressoRepository.findAllByParticipanteId(participante.getId())).thenReturn(lista);

        List<VendaIngresso> resultado = vendaIngressoService.listarIngressosPorParticipante(participante.getId());

        assertEquals(1, resultado.size());
        assertEquals(ticket1, resultado.get(0));
    }
}
