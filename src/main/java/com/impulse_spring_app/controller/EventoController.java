package com.impulse_spring_app.controller;

import com.impulse_spring_app.dto.EventoDTO;
import com.impulse_spring_app.service.EventoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
public class EventoController {

    private final EventoService service;

    @GetMapping
    public ResponseEntity<Page<EventoDTO>> listar(Pageable pageable) {

        return ResponseEntity.ok(service.listar(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoDTO> buscar(@PathVariable Long id) {

        return ResponseEntity.ok(service.buscar(id));
    }

    @PostMapping
    public ResponseEntity<EventoDTO> criar(
            @Valid @RequestBody EventoDTO dto) {

        return ResponseEntity
                .status(201)
                .body(service.criar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody EventoDTO dto) {

        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {

        service.deletar(id);

        return ResponseEntity.noContent().build();
    }
}