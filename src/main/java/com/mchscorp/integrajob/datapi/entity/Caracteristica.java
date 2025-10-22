package com.mchscorp.integrajob.datapi.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CARACTERISTICA")
public class Caracteristica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCaracteristica;

    @Column(nullable = false)
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_agrupacion", nullable = false)
    private Agrupacion agrupacion;

    @OneToMany(mappedBy = "caracteristica", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Sinonimo> sinonimos;
}
