package com.mchscorp.integrajob.datapi.entity;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "UBICACION")
public class Ubicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUbicacion;

    private String region;
    private String comuna;
    private String direccion;
    private BigDecimal latitud;
    private BigDecimal longitud;

    @OneToMany(mappedBy = "ubicacion")
    private List<OfertaJob> ofertas;

	public void setIdUbicacion(Integer id) {
		// TODO Auto-generated method stub
		
	}
}