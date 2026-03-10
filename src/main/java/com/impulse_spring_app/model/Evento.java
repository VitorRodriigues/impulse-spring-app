package com.impulse_spring_app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "eventos")

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Evento {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private String local;

    @Column(nullable = false)
    private int capacidade;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL)
    private List<VendaIngresso> vendas = new ArrayList<>();

    @Version
    private Long version;
}