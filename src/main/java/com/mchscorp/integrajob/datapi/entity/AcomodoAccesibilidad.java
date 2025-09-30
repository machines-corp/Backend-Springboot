package com.mchscorp.integrajob.datapi.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ACOMODOS_ACCESIBILIDAD")
public class AcomodoAccesibilidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAcomodo;

    @ManyToOne
    @JoinColumn(name = "id_oferta", nullable = false)
    private OfertaJob oferta;

    private String tipo;
    private String detalle;

    @ManyToOne
    @JoinColumn(name = "id_taxonomia")
    private Taxonomia taxonomia;

	public void setIdAcomodo(Integer id) {
		// TODO Auto-generated method stub
		
	}

}

