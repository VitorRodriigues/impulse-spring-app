package com.impulse_spring_app.repository;

import com.impulse_spring_app.model.VendaIngresso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendaIngressoRepository extends JpaRepository<VendaIngresso, Long> {

}
