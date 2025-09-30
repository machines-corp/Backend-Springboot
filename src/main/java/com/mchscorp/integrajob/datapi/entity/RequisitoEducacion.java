package com.mchscorp.integrajob.datapi.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "REQUISITO_EDUCACION")
public class RequisitoEducacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idReqEdu;

    @ManyToOne
    @JoinColumn(name = "id_oferta", nullable = false)
    private OfertaJob oferta;

    private String nivel;
    private String descripcion;
	public void setIdReqEdu(Integer id) {
		// TODO Auto-generated method stub
		
	}


}
