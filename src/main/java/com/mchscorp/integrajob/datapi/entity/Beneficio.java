package com.mchscorp.integrajob.datapi.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "BENEFICIOS")
public class Beneficio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idBeneficio;

    @ManyToOne
    @JoinColumn(name = "id_oferta", nullable = false)
    private OfertaJob oferta;

    private String descripcion;

	public void setIdBeneficio(Integer id) {
		this.idBeneficio = id;
	}

    // getters y setters
}
