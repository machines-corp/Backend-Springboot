package com.mchscorp.integrajob.datapi.entity;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "SALARIO")
public class Salario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSalario;

    @ManyToOne
    @JoinColumn(name = "id_oferta", nullable = false)
    private OfertaJob oferta;

    private String moneda;
    private BigDecimal minimo;
    private BigDecimal maximo;
    private Boolean mostrarSueldo = true;
	public void setIdSalario(Integer id) {
		// TODO Auto-generated method stub
		
	}
}

