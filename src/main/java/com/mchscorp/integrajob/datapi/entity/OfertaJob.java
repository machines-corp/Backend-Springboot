package com.mchscorp.integrajob.datapi.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "OFERTA_JOB")
public class OfertaJob {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idOferta;

    @ManyToOne
    @JoinColumn(name = "id_empresa", nullable = false)
    private Empresa empresa;

    @ManyToOne
    @JoinColumn(name = "id_ubicacion")
    private Ubicacion ubicacion;

    private String fuente;
    private String puesto;
    @Column(length = 10000)
    private String descripcion;
    private String url;
    private String contrato;
    private String expReq;
    private LocalDate fechaPost;
    private LocalDate validThrough;
    private String hrsLaborales;
    private LocalDate fechaInicioJob;
    private Integer vacantes;

    @OneToMany(mappedBy = "oferta")
    private List<Salario> salarios;

    @OneToMany(mappedBy = "oferta")
    private List<RequisitoEducacion> requisitos;

    @OneToMany(mappedBy = "oferta")
    private List<CategoriaOcupacional> categorias;

    @OneToMany(mappedBy = "oferta")
    private List<AcomodoAccesibilidad> acomodos;

    @OneToMany(mappedBy = "oferta")
    private List<Beneficio> beneficios;

	public void setIdOferta(Integer id) {
		// TODO Auto-generated method stub
		
	}
}
