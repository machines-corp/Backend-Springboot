package com.mchscorp.integrajob.datapi.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "AGRUPACION")
public class Agrupacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAgrupacion;

    @Column(nullable = false, unique = true)
    private String nombre;

    @OneToMany(mappedBy = "agrupacion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Caracteristica> caracteristicas;
}
