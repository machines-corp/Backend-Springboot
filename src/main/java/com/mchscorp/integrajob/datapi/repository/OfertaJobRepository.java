package com.mchscorp.integrajob.datapi.repository;

import com.mchscorp.integrajob.datapi.dto.OfertaJobDTO;
import com.mchscorp.integrajob.datapi.entity.OfertaJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfertaJobRepository extends JpaRepository<OfertaJob, Integer> {

    boolean existsByUrl(String url);

    @Query("""
        SELECT new com.mchscorp.integrajob.datapi.dto.OfertaJobDTO(
            o.idOferta,
            o.puesto,
            o.descripcion,
            o.contrato,
            o.expReq,
            o.fechaPost,
            e.nombreEmp,
            e.direccion
        )
        FROM OfertaJob o
        LEFT JOIN o.empresa e
        """)
    List<OfertaJobDTO> findAllDTO();
}
