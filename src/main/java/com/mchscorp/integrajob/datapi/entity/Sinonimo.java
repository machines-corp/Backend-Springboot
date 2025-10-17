package com.mchscorp.integrajob.datapi.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SINONIMO")
public class Sinonimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSinonimo;

    @Column(nullable = false)
    private String palabra;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_caracteristica", nullable = false)
    private Caracteristica caracteristica;
}
