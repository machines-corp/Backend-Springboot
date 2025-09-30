package com.mchscorp.integrajob.datapi.entity;

import jakarta.persistence.*;
import java.util.List;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "TAXONOMIA")
public class Taxonomia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTaxonomia;

    private String categoria;
    private String valor;

    @OneToMany(mappedBy = "taxonomia")
    private List<AcomodoAccesibilidad> acomodos;

	public void setIdTaxonomia(Integer id) {
		// TODO Auto-generated method stub
		
	}

    // getters y setters
}