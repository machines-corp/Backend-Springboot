package com.mchscorp.integrajob.datapi.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "CATEGORIA_OCUPACIONAL")
public class CategoriaOcupacional {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCategoria;

    @ManyToOne
    @JoinColumn(name = "id_oferta", nullable = false)
    private OfertaJob oferta;

    private String codigo;
    private String nombre;
	public void setIdCategoria(Integer id) {
		// TODO Auto-generated method stub
		
	}

    // getters y setters
}