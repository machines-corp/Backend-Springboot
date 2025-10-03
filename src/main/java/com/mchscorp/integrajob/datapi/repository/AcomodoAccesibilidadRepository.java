package com.mchscorp.integrajob.datapi.repository;

import com.mchscorp.integrajob.datapi.entity.AcomodoAccesibilidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcomodoAccesibilidadRepository extends JpaRepository<AcomodoAccesibilidad, Integer> {
    // List<AcomodoAccesibilidad> findByTipo(String tipo);
}
