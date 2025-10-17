package com.mchscorp.integrajob.datapi.entity;

import jakarta.persistence.*;
import java.util.List;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "RUBRO")
public class Rubro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRubro;

    private String nombre;

    @OneToMany(mappedBy = "rubro")
    private List<Empresa> empresas;

	public void setIdRubro(Integer id) {
		// TODO Auto-generated method stub
		
	}
}
