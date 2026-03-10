package com.impulse_spring_app.repository;

import com.impulse_spring_app.model.VendaIngresso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendaIngressoRepository extends JpaRepository<VendaIngresso, Long> {

    long countByEventoId(Long eventoId);

    List<VendaIngresso> findByParticipanteEmail(String email);
}
