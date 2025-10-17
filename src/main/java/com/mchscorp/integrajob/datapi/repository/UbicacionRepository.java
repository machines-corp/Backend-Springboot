package com.mchscorp.integrajob.datapi.repository;

import com.mchscorp.integrajob.datapi.entity.Ubicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


import java.util.List;

@Repository
public interface UbicacionRepository extends JpaRepository<Ubicacion, Integer> {
    Optional<Ubicacion> findByRegionAndComuna(String region, String comuna);

    // Buscar por región
    List<Ubicacion> findByRegion(String region);

    // Buscar por comuna
    List<Ubicacion> findByComuna(String comuna);
}
