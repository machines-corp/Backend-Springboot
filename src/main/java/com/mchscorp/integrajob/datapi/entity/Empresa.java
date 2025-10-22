package com.mchscorp.integrajob.datapi.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "EMPRESA")
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEmpresa;

    private String idXFuente;
    private String nombreEmp;
    @Column(name = "descrip_emp", columnDefinition = "TEXT")
    private String descripEmp;
    private String direccion;
    private String url;

    @ManyToOne
    @JoinColumn(name = "id_rubro")
    private Rubro rubro;

    @OneToMany(mappedBy = "empresa")
    @JsonIgnore
    private List<OfertaJob> ofertas;

	public void setIdEmpresa(Integer id) {
		// TODO Auto-generated method stub
		
	}
}
