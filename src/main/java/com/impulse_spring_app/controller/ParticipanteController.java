package com.impulse_spring_app.controller;

import com.impulse_spring_app.dto.CompraIngressoDTO;
import com.impulse_spring_app.dto.ParticipanteDTO;
import com.impulse_spring_app.dto.VendaIngressoDTO;
import com.impulse_spring_app.mapper.ParticipanteMapper;
import com.impulse_spring_app.model.Evento;
import com.impulse_spring_app.model.Participante;
import com.impulse_spring_app.model.VendaIngresso;
import com.impulse_spring_app.service.ParticipanteService;
import com.impulse_spring_app.service.VendaIngressoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/participantes")
@RequiredArgsConstructor
public class ParticipanteController {

    private final ParticipanteService participanteService;
    private final VendaIngressoService vendaIngressoService;
    private final ParticipanteMapper participanteMapper;
    private final VendaIngressoMapper vendaIngressoMapper;

    @PostMapping
    public ResponseEntity<ParticipanteDTO> criarParticipante(@Valid @RequestBody ParticipanteDTO dto){
        Participante participante = participanteService.criarParticipante(dto);
        return ResponseEntity.status(201).body(participanteMapper.toDTO(participante));
    }

    @GetMapping
    public ResponseEntity<List<ParticipanteDTO>> listarParticipantes(){
        List<ParticipanteDTO> dtos = participanteService.listarParticipantes()
                .stream().map(participanteMapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParticipanteDTO> buscarPorId(@PathVariable Long id){
        Participante participante = participanteService.buscarPorId(id);
        return ResponseEntity.ok(participanteMapper.toDTO(participante));
    }

    // Compra de ingresso
    @PostMapping("/comprar")
    public ResponseEntity<VendaIngressoDTO> comprarIngresso(@Valid @RequestBody CompraIngressoDTO dto){
        VendaIngresso ticket = vendaIngressoService.comprarIngresso(dto);
        return ResponseEntity.status(201).body(vendaIngressoMapper.toDTO(ticket));
    }

    // Histórico de ingressos
    @GetMapping("/{id}/tickets")
    public ResponseEntity<List<VendaIngressoDTO>> listarTickets(@PathVariable Long id){
        List<VendaIngressoDTO> dtos = vendaIngressoService.listarIngressosPorParticipante(id)
                .stream().map(vendaIngressoMapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}
